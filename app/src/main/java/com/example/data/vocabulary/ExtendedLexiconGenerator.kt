package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

object ExtendedLexiconGenerator {

    fun generateUniqueWord(languageCode: String, rank: Int, tier: Int, cefr: String, category: String): VocabularyWordEntity {
        val items = VocabularyMultilingualLexicon.rawEntries
        val count = items.size
        val idx = ((rank - 1) % count + count) % count
        val item = items[idx]
        
        val actualWord = item.translations[languageCode] 
            ?: item.translations["en"] 
            ?: item.key
            
        val actualCefr = when {
            rank <= 100 -> "A1"
            rank <= 200 -> "A2"
            rank <= 300 -> "B1"
            rank <= 400 -> "B2"
            rank <= 450 -> "C1"
            rank <= 500 -> "C2"
            else -> cefr.ifBlank { "B1" }
        }
        
        val actualTier = ((rank - 1) / 100) + 1
        
        val phonetic = when (languageCode) {
            "es" -> "/$actualWord/"
            "fr" -> "/$actualWord/"
            "de" -> "/$actualWord/"
            "it" -> "/$actualWord/"
            "pt" -> "/$actualWord/"
            "ru" -> "/$actualWord/"
            "uk" -> "/$actualWord/"
            "zh" -> "pīnyīn"
            "ja" -> "かな"
            "ko" -> "발음"
            else -> "/$actualWord/"
        }
        
        val (exampleSentence, exampleTranslation) = when (languageCode) {
            "es" -> Pair("Practicamos con $actualWord todos los días.", "We practice with ${item.key} every day.")
            "fr" -> Pair("Nous pratiquons avec $actualWord chaque jour.", "We practice with ${item.key} every day.")
            "de" -> Pair("Wir üben $actualWord jeden Tag.", "We practice with ${item.key} every day.")
            "it" -> Pair("Pratichiamo con $actualWord ogni giorno.", "We practice with ${item.key} every day.")
            "pt" -> Pair("Praticamos com $actualWord todos os dias.", "We practice with ${item.key} every day.")
            "ru" -> Pair("Мы практикуем $actualWord каждый день.", "We practice with ${item.key} every day.")
            "uk" -> Pair("Ми практикуємо $actualWord щодня.", "We practice with ${item.key} every day.")
            "zh" -> Pair("我们每天学习$actualWord。", "We practice with ${item.key} every day.")
            "ja" -> Pair("私たちは毎日${actualWord}を練習します。", "We practice with ${item.key} every day.")
            "ko" -> Pair("우리는 매일 ${actualWord}을(를) 연습합니다.", "We practice with ${item.key} every day.")
            else -> Pair("We practice with $actualWord every day.", "We practice with ${item.key} every day.")
        }
        
        return VocabularyWordEntity(
            languageCode = languageCode,
            frequencyRank = rank,
            word = actualWord,
            translation = item.key,
            phonetic = phonetic,
            partOfSpeech = item.pos,
            category = item.cat,
            tier = actualTier,
            cefrLevel = actualCefr,
            exampleSentence = exampleSentence,
            exampleTranslation = exampleTranslation
        )
    }
}
