package com.example.data.api

import android.util.Log
import com.example.BuildConfig
import com.example.data.models.ChatMessage
import com.example.data.models.Language
import com.example.data.models.MessageSender
import com.example.data.models.PracticeScenario
import com.example.data.models.ProficiencyLevel
import com.example.data.models.SupportedLanguages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class AIResponseResult(
    val reply: String,
    val translation: String,
    val pronunciation: String,
    val grammarFeedback: String,
    val betterAlternative: String?,
    val suggestedReplies: List<String>
)

class GeminiLanguageService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(8, TimeUnit.SECONDS)
        .build()

    private val modelCandidates = listOf(
        "gemini-2.5-flash",
        "gemini-flash-latest",
        "gemini-2.5-flash-lite",
        "gemini-3.1-flash-lite-preview"
    )

    suspend fun sendConversationTurn(
        userMessage: String,
        history: List<ChatMessage>,
        language: Language,
        level: ProficiencyLevel,
        scenario: PracticeScenario,
        nativeLanguage: Language = SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[0]
    ): AIResponseResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isNotBlank() && !key.contains("MY_GEMINI_API_KEY") && key != "null") {
                key
            } else {
                System.getenv("GEMINI_API_KEY") ?: ""
            }
        } catch (e: Throwable) {
            System.getenv("GEMINI_API_KEY") ?: ""
        }

        val hasValidKey = apiKey.isNotBlank() &&
                !apiKey.contains("MY_GEMINI_API_KEY") &&
                apiKey != "null"

        if (hasValidKey) {
            try {
                val result = callGeminiApi(apiKey, userMessage, history, language, level, scenario, nativeLanguage)
                if (result != null) {
                    return@withContext result
                }
            } catch (e: Exception) {
                Log.w("GeminiLanguageService", "Gemini API request failed, falling back to simulated partner: ${e.message}")
            }
        }

        // Resilient fallback with natural conversational responses for language learning
        generateFallbackResponse(userMessage, language, level, scenario, nativeLanguage)
    }

    private fun callGeminiApi(
        apiKey: String,
        userMessage: String,
        history: List<ChatMessage>,
        language: Language,
        level: ProficiencyLevel,
        scenario: PracticeScenario,
        nativeLanguage: Language
    ): AIResponseResult? {
        val systemInstruction = """
            You are Maya, an encouraging, friendly, and culturally authentic AI language learning partner.
            The user is practicing: ${language.name} (${language.nativeName}).
            The user's native language is: ${nativeLanguage.name} (${nativeLanguage.nativeName}).
            Learner Proficiency Level: ${level.label} (${level.levelCode}) - ${level.description}.
            Active Practice Scenario: ${scenario.title} (${scenario.category}).
            Scenario Context: ${scenario.promptContext}
            Scenario Learning Goals: ${scenario.goals.joinToString("; ")}.

            RULES:
            1. Always respond in the target language (${language.name}).
            2. Match your sentence complexity and vocabulary to the user's proficiency level (${level.label}).
            3. Actively listen and directly address what the user said in their latest message. Never ignore their statement or reply with generic greetings if they asked a question or placed an order.
            4. Keep the conversation lively, natural, and ask an open follow-up question to keep the user speaking.
            5. Analyze what the user said in their latest turn. If they made any grammar, conjugation, tense, gender, or vocabulary mistake, provide gentle, educational, encouraging feedback explaining the correction clearly in ${nativeLanguage.name}.
            6. If their sentence was flawless, provide a brief praise or nuance tip in grammarFeedback in ${nativeLanguage.name}.
            7. Provide a natural "betterAlternative" showing how a native speaker would phrase what the user intended to say.
            8. Provide 3 short, realistic "suggestedReplies" in ${language.name} that the user can choose or learn from to continue the dialog.

            You MUST respond with valid JSON ONLY in this format:
            {
              "reply": "Your conversational response in ${language.name}",
              "translation": "${nativeLanguage.name} translation of your reply",
              "pronunciation": "Phonetic pronunciation guide or romanization (e.g. Romaji/Pinyin/easy phonetic syllables)",
              "grammarFeedback": "Gentle correction or praise note about the user's utterance in ${nativeLanguage.name}",
              "betterAlternative": "A native-sounding rephrasing of the user's statement, or null if perfect",
              "suggestedReplies": ["Option 1 in ${language.name}", "Option 2 in ${language.name}", "Option 3 in ${language.name}"]
            }
        """.trimIndent()

        val contentsArray = JSONArray()

        // Prior history turns - Gemini API MANDATE:
        // 1. Must start with role 'user'
        // 2. Roles must strictly alternate: user -> model -> user -> model -> user
        // 3. Current user message must be the final 'user' turn
        val priorMessages = if (history.lastOrNull()?.text?.trim() == userMessage.trim()) {
            history.dropLast(1)
        } else {
            history
        }

        // Collect alternating turns starting from the first user turn in history
        val cleanTurns = mutableListOf<Pair<String, String>>()
        val firstUserIdx = priorMessages.indexOfFirst { it.sender == MessageSender.USER }
        if (firstUserIdx != -1) {
            val relevantHistory = priorMessages.subList(firstUserIdx, priorMessages.size).takeLast(6)
            for (msg in relevantHistory) {
                val role = if (msg.sender == MessageSender.USER) "user" else "model"
                if (cleanTurns.isEmpty()) {
                    if (role == "user") {
                        cleanTurns.add(role to msg.text)
                    }
                } else {
                    val lastRole = cleanTurns.last().first
                    if (lastRole == role) {
                        // Merge consecutive turns with identical role
                        val merged = cleanTurns.last().second + "\n" + msg.text
                        cleanTurns[cleanTurns.lastIndex] = role to merged
                    } else {
                        cleanTurns.add(role to msg.text)
                    }
                }
            }
        }

        // Now append current user message
        if (cleanTurns.isNotEmpty() && cleanTurns.last().first == "user") {
            cleanTurns[cleanTurns.lastIndex] = "user" to (cleanTurns.last().second + "\n" + userMessage)
        } else {
            cleanTurns.add("user" to userMessage)
        }

        for ((role, text) in cleanTurns) {
            val turnObj = JSONObject()
            turnObj.put("role", role)
            val partsArr = JSONArray()
            partsArr.put(JSONObject().put("text", text))
            turnObj.put("parts", partsArr)
            contentsArray.put(turnObj)
        }

        // System Instruction
        val systemInstructionObj = JSONObject()
        val sysParts = JSONArray()
        sysParts.put(JSONObject().put("text", systemInstruction))
        systemInstructionObj.put("parts", sysParts)

        // Generation Config - thinkingBudget 0 ensures instant response without reasoning latency
        val genConfig = JSONObject()
        genConfig.put("temperature", 0.7)
        genConfig.put("maxOutputTokens", 380)
        genConfig.put("responseMimeType", "application/json")
        val thinkingConfig = JSONObject()
        thinkingConfig.put("thinkingBudget", 0)
        genConfig.put("thinkingConfig", thinkingConfig)

        val rootRequest = JSONObject()
        rootRequest.put("contents", contentsArray)
        rootRequest.put("systemInstruction", systemInstructionObj)
        rootRequest.put("generationConfig", genConfig)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootRequest.toString().toRequestBody(mediaType)

        for (candidateModel in modelCandidates) {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$candidateModel:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    val errBody = response.body?.string() ?: ""
                    Log.w("GeminiLanguageService", "API call to $candidateModel returned ${response.code}: $errBody")
                    continue
                }

                val responseBodyStr = response.body?.string() ?: continue
                val responseJson = JSONObject(responseBodyStr)
                val candidates = responseJson.optJSONArray("candidates") ?: continue
                if (candidates.length() == 0) continue

                val candidate0 = candidates.getJSONObject(0)
                val content = candidate0.optJSONObject("content") ?: continue
                val parts = content.optJSONArray("parts") ?: continue
                if (parts.length() == 0) continue

                val rawText = parts.getJSONObject(0).optString("text", "")
                if (rawText.isBlank()) continue

                val parsed = parseStructuredJson(rawText)
                if (parsed != null) {
                    Log.d("GeminiLanguageService", "Successfully received AI response using model: $candidateModel")
                    return parsed
                }
            } catch (e: Exception) {
                Log.w("GeminiLanguageService", "Error calling $candidateModel: ${e.message}")
            }
        }
        return null
    }

    private fun parseStructuredJson(rawText: String): AIResponseResult? {
        try {
            // Strip markdown block if model wrapped in ```json ... ```
            var cleaned = rawText.trim()
            if (cleaned.startsWith("```json")) {
                cleaned = cleaned.removePrefix("```json").trim()
            }
            if (cleaned.startsWith("```")) {
                cleaned = cleaned.removePrefix("```").trim()
            }
            if (cleaned.endsWith("```")) {
                cleaned = cleaned.removeSuffix("```").trim()
            }

            val json = JSONObject(cleaned)
            val reply = json.optString("reply", "")
            if (reply.isBlank()) return null

            val translation = json.optString("translation", "")
            val pronunciation = json.optString("pronunciation", "")
            val grammarFeedback = json.optString("grammarFeedback", "Well done! Keep expressing your thoughts.")
            val betterAlternative = if (json.has("betterAlternative") && !json.isNull("betterAlternative")) {
                json.optString("betterAlternative")
            } else null

            val suggestedReplies = mutableListOf<String>()
            val suggArray = json.optJSONArray("suggestedReplies")
            if (suggArray != null) {
                for (i in 0 until suggArray.length()) {
                    val s = suggArray.optString(i)
                    if (s.isNotBlank()) suggestedReplies.add(s)
                }
            }

            return AIResponseResult(
                reply = reply,
                translation = translation,
                pronunciation = pronunciation,
                grammarFeedback = grammarFeedback,
                betterAlternative = betterAlternative,
                suggestedReplies = suggestedReplies
            )
        } catch (e: Exception) {
            Log.w("GeminiLanguageService", "Error parsing response JSON: ${e.message}")
            return null
        }
    }

    private fun generateFallbackResponse(
        userMessage: String,
        language: Language,
        level: ProficiencyLevel,
        scenario: PracticeScenario,
        nativeLanguage: Language
    ): AIResponseResult {
        val lower = userMessage.lowercase().trim()
        val isNativeGerman = nativeLanguage.code == "de"

        val isGreeting = lower.contains("hallo") || lower.contains("guten") || lower.contains("hi") ||
                lower.contains("hello") || lower.contains("hola") || lower.contains("ciao") ||
                lower.contains("bonjour") || lower.contains("konnichiwa") || lower.contains("nihao") ||
                lower.contains("privet") || lower.contains("ola")

        val isOrdering = lower.contains("kaffee") || lower.contains("coffee") || lower.contains("café") ||
                lower.contains("tee") || lower.contains("tea") || lower.contains("wasser") ||
                lower.contains("water") || lower.contains("bier") || lower.contains("pizza") ||
                lower.contains("croissant") || lower.contains("essen") || lower.contains("menü") ||
                lower.contains("karte") || lower.contains("rechnung") || lower.contains("bestellen") ||
                lower.contains("order")

        val isAskingQuestion = lower.contains("wo") || lower.contains("where") || lower.contains("wie") ||
                lower.contains("how") || lower.contains("was") || lower.contains("what") ||
                lower.contains("wann") || lower.contains("warum") || lower.contains("cost") ||
                lower.contains("kostet") || lower.endsWith("?")

        val isGratitude = lower.contains("danke") || lower.contains("thanks") || lower.contains("thank") ||
                lower.contains("gracias") || lower.contains("merci") || lower.contains("grazie") ||
                lower.contains("arigat") || lower.contains("spasibo") || lower.contains("obrigad")

        return when (language.code) {
            "zh" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "好的，没问题！我已经为您记下了。请问您还需要加糖或者牛奶吗？",
                        translation = if (isNativeGerman) "Alles klar, kein Problem! Ich habe Ihre Bestellung notiert. Möchten Sie noch Zucker oder Milch dazu?" else "All right, no problem! I have noted your order. Would you like sugar or milk with that?",
                        pronunciation = "Hǎo de, méi wèntí! Wǒ yǐjīng wèi nín jì xià le. Qǐngwèn nín hái xūyào jiā táng huòzhě niúnǎi ma?",
                        grammarFeedback = if (isNativeGerman) "Toller Satz! Im Chinesischen wird '请问' (qǐngwèn) verwendet, um höflich eine Frage einzuleiten." else "Great sentence! In Chinese, 'qǐngwèn' is used to politely introduce questions.",
                        betterAlternative = "我想点一杯咖啡，请少放点糖。",
                        suggestedReplies = listOf("不用了，谢谢！", "请加一点牛奶。", "请问大概需要等多久？")
                    )
                    isAskingQuestion -> AIResponseResult(
                        reply = "这个问题问得很好！就在前面右转，步行大约五分钟就到了。需要我为您在地图上指出来吗？",
                        translation = if (isNativeGerman) "Gute Frage! Direkt vorne rechts abbiegen, etwa fünf Minuten zu Fuß. Soll ich es Ihnen auf der Karte zeigen?" else "Good question! Turn right just ahead, about five minutes on foot. Shall I show you on the map?",
                        pronunciation = "Zhège wèntí wèn de hěn hǎo! Jiù zài qiánmiàn yòuzhuǎn, bùxíng dàyuē wǔ fēnzhōng jiù dào le.",
                        grammarFeedback = if (isNativeGerman) "Sehr gut! '在前面' (zài qiánmiàn) bedeutet 'vorne' oder 'geradeaus'." else "Very good! 'zài qiánmiàn' means 'up ahead'.",
                        betterAlternative = "请问洗手间在哪里？",
                        suggestedReplies = listOf("太感谢您了！", "好的，我明白了。", "那离这里远吗？")
                    )
                    isGratitude -> AIResponseResult(
                        reply = "不客气！能帮到您我很高兴。接下来您还有什么需要帮忙的吗？",
                        translation = if (isNativeGerman) "Gern geschehen! Ich freue mich, Ihnen geholfen zu haben. Kann ich Ihnen noch bei etwas behilflich sein?" else "You are welcome! I'm glad I could help. Is there anything else you need assistance with?",
                        pronunciation = "Bù kèqì! Néng bāng dào nín wǒ hěn gāoxìng. Jiē xiàlái nín hái yǒu shénme xūyào bāngmáng de ma?",
                        grammarFeedback = if (isNativeGerman) "'不客气' (bù kèqì) ist die gebräuchlichste Erwiderung auf '谢谢' (Danke)." else "'bù kèqì' is the most natural reply to 'xièxiè' (Thank you).",
                        betterAlternative = "非常感谢你的帮助！",
                        suggestedReplies = listOf("暂时没有了，祝您生活愉快！", "我想再练习一下中文。", "今天天气真好！")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "你好！很高兴能和你一起练习中文。今天过得怎么样？",
                        translation = if (isNativeGerman) "Hallo! Es freut mich sehr, mit dir Chinesisch zu üben. Wie läuft dein Tag so?" else "Hello! Glad to practice Chinese with you. How is your day going?",
                        pronunciation = "Nǐ hǎo! Hěn gāoxìng néng hé nǐ yīqǐ liànxí zhōngwén. Jīntiān guò de zěnmeyàng?",
                        grammarFeedback = if (isNativeGerman) "Super Start! '你好' ist die freundliche Standardbegrüßung im Alltag." else "Great start! 'Nǐ hǎo' is the standard friendly greeting in daily life.",
                        betterAlternative = "你好！今天我很开心，想跟你聊聊天。",
                        suggestedReplies = listOf("我今天过得很充实！", "今天有点忙，但很开心。", "你今天过得怎么样？")
                    )
                    else -> AIResponseResult(
                        reply = "说得真好！你的中文表达越来越自然了。能多跟我分享一些你的想法吗？",
                        translation = if (isNativeGerman) "Schön gesagt! Dein Chinesisch klingt immer natürlicher. Kannst du mir mehr darüber erzählen?" else "Well said! Your Chinese is sounding more natural. Can you share more about your thoughts?",
                        pronunciation = "Shuō de zhēn hǎo! Nǐ de zhōngwén biǎodá yuè lái yuè zìrán le.",
                        grammarFeedback = if (isNativeGerman) "Ausgezeichnet! Achte bei der Aussprache besonders auf die vier Töne." else "Excellent! Pay attention to the four tones during pronunciation.",
                        betterAlternative = "我想多练习一些生活中的日常对话。",
                        suggestedReplies = listOf("我想聊聊旅行和美食。", "你有什么推荐的好电影吗？", "学习中文真有趣！")
                    )
                }
            }

            "ja" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "かしこまりました！ご注文を承りました。お持ち帰りですか、それとも店内でお召し上がりですか？",
                        translation = if (isNativeGerman) "Sehr gerne! Ihre Bestellung ist aufgenommen. Möchten Sie sie mitnehmen oder hier verzehren?" else "Understood! I've taken your order. Is it for take-out or will you dine in?",
                        pronunciation = "Kashikomarimashita! Go-chūmon o uketamawarimashita. O-mochikaeri desu ka, soretomo tennai de o-meshitsagari desu ka?",
                        grammarFeedback = if (isNativeGerman) "'かしこまりました' ist die vorbildliche höfliche Serviceformel in Japan." else "'Kashikomarimashita' is the quintessential polite Japanese service phrase.",
                        betterAlternative = "ホットコーヒーを一つ、店内でお願いします。",
                        suggestedReplies = listOf("店内でお願いします。", "持ち帰りでお願いします。", "あと、お水もいただけますか？")
                    )
                    isAskingQuestion -> AIResponseResult(
                        reply = "はい、ご案内いたします！あちらの角を右に曲がって、すぐの場所にございますよ。",
                        translation = if (isNativeGerman) "Ja, sehr gerne zeige ich Ihnen den Weg! Biegen Sie dort an der Ecke rechts ab, es liegt direkt dahinter." else "Yes, let me show you the way! Turn right around that corner, it's right there.",
                        pronunciation = "Hai, go-annai itashimasu! Achira no kado o migi ni magatte, sugu no basho ni gozaimasu yo.",
                        grammarFeedback = if (isNativeGerman) "Schöne Frage! Im Japanischen drückt '〜はどこですか' (wa doko desu ka) 'wo ist...' aus." else "Great question! '... wa doko desu ka' is the standard way to ask 'where is...'.",
                        betterAlternative = "すみません、駅はどちらの方向にありますか？",
                        suggestedReplies = listOf("わかりました、ありがとうございます！", "歩いてどのくらいかかりますか？", "助かりました！")
                    )
                    isGratitude -> AIResponseResult(
                        reply = "どういたしまして！お役に立てて嬉しいです。他に気になることはありますか？",
                        translation = if (isNativeGerman) "Gern geschehen! Ich freue mich, geholfen zu haben. Gibt es sonst noch etwas?" else "You're welcome! Glad to help. Is there anything else you'd like to ask?",
                        pronunciation = "Dōitashimashite! O-yaku ni tatete ureshii desu. Hoka ni ki ni naru koto wa arimasu ka?",
                        grammarFeedback = if (isNativeGerman) "'どういたしまして' (Dōitashimashite) ist die klassische Antwort auf 'ありがとう'." else "'Dōitashimashite' is the standard reply to 'Arigatō'.",
                        betterAlternative = "親切に教えていただき、ありがとうございます。",
                        suggestedReplies = listOf("大丈夫です、ありがとう！", "おすすめの観光地はありますか？", "また質問してもいいですか？")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "こんにちは！お会いできて嬉しいです。今日はどんな日本語を練習したいですか？",
                        translation = if (isNativeGerman) "Guten Tag! Schön, dich zu treffen. Welches Thema möchtest du heute auf Japanisch üben?" else "Hello! Nice to meet you. What kind of Japanese would you like to practice today?",
                        pronunciation = "Konnichiwa! O-ai dekite ureshii desu. Kyō wa donna Nihongo o renshū shitai desu ka?",
                        grammarFeedback = if (isNativeGerman) "Perfekt! 'こんにちは' wird tagsüber als Begrüßung für jeden Anlass genutzt." else "Perfect! 'Konnichiwa' is universally used as a daytime greeting.",
                        betterAlternative = "こんにちは！日常会話の練習がしたいです。",
                        suggestedReplies = listOf("レストランでの会話を練習したいです。", "自己紹介をしてみてもいいですか？", "日本の文化について話しましょう。")
                    )
                    else -> AIResponseResult(
                        reply = "なるほど、面白いですね！日本語の発音もとても綺麗ですよ。続きを聞かせていただけますか？",
                        translation = if (isNativeGerman) "Verstehe, sehr interessant! Deine japanische Aussprache ist auch sehr schön. Erzählst du mir mehr?" else "I see, very interesting! Your Japanese pronunciation is very clean. Could you tell me more?",
                        pronunciation = "Naruhodo, omoshiroi desu ne! Nihongo no hatsuon mo totemo kirei desu yo.",
                        grammarFeedback = if (isNativeGerman) "Sehr natürlich! Achte bei Partikeln wie 'は' (wa) und 'が' (ga) auf den Nuancenunterschied." else "Very natural! Watch the subtle difference between topic particle 'wa' and subject 'ga'.",
                        betterAlternative = "もっとたくさん日本語で話せるようになりたいです。",
                        suggestedReplies = listOf("最近覚えた単語を使ってみます！", "休みの日は何をしていますか？", "日本語の勉強がとても楽しいです。")
                    )
                }
            }

            "ko" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "네, 주문 도와드리겠습니다! 따뜻한 것으로 준비해 드릴까요, 아니면 아이스로 드릴까요?",
                        translation = if (isNativeGerman) "Ja, sehr gerne nehme ich Ihre Bestellung auf! Darf es heiß oder mit Eis sein?" else "Yes, I will take your order! Would you like it hot or iced?",
                        pronunciation = "Ne, jumun dowadeurigessseumnida! Ttatteutan geoseuro junbihae deurilkkayo, animyeon aiseuro deurilkkayo?",
                        grammarFeedback = if (isNativeGerman) "Ausgezeichnet! In Korea fragt man im Café fast immer '따뜻한 것' (heiß) oder '아이스' (Eis)." else "Great! In Korean cafes, baristas typically ask if you want hot or iced.",
                        betterAlternative = "아이스 아메리카노 한 잔 부탁드립니다.",
                        suggestedReplies = listOf("아이스로 부탁드려요.", "따뜻한 걸로 주세요.", "포장해 갈 수 있나요?")
                    )
                    isAskingQuestion -> AIResponseResult(
                        reply = "네! 여기서 직진하시다가 첫 번째 사거리에서 왼쪽으로 가시면 바로 보여요.",
                        translation = if (isNativeGerman) "Ja! Gehen Sie hier geradeaus und biegen Sie an der ersten Kreuzung links ab, dann sehen Sie es direkt." else "Yes! Go straight from here and turn left at the first intersection, you will see it right away.",
                        pronunciation = "Ne! Yeogiseo jikjinhashidaga cheot beonjjae sageorieseo oenjjogeuro gasimyeon baro boyeoyo.",
                        grammarFeedback = if (isNativeGerman) "Klasse! '직진' (Geradeaus) und '왼쪽' (Links) sind essenzielle Richtungsangaben." else "Great! 'Jikjin' (straight) and 'oenjjok' (left) are essential directional words.",
                        betterAlternative = "실례지만 지하철역이 어디에 있나요?",
                        suggestedReplies = listOf("친절하게 알려주셔서 감사합니다!", "걸어서 얼마나 걸리나요?", "좋은 하루 보내세요!")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "안녕하세요! 반갑습니다. 오늘 어떤 이야기를 나누고 싶으신가요?",
                        translation = if (isNativeGerman) "Guten Tag! Schön, dich kennenzulernen. Worüber möchtest du heute sprechen?" else "Hello! Nice to meet you. What would you like to talk about today?",
                        pronunciation = "Annyeonghaseyo! Bangapseumnida. Oneul eotteon iyagireul nanugo sipeusingayo?",
                        grammarFeedback = if (isNativeGerman) "'안녕하세요' ist die höfliche Standardbegrüßung auf Koreanisch." else "'Annyeonghaseyo' is the polite standard Korean greeting.",
                        betterAlternative = "안녕하세요! 한국어 대화 연습을 하러 왔어요.",
                        suggestedReplies = listOf("한국 여행에 대해 이야기하고 싶어요.", "오늘 날씨가 참 좋네요!", "취미가 무엇인가요?")
                    )
                    else -> AIResponseResult(
                        reply = "정말 잘하셨어요! 한국어 문장 구성이 자연스럽네요. 다음에는 어떤 표현을 연습해 볼까요?",
                        translation = if (isNativeGerman) "Wirklich gut gemacht! Dein Satzbau auf Koreanisch ist sehr natürlich. Welchen Ausdruck möchtest du als Nächstes üben?" else "Well done! Your Korean sentence structure is natural. Which phrase should we practice next?",
                        pronunciation = "Jeongmal jalhasyeosseoyo! Hangugeo munjang guseongi jayeonseureopneyo.",
                        grammarFeedback = if (isNativeGerman) "Sehr gut! Achte auf die Höflichkeitsendungen wie '-요' und '-습니다'." else "Very good! Pay attention to polite verb endings like '-yo' and '-seumnida'.",
                        betterAlternative = "한국어를 매일 조금씩 연습하고 있어요.",
                        suggestedReplies = listOf("한국 드라마를 보면서 공부해요.", "한국 음식 중에 비빔밥을 제일 좋아해요.", "더 많은 단어를 배우고 싶어요.")
                    )
                }
            }

            "it" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "Certamente! Un'ottima scelta. Desideri anche un cornetto o qualcosa da mangiare insieme?",
                        translation = if (isNativeGerman) "Natürlich! Sehr gute Wahl. Möchtest du auch ein Croissant oder etwas zu essen dazu?" else "Certainly! Excellent choice. Would you also like a croissant or something to eat with that?",
                        pronunciation = "Cher-tah-men-teh! Oon OT-tee-mah SHEL-tah. Deh-ZEE-deh-ree AHN-kay oon kor-NET-toh?",
                        grammarFeedback = if (isNativeGerman) "Perfekt formuliert! 'Vorrei...' (Ich möchte...) ist die höflichste Art, in Italien zu bestellen." else "Perfect! 'Vorrei...' (I would like...) is the polite standard for ordering in Italy.",
                        betterAlternative = "Vorrei un cappuccino e un bicchiere d'acqua, per favore.",
                        suggestedReplies = listOf("Sì, un cornetto alla crema, grazie!", "Solo il caffè, grazie.", "Quant'è in totale?")
                    )
                    isAskingQuestion -> AIResponseResult(
                        reply = "Volentieri! È molto facile: prosegui dritto per duecento metri, poi gira a sinistra.",
                        translation = if (isNativeGerman) "Sehr gerne! Es ist ganz einfach: Geh zweihundert Meter geradeaus, dann biege links ab." else "With pleasure! It's very easy: go straight for two hundred meters, then turn left.",
                        pronunciation = "Voh-len-TYEH-ree! EH MOL-toh FAH-chee-leh: proh-SEH-gwee DREET-toh...",
                        grammarFeedback = if (isNativeGerman) "'Dritto' heißt geradeaus und 'a sinistra' nach links." else "'Dritto' means straight ahead and 'a sinistra' to the left.",
                        betterAlternative = "Scusi, mi sa dire dov'è la stazione?",
                        suggestedReplies = listOf("Grazie mille, sei gentilissimo!", "È lontano a piedi?", "Buona giornata!")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "Ciao! Che piacere fare una chiacchierata con te. Come vanno le cose oggi?",
                        translation = if (isNativeGerman) "Hallo! Was für ein Vergnügen, mit dir zu plaudern. Wie laufen die Dinge heute?" else "Hello! What a pleasure to chat with you. How are things going today?",
                        pronunciation = "CHOW! Kay pyah-CHEH-reh FAH-reh OO-nah kyahk-kyeh-RAH-tah kohn teh...",
                        grammarFeedback = if (isNativeGerman) "Ausgezeichnet! 'Ciao' ist dynamisch und sympathisch für lockere Gespräche." else "Excellent! 'Ciao' is universally warm for casual chats.",
                        betterAlternative = "Ciao! Tutto bene, grazie. E a te come va?",
                        suggestedReplies = listOf("Tutto bene, grazie!", "Oggi ho una giornata tranquilla.", "Ho voglia di fare un po' di conversazione.")
                    )
                    else -> AIResponseResult(
                        reply = "Bravissimo! Il tuo italiano suona davvero fluido e naturale. Raccontami qualcosa in più!",
                        translation = if (isNativeGerman) "Sehr gut! Dein Italienisch klingt wirklich flüssig und natürlich. Erzähl mir mehr darüber!" else "Very well done! Your Italian sounds really fluid and natural. Tell me more!",
                        pronunciation = "Brah-VEES-see-moh! Eel TOO-oh ee-tahl-YAH-noh SWOH-nah dahv-VEH-roh FLOO-ee-doh...",
                        grammarFeedback = if (isNativeGerman) "Sehr gut! Im Italienischen lässt man Personalpronomen wie 'io' oder 'tu' meist weg." else "Great! In Italian, subject pronouns like 'io' or 'tu' are usually omitted.",
                        betterAlternative = "Mi piacerebbe visitare Roma e Firenze presto.",
                        suggestedReplies = listOf("Vorrei viaggiare in Italia quest'anno.", "Mi piace molto la cucina italiana.", "Possiamo fare un po' di vocabolario?")
                    )
                }
            }

            "ru" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "Отлично, заказ принят! Желаете что-нибудь к кофе, например, свежую выпечку или десерт?",
                        translation = if (isNativeGerman) "Ausgezeichnet, die Bestellung ist aufgenommen! Möchten Sie etwas zum Kaffee, z. B. frisches Gebäck oder ein Dessert?" else "Excellent, order taken! Would you like anything with your coffee, like fresh pastry or dessert?",
                        pronunciation = "Ot-LEECH-no, za-KAZ pree-NYAT! Zhe-LA-ye-te chto-nee-bood' k KO-fe?",
                        grammarFeedback = if (isNativeGerman) "Sehr gut! 'Пожалуйста' bedeutet sowohl 'bitte' als auch 'gern geschehen'." else "Very good! 'Pozhaluysta' means both 'please' and 'you're welcome'.",
                        betterAlternative = "Я бы хотел заказать кофе и круассан, пожалуйста.",
                        suggestedReplies = listOf("Да, кусочек чизкейка, пожалуйста.", "Нет, спасибо, только кофе.", "Сколько с меня?")
                    )
                    isAskingQuestion -> AIResponseResult(
                        reply = "Конечно, с удовольствием подскажу! Идите прямо по этой улице, и через пять минут вы будете на месте.",
                        translation = if (isNativeGerman) "Natürlich, sehr gerne zeige ich Ihnen den Weg! Gehen Sie diese Straße geradeaus, in fünf Minuten sind Sie da." else "Of course! Walk straight along this street, and in five minutes you'll be there.",
                        pronunciation = "Ko-NECH-no, s oo-do-VOL'ST-vee-yem pod-ska-ZHU! Ee-DEE-tye PRYA-mo...",
                        grammarFeedback = if (isNativeGerman) "'Идите прямо' bedeutet 'gehen Sie geradeaus'." else "'Idite pryamo' means 'walk straight ahead'.",
                        betterAlternative = "Скажите, пожалуйста, где находится вокзал?",
                        suggestedReplies = listOf("Большое спасибо за помощь!", "Это далеко отсюда?", "Хорошего дня!")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "Привет! Очень рад общению с тобой. Как проходит твой день?",
                        translation = if (isNativeGerman) "Hallo! Ich freue mich sehr, mich mit dir zu unterhalten. Wie läuft dein Tag?" else "Hello! So glad to chat with you. How is your day going?",
                        pronunciation = "Pree-VYET! O-chen' RAD ob-SHCHE-nee-yu s to-BOY. Kak pro-KHO-deet tvoy DYEN'?",
                        grammarFeedback = if (isNativeGerman) "'Привет' ist die herzliche Begrüßung unter Freunden und Bekannten." else "'Privet' is the warm, casual greeting among acquaintances.",
                        betterAlternative = "Привет! У меня всё отлично, а у тебя?",
                        suggestedReplies = listOf("Всё отлично, спасибо!", "Сегодня много дел, но я нахожу время для практики.", "Давай поговорим о путешествиях!")
                    )
                    else -> AIResponseResult(
                        reply = "Замечательно! Ты строишь предложения очень уверенно. О чём ещё хочешь поговорить?",
                        translation = if (isNativeGerman) "Wunderbar! Du bildest Sätze sehr sicher. Worüber möchtest du noch sprechen?" else "Wonderful! You construct sentences very confidently. What else would you like to talk about?",
                        pronunciation = "Za-me-CHA-tel'no! Ty STRO-eesh pred-lo-ZHE-nee-ya O-chen' oo-VE-ren-no.",
                        grammarFeedback = if (isNativeGerman) "Super Fortschritt! Achte auf die korrekten Endungen im Akkusativ und Dativ." else "Great progress! Keep an eye on case endings for nouns.",
                        betterAlternative = "Я хочу свободно говорить по-русски.",
                        suggestedReplies = listOf("Расскажи интересную историю.", "Мне нравится изучать языки.", "Какие книги ты посоветуешь почитать?")
                    )
                }
            }

            "pt" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "Com certeza! Ótimo pedido. Você gostaria de adicionar açúcar, adoçante ou leite?",
                        translation = if (isNativeGerman) "Ganz bestimmt! Tolle Bestellung. Möchtest du Zucker, Süßstoff oder Milch dazu?" else "Certainly! Great order. Would you like to add sugar, sweetener, or milk?",
                        pronunciation = "Kohm sehr-TEH-zah! OH-tee-moh peh-DEE-doo...",
                        grammarFeedback = if (isNativeGerman) "'Gostaria de...' (Ich hätte gerne...) ist die beste höfliche Formulierung." else "'Gostaria de...' is the polite Portuguese form for ordering.",
                        betterAlternative = "Eu gostaria de um café e um pão de queijo, por favor.",
                        suggestedReplies = listOf("Apenas com leite, por favor.", "Sem açúcar, obrigado!", "Quanto custa tudo?")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "Olá! Que prazer conversar com você. Como estão as coisas hoje?",
                        translation = if (isNativeGerman) "Hallo! Was für eine Freude, mit dir zu sprechen. Wie laufen die Dinge heute?" else "Hello! What a pleasure to chat with you. How are things today?",
                        pronunciation = "oh-LAH! Kay prah-ZEHR kohn-vehr-SAHR kohn voh-SEH...",
                        grammarFeedback = if (isNativeGerman) "'Olá' oder 'Oi' sind universell freundliche Begrüßungen im Portugiesischen." else "'Olá' or 'Oi' are universally friendly Portuguese greetings.",
                        betterAlternative = "Olá! Está tudo ótimo por aqui, e com você?",
                        suggestedReplies = listOf("Tudo ótimo por aqui!", "Estou animado para praticar português hoje.", "O que vamos aprender agora?")
                    )
                    else -> AIResponseResult(
                        reply = "Muito bom! O seu português está cada vez mais natural e fluido. O que mais você gostaria de praticar?",
                        translation = if (isNativeGerman) "Sehr gut! Dein Portugiesisch wird immer natürlicher und flüssiger. Was möchtest du noch üben?" else "Very good! Your Portuguese is getting more natural and fluent. What else would you like to practice?",
                        pronunciation = "MOO-ee-toh BOHM! Oo seh-oo poor-too-GAYZ ess-TAH KAH-dah vez myce nah-too-RAHL...",
                        grammarFeedback = if (isNativeGerman) "Sehr gut! Achte auf die Nasallaute wie 'ão' und 'õe'." else "Very good! Pay attention to nasal vowels like 'ão' and 'õe'.",
                        betterAlternative = "Quero aprender mais gírias e expressões do dia a dia.",
                        suggestedReplies = listOf("Gostaria de falar sobre o Brasil e Portugal.", "Como se diz isso de forma informal?", "Vamos continuar!")
                    )
                }
            }

            "es" -> {
                when {
                    isOrdering -> AIResponseResult(
                        reply = "¡Por supuesto! Marchando tu pedido. ¿Te gustaría añadir algo de comer, como una tostada o un croissant?",
                        translation = if (isNativeGerman) "Natürlich! Deine Bestellung kommt sofort. Möchtest du etwas zu essen dazu, etwa einen Toast oder ein Croissant?" else "Of course! Coming right up. Would you like to add something to eat, like toast or a croissant?",
                        pronunciation = "por soo-PWEHS-toh! mar-CHAN-doh too peh-DEE-doh...",
                        grammarFeedback = if (isNativeGerman) "Ausgezeichnet! 'Por favor' steht am Ende von Bitten immer gut." else "Excellent! Adding 'por favor' makes any order polite and natural.",
                        betterAlternative = "Me gustaría pedir un café con leche y un croissant, por favor.",
                        suggestedReplies = listOf("Solo el café, muchas gracias.", "Sí, una tostada con tomate, por favor.", "¿Cuánto es la cuenta?")
                    )
                    isGreeting -> AIResponseResult(
                        reply = "¡Hola! Qué gusto saludarte. ¿Qué planes interesantes tienes para el día de hoy?",
                        translation = if (isNativeGerman) "Hallo! Schön, dich zu grüßen. Welche interessanten Pläne hast du für heute?" else "Hello! Nice to greet you. What interesting plans do you have for today?",
                        pronunciation = "OH-lah! kay GOO-stoh sah-loo-DAR-teh. kay PLAH-ness tee-EH-ness?",
                        grammarFeedback = if (isNativeGerman) "Toller Start! '¡Hola! ¿Cómo estás?' ist der Klassiker im Spanischen." else "Great start! '¡Hola! ¿Cómo estás?' is the classic Spanish opening.",
                        betterAlternative = "¡Hola! Hoy tengo un día tranquilo y quiero practicar español.",
                        suggestedReplies = listOf("Hoy voy a salir con amigos.", "Quiero practicar mi vocabulario en español.", "¿Cómo estás tú hoy?")
                    )
                    else -> AIResponseResult(
                        reply = "¡Excelente! Me encanta cómo estás usando el español. ¿Puedes decirme más sobre eso?",
                        translation = if (isNativeGerman) "Ausgezeichnet! Es gefällt mir, wie du Spanisch sprichst. Kannst du mir mehr darüber erzählen?" else "Excellent! I love how you are speaking Spanish. Can you tell me more about that?",
                        pronunciation = "ek-seh-LEN-teh! may en-KAHN-tah KOH-moh ess-TAHS oo-SAHN-doh ell ess-pah-NYOL...",
                        grammarFeedback = if (isNativeGerman) "Achte auf die Übereinstimmung von Geschlecht (männlich/weiblich) und Einzahl/Mehrzahl." else "Keep track of gender agreement between nouns and adjectives.",
                        betterAlternative = "Me parece muy interesante aprender este idioma.",
                        suggestedReplies = listOf("Me gustaría aprender más expresiones cotidianas.", "Cuéntame más sobre la cultura hispana.", "¿Qué me recomiendas visitar en España?")
                    )
                }
            }

            "fr" -> {
                AIResponseResult(
                    reply = "C'est formidable ! Votre français devient de plus en plus fluide. Que souhaitez-vous faire ensuite ?",
                    translation = if (isNativeGerman) "Das ist großartig! Ihr Französisch wird immer flüssiger. Was möchten Sie als Nächstes tun?" else "That's wonderful! Your French is becoming increasingly fluent. What would you like to do next?",
                    pronunciation = "say for-mee-DAHBL! vohtr frahn-SAY duh-vyahn duh plooz ahn ploo floo-EED...",
                    grammarFeedback = if (isNativeGerman) "Sehr gut! Achte auf die Bindung (Liaison) zwischen Wörtern." else "Very good! Pay attention to liaison between words.",
                    betterAlternative = "J'aimerais commander un café et un croissant, s'il vous plaît.",
                    suggestedReplies = listOf("Je voudrais un café s'il vous plaît.", "Parlez-moi de votre ville préférée.", "Comment dit-on cela en français familier ?")
                )
            }

            "de" -> {
                AIResponseResult(
                    reply = "Das klingt wirklich klasse! Du machst spürbare Fortschritte. Worüber möchtest du heute sprechen?",
                    translation = if (isNativeGerman) "Das klingt wirklich klasse! Du machst spürbare Fortschritte. Worüber möchtest du heute sprechen?" else "That sounds really great! You are making noticeable progress. What would you like to talk about today?",
                    pronunciation = "dahs klinkt VIRK-likh KLAS-seh! doo mahkst SHPOOR-bah-reh fort-SHRIT-teh...",
                    grammarFeedback = "Super! Achte im Deutschen auf die richtige Endung der Artikel (der, die, das, den, dem).",
                    betterAlternative = "Ich möchte gerne mein Deutsch für den Alltag verbessern.",
                    suggestedReplies = listOf("Ich möchte eine Kaffeebestellung üben.", "Was gibt es heute Neues bei dir?", "Erzähl mir etwas über Deutschland!")
                )
            }

            else -> {
                AIResponseResult(
                    reply = "That is great! I really enjoy chatting with you and helping you practice. What would you like to explore next?",
                    translation = if (isNativeGerman) "Das ist großartig! Ich unterhalte mich sehr gerne mit dir. Was möchtest du als Nächstes erkunden?" else "That is great! I really enjoy chatting with you. What would you like to explore next?",
                    pronunciation = "that iz grayt! eye REE-lee en-JOY chat-ing with yoo.",
                    grammarFeedback = "Great phrasing! Keep focusing on natural conversational flow and varied vocabulary.",
                    betterAlternative = "I would love to practice more conversational topics.",
                    suggestedReplies = listOf(
                        "Could you recommend something interesting to visit?",
                        "I have been practicing languages every day this week.",
                        "Tell me a fun cultural tradition from your hometown!"
                    )
                )
            }
        }
    }

    suspend fun getWordDeepDive(
        word: String,
        translation: String,
        language: Language,
        nativeLanguage: Language = SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[1]
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isNotBlank() && !key.contains("MY_GEMINI_API_KEY") && key != "null") {
                key
            } else {
                System.getenv("GEMINI_API_KEY") ?: ""
            }
        } catch (e: Throwable) {
            System.getenv("GEMINI_API_KEY") ?: ""
        }

        val hasValidKey = apiKey.isNotBlank() &&
                !apiKey.contains("MY_GEMINI_API_KEY") &&
                apiKey != "null"

        if (hasValidKey) {
            val prompt = """
                Provide a concise, engaging, and memorable breakdown for learning the ${language.name} word "$word" ($translation).
                The learner's native mother tongue is ${nativeLanguage.name}. Explain primarily in ${nativeLanguage.name} so that every explanation, mnemonic, and context is crystal clear.
                Include:
                1. 💡 Mnemonic hook (an unforgettable way to remember this word).
                2. 🗣️ Pronunciation tip & phonetic cue.
                3. 🌟 2 realistic short conversational sentences with translations in ${nativeLanguage.name}.
                4. 🔍 Nuance or cultural context (when native speakers use it vs avoid it).
                Keep the response formatted cleanly with emojis and bullet points, under 180 words.
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
            }

            for (candidateModel in modelCandidates) {
                try {
                    val url = "https://generativelanguage.googleapis.com/v1beta/models/$candidateModel:generateContent?key=$apiKey"
                    val request = Request.Builder()
                        .url(url)
                        .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                        .build()

                    val response = client.newCall(request).execute()
                    val responseString = response.body?.string()
                    if (response.isSuccessful && !responseString.isNullOrBlank()) {
                        val root = JSONObject(responseString)
                        val candidates = root.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val content = candidates.getJSONObject(0).optJSONObject("content")
                            val parts = content?.optJSONArray("parts")
                            val text = parts?.optJSONObject(0)?.optString("text")
                            if (!text.isNullOrBlank()) {
                                return@withContext text.trim()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w("GeminiLanguageService", "Error calling getWordDeepDive on $candidateModel: ${e.message}")
                }
            }
        }

        // Fallback offline breakdown localized by native language
        when (nativeLanguage.code) {
            "de" -> """
            💡 Eselsbrücke: Verbinde „$word“ gedanklich direkt mit der Bedeutung „$translation“.
            🗣️ Aussprache: Achte auf die korrekte Silbenbetonung und Artikulation im ${language.name}.
            🌟 Beispiel 1: „$word“ lässt sich hervorragend in kurzen Alltagssätzen üben.
            🌟 Beispiel 2: Achte darauf, wie Muttersprachler dieses Wort im Kontext verwenden!
            """.trimIndent()
            "es" -> """
            💡 Mnemotecnia: Asocia mentalmente "$word" directamente con el significado "$translation".
            🗣️ Pronunciación: Presta atención a la acentuación y articulación en ${language.name}.
            🌟 Ejemplo 1: Practica "$word" en oraciones cotidianas breves todos los días.
            🌟 Ejemplo 2: ¡Fíjate en cómo los hablantes nativos usan esta palabra en contexto!
            """.trimIndent()
            "fr" -> """
            💡 Moyen mnémotechnique : Associez « $word » directement au sens « $translation ».
            🗣️ Prononciation : Faites attention à l'accentuation des syllabes en ${language.name}.
            🌟 Exemple 1 : Entraînez-vous avec « $word » dans de courtes phrases du quotidien.
            🌟 Exemple 2 : Observez comment les locuteurs natifs emploient ce mot en contexte !
            """.trimIndent()
            "it" -> """
            💡 Suggerimento mnemonico: Collega mentalmente «$word» al significato «$translation».
            🗣️ Pronuncia: Fai attenzione all'accento delle sillabe e all'articolazione in ${language.name}.
            🌟 Esempio 1: Esercitati con «$word» in brevi frasi quotidiane ogni giorno.
            🌟 Esempio 2: Nota come i madrelingua usano questa parola nel contesto reale!
            """.trimIndent()
            "pt" -> """
            💡 Dica mnemônica: Conecte mentalmente "$word" ao significado "$translation".
            🗣️ Pronúncia: Preste atenção à ênfase nas sílabas e na articulação em ${language.name}.
            🌟 Exemplo 1: Pratique "$word" em frases curtas do dia a dia.
            🌟 Exemplo 2: Observe como os falantes nativos usam essa palavra no contexto!
            """.trimIndent()
            "zh" -> """
            💡 联想记忆法：将“$word”在脑海中与“$translation”直接联系起来。
            🗣️ 发音指导：注意在${language.name}中的音节重音和口型发音。
            🌟 例句 1：在日常简短对话中多加练习“$word”。
            🌟 例句 2：留意母语者在实际语境中如何自然运用该词汇！
            """.trimIndent()
            "ja" -> """
            💡 記憶のコツ：「$word」をその意味である「$translation」とイメージで直結させましょう。
            🗣️ 発音ポイント：${language.name}特有の音節の強弱やイントネーションに注意しましょう。
            🌟 例文 1：「$word」を毎日の短い日常会話で積極的に使ってみましょう。
            🌟 例文 2：ネイティブスピーカーが会話の中でどのように使っているか注目してみましょう！
            """.trimIndent()
            "ko" -> """
            💡 기억법 팁: "$word"를 그 의미인 "$translation"와(과) 생생한 이미지로 연결해 보세요.
            🗣️ 발음 안내: ${language.name}의 정확한 음절 강세와 발음에 주의하세요.
            🌟 예문 1: 매일 짧은 일상 문장으로 "$word"를 연습해 보세요.
            🌟 예문 2: 원어민이 실제 맥락에서 이 단어를 어떻게 활용하는지 관찰해 보세요!
            """.trimIndent()
            "uk" -> """
            💡 Мнемоніка: Створіть яскравий образ, що поєднує «$word» зі значенням «$translation».
            🗣️ Вимова: Зверніть увагу на наголос та інтонацію у ${language.name}.
            🌟 Приклад 1: Використовуйте «$word» у простих розмовних фразах щодня.
            🌟 Приклад 2: Звертайте увагу на контекст вживання носіями мови!
            """.trimIndent()
            "ru" -> """
            💡 Мнемоника: Создайте яркую ассоциацию слова «$word» со значением «$translation».
            🗣️ Произношение: Следите за ударением и четкостью звуков в ${language.name}.
            🌟 Пример 1: Практикуйте слово «$word» в коротких фразах каждый день.
            🌟 Пример 2: Обратите внимание на живые контексты использования носителем!
            """.trimIndent()
            else -> """
            💡 Mnemonic: Picture a memorable mental image associating "$word" with its meaning "$translation".
            🗣️ Native Tip: Pay attention to syllable stress and open vowel clarity in ${language.name}.
            🌟 Example 1: Practicing "$word" in daily dialogues cements long-term memory.
            🌟 Example 2: Notice how native ${language.name} speakers frequently use this in conversational context!
            """.trimIndent()
        }
    }

    suspend fun getPhraseDeepDive(
        phrase: String,
        literalMeaning: String,
        meaning: String,
        isIdiom: Boolean,
        language: Language,
        nativeLanguage: Language = SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[1]
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            val key = BuildConfig.GEMINI_API_KEY
            if (key.isNotBlank() && !key.contains("MY_GEMINI_API_KEY") && key != "null") {
                key
            } else {
                System.getenv("GEMINI_API_KEY") ?: ""
            }
        } catch (e: Throwable) {
            System.getenv("GEMINI_API_KEY") ?: ""
        }

        val hasValidKey = apiKey.isNotBlank() &&
                !apiKey.contains("MY_GEMINI_API_KEY") &&
                apiKey != "null"

        if (hasValidKey) {
            val promptType = if (isIdiom) "idiom / metaphorical expression" else "essential everyday conversational phrase"
            val prompt = """
                Provide an inspiring, cultural breakdown for learning the $promptType in ${language.name}:
                "$phrase"
                Literal translation: "$literalMeaning"
                Figurative meaning: "$meaning"
                
                The learner's mother tongue is ${nativeLanguage.name}. Explain in ${nativeLanguage.name} so that nuances and culture are immediately understood.
                Include:
                1. 📜 Herkunft / Ursprung (Historical or cultural origin of this expression).
                2. 🎭 Wann verwenden? (Formality level: informal with friends, business, or slang?).
                3. 💬 2-zeiliger Beispieldialog (Target language sentence with ${nativeLanguage.name} translation).
                4. 💡 Typischer Fehler (Common mistake foreign learners make).
                Keep the response concise, formatted with clear emojis and bullet points, under 180 words.
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 500)
                })
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonBody.toString().toRequestBody(mediaType)

            for (candidateModel in modelCandidates) {
                try {
                    val url = "https://generativelanguage.googleapis.com/v1beta/models/$candidateModel:generateContent?key=$apiKey"
                    val request = Request.Builder().url(url).post(requestBody).build()
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseStr = response.body?.string() ?: ""
                            val root = JSONObject(responseStr)
                            val candidates = root.optJSONArray("candidates")
                            if (candidates != null && candidates.length() > 0) {
                                val contentObj = candidates.getJSONObject(0).optJSONObject("content")
                                val parts = contentObj?.optJSONArray("parts")
                                if (parts != null && parts.length() > 0) {
                                    val text = parts.getJSONObject(0).optString("text").trim()
                                    if (text.isNotBlank()) return@withContext text
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.w("GeminiLanguageService", "Error calling getPhraseDeepDive on $candidateModel: ${e.message}")
                }
            }
        }

        // Fallback offline breakdown
        """
        📜 Herkunft & Hintergrund:
        „$phrase“ ist ein lebendiger Bestandteil der Alltagskultur im ${language.name}. Wörtlich bedeutet es „$literalMeaning“, bildhaft meint es „$meaning“.

        🎭 Wann verwenden?
        Ideal für lebendige, natürliche Unterhaltungen mit Muttersprachlern. Zeigt sofort hohes Sprachgefühl!

        💬 Beispieldialog:
        — „$phrase!“
        — „Genau so ist es!“

        💡 Tipp für Lernende:
        Achte auf Betonung und Melodie – Redewendungen klingen am besten, wenn man sie selbstbewusst und mit der typischen Sprachmelodie vorträgt.
        """.trimIndent()
    }
}

