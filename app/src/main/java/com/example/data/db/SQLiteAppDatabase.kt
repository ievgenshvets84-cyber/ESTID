package com.example.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

open class SQLiteAppDatabase(context: Context) : AppDatabase() {

    private val openHelper = DatabaseHelper(context.applicationContext)
    private val dao = SQLiteVocabularyDao(openHelper)

    override fun vocabularyDao(): VocabularyDao = dao

    private class DatabaseHelper(context: Context) : SQLiteOpenHelper(
        context,
        "lingua_vocabulary_database.db",
        null,
        3
    ) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS vocabulary_words (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    languageCode TEXT NOT NULL,
                    frequencyRank INTEGER NOT NULL,
                    word TEXT NOT NULL,
                    translation TEXT NOT NULL,
                    phonetic TEXT NOT NULL DEFAULT '',
                    partOfSpeech TEXT NOT NULL DEFAULT 'noun',
                    category TEXT NOT NULL DEFAULT 'General',
                    tier INTEGER NOT NULL DEFAULT 1,
                    cefrLevel TEXT NOT NULL DEFAULT 'A1',
                    exampleSentence TEXT NOT NULL DEFAULT '',
                    exampleTranslation TEXT NOT NULL DEFAULT '',
                    masteryLevel INTEGER NOT NULL DEFAULT 0,
                    timesReviewed INTEGER NOT NULL DEFAULT 0,
                    lastReviewedTimestamp INTEGER NOT NULL DEFAULT 0,
                    isBookmarked INTEGER NOT NULL DEFAULT 0,
                    UNIQUE(languageCode, frequencyRank) ON CONFLICT REPLACE
                );
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_lang_rank ON vocabulary_words (languageCode, frequencyRank);")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_lang_tier ON vocabulary_words (languageCode, tier);")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_lang_pos ON vocabulary_words (languageCode, partOfSpeech);")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_lang_word ON vocabulary_words (languageCode, word);")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS vocabulary_words;")
            onCreate(db)
        }
    }

    private class SQLiteVocabularyDao(
        private val helper: DatabaseHelper
    ) : VocabularyDao {

        private val changeNotifier = MutableStateFlow(0L)

        private fun notifyChange() {
            changeNotifier.update { it + 1 }
        }

        private fun Cursor.toEntity(): VocabularyWordEntity {
            return VocabularyWordEntity(
                id = getLong(getColumnIndexOrThrow("id")),
                languageCode = getString(getColumnIndexOrThrow("languageCode")),
                frequencyRank = getInt(getColumnIndexOrThrow("frequencyRank")),
                word = getString(getColumnIndexOrThrow("word")),
                translation = getString(getColumnIndexOrThrow("translation")),
                phonetic = getString(getColumnIndexOrThrow("phonetic")),
                partOfSpeech = getString(getColumnIndexOrThrow("partOfSpeech")),
                category = getString(getColumnIndexOrThrow("category")),
                tier = getInt(getColumnIndexOrThrow("tier")),
                cefrLevel = getString(getColumnIndexOrThrow("cefrLevel")),
                exampleSentence = getString(getColumnIndexOrThrow("exampleSentence")),
                exampleTranslation = getString(getColumnIndexOrThrow("exampleTranslation")),
                masteryLevel = getInt(getColumnIndexOrThrow("masteryLevel")),
                timesReviewed = getInt(getColumnIndexOrThrow("timesReviewed")),
                lastReviewedTimestamp = getLong(getColumnIndexOrThrow("lastReviewedTimestamp")),
                isBookmarked = getInt(getColumnIndexOrThrow("isBookmarked")) == 1
            )
        }

        private fun createContentValues(word: VocabularyWordEntity): ContentValues {
            return ContentValues().apply {
                if (word.id > 0) put("id", word.id)
                put("languageCode", word.languageCode)
                put("frequencyRank", word.frequencyRank)
                put("word", word.word)
                put("translation", word.translation)
                put("phonetic", word.phonetic)
                put("partOfSpeech", word.partOfSpeech)
                put("category", word.category)
                put("tier", word.tier)
                put("cefrLevel", word.cefrLevel)
                put("exampleSentence", word.exampleSentence)
                put("exampleTranslation", word.exampleTranslation)
                put("masteryLevel", word.masteryLevel)
                put("timesReviewed", word.timesReviewed)
                put("lastReviewedTimestamp", word.lastReviewedTimestamp)
                put("isBookmarked", if (word.isBookmarked) 1 else 0)
            }
        }

        private fun queryList(sql: String, args: Array<String>): List<VocabularyWordEntity> {
            val db = helper.readableDatabase
            val cursor = db.rawQuery(sql, args)
            val result = ArrayList<VocabularyWordEntity>(cursor.count)
            cursor.use {
                while (it.moveToNext()) {
                    result.add(it.toEntity())
                }
            }
            return result
        }

        private fun queryCount(sql: String, args: Array<String>): Int {
            val db = helper.readableDatabase
            val cursor = db.rawQuery(sql, args)
            var count = 0
            cursor.use {
                if (it.moveToFirst()) {
                    count = it.getInt(0)
                }
            }
            return count
        }

        override fun getAllWordsForLanguage(languageCode: String): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? ORDER BY frequencyRank ASC",
                        arrayOf(languageCode)
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getWordsByTier(languageCode: String, tier: Int): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND tier = ? ORDER BY frequencyRank ASC",
                        arrayOf(languageCode, tier.toString())
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getBookmarkedWords(languageCode: String): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND isBookmarked = 1 ORDER BY frequencyRank ASC",
                        arrayOf(languageCode)
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getWordsByMastery(languageCode: String, masteryLevel: Int): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND masteryLevel = ? ORDER BY frequencyRank ASC",
                        arrayOf(languageCode, masteryLevel.toString())
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun searchWords(languageCode: String, query: String): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    val p = "%$query%"
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND (word LIKE ? OR translation LIKE ?) ORDER BY frequencyRank ASC",
                        arrayOf(languageCode, p, p)
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getWordsByRankRange(languageCode: String, startRank: Int, endRank: Int): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND frequencyRank BETWEEN ? AND ? ORDER BY frequencyRank ASC",
                        arrayOf(languageCode, startRank.toString(), endRank.toString())
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override suspend fun getWordByRank(languageCode: String, rank: Int): VocabularyWordEntity? = withContext(Dispatchers.IO) {
            val list = queryList(
                "SELECT * FROM vocabulary_words WHERE languageCode = ? AND frequencyRank = ? LIMIT 1",
                arrayOf(languageCode, rank.toString())
            )
            list.firstOrNull()
        }

        override fun getVerbsForLanguage(languageCode: String): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND partOfSpeech = 'verb' ORDER BY frequencyRank ASC",
                        arrayOf(languageCode)
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun searchVerbs(languageCode: String, query: String): Flow<List<VocabularyWordEntity>> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    val p = "%$query%"
                    queryList(
                        "SELECT * FROM vocabulary_words WHERE languageCode = ? AND partOfSpeech = 'verb' AND (word LIKE ? OR translation LIKE ?) ORDER BY frequencyRank ASC",
                        arrayOf(languageCode, p, p)
                    )
                })
            }
        }.flowOn(Dispatchers.IO)

        override suspend fun getWordByText(languageCode: String, word: String): VocabularyWordEntity? = withContext(Dispatchers.IO) {
            val list = queryList(
                "SELECT * FROM vocabulary_words WHERE languageCode = ? AND word = ? LIMIT 1",
                arrayOf(languageCode, word)
            )
            list.firstOrNull()
        }

        override suspend fun getWordCount(languageCode: String): Int = withContext(Dispatchers.IO) {
            queryCount("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = ?", arrayOf(languageCode))
        }

        override fun getWordCountFlow(languageCode: String): Flow<Int> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryCount("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = ?", arrayOf(languageCode))
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getMasteredCountFlow(languageCode: String): Flow<Int> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryCount("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = ? AND masteryLevel >= 3", arrayOf(languageCode))
                })
            }
        }.flowOn(Dispatchers.IO)

        override fun getLearningCountFlow(languageCode: String): Flow<Int> = flow {
            changeNotifier.collect {
                emit(withContext(Dispatchers.IO) {
                    queryCount("SELECT COUNT(*) FROM vocabulary_words WHERE languageCode = ? AND masteryLevel BETWEEN 1 AND 2", arrayOf(languageCode))
                })
            }
        }.flowOn(Dispatchers.IO)

        override suspend fun insertWords(words: List<VocabularyWordEntity>): Unit = withContext(Dispatchers.IO) {
            if (words.isEmpty()) return@withContext
            val db = helper.writableDatabase
            db.beginTransaction()
            try {
                for (w in words) {
                    db.insertWithOnConflict("vocabulary_words", null, createContentValues(w), SQLiteDatabase.CONFLICT_REPLACE)
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
            notifyChange()
        }

        override suspend fun insertWord(word: VocabularyWordEntity): Unit = withContext(Dispatchers.IO) {
            val db = helper.writableDatabase
            db.insertWithOnConflict("vocabulary_words", null, createContentValues(word), SQLiteDatabase.CONFLICT_REPLACE)
            notifyChange()
        }

        override suspend fun updateWord(word: VocabularyWordEntity): Unit = withContext(Dispatchers.IO) {
            val db = helper.writableDatabase
            db.update("vocabulary_words", createContentValues(word), "id = ?", arrayOf(word.id.toString()))
            notifyChange()
        }

        override suspend fun updateMastery(id: Long, masteryLevel: Int, timestamp: Long): Unit = withContext(Dispatchers.IO) {
            val db = helper.writableDatabase
            db.execSQL(
                "UPDATE vocabulary_words SET masteryLevel = ?, timesReviewed = timesReviewed + 1, lastReviewedTimestamp = ? WHERE id = ?",
                arrayOf(masteryLevel.toString(), timestamp.toString(), id.toString())
            )
            notifyChange()
        }

        override suspend fun updateBookmark(id: Long, isBookmarked: Boolean): Unit = withContext(Dispatchers.IO) {
            val db = helper.writableDatabase
            db.execSQL(
                "UPDATE vocabulary_words SET isBookmarked = ? WHERE id = ?",
                arrayOf(if (isBookmarked) "1" else "0", id.toString())
            )
            notifyChange()
        }

        override suspend fun getRandomQuizWords(languageCode: String, limit: Int): List<VocabularyWordEntity> = withContext(Dispatchers.IO) {
            queryList(
                "SELECT * FROM vocabulary_words WHERE languageCode = ? ORDER BY RANDOM() LIMIT ?",
                arrayOf(languageCode, limit.toString())
            )
        }

        override suspend fun getRandomWordsByRankRange(
            languageCode: String,
            startRank: Int,
            endRank: Int,
            limit: Int
        ): List<VocabularyWordEntity> = withContext(Dispatchers.IO) {
            queryList(
                "SELECT * FROM vocabulary_words WHERE languageCode = ? AND frequencyRank BETWEEN ? AND ? ORDER BY RANDOM() LIMIT ?",
                arrayOf(languageCode, startRank.toString(), endRank.toString(), limit.toString())
            )
        }

        override suspend fun getWordsByRankRangeList(
            languageCode: String,
            startRank: Int,
            endRank: Int
        ): List<VocabularyWordEntity> = withContext(Dispatchers.IO) {
            queryList(
                "SELECT * FROM vocabulary_words WHERE languageCode = ? AND frequencyRank BETWEEN ? AND ? ORDER BY frequencyRank ASC",
                arrayOf(languageCode, startRank.toString(), endRank.toString())
            )
        }

        override suspend fun clearLanguage(languageCode: String): Unit = withContext(Dispatchers.IO) {
            val db = helper.writableDatabase
            db.execSQL("DELETE FROM vocabulary_words WHERE languageCode = ?", arrayOf(languageCode))
            notifyChange()
        }
    }
}
