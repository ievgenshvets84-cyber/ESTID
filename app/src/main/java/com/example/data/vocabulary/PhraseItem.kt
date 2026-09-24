package com.example.data.vocabulary

enum class PhraseCategory(val id: String, val emoji: String) {
    ALL("all", "✨"),
    IDIOMS("idioms", "💡"),       // Redewendungen & Sprichwörter
    DAILY("daily", "☕"),         // Alltag & Begrüßung
    TRAVEL("travel", "✈️"),       // Reisen & Orientierung
    DINING("dining", "🍽️"),       // Restaurant & Essen
    BUSINESS("business", "💼"),   // Beruf & Smalltalk
    FEELINGS("feelings", "❤️"),   // Gefühle & Meinungen
    EMERGENCY("emergency", "🚨"), // Notfall & Hilfe
    BOOKMARKED("saved", "⭐")     // Gespeicherte Phrasen
}

data class PhraseItem(
    val id: String,
    val languageCode: String,
    val phrase: String,
    val phonetic: String,
    val literalMeaning: String,
    val meaning: String,
    val category: PhraseCategory,
    val isIdiom: Boolean,
    val exampleSentence: String,
    val exampleTranslation: String,
    val culturalTip: String = "",
    val isBookmarked: Boolean = false
)

enum class PhraseSubMode {
    BROWSE,    // Katalog / Durchstöbern
    TRAINER,   // Karteikarten-Trainer
    QUIZ       // Redewendungen-Quiz
}

data class PhraseQuizQuestion(
    val phrase: PhraseItem,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
