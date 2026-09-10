package com.example.data.models

import java.util.Locale
import java.util.UUID

enum class MessageSender {
    USER, AI
}

enum class ProficiencyLevel(val label: String, val levelCode: String, val description: String) {
    BEGINNER("Beginner", "A1-A2", "Simple words, slow speech, phonetic guides"),
    INTERMEDIATE("Intermediate", "B1-B2", "Everyday topics, idiomatic polish, moderate pace"),
    ADVANCED("Advanced", "C1-C2", "Native speed, nuanced expressions, in-depth feedback")
}

data class Language(
    val code: String,
    val name: String,
    val nativeName: String,
    val flag: String,
    val ttsLocale: Locale,
    val initialGreeting: String,
    val initialTranslation: String,
    val initialPronunciation: String
) {
    val flagEmoji: String get() = flag
}

val SupportedLanguages = listOf(
    Language(
        code = "es",
        name = "Spanish",
        nativeName = "Español",
        flag = "🇪🇸",
        ttsLocale = Locale.forLanguageTag("es-ES"),
        initialGreeting = "¡Hola! ¿Cómo estás hoy? Estoy listo para ayudarte a practicar español.",
        initialTranslation = "Hello! How are you today? I'm ready to help you practice Spanish.",
        initialPronunciation = "OH-lah! KOH-moh ess-TAHS oy?"
    ),
    Language(
        code = "fr",
        name = "French",
        nativeName = "Français",
        flag = "🇫🇷",
        ttsLocale = Locale.FRENCH,
        initialGreeting = "Bonjour ! Comment ça va aujourd'hui ? Prêt à pratiquer le français ?",
        initialTranslation = "Hello! How are you today? Ready to practice French?",
        initialPronunciation = "bohn-zhoor! koh-mahn sah vah oh-zhoor-dwee?"
    ),
    Language(
        code = "de",
        name = "German",
        nativeName = "Deutsch",
        flag = "🇩🇪",
        ttsLocale = Locale.GERMAN,
        initialGreeting = "Hallo! Wie geht es dir heute? Lass uns Deutsch üben!",
        initialTranslation = "Hello! How are you today? Let's practice German!",
        initialPronunciation = "HAH-loh! vee gayt ess deer HOY-teh?"
    ),
    Language(
        code = "ja",
        name = "Japanese",
        nativeName = "日本語",
        flag = "🇯🇵",
        ttsLocale = Locale.JAPANESE,
        initialGreeting = "こんにちは！今日はどんな話をしましょうか？楽しく日本語を練習しましょう！",
        initialTranslation = "Hello! What shall we talk about today? Let's practice Japanese pleasantly!",
        initialPronunciation = "Konnichiwa! Kyō wa donna hanashi o shimashō ka?"
    ),
    Language(
        code = "it",
        name = "Italian",
        nativeName = "Italiano",
        flag = "🇮🇹",
        ttsLocale = Locale.ITALIAN,
        initialGreeting = "Ciao! Come stai oggi? Sono pronto per parlare in italiano con te!",
        initialTranslation = "Hi! How are you today? I'm ready to chat in Italian with you!",
        initialPronunciation = "CHOW! KOH-meh STAH-ee OH-jee?"
    ),
    Language(
        code = "zh",
        name = "Mandarin",
        nativeName = "中文",
        flag = "🇨🇳",
        ttsLocale = Locale.CHINESE,
        initialGreeting = "你好！今天想聊些什么呢？我们一起开心地练习中文吧！",
        initialTranslation = "Hello! What would you like to chat about today? Let's happily practice Chinese together!",
        initialPronunciation = "Nǐ hǎo! Jīntiān xiǎng liáo xiē shénme ne?"
    ),
    Language(
        code = "ko",
        name = "Korean",
        nativeName = "한국어",
        flag = "🇰🇷",
        ttsLocale = Locale.KOREAN,
        initialGreeting = "안녕하세요! 오늘 기분이 어떠신가요? 함께 한국어 연습해 봐요!",
        initialTranslation = "Hello! How are you feeling today? Let's practice Korean together!",
        initialPronunciation = "Annyeonghaseyo! Oneul gibun-i eotteosingayo?"
    ),
    Language(
        code = "pt",
        name = "Portuguese",
        nativeName = "Português",
        flag = "🇧🇷",
        ttsLocale = Locale.forLanguageTag("pt-BR"),
        initialGreeting = "Olá! Como você está? Vamos praticar português juntos hoje!",
        initialTranslation = "Hello! How are you? Let's practice Portuguese together today!",
        initialPronunciation = "oh-LAH! KOH-moo voh-SEH ess-TAH?"
    ),
    Language(
        code = "en",
        name = "English",
        nativeName = "English",
        flag = "🇬🇧",
        ttsLocale = Locale.ENGLISH,
        initialGreeting = "Hello! Great to see you. What would you like to talk about today?",
        initialTranslation = "Hello! Great to see you. What would you like to talk about today?",
        initialPronunciation = "heh-LOH! grayt too see yoo."
    )
)

data class ScenarioPhrase(
    val id: String = UUID.randomUUID().toString(),
    val targetText: String,
    val translation: String,
    val germanTranslation: String? = null,
    val pronunciation: String? = null,
    val cefrLevel: String = "A1",
    val formality: String = "Neutral",
    val category: String = "General",
    val note: String? = null
)

data class PracticeScenario(
    val id: String,
    val title: String,
    val category: String,
    val iconEmoji: String,
    val description: String,
    val promptContext: String,
    val goals: List<String>,
    val cefrLevel: String = "A1",
    val proficiencyLevel: ProficiencyLevel = ProficiencyLevel.BEGINNER,
    val phrases: List<ScenarioPhrase> = emptyList()
)

val PracticeScenarios: List<PracticeScenario>
    get() = com.example.data.scenarios.ScenarioRepository.allScenarios


data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val translation: String? = null,
    val pronunciation: String? = null,
    val grammarFeedback: String? = null,
    val betterAlternative: String? = null,
    val suggestedReplies: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

data class SavedWord(
    val id: String = UUID.randomUUID().toString(),
    val word: String,
    val translation: String,
    val contextSentence: String,
    val languageCode: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class UserStats(
    val streakDays: Int = 1,
    val totalTurnsSpoken: Int = 0,
    val vocabularyLearned: Int = 0,
    val minutesPracticed: Int = 0
)
