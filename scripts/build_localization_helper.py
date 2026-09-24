import sys
import os

helper_content = """package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

object VocabularyLocalizationHelper {

    // Reverse lookup: (langCode, lowercase word) -> conceptKey
    private val wordToConceptMap: Map<String, String> by lazy {
        val map = mutableMapOf<String, String>()
        for ((conceptKey, transMap) in VocabularyMultilingualLexicon.entries) {
            for ((lang, word) in transMap) {
                val cleanWord = word.lowercase().trim()
                map["${lang}_$cleanWord"] = conceptKey
                // Also map clean word without article if German
                if (lang == "de") {
                    val stripped = cleanWord.removePrefix("der ").removePrefix("die ").removePrefix("das ").trim()
                    map["${lang}_$stripped"] = conceptKey
                }
                // Also map clean word without article if Spanish/French/Italian/Portuguese
                if (lang in listOf("es", "fr", "it", "pt")) {
                    val stripped = cleanWord.removePrefix("el ").removePrefix("la ").removePrefix("los ").removePrefix("las ")
                        .removePrefix("le ").removePrefix("l'").removePrefix("les ")
                        .removePrefix("il ").removePrefix("lo ").removePrefix("i ").removePrefix("gli ")
                        .removePrefix("o ").removePrefix("a ").removePrefix("os ").removePrefix("as ").trim()
                    map["${lang}_$stripped"] = conceptKey
                }
            }
        }
        map
    }

    fun getLocalizedTranslation(entity: VocabularyWordEntity, nativeLangCode: String): String {
        val nLang = nativeLangCode.lowercase()
        val targetLang = entity.languageCode.lowercase()

        // If target word is already in user native language
        if (targetLang == nLang) {
            return entity.word
        }

        // English requested
        if (nLang == "en") {
            return entity.translation
        }

        val rawTranslation = entity.translation.trim()
        val lowerTrans = rawTranslation.lowercase()

        // 1. Direct match in Multilingual Lexicon
        VocabularyMultilingualLexicon.entries[lowerTrans]?.get(nLang)?.let { return it }

        // 2. Reverse match via word and language code
        val cleanWord = entity.word.lowercase().trim()
        val conceptFromWord = wordToConceptMap["${targetLang}_$cleanWord"]
        if (conceptFromWord != null) {
            VocabularyMultilingualLexicon.entries[conceptFromWord]?.get(nLang)?.let { return it }
        }

        // 3. Match without verb prefix "to "
        if (lowerTrans.startsWith("to ")) {
            val base = lowerTrans.removePrefix("to ").trim()
            VocabularyMultilingualLexicon.entries[base]?.get(nLang)?.let { return it }
        }

        // 4. Split by slash
        if (rawTranslation.contains(" / ")) {
            val parts = rawTranslation.split(" / ")
            val validLocalized = mutableListOf<String>()
            for (p in parts) {
                val pt = p.trim().lowercase()
                val loc = VocabularyMultilingualLexicon.entries[pt]?.get(nLang)
                    ?: VocabularyMultilingualLexicon.entries["to $pt"]?.get(nLang)
                if (loc != null && loc.isNotBlank()) {
                    validLocalized.add(loc)
                }
            }
            if (validLocalized.isNotEmpty()) {
                return validLocalized.distinct().joinToString(" / ")
            }
        }

        // 5. If translation contains spaces (e.g. "Action Verbs" or phrases)
        for ((concept, trans) in VocabularyMultilingualLexicon.entries) {
            if (concept.equals(lowerTrans, ignoreCase = true) || lowerTrans.contains(concept)) {
                trans[nLang]?.let { return it }
            }
        }

        // 6. Strict Fallback: NEVER return English if user chose another native language!
        // Return word itself or localized fallback
        return when (nLang) {
            "de" -> "Wortschatz: ${entity.word}"
            "ru" -> "Слово: ${entity.word}"
            "uk" -> "Слово: ${entity.word}"
            "es" -> "Palabra: ${entity.word}"
            "fr" -> "Mot : ${entity.word}"
            "it" -> "Parola: ${entity.word}"
            "pt" -> "Palavra: ${entity.word}"
            "zh" -> "词汇：${entity.word}"
            "ja" -> "単語：${entity.word}"
            "ko" -> "단어: ${entity.word}"
            else -> entity.translation
        }
    }

    fun getLocalizedExampleTranslation(entity: VocabularyWordEntity, nativeLangCode: String): String {
        val nLang = nativeLangCode.lowercase()
        val targetLang = entity.languageCode.lowercase()

        if (targetLang == nLang) {
            return entity.exampleSentence
        }

        if (nLang == "en") {
            return entity.exampleTranslation
        }

        val rawEx = entity.exampleTranslation.trim()
        if (rawEx.isBlank()) return ""

        // 1. Curated sentences map
        VocabularySentencesLocalization.curatedSentences[rawEx]?.get(nLang)?.let { return it }

        // 2. Pattern: "We practice with ... every day."
        val wordTranslation = getLocalizedTranslation(entity, nLang)
        if (rawEx.startsWith("We practice with ") && rawEx.endsWith(" every day.")) {
            return when (nLang) {
                "de" -> "Wir üben jeden Tag mit $wordTranslation."
                "ru" -> "Мы практикуем $wordTranslation каждый день."
                "uk" -> "Ми практикуємо $wordTranslation щодня."
                "es" -> "Practicamos con $wordTranslation todos los días."
                "fr" -> "Nous pratiquons avec $wordTranslation chaque jour."
                "it" -> "Pratichiamo con $wordTranslation ogni giorno."
                "pt" -> "Praticamos com $wordTranslation todos os dias."
                "zh" -> "我们每天练习$wordTranslation。"
                "ja" -> "私たちは毎日${wordTranslation}を練習します。"
                "ko" -> "우리는 매일 ${wordTranslation}을(를) 연습합니다."
                else -> rawEx
            }
        }

        // 3. Sentence Pattern Translator
        VocabularySentencesLocalization.translateSentencePattern(rawEx, nLang, wordTranslation)?.let {
            return it
        }

        // 4. Strict Fallback: NEVER return English if user chose another native language!
        return when (nLang) {
            "de" -> "Beispielsatz mit: $wordTranslation"
            "ru" -> "Пример предложения с: $wordTranslation"
            "uk" -> "Приклад речення з: $wordTranslation"
            "es" -> "Ejemplo de frase con: $wordTranslation"
            "fr" -> "Exemple de phrase avec : $wordTranslation"
            "it" -> "Frase di esempio con: $wordTranslation"
            "pt" -> "Frase de exemplo com: $wordTranslation"
            "zh" -> "例句参考：$wordTranslation"
            "ja" -> "例文：$wordTranslation"
            "ko" -> "예문: $wordTranslation"
            else -> rawEx
        }
    }

    fun localizeWord(entity: VocabularyWordEntity, nativeLangCode: String): VocabularyWordEntity {
        val trans = getLocalizedTranslation(entity, nativeLangCode)
        val exTrans = getLocalizedExampleTranslation(entity, nativeLangCode)

        return entity.copy(
            translation = trans,
            exampleTranslation = exTrans
        )
    }

    fun localizeWords(words: List<VocabularyWordEntity>, nativeLangCode: String): List<VocabularyWordEntity> {
        return words.map { localizeWord(it, nativeLangCode) }
    }
}
"""

with open("app/src/main/java/com/example/data/vocabulary/VocabularyLocalizationHelper.kt", "w") as f:
    f.write(helper_content)

print("VocabularyLocalizationHelper.kt updated successfully!")
