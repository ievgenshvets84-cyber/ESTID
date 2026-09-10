package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode ORDER BY frequencyRank ASC")
    fun getAllWordsForLanguage(languageCode: String): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND tier = :tier ORDER BY frequencyRank ASC")
    fun getWordsByTier(languageCode: String, tier: Int): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND isBookmarked = 1 ORDER BY frequencyRank ASC")
    fun getBookmarkedWords(languageCode: String): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND masteryLevel = :masteryLevel ORDER BY frequencyRank ASC")
    fun getWordsByMastery(languageCode: String, masteryLevel: Int): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND (word LIKE '%' || :query || '%' OR translation LIKE '%' || :query || '%') ORDER BY frequencyRank ASC")
    fun searchWords(languageCode: String, query: String): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND frequencyRank BETWEEN :startRank AND :endRank ORDER BY frequencyRank ASC")
    fun getWordsByRankRange(languageCode: String, startRank: Int, endRank: Int): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND frequencyRank = :rank LIMIT 1")
    suspend fun getWordByRank(languageCode: String, rank: Int): VocabularyWordEntity?

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND partOfSpeech = 'verb' ORDER BY frequencyRank ASC")
    fun getVerbsForLanguage(languageCode: String): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND partOfSpeech = 'verb' AND (word LIKE '%' || :query || '%' OR translation LIKE '%' || :query || '%') ORDER BY frequencyRank ASC")
    fun searchVerbs(languageCode: String, query: String): Flow<List<VocabularyWordEntity>>

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode AND word = :word LIMIT 1")
    suspend fun getWordByText(languageCode: String, word: String): VocabularyWordEntity?

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = :languageCode")
    suspend fun getWordCount(languageCode: String): Int

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = :languageCode")
    fun getWordCountFlow(languageCode: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = :languageCode AND masteryLevel >= 3")
    fun getMasteredCountFlow(languageCode: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = :languageCode AND masteryLevel BETWEEN 1 AND 2")
    fun getLearningCountFlow(languageCode: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<VocabularyWordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: VocabularyWordEntity)

    @Update
    suspend fun updateWord(word: VocabularyWordEntity)

    @Query("UPDATE vocabulary_words SET masteryLevel = :masteryLevel, timesReviewed = timesReviewed + 1, lastReviewedTimestamp = :timestamp WHERE id = :id")
    suspend fun updateMastery(id: Long, masteryLevel: Int, timestamp: Long)

    @Query("UPDATE vocabulary_words SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Long, isBookmarked: Boolean)

    @Query("SELECT * FROM vocabulary_words WHERE languageCode = :languageCode ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomQuizWords(languageCode: String, limit: Int): List<VocabularyWordEntity>
}
