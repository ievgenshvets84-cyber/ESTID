package com.example.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vocabulary_words",
    indices = [
        Index(value = ["languageCode", "frequencyRank"], unique = true),
        Index(value = ["languageCode", "tier"]),
        Index(value = ["languageCode", "partOfSpeech"]),
        Index(value = ["languageCode", "word"])
    ]
)
data class VocabularyWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val languageCode: String,
    val frequencyRank: Int, // 1 to 10,000
    val word: String,
    val translation: String,
    val phonetic: String = "",
    val partOfSpeech: String = "noun", // noun, verb, adjective, phrase, etc.
    val category: String = "General", // Essentials, Travel, Food, Work, etc.
    val tier: Int = 1, // 1: Top 1000, 2: 1001-2500, 3: 2501-5000, 4: 5001-7500, 5: 7501-10000
    val cefrLevel: String = "A1", // A1, A2, B1, B2, C1, C2
    val exampleSentence: String = "",
    val exampleTranslation: String = "",
    val masteryLevel: Int = 0, // 0: New, 1: Learning, 2: Reviewing, 3: Mastered
    val timesReviewed: Int = 0,
    val lastReviewedTimestamp: Long = 0L,
    val isBookmarked: Boolean = false
)
