package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.GeminiLanguageService
import com.example.data.models.ChatMessage
import com.example.data.models.Language
import com.example.data.models.MessageSender
import com.example.data.models.PracticeScenario
import com.example.data.models.PracticeScenarios
import com.example.data.models.ProficiencyLevel
import com.example.data.models.SavedWord
import com.example.data.models.SupportedLanguages
import com.example.data.models.UserStats
import com.example.data.speech.SpeechRecognizerManager
import com.example.data.tts.TtsManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

data class ConversationUiState(
    val selectedLanguage: Language = SupportedLanguages[0], // Spanish
    val nativeLanguage: Language = SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[0],
    val selectedLevel: ProficiencyLevel = ProficiencyLevel.BEGINNER,
    val selectedScenario: PracticeScenario = PracticeScenarios[0],
    val messages: List<ChatMessage> = emptyList(),
    val isGenerating: Boolean = false,
    val isVoiceModeActive: Boolean = false,
    val isListening: Boolean = false,
    val rmsDb: Float = 0f,
    val partialSpeech: String = "",
    val isTtsSpeaking: Boolean = false,
    val currentSpeakingId: String? = null,
    val autoPlayAudio: Boolean = true,
    val speechSpeed: Float = 0.9f,
    val showTranslations: Boolean = true,
    val showPronunciations: Boolean = true,
    val savedWords: List<SavedWord> = emptyList(),
    val stats: UserStats = UserStats(streakDays = 3, totalTurnsSpoken = 12, vocabularyLearned = 5, minutesPracticed = 18),
    val userErrorMessage: String? = null
)

class LanguagePartnerViewModel(application: Application) : AndroidViewModel(application) {
    private val geminiService = GeminiLanguageService()
    val ttsManager = TtsManager(application)
    val speechManager = SpeechRecognizerManager(application)
    private val prefs = application.getSharedPreferences("lingua_partner_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(ConversationUiState())
    val uiState: StateFlow<ConversationUiState> = _uiState.asStateFlow()

    private var autoPlayJob: Job? = null

    init {
        loadPersistedData()
        startScenarioConversation(
            language = _uiState.value.selectedLanguage,
            scenario = _uiState.value.selectedScenario
        )

        // Observe TTS state
        viewModelScope.launch {
            ttsManager.isSpeaking.collect { speaking ->
                _uiState.update { it.copy(isTtsSpeaking = speaking) }
            }
        }
        viewModelScope.launch {
            ttsManager.currentUtteranceId.collect { id ->
                _uiState.update { it.copy(currentSpeakingId = id) }
            }
        }

        // Observe Speech state
        viewModelScope.launch {
            speechManager.isListening.collect { listening ->
                _uiState.update { it.copy(isListening = listening) }
            }
        }
        viewModelScope.launch {
            speechManager.rmsDb.collect { db ->
                _uiState.update { it.copy(rmsDb = db) }
            }
        }
        viewModelScope.launch {
            speechManager.partialText.collect { text ->
                _uiState.update { it.copy(partialSpeech = text) }
            }
        }
    }

    private fun loadPersistedData() {
        val langCode = prefs.getString("selected_lang", "es") ?: "es"
        val lang = SupportedLanguages.find { it.code == langCode } ?: SupportedLanguages[0]
        val nativeCode = prefs.getString("native_lang", "de") ?: "de"
        val nativeLang = SupportedLanguages.find { it.code == nativeCode } ?: SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[0]
        val levelName = prefs.getString("selected_level", ProficiencyLevel.BEGINNER.name) ?: ProficiencyLevel.BEGINNER.name
        val level = try { ProficiencyLevel.valueOf(levelName) } catch (e: Exception) { ProficiencyLevel.BEGINNER }
        val speed = prefs.getFloat("speech_speed", 0.9f)
        val autoPlay = prefs.getBoolean("auto_play", true)
        val streak = prefs.getInt("streak_days", 3)
        val turns = prefs.getInt("turns_spoken", 12)

        // Load saved words
        val savedWordsList = mutableListOf<SavedWord>()
        val savedWordsJson = prefs.getString("saved_words_json", null)
        if (savedWordsJson != null) {
            try {
                val array = JSONArray(savedWordsJson)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    savedWordsList.add(
                        SavedWord(
                            id = obj.optString("id"),
                            word = obj.optString("word"),
                            translation = obj.optString("translation"),
                            contextSentence = obj.optString("contextSentence"),
                            languageCode = obj.optString("languageCode"),
                            timestamp = obj.optLong("timestamp")
                        )
                    )
                }
            } catch (e: Exception) {
                // Ignore parse errors
            }
        } else {
            // Default starter vocabulary
            savedWordsList.addAll(
                listOf(
                    SavedWord(word = "¡Mucho gusto!", translation = "Pleased to meet you!", contextSentence = "¡Hola! Mucho gusto en conocerte.", languageCode = "es"),
                    SavedWord(word = "Por favor", translation = "Please", contextSentence = "Un café con leche, por favor.", languageCode = "es"),
                    SavedWord(word = "La cuenta", translation = "The check / bill", contextSentence = "¿Nos trae la cuenta, por favor?", languageCode = "es"),
                    SavedWord(word = "Merci beaucoup", translation = "Thank you very much", contextSentence = "Merci beaucoup pour votre aide !", languageCode = "fr"),
                    SavedWord(word = "ありがとうございます", translation = "Thank you very much", contextSentence = "美味しい料理をありがとうございます。", languageCode = "ja")
                )
            )
        }

        _uiState.update {
            it.copy(
                selectedLanguage = lang,
                nativeLanguage = nativeLang,
                selectedLevel = level,
                speechSpeed = speed,
                autoPlayAudio = autoPlay,
                savedWords = savedWordsList,
                stats = it.stats.copy(streakDays = streak, totalTurnsSpoken = turns, vocabularyLearned = savedWordsList.size)
            )
        }
    }

    fun selectNativeLanguage(language: Language) {
        if (language.code == _uiState.value.nativeLanguage.code) return
        prefs.edit().putString("native_lang", language.code).apply()
        _uiState.update { it.copy(nativeLanguage = language) }
    }

    private fun persistSavedWords(words: List<SavedWord>) {
        val array = JSONArray()
        for (w in words) {
            val obj = JSONObject()
            obj.put("id", w.id)
            obj.put("word", w.word)
            obj.put("translation", w.translation)
            obj.put("contextSentence", w.contextSentence)
            obj.put("languageCode", w.languageCode)
            obj.put("timestamp", w.timestamp)
            array.put(obj)
        }
        prefs.edit().putString("saved_words_json", array.toString()).apply()
    }

    fun selectLanguage(language: Language) {
        if (language.code == _uiState.value.selectedLanguage.code) return
        autoPlayJob?.cancel()
        ttsManager.stop()
        speechManager.stopListening()
        prefs.edit().putString("selected_lang", language.code).apply()

        _uiState.update { it.copy(selectedLanguage = language, userErrorMessage = null) }
        startScenarioConversation(language, _uiState.value.selectedScenario)
    }

    fun selectScenario(scenario: PracticeScenario) {
        if (scenario.id == _uiState.value.selectedScenario.id) return
        autoPlayJob?.cancel()
        ttsManager.stop()
        speechManager.stopListening()

        _uiState.update { it.copy(selectedScenario = scenario, userErrorMessage = null) }
        startScenarioConversation(_uiState.value.selectedLanguage, scenario)
    }

    fun selectLevel(level: ProficiencyLevel) {
        prefs.edit().putString("selected_level", level.name).apply()
        _uiState.update { it.copy(selectedLevel = level) }
    }

    private fun startScenarioConversation(language: Language, scenario: PracticeScenario) {
        val initialGreeting = when (scenario.id) {
            "cafe_ordering" -> when (language.code) {
                "es" -> Triple(
                    "¡Buenos días! Bienvenido a nuestro café. ¿Qué te gustaría ordenar hoy?",
                    "Good morning! Welcome to our café. What would you like to order today?",
                    "BWEH-nohs DEE-ahs! bee-en-veh-NEE-doh ah NWEHS-troh kah-FEH..."
                )
                "fr" -> Triple(
                    "Bonjour ! Bienvenue au café. Qu'est-ce qui vous ferait plaisir aujourd'hui ?",
                    "Good morning! Welcome to the café. What would you like today?",
                    "bohn-zhoor! byan-vuh-noo oh kah-fay..."
                )
                "de" -> Triple(
                    "Guten Tag! Willkommen im Café. Was darf ich Ihnen heute bringen?",
                    "Good day! Welcome to the café. What can I bring you today?",
                    "GOO-ten TAHG! vil-KOM-men im kah-FAY..."
                )
                "ja" -> Triple(
                    "いらっしゃいませ！カフェへようこそ。本日は何をご注文なさいますか？",
                    "Welcome! Welcome to the café. What would you like to order today?",
                    "Irasshaimase! Kafe e yōkoso. Honjitsu wa nani o go-chūmon nasaimasu ka?"
                )
                "it" -> Triple(
                    "Buongiorno! Benvenuto al nostro caffè. Cosa vorresti ordinare oggi?",
                    "Good morning! Welcome to our café. What would you like to order today?",
                    "Bwon-JOR-noh! Ben-veh-NOO-toh ahl NOH-stroh kahf-FEH. KOH-zah vor-REH-stee or-dee-NAH-reh OH-jee?"
                )
                "zh" -> Triple(
                    "早上好！欢迎光临我们的咖啡馆。您今天想喝点什么？",
                    "Good morning! Welcome to our café. What would you like to drink today?",
                    "Zǎoshang hǎo! Huānyíng guānglín wǒmen de kāfēiguǎn. Nín jīntiān xiǎng hē diǎn shénme?"
                )
                "ko" -> Triple(
                    "좋은 아침입니다! 카페에 오신 것을 환영해요. 오늘 어떤 음료로 드릴까요?",
                    "Good morning! Welcome to our café. What drink would you like today?",
                    "Joeun achimimnida! Kafe-e osin geoseul hwanyeonghaeyo. Oneul eotteon eumryoro deurilkkayo?"
                )
                "pt" -> Triple(
                    "Bom dia! Bem-vindo ao nosso café. O que você gostaria de pedir hoje?",
                    "Good morning! Welcome to our café. What would you like to order today?",
                    "Bohm DEE-ah! Baym-VEEN-doo ao NOH-soo kah-FEH. Oo kay voh-SEH go-stah-REE-ah deh peh-DEER OH-zhee?"
                )
                "ru" -> Triple(
                    "Доброе утро! Добро пожаловать в наше кафе. Что бы вы хотели заказать сегодня?",
                    "Good morning! Welcome to our café. What would you like to order today?",
                    "DOB-ro-ye OO-tro! Dobro pozhalovat' v nashe kafe. Chto by vy khoteli zakazat' segodnya?"
                )
                "uk" -> Triple(
                    "Доброго ранку! Ласкаво просимо до нашої кав'ярні. Що бажаєте замовити сьогодні?",
                    "Good morning! Welcome to our café. What would you like to order today?",
                    "DOB-ro-ho RAHN-koo! Las-KA-vo PRO-sy-mo do na-SHO-yi kav-YAR-ni..."
                )
                else -> Triple(
                    "Hello! Welcome to our café. What would you like to have today?",
                    "Hello! Welcome to our café. What would you like to have today?",
                    "heh-LOH! WEL-kum too our kah-FAY."
                )
            }
            "travel_hotel" -> when (language.code) {
                "es" -> Triple(
                    "¡Buenas tardes! Bienvenido al Hotel Central. ¿Tienes una reserva con nosotros?",
                    "Good afternoon! Welcome to Hotel Central. Do you have a reservation with us?",
                    "BWEH-nahs TAR-dehs! bee-en-veh-NEE-doh ahl oh-TELL sen-TRAHL..."
                )
                "fr" -> Triple(
                    "Bonjour ! Bienvenue à l'Hôtel Central. Avez-vous une réservation à votre nom ?",
                    "Hello! Welcome to Central Hotel. Do you have a reservation in your name?",
                    "bohn-zhoor! byan-vuh-noo ah loh-tell..."
                )
                "de" -> Triple(
                    "Guten Tag! Herzlich willkommen im Hotel Central. Haben Sie eine Reservierung bei uns?",
                    "Good day! Welcome to Hotel Central. Do you have a reservation with us?",
                    "GOO-ten TAHG! HERTS-likh vil-KOM-men im ho-TEL..."
                )
                "it" -> Triple(
                    "Buon pomeriggio! Benvenuto all'Hotel Centrale. Ha una prenotazione a suo nome?",
                    "Good afternoon! Welcome to Central Hotel. Do you have a reservation under your name?",
                    "Bwon poh-meh-REE-joh! Ben-veh-NOO-toh ahl oh-TEL chen-TRAH-leh. Ah OO-nah preh-noh-tah-TSYOH-neh?"
                )
                "zh" -> Triple(
                    "您好！欢迎入住中心大酒店。请问您有预订吗？",
                    "Hello! Welcome to Central Hotel. Do you have a reservation?",
                    "Nín hǎo! Huānyíng rùzhù zhōngxīn dà jiǔdiàn. Qǐngwèn nín yǒu yùdìng ma?"
                )
                "ko" -> Triple(
                    "안녕하세요! 센트럴 호텔에 오신 것을 환영합니다. 예약하셨나요?",
                    "Hello! Welcome to Central Hotel. Did you make a reservation?",
                    "Annyeonghaseyo! Senteureol hotere osin geoseul hwanyeonghamnida. Yeyakhasyeonnayo?"
                )
                "pt" -> Triple(
                    "Boa tarde! Bem-vindo ao Hotel Central. Você possui uma reserva conosco?",
                    "Good afternoon! Welcome to Central Hotel. Do you have a reservation with us?",
                    "BOH-ah TAR-deh! Baym-VEEN-doo ao oh-TELL sen-TRAHL. Voh-SEH poh-SOO-ee OO-mah reh-ZEHR-vah?"
                )
                "ru" -> Triple(
                    "Добрый день! Добро пожаловать в отель Центральный. У вас есть бронирование?",
                    "Good afternoon! Welcome to Central Hotel. Do you have a reservation?",
                    "DOB-ryy DYEN'! Dobro pozhalovat' v otel' Tsentral'nyy. Oo vas yest' bro-nee-ro-va-nee-ye?"
                )
                "ja" -> Triple(
                    "いらっしゃいませ！ホテル・セントラルへようこそ。ご予約のお名前を伺えますか？",
                    "Welcome! Welcome to Hotel Central. May I have the name for the reservation?",
                    "Irasshaimase! Hoteru Sentoraru e yōkoso. Go-yoyaku no o-namae o ukagaemasu ka?"
                )
                else -> Triple(
                    "Welcome to Central Hotel! Do you have a reservation under your name?",
                    "Welcome to Central Hotel! Do you have a reservation under your name?",
                    "WEL-kum too SEN-trul hoh-TEL!"
                )
            }
            else -> Triple(
                language.initialGreeting,
                language.initialTranslation,
                language.initialPronunciation
            )
        }

        val starterReplies = when (language.code) {
            "es" -> listOf("¡Hola! Me alegro de verte.", "Un café con leche y un croissant, por favor.", "¿Qué me recomiendas?")
            "fr" -> listOf("Bonjour ! Très heureux d'être ici.", "Un café et un croissant, s'il vous plaît.", "Que me conseillez-vous ?")
            "de" -> listOf("Hallo! Schön dich zu sehen.", "Einen Kaffee bitte.", "Was kannst du empfehlen?")
            "ja" -> listOf("こんにちは！よろしくお願いします。", "おすすめは何ですか？", "コーヒーを一つお願いします。")
            "it" -> listOf("Ciao! Vorrei un cappuccino e un cornetto, per favore.", "Cosa mi consiglia oggi?", "Vorrei vedere il menù, grazie.")
            "zh" -> listOf("你好！请给我一杯热咖啡。", "请问这里有什么特色推荐吗？", "我想先看一下菜单，谢谢。")
            "ko" -> listOf("안녕하세요! 따뜻한 아메리카노 한 잔 주세요.", "여기 추천 메뉴가 무엇인가요?", "메뉴판 좀 볼 수 있을까요?")
            "pt" -> listOf("Olá! Um café com leite e um pão de queijo, por favor.", "O que você me recomenda hoje?", "Poderia ver o cardápio, por favor?")
            "ru" -> listOf("Здравствуйте! Чашку капучино, пожалуйста.", "Что вы порекомендуете?", "Можно меню, пожалуйста?")
            "uk" -> listOf("Привіт! Чашку кави, будь ласка.", "Що ви порекомендуєте?", "Можна подивитися меню?")
            else -> listOf("Hello! Great to be here.", "I'd like a coffee, please.", "What do you recommend?")
        }

        val initialMessage = ChatMessage(
            sender = MessageSender.AI,
            text = initialGreeting.first,
            translation = initialGreeting.second,
            pronunciation = initialGreeting.third,
            suggestedReplies = starterReplies
        )

        _uiState.update {
            it.copy(
                messages = listOf(initialMessage),
                isGenerating = false,
                userErrorMessage = null
            )
        }

        if (_uiState.value.autoPlayAudio) {
            autoPlayJob?.cancel()
            autoPlayJob = viewModelScope.launch {
                delay(400)
                speakMessage(initialMessage)
            }
        }
    }

    fun sendMessage(userText: String) {
        val cleanText = userText.trim()
        if (cleanText.isBlank() || _uiState.value.isGenerating) return

        autoPlayJob?.cancel()
        ttsManager.stop()
        speechManager.stopListening()

        val userMessage = ChatMessage(
            sender = MessageSender.USER,
            text = cleanText
        )

        val updatedMessages = _uiState.value.messages + userMessage
        val updatedTurns = _uiState.value.stats.totalTurnsSpoken + 1

        _uiState.update {
            it.copy(
                messages = updatedMessages,
                isGenerating = true,
                partialSpeech = "",
                stats = it.stats.copy(totalTurnsSpoken = updatedTurns)
            )
        }
        prefs.edit().putInt("turns_spoken", updatedTurns).apply()

        viewModelScope.launch {
            val currentState = _uiState.value
            val responseResult = geminiService.sendConversationTurn(
                userMessage = cleanText,
                history = updatedMessages,
                language = currentState.selectedLanguage,
                level = currentState.selectedLevel,
                scenario = currentState.selectedScenario,
                nativeLanguage = currentState.nativeLanguage
            )

            val aiMessage = ChatMessage(
                sender = MessageSender.AI,
                text = responseResult.reply,
                translation = responseResult.translation,
                pronunciation = responseResult.pronunciation,
                grammarFeedback = responseResult.grammarFeedback,
                betterAlternative = responseResult.betterAlternative,
                suggestedReplies = responseResult.suggestedReplies
            )

            _uiState.update {
                it.copy(
                    messages = it.messages + aiMessage,
                    isGenerating = false
                )
            }

            if (_uiState.value.autoPlayAudio) {
                autoPlayJob?.cancel()
                autoPlayJob = viewModelScope.launch {
                    delay(300)
                    speakMessage(aiMessage)
                }
            }
        }
    }

    fun startVoiceRecording() {
        ttsManager.stop()
        val langCode = _uiState.value.selectedLanguage.code
        _uiState.update { it.copy(userErrorMessage = null, partialSpeech = "") }

        speechManager.startListening(
            languageCode = langCode,
            onFinalResult = { text ->
                if (text.isNotBlank()) {
                    sendMessage(text)
                }
            },
            onError = { err ->
                _uiState.update { it.copy(userErrorMessage = err) }
            }
        )
    }

    fun stopVoiceRecording() {
        speechManager.stopListening()
    }

    fun speakMessage(message: ChatMessage) {
        if (_uiState.value.isTtsSpeaking && _uiState.value.currentSpeakingId == message.id) {
            stopSpeaking()
        } else {
            stopSpeaking()
            val locale = _uiState.value.selectedLanguage.ttsLocale
            val speed = _uiState.value.speechSpeed
            ttsManager.speak(message.text, locale, speed, message.id)
        }
    }

    fun speakText(text: String, language: Language = _uiState.value.selectedLanguage, id: String = text) {
        if (_uiState.value.isTtsSpeaking && _uiState.value.currentSpeakingId == id) {
            stopSpeaking()
        } else {
            stopSpeaking()
            val locale = language.ttsLocale
            val speed = _uiState.value.speechSpeed
            ttsManager.speak(text, locale, speed, id)
        }
    }

    fun stopSpeaking() {
        autoPlayJob?.cancel()
        ttsManager.stop()
    }

    fun toggleVoiceMode() {
        ttsManager.stop()
        speechManager.stopListening()
        _uiState.update { it.copy(isVoiceModeActive = !it.isVoiceModeActive) }
    }

    fun setVoiceMode(active: Boolean) {
        ttsManager.stop()
        speechManager.stopListening()
        _uiState.update { it.copy(isVoiceModeActive = active) }
    }

    fun toggleAutoPlayAudio() {
        val newValue = !_uiState.value.autoPlayAudio
        prefs.edit().putBoolean("auto_play", newValue).apply()
        _uiState.update { it.copy(autoPlayAudio = newValue) }
    }

    fun setSpeechSpeed(speed: Float) {
        prefs.edit().putFloat("speech_speed", speed).apply()
        _uiState.update { it.copy(speechSpeed = speed) }
    }

    fun toggleTranslations() {
        _uiState.update { it.copy(showTranslations = !it.showTranslations) }
    }

    fun togglePronunciations() {
        _uiState.update { it.copy(showPronunciations = !it.showPronunciations) }
    }

    fun saveVocabulary(word: String, translation: String, contextSentence: String) {
        val currentWords = _uiState.value.savedWords
        if (currentWords.any { it.word.equals(word.trim(), ignoreCase = true) }) return

        val newWord = SavedWord(
            word = word.trim(),
            translation = translation.trim(),
            contextSentence = contextSentence.trim(),
            languageCode = _uiState.value.selectedLanguage.code
        )
        val updated = listOf(newWord) + currentWords
        _uiState.update {
            it.copy(
                savedWords = updated,
                stats = it.stats.copy(vocabularyLearned = updated.size)
            )
        }
        persistSavedWords(updated)
    }

    fun deleteVocabulary(id: String) {
        val updated = _uiState.value.savedWords.filterNot { it.id == id }
        _uiState.update {
            it.copy(
                savedWords = updated,
                stats = it.stats.copy(vocabularyLearned = updated.size)
            )
        }
        persistSavedWords(updated)
    }

    fun clearChat() {
        autoPlayJob?.cancel()
        ttsManager.stop()
        speechManager.stopListening()
        startScenarioConversation(_uiState.value.selectedLanguage, _uiState.value.selectedScenario)
    }

    fun dismissError() {
        _uiState.update { it.copy(userErrorMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
        speechManager.destroy()
    }
}
