package com.example.data.api

import android.util.Log
import com.example.BuildConfig
import com.example.data.models.ChatMessage
import com.example.data.models.Language
import com.example.data.models.MessageSender
import com.example.data.models.PracticeScenario
import com.example.data.models.ProficiencyLevel
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
        .connectTimeout(35, TimeUnit.SECONDS)
        .readTimeout(35, TimeUnit.SECONDS)
        .writeTimeout(35, TimeUnit.SECONDS)
        .build()

    // Default to gemini-2.5-flash as per skill guidelines
    private val modelName = "gemini-2.5-flash"

    suspend fun sendConversationTurn(
        userMessage: String,
        history: List<ChatMessage>,
        language: Language,
        level: ProficiencyLevel,
        scenario: PracticeScenario
    ): AIResponseResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        val hasValidKey = apiKey.isNotBlank() &&
                !apiKey.contains("MY_GEMINI_API_KEY") &&
                apiKey != "null"

        if (hasValidKey) {
            try {
                val result = callGeminiApi(apiKey, userMessage, history, language, level, scenario)
                if (result != null) {
                    return@withContext result
                }
            } catch (e: Exception) {
                Log.w("GeminiLanguageService", "Gemini API request failed, falling back to simulated partner: ${e.message}")
            }
        }

        // Resilient fallback with natural conversational responses for language learning
        generateFallbackResponse(userMessage, language, level, scenario)
    }

    private fun callGeminiApi(
        apiKey: String,
        userMessage: String,
        history: List<ChatMessage>,
        language: Language,
        level: ProficiencyLevel,
        scenario: PracticeScenario
    ): AIResponseResult? {
        val systemInstruction = """
            You are Maya, an encouraging, friendly, and culturally authentic AI language learning partner.
            The user is practicing: ${language.name} (${language.nativeName}).
            Learner Proficiency Level: ${level.label} (${level.levelCode}) - ${level.description}.
            Active Practice Scenario: ${scenario.title} (${scenario.category}).
            Scenario Context: ${scenario.promptContext}
            Scenario Learning Goals: ${scenario.goals.joinToString("; ")}.

            RULES:
            1. Always respond in the target language (${language.name}).
            2. Match your sentence complexity and vocabulary to the user's proficiency level (${level.label}).
            3. Keep the conversation lively, natural, and ask an open follow-up question to keep the user speaking.
            4. Analyze what the user said in their latest turn. If they made any grammar, conjugation, tense, gender, or vocabulary mistake, provide gentle, educational, encouraging feedback explaining the correction clearly in English.
            5. If their sentence was flawless, provide a brief praise or nuance tip in grammarFeedback.
            6. Provide a natural "betterAlternative" showing how a native speaker would phrase what the user intended to say.
            7. Provide 3 short, realistic "suggestedReplies" in ${language.name} that the user can choose or learn from to continue the dialog.

            You MUST respond with valid JSON ONLY in this format:
            {
              "reply": "Your conversational response in ${language.name}",
              "translation": "English translation of your reply",
              "pronunciation": "Phonetic pronunciation guide or romanization (e.g. Romaji/Pinyin/easy phonetic syllables)",
              "grammarFeedback": "Gentle correction or praise note about the user's utterance",
              "betterAlternative": "A native-sounding rephrasing of the user's statement, or null if perfect",
              "suggestedReplies": ["Option 1 in ${language.name}", "Option 2 in ${language.name}", "Option 3 in ${language.name}"]
            }
        """.trimIndent()

        val contentsArray = JSONArray()

        // Include recent history (up to last 6 turns)
        val recentHistory = history.takeLast(6)
        for (msg in recentHistory) {
            val turnObj = JSONObject()
            turnObj.put("role", if (msg.sender == MessageSender.USER) "user" else "model")
            val partsArr = JSONArray()
            val textPart = JSONObject().put("text", msg.text)
            partsArr.put(textPart)
            turnObj.put("parts", partsArr)
            contentsArray.put(turnObj)
        }

        // Current turn
        val currentTurn = JSONObject()
        currentTurn.put("role", "user")
        val currentParts = JSONArray()
        currentParts.put(JSONObject().put("text", userMessage))
        currentTurn.put("parts", currentParts)
        contentsArray.put(currentTurn)

        // System Instruction
        val systemInstructionObj = JSONObject()
        val sysParts = JSONArray()
        sysParts.put(JSONObject().put("text", systemInstruction))
        systemInstructionObj.put("parts", sysParts)

        // Generation Config
        val genConfig = JSONObject()
        genConfig.put("temperature", 0.7)
        genConfig.put("responseMimeType", "application/json")

        val rootRequest = JSONObject()
        rootRequest.put("contents", contentsArray)
        rootRequest.put("systemInstruction", systemInstructionObj)
        rootRequest.put("generationConfig", genConfig)

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootRequest.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            val errBody = response.body?.string() ?: ""
            Log.w("GeminiLanguageService", "API call returned ${response.code}: $errBody")
            return null
        }

        val responseBodyStr = response.body?.string() ?: return null
        val responseJson = JSONObject(responseBodyStr)
        val candidates = responseJson.optJSONArray("candidates") ?: return null
        if (candidates.length() == 0) return null

        val candidate0 = candidates.getJSONObject(0)
        val content = candidate0.optJSONObject("content") ?: return null
        val parts = content.optJSONArray("parts") ?: return null
        if (parts.length() == 0) return null

        val rawText = parts.getJSONObject(0).optString("text", "")
        if (rawText.isBlank()) return null

        return parseStructuredJson(rawText)
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
        scenario: PracticeScenario
    ): AIResponseResult {
        val lower = userMessage.lowercase().trim()

        when (language.code) {
            "es" -> {
                val (reply, translation, pronunciation) = when {
                    lower.contains("hola") || lower.contains("buenos") -> Triple(
                        "¡Hola! Qué gusto saludarte. ¿Qué planes tienes para el día de hoy?",
                        "Hello! So good to greet you. What plans do you have for today?",
                        "OH-lah! kay GOO-stoh sah-loo-DAR-teh. kay PLAH-ness tee-EH-ness?"
                    )
                    lower.contains("bien") || lower.contains("gracias") -> Triple(
                        "¡Me alegro mucho! Cuéntame, ¿qué te gusta hacer en tu tiempo libre?",
                        "I'm very glad! Tell me, what do you like to do in your free time?",
                        "may ah-LEH-groh MOO-choh! KWEN-tah-may, kay tay GOO-stah ah-SARE?"
                    )
                    lower.contains("café") || lower.contains("pedir") || lower.contains("agua") -> Triple(
                        "¡Por supuesto! Tenemos café con leche, capuchino y croissants recién horneados. ¿Deseas algo más?",
                        "Of course! We have café con leche, cappuccino, and freshly baked croissants. Would you like anything else?",
                        "por soo-PWEHS-toh! tay-NEH-mohs kah-FEH kohn LEH-chay..."
                    )
                    else -> Triple(
                        "¡Muy interesante! Me encanta cómo estás usando el español. ¿Puedes decirme más sobre eso?",
                        "Very interesting! I love how you're using Spanish. Can you tell me more about that?",
                        "MOO-ee een-tay-ray-SAHN-tay! may en-KAHN-tah..."
                    )
                }

                val grammarNote = if (lower.length > 3) {
                    "¡Excelente comunicación! Recuerda mantener la concordancia de género y número al hablar."
                } else {
                    "¡Buen intento! Intenta formular una oración completa usando 'Yo quiero...' o 'Me gustaría...'."
                }

                return AIResponseResult(
                    reply = reply,
                    translation = translation,
                    pronunciation = pronunciation,
                    grammarFeedback = grammarNote,
                    betterAlternative = "¡Me gustaría saber más sobre tus recomendaciones!",
                    suggestedReplies = listOf(
                        "Me gustaría pedir un café con leche, por favor.",
                        "En mi tiempo libre me gusta escuchar música y viajar.",
                        "¿Cuál es tu lugar favorito en la ciudad?"
                    )
                )
            }

            "fr" -> {
                return AIResponseResult(
                    reply = "C'est merveilleux ! Votre français s'améliore à chaque phrase. Que voulez-vous faire ensuite ?",
                    translation = "That's wonderful! Your French is improving with every sentence. What do you want to do next?",
                    pronunciation = "say mehr-vay-YUH! vohtr frahn-say sah-may-lyohr ah shahk frahz...",
                    grammarFeedback = "Bravo ! Veillez à bien accorder les adjectifs avec les noms.",
                    betterAlternative = "J'aimerais beaucoup en savoir plus sur ce sujet.",
                    suggestedReplies = listOf(
                        "Je voudrais un croissant et un café, s'il vous plaît.",
                        "J'adore voyager et découvrir de nouvelles cultures.",
                        "Pouvez-vous me recommander un bon restaurant ici ?"
                    )
                )
            }

            "de" -> {
                return AIResponseResult(
                    reply = "Das klingt super! Du machst tolle Fortschritte. Was machst du heute Nachmittag?",
                    translation = "That sounds great! You are making great progress. What are you doing this afternoon?",
                    pronunciation = "dahs klinkt ZOO-per! doo mahkst TOH-leh fort-SHRIT-teh...",
                    grammarFeedback = "Sehr gut! Achte im Deutschen auf die Verbposition an zweiter Stelle im Hauptsatz.",
                    betterAlternative = "Ich möchte gerne mein Deutsch im Alltag verbessern.",
                    suggestedReplies = listOf(
                        "Ich möchte bitte einen Kaffee und ein Stück Kuchen bestellen.",
                        "Heute Nachmittag treffe ich mich mit Freunden im Park.",
                        "Was ist die beste Sehenswürdigkeit hier in der Stadt?"
                    )
                )
            }

            "ja" -> {
                return AIResponseResult(
                    reply = "素晴らしいですね！とても自然な日本語です。最近何か面白いことはありましたか？",
                    translation = "That's wonderful! Very natural Japanese. Has anything interesting happened recently?",
                    pronunciation = "Subarashii desu ne! Totemo shizen na Nihongo desu. Saikin nanika omoshiroi koto wa arimashita ka?",
                    grammarFeedback = "文法がとても上手です！助詞（「は」や「を」）の使い方を意識するとさらに自然になります。",
                    betterAlternative = "最近、日本語の勉強を毎日頑張っています。",
                    suggestedReplies = listOf(
                        "おすすめのメニューはありますか？",
                        "休みの日は映画を見たり音楽を聴いたりします。",
                        "日本語の会話をもっと練習したいです。"
                    )
                )
            }

            "it" -> {
                return AIResponseResult(
                    reply = "Che bello! Stai parlando un italiano davvero fantastico. Dimmi, cosa farai stasera?",
                    translation = "How nice! You are speaking truly fantastic Italian. Tell me, what will you do tonight?",
                    pronunciation = "kay BEL-loh! STAH-ee par-LAHN-doh oon ee-tahl-YAH-noh...",
                    grammarFeedback = "Ottimo lavoro! Ricorda che in italiano i pronomi soggetto spesso si omettono.",
                    betterAlternative = "Vorrei ordinare un piatto tipico locale, per favore.",
                    suggestedReplies = listOf(
                        "Un cappuccino e un cornetto alla crema, per favore.",
                        "Stasera vorrei fare una passeggiata in centro.",
                        "Qual è il tuo piatto italiano preferito?"
                    )
                )
            }

            else -> {
                return AIResponseResult(
                    reply = "That is great! I really enjoy chatting with you. What would you like to explore next?",
                    translation = "That is great! I really enjoy chatting with you. What would you like to explore next?",
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
        language: Language
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        val hasValidKey = apiKey.isNotBlank() &&
                !apiKey.contains("MY_GEMINI_API_KEY") &&
                apiKey != "null"

        if (hasValidKey) {
            try {
                val prompt = """
                    Provide a concise, engaging, and memorable breakdown for learning the ${language.name} word "$word" ($translation).
                    Include:
                    1. 💡 Mnemonic hook (an unforgettable way to remember this word).
                    2. 🗣️ Pronunciation tip & phonetic cue.
                    3. 🌟 2 realistic short conversational sentences with English translations.
                    4. 🔍 Nuance or cultural context (when native speakers use it vs avoid it).
                    Keep the response formatted cleanly with emojis and bullet points, under 180 words.
                """.trimIndent()

                val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
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
                Log.e("GeminiService", "Error calling getWordDeepDive", e)
            }
        }

        // Fallback offline breakdown
        """
        💡 Mnemonic: Picture a memorable mental image associating "$word" with its meaning "$translation".
        🗣️ Native Tip: Pay attention to syllable stress and open vowel clarity.
        🌟 Example 1: Practicing "$word" in daily dialogues cements long-term memory.
        🌟 Example 2: Notice how native ${language.name} speakers frequently use this in conversational context!
        """.trimIndent()
    }
}

