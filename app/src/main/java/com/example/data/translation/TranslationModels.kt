package com.example.data.translation

import com.example.data.models.Language
import java.util.UUID

data class WordToken(
    val word: String,
    val partOfSpeech: String,
    val meaning: String
)

data class TranslationResult(
    val id: String = UUID.randomUUID().toString(),
    val sourceText: String,
    val translatedText: String,
    val sourceLanguage: Language,
    val targetLanguage: Language,
    val pronunciation: String? = null,
    val formality: String = "Neutral", // "Formal", "Informal", "Polite", "Neutral"
    val breakdown: List<WordToken> = emptyList(),
    val grammarNuances: String? = null,
    val culturalContext: String? = null,
    val alternatives: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

data class TranslationHistoryItem(
    val id: String = UUID.randomUUID().toString(),
    val sourceText: String,
    val translatedText: String,
    val sourceLangCode: String,
    val targetLangCode: String,
    val pronunciation: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false
)

data class QuickPhraseItem(
    val category: String,
    val text: String,
    val iconEmoji: String
)
