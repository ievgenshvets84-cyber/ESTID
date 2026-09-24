import sys
import os
sys.path.append("scripts")
from vocab_a1 import A1_WORDS
from vocab_a2 import A2_WORDS
from vocab_b1 import B1_WORDS
from vocab_b2_c1_c2 import B2_WORDS, C1_WORDS, C2_WORDS
from vocab_expansion import EXPANSION_WORDS
from vocab_500_final import FINAL_WORDS

all_words = []
seen = set()
for w_list in [A1_WORDS, A2_WORDS, B1_WORDS, B2_WORDS, C1_WORDS, C2_WORDS, EXPANSION_WORDS, FINAL_WORDS]:
    for item in w_list:
        k = item["key"].strip().lower()
        if k not in seen:
            seen.add(k)
            all_words.append(item)

print("Consolidated total unique words:", len(all_words))

encoded_lines = []
for item in all_words:
    k = item["key"].strip()
    pos = item.get("pos", "noun")
    cefr = item.get("cefr", "A1")
    cat = item.get("cat", "General")
    trans_pairs = [f"{lang}:{val.strip()}" for lang, val in sorted(item["trans"].items())]
    trans_str = ";".join(trans_pairs)
    encoded = f"{k}|{pos}|{cefr}|{cat}|{trans_str}"
    encoded_lines.append(encoded)

# Split into chunks of 100 to keep multiline string literals well within JVM 65535 UTF8 constant limit
chunks = []
chunk_size = 100
for i in range(0, len(encoded_lines), chunk_size):
    chunks.append(encoded_lines[i:i+chunk_size])

lexicon_kt = []
lexicon_kt.append("package com.example.data.vocabulary\n")
lexicon_kt.append("object VocabularyMultilingualLexicon {")
lexicon_kt.append("    data class LexiconEntry(")
lexicon_kt.append("        val key: String,")
lexicon_kt.append("        val pos: String,")
lexicon_kt.append("        val cefr: String,")
lexicon_kt.append("        val cat: String,")
lexicon_kt.append("        val translations: Map<String, String>")
lexicon_kt.append("    )\n")

lexicon_kt.append("    val rawEntries: List<LexiconEntry> by lazy {")
lexicon_kt.append("        RAW_CHUNKS.flatMap { chunk ->")
lexicon_kt.append('            chunk.trim().lines().filter { it.isNotBlank() }.map { line ->')
lexicon_kt.append("                val parts = line.split('|')")
lexicon_kt.append("                val key = parts[0]")
lexicon_kt.append('                val pos = parts.getOrElse(1) { "noun" }')
lexicon_kt.append('                val cefr = parts.getOrElse(2) { "A1" }')
lexicon_kt.append('                val cat = parts.getOrElse(3) { "General" }')
lexicon_kt.append('                val transPairs = parts.getOrElse(4) { "" }.split(\';\')')
lexicon_kt.append("                val transMap = mutableMapOf<String, String>()")
lexicon_kt.append("                for (p in transPairs) {")
lexicon_kt.append("                    val colonIdx = p.indexOf(':')")
lexicon_kt.append("                    if (colonIdx > 0) {")
lexicon_kt.append("                        val lang = p.substring(0, colonIdx)")
lexicon_kt.append("                        val value = p.substring(colonIdx + 1)")
lexicon_kt.append("                        transMap[lang] = value")
lexicon_kt.append("                    }")
lexicon_kt.append("                }")
lexicon_kt.append("                LexiconEntry(key, pos, cefr, cat, transMap)")
lexicon_kt.append("            }")
lexicon_kt.append("        }")
lexicon_kt.append("    }\n")

lexicon_kt.append("    val entries: Map<String, Map<String, String>> by lazy {")
lexicon_kt.append("        rawEntries.associate { it.key.lowercase() to it.translations }")
lexicon_kt.append("    }\n")

for idx, chunk in enumerate(chunks):
    lexicon_kt.append(f"    private const val CHUNK_{idx} = \"\"\"")
    for line in chunk:
        # escape dollar sign in multiline string
        escaped_line = line.replace("$", "\\$")
        lexicon_kt.append(escaped_line)
    lexicon_kt.append("\"\"\"\n")

chunk_names = [f"CHUNK_{idx}" for idx in range(len(chunks))]
lexicon_kt.append(f"    private val RAW_CHUNKS = listOf({', '.join(chunk_names)})\n")
lexicon_kt.append("}\n")

with open("app/src/main/java/com/example/data/vocabulary/VocabularyMultilingualLexicon.kt", "w") as f:
    f.write("\n".join(lexicon_kt))

print("Wrote app/src/main/java/com/example/data/vocabulary/VocabularyMultilingualLexicon.kt")

# Now write ExtendedLexiconGenerator.kt which simply consumes VocabularyMultilingualLexicon.rawEntries
generator_kt = """package com.example.data.vocabulary

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
"""

with open("app/src/main/java/com/example/data/vocabulary/ExtendedLexiconGenerator.kt", "w") as f:
    f.write(generator_kt)

print("Wrote app/src/main/java/com/example/data/vocabulary/ExtendedLexiconGenerator.kt")
