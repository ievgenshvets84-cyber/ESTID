package com.example.data.db

import android.content.Context
import androidx.room.Database

@Database(
    entities = [VocabularyWordEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase {

    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = SQLiteAppDatabase(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
