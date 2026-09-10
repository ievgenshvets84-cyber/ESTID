package com.example.data.vocabulary

/**
 * Represents a single conjugated form of a verb for a specific person/subject.
 */
data class ConjugatedForm(
    val pronoun: String,            // e.g., "yo", "je", "ich", "I", "私"
    val form: String,               // e.g., "hablo", "parle", "spreche", "speak"
    val phonetic: String = "",      // e.g., "AH-bloh"
    val translation: String = "",   // e.g., "I speak / am speaking"
    val exampleSentence: String = ""// e.g., "Yo hablo español todos los días."
)

/**
 * Represents a tense or mood group with all its conjugations.
 */
data class TenseConjugation(
    val tenseName: String,          // e.g., "Present (Presente)", "Preterite (Indefinido)"
    val tenseCategory: String,      // "Indicative", "Subjunctive", "Imperative", "Compound"
    val description: String,        // Usage explanation
    val forms: List<ConjugatedForm>
)

/**
 * Complete conjugation table for a verb, containing all using forms across tenses.
 */
data class VerbTableData(
    val infinitive: String,
    val languageCode: String,
    val englishMeaning: String,
    val regularType: String,        // e.g., "-ar regular", "Stem-changing (e->ie)", "Irregular"
    val auxiliaryVerb: String,      // e.g., "haber", "avoir", "haben", "have"
    val gerund: String,             // Present Participle / Gerund
    val gerundPhonetic: String = "",
    val pastParticiple: String,     // Past Participle
    val pastParticiplePhonetic: String = "",
    val tenses: List<TenseConjugation>
)
