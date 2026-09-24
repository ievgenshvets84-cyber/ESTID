package com.example.data.vocabulary

import android.content.Context
import com.example.data.db.AppDatabase
import com.example.data.db.VocabularyDao
import com.example.data.db.VocabularyWordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class VocabularyRepository(
    private val vocabularyDao: VocabularyDao,
    private val context: Context? = null
) {
    companion object {
        @Volatile
        private var INSTANCE: VocabularyRepository? = null

        fun getInstance(context: Context): VocabularyRepository {
            return INSTANCE ?: synchronized(this) {
                val db = AppDatabase.getDatabase(context)
                val repo = VocabularyRepository(db.vocabularyDao(), context.applicationContext)
                INSTANCE = repo
                repo
            }
        }
    }

    suspend fun initializeLanguageIfNeeded(languageCode: String) = withContext(Dispatchers.IO) {
        val prefs = context?.getSharedPreferences("vocab_prefs", Context.MODE_PRIVATE)
        val currentVer = prefs?.getInt("vocab_seed_version_$languageCode", 0) ?: 0
        val targetVer = 6
        val count = vocabularyDao.getWordCount(languageCode)

        if (currentVer < targetVer || count < 500) {
            vocabularyDao.clearLanguage(languageCode)
            // Seed initial 500 words with pristine, distinct, CEFR-graded vocabulary
            val stageWords = VocabularyDataGenerator.generateWordsForRange(languageCode, 1, 500)
            vocabularyDao.insertWords(stageWords)
            prefs?.edit()?.putInt("vocab_seed_version_$languageCode", targetVer)?.apply()
        }
    }

    suspend fun getWordByRank(languageCode: String, rank: Int): VocabularyWordEntity? = withContext(Dispatchers.IO) {
        val existing = vocabularyDao.getWordByRank(languageCode, rank)
        if (existing != null) {
            existing
        } else {
            val generated = VocabularyDataGenerator.generateWord(languageCode, rank)
            vocabularyDao.insertWord(generated)
            generated
        }
    }

    fun getVerbsForLanguage(languageCode: String): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getVerbsForLanguage(languageCode)
    }

    fun searchVerbs(languageCode: String, query: String): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.searchVerbs(languageCode, query)
    }

    suspend fun loadStageWords(languageCode: String, startRank: Int, count: Int = 100) = withContext(Dispatchers.IO) {
        val words = VocabularyDataGenerator.generateWordsForRange(languageCode, startRank, count)
        vocabularyDao.insertWords(words)
    }

    fun getAllWords(languageCode: String): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getAllWordsForLanguage(languageCode)
    }

    fun getWordsByTier(languageCode: String, tier: Int): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getWordsByTier(languageCode, tier)
    }

    fun getWordsByRankRange(languageCode: String, startRank: Int, endRank: Int): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getWordsByRankRange(languageCode, startRank, endRank)
    }

    fun searchWords(languageCode: String, query: String): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.searchWords(languageCode, query)
    }

    fun getBookmarkedWords(languageCode: String): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getBookmarkedWords(languageCode)
    }

    fun getWordsByMastery(languageCode: String, masteryLevel: Int): Flow<List<VocabularyWordEntity>> {
        return vocabularyDao.getWordsByMastery(languageCode, masteryLevel)
    }

    fun getWordCountFlow(languageCode: String): Flow<Int> {
        return vocabularyDao.getWordCountFlow(languageCode)
    }

    fun getMasteredCountFlow(languageCode: String): Flow<Int> {
        return vocabularyDao.getMasteredCountFlow(languageCode)
    }

    fun getLearningCountFlow(languageCode: String): Flow<Int> {
        return vocabularyDao.getLearningCountFlow(languageCode)
    }

    suspend fun updateMastery(id: Long, masteryLevel: Int) = withContext(Dispatchers.IO) {
        vocabularyDao.updateMastery(id, masteryLevel, System.currentTimeMillis())
    }

    suspend fun toggleBookmark(id: Long, isBookmarked: Boolean) = withContext(Dispatchers.IO) {
        vocabularyDao.updateBookmark(id, !isBookmarked)
    }

    suspend fun insertCustomWord(word: VocabularyWordEntity) = withContext(Dispatchers.IO) {
        vocabularyDao.insertWord(word)
    }

    suspend fun getRandomQuizWords(languageCode: String, count: Int = 10): List<VocabularyWordEntity> = withContext(Dispatchers.IO) {
        val words = vocabularyDao.getRandomQuizWords(languageCode, count)
        if (words.size < count) {
            // Seed a batch if not enough words in DB yet
            loadStageWords(languageCode, 1, 100)
            vocabularyDao.getRandomQuizWords(languageCode, count)
        } else {
            words
        }
    }
}
