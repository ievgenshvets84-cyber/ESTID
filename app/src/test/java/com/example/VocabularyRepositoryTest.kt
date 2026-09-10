package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.db.VocabularyDao
import com.example.data.db.VocabularyWordEntity
import com.example.data.models.SupportedLanguages
import com.example.data.vocabulary.VocabularyDataGenerator
import com.example.data.vocabulary.VocabularyTiers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class VocabularyRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: VocabularyDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.vocabularyDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testVocabularyTiersDefinitions() {
        assertEquals(5, VocabularyTiers.size)
        assertEquals(1, VocabularyTiers[0].startRank)
        assertEquals(1000, VocabularyTiers[0].endRank)
        assertEquals(10000, VocabularyTiers[4].endRank)
    }

    @Test
    fun testWordGenerationForRanksUpTo10000() {
        val spanish = SupportedLanguages.first { it.code == "es" }

        // Test tier 1 (curated)
        val wordRank1 = VocabularyDataGenerator.generateWord(spanish, 1)
        assertNotNull(wordRank1)
        assertEquals(1, wordRank1.frequencyRank)
        assertEquals("A1", wordRank1.cefrLevel)

        // Test high rank tier 5 (up to 10,000)
        val wordRank10000 = VocabularyDataGenerator.generateWord(spanish, 10000)
        assertNotNull(wordRank10000)
        assertEquals(10000, wordRank10000.frequencyRank)
        assertEquals("C2", wordRank10000.cefrLevel)
        assertTrue(wordRank10000.word.isNotBlank())
        assertTrue(wordRank10000.translation.isNotBlank())
    }

    @Test
    fun testRoomVocabularyDaoOperations() = runBlocking {
        val word = VocabularyWordEntity(
            languageCode = "es",
            frequencyRank = 42,
            word = "tiempo",
            translation = "time / weather",
            phonetic = "tjem.po",
            partOfSpeech = "noun",
            exampleSentence = "No tengo mucho tiempo hoy.",
            exampleTranslation = "I do not have much time today.",
            cefrLevel = "A1",
            category = "Daily Life",
            masteryLevel = 0,
            isBookmarked = false
        )

        dao.insertWords(listOf(word))

        val fetchedWord = dao.getWordByRank("es", 42)
        assertNotNull(fetchedWord)
        assertEquals("tiempo", fetchedWord?.word)

        // Test update mastery
        dao.updateMastery(fetchedWord!!.id, 3, System.currentTimeMillis())
        val updated = dao.getWordByRank("es", 42)
        assertEquals(3, updated?.masteryLevel)

        // Test bookmark toggle
        dao.updateBookmark(updated!!.id, true)
        val bookmarked = dao.getBookmarkedWords("es").first()
        assertEquals(1, bookmarked.size)
        assertEquals("tiempo", bookmarked[0].word)

        // Test search
        val searchResult = dao.searchWords("es", "tiem").first()
        assertEquals(1, searchResult.size)
        assertEquals("tiempo", searchResult[0].word)
    }
}
