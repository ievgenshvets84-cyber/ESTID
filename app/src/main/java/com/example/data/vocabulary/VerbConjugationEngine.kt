package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

object VerbConjugationEngine {

    /**
     * Generates a complete verb table with all using forms for the given word.
     */
    fun getConjugationTable(word: VocabularyWordEntity): VerbTableData {
        val infinitive = cleanInfinitive(word.word)
        val meaning = word.translation.split(" / ").firstOrNull() ?: word.translation

        return when (word.languageCode) {
            "es" -> generateSpanishConjugations(infinitive, meaning)
            "fr" -> generateFrenchConjugations(infinitive, meaning)
            "de" -> generateGermanConjugations(infinitive, meaning)
            "it" -> generateItalianConjugations(infinitive, meaning)
            "pt" -> generatePortugueseConjugations(infinitive, meaning)
            "ja" -> generateJapaneseConjugations(infinitive, meaning)
            "zh" -> generateChineseConjugations(infinitive, meaning)
            "ko" -> generateKoreanConjugations(infinitive, meaning)
            "uk" -> generateUkrainianConjugations(infinitive, meaning)
            "ru" -> generateRussianConjugations(infinitive, meaning)
            else -> generateEnglishConjugations(infinitive, meaning)
        }
    }

    private fun cleanInfinitive(raw: String): String {
        return raw.trim()
            .replace("to ", "", ignoreCase = true)
            .replace("die ", "", ignoreCase = true)
            .replace("der ", "", ignoreCase = true)
            .replace("das ", "", ignoreCase = true)
            .replace("el / la ", "", ignoreCase = true)
            .replace("el ", "", ignoreCase = true)
            .replace("la ", "", ignoreCase = true)
    }

    // ==========================================
    // SPANISH CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateSpanishConjugations(verb: String, meaning: String): VerbTableData {
        val isIrregular = isSpanishIrregular(verb)
        val ending = when {
            verb.endsWith("ar") -> "ar"
            verb.endsWith("er") -> "er"
            verb.endsWith("ir") -> "ir"
            else -> "ar"
        }
        val stem = if (verb.length > 2) verb.dropLast(2) else verb

        val gerund = when {
            verb == "ir" -> "yendo"
            verb == "decir" -> "diciendo"
            verb == "dormir" -> "durmiendo"
            verb == "poder" -> "pudiendo"
            verb == "leer" -> "leyendo"
            ending == "ar" -> "${stem}ando"
            else -> "${stem}iendo"
        }

        val participle = when {
            verb == "hacer" -> "hecho"
            verb == "decir" -> "dicho"
            verb == "ver" -> "visto"
            verb == "escribir" -> "escrito"
            verb == "abrir" -> "abierto"
            verb == "poner" -> "puesto"
            ending == "ar" -> "${stem}ado"
            else -> "${stem}ido"
        }

        val pronouns = listOf("yo", "tú", "él / ella / usted", "nosotros", "vosotros", "ellos / ellas / ustedes")

        // 1. Present
        val presentForms = getSpanishPresent(verb, stem, ending)
        val presentTense = TenseConjugation(
            tenseName = "Present (Presente)",
            tenseCategory = "Indicative",
            description = "Expresses current actions, facts, routines, and universal truths.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = presentForms.getOrElse(idx) { "${stem}a" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = spanishPresentTranslation(p, meaning),
                    exampleSentence = "$p $f en esta situación."
                )
            }
        )

        // 2. Preterite (Past Simple)
        val pretForms = getSpanishPreterite(verb, stem, ending)
        val preteriteTense = TenseConjugation(
            tenseName = "Preterite / Past (Pretérito Indefinido)",
            tenseCategory = "Indicative",
            description = "Completed actions in the past with a definite beginning and end.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = pretForms.getOrElse(idx) { "${stem}ó" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = spanishPastTranslation(p, meaning),
                    exampleSentence = "Ayer, $p $f con mucho entusiasmo."
                )
            }
        )

        // 3. Imperfect (Pretérito Imperfecto)
        val impForms = getSpanishImperfect(verb, stem, ending)
        val imperfectTense = TenseConjugation(
            tenseName = "Imperfect (Pretérito Imperfecto)",
            tenseCategory = "Indicative",
            description = "Ongoing, habitual, or background past actions ('used to' / 'was doing').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = impForms.getOrElse(idx) { "${stem}aba" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = "$p used to ${meaning.removePrefix("to ")}",
                    exampleSentence = "Cuando era joven, $p $f a menudo."
                )
            }
        )

        // 4. Future (Futuro Simple)
        val futForms = getSpanishFuture(verb)
        val futureTense = TenseConjugation(
            tenseName = "Future (Futuro Simple)",
            tenseCategory = "Indicative",
            description = "Actions that will occur in the future or expressions of probability.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = futForms.getOrElse(idx) { "${verb}á" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = "$p will ${meaning.removePrefix("to ")}",
                    exampleSentence = "Mañana, $p $f sin falta."
                )
            }
        )

        // 5. Conditional (Condicional Simple)
        val condForms = getSpanishConditional(verb)
        val conditionalTense = TenseConjugation(
            tenseName = "Conditional (Condicional)",
            tenseCategory = "Indicative",
            description = "Hypothetical actions, polite requests, and future in the past ('would do').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = condForms.getOrElse(idx) { "${verb}ía" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = "$p would ${meaning.removePrefix("to ")}",
                    exampleSentence = "Si fuera posible, $p $f con gusto."
                )
            }
        )

        // 6. Subjunctive Present (Presente de Subjuntivo)
        val subjForms = getSpanishSubjunctivePresent(verb, stem, ending)
        val subjunctiveTense = TenseConjugation(
            tenseName = "Present Subjunctive (Subjuntivo Presente)",
            tenseCategory = "Subjunctive",
            description = "Expresses wishes, doubts, emotions, hypothetical conditions, and recommendations.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = subjForms.getOrElse(idx) { "${stem}e" }
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = "(that) $p ${meaning.removePrefix("to ")}",
                    exampleSentence = "Espero que $p $f hoy."
                )
            }
        )

        // 7. Imperative (Commands / Imperativo)
        val impTense = TenseConjugation(
            tenseName = "Imperative (Imperativo)",
            tenseCategory = "Imperative",
            description = "Direct commands, instructions, and requests.",
            forms = listOf(
                ConjugatedForm("tú (affirmative)", getSpanishImperativeTu(verb, stem, ending), translation = "Do ${meaning.removePrefix("to ")}!", exampleSentence = "¡$verb rápido!"),
                ConjugatedForm("tú (negative)", "no ${subjForms[1]}", translation = "Don't ${meaning.removePrefix("to ")}!", exampleSentence = "¡No ${subjForms[1]} todavía!"),
                ConjugatedForm("usted", subjForms[2], translation = "Please ${meaning.removePrefix("to ")}", exampleSentence = "Por favor, ${subjForms[2]} aquí."),
                ConjugatedForm("nosotros", subjForms[3], translation = "Let's ${meaning.removePrefix("to ")}!", exampleSentence = "¡${subjForms[3]} juntos!"),
                ConjugatedForm("vosotros", if (verb.length > 2) verb.dropLast(1) + "d" else verb + "d", translation = "You all ${meaning.removePrefix("to ")}!", exampleSentence = "¡${verb.dropLast(1)}d ahora!"),
                ConjugatedForm("ustedes", subjForms[5], translation = "You all please ${meaning.removePrefix("to ")}", exampleSentence = "Por favor ustedes, ${subjForms[5]}.")
            )
        )

        // 8. Compound / Present Perfect (Pretérito Perfecto)
        val perfAux = listOf("he", "has", "ha", "hemos", "habéis", "han")
        val perfectTense = TenseConjugation(
            tenseName = "Present Perfect (Pretérito Perfecto Compuesto)",
            tenseCategory = "Compound",
            description = "Completed past actions connected to the present ('have done').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = "${perfAux[idx]} $participle"
                ConjugatedForm(
                    pronoun = p,
                    form = f,
                    phonetic = spanishPhonetic(f),
                    translation = "$p have ${meaning.removePrefix("to ")}",
                    exampleSentence = "Hoy, $p $f con éxito."
                )
            }
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "es",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Irregular" else "-$ending regular",
            auxiliaryVerb = "haber",
            gerund = gerund,
            gerundPhonetic = spanishPhonetic(gerund),
            pastParticiple = participle,
            pastParticiplePhonetic = spanishPhonetic(participle),
            tenses = listOf(presentTense, preteriteTense, imperfectTense, futureTense, conditionalTense, subjunctiveTense, impTense, perfectTense)
        )
    }

    private fun isSpanishIrregular(v: String): Boolean {
        return v in listOf("ser", "estar", "ir", "tener", "hacer", "decir", "poder", "ver", "dar", "saber", "querer", "poner", "salir", "venir", "haber")
    }

    private fun getSpanishPresent(v: String, stem: String, ending: String): List<String> {
        return when (v) {
            "ser" -> listOf("soy", "eres", "es", "somos", "sois", "son")
            "estar" -> listOf("estoy", "estás", "está", "estamos", "estáis", "están")
            "ir" -> listOf("voy", "vas", "va", "vamos", "vais", "van")
            "tener" -> listOf("tengo", "tienes", "tiene", "tenemos", "tenéis", "tienen")
            "hacer" -> listOf("hago", "haces", "hace", "hacemos", "hacéis", "hacen")
            "decir" -> listOf("digo", "dices", "dice", "decimos", "decís", "dicen")
            "poder" -> listOf("puedo", "puedes", "puede", "podemos", "podéis", "pueden")
            "ver" -> listOf("veo", "ves", "ve", "vemos", "veis", "ven")
            "dar" -> listOf("doy", "das", "da", "damos", "dais", "dan")
            "saber" -> listOf("sé", "sabes", "sabe", "sabemos", "sabéis", "saben")
            "querer" -> listOf("quiero", "quieres", "quiere", "queremos", "queréis", "quieren")
            "poner" -> listOf("pongo", "pones", "pone", "ponemos", "ponéis", "ponen")
            "salir" -> listOf("salgo", "sales", "sale", "salimos", "salís", "salen")
            "venir" -> listOf("vengo", "vienes", "viene", "venimos", "venís", "vienen")
            "haber" -> listOf("he", "has", "ha / hay", "hemos", "habéis", "han")
            else -> when (ending) {
                "ar" -> listOf("${stem}o", "${stem}as", "${stem}a", "${stem}amos", "${stem}áis", "${stem}an")
                "er" -> listOf("${stem}o", "${stem}es", "${stem}e", "${stem}emos", "${stem}éis", "${stem}en")
                else -> listOf("${stem}o", "${stem}es", "${stem}e", "${stem}imos", "${stem}ís", "${stem}en")
            }
        }
    }

    private fun getSpanishPreterite(v: String, stem: String, ending: String): List<String> {
        return when (v) {
            "ser", "ir" -> listOf("fui", "fuiste", "fue", "fuimos", "fuisteis", "fueron")
            "estar" -> listOf("estuve", "estuviste", "estuvo", "estuvimos", "estuvisteis", "estuvieron")
            "tener" -> listOf("tuve", "tuviste", "tuvo", "tuvimos", "tuvisteis", "tuvieron")
            "hacer" -> listOf("hice", "hiciste", "hizo", "hicimos", "hicisteis", "hicieron")
            "decir" -> listOf("dije", "dijiste", "dijo", "dijimos", "dijisteis", "dijeron")
            "poder" -> listOf("pude", "pudiste", "pudo", "pudimos", "pudisteis", "pudieron")
            "ver" -> listOf("vi", "viste", "vio", "vimos", "visteis", "vieron")
            "dar" -> listOf("di", "diste", "dio", "dimos", "disteis", "dieron")
            "saber" -> listOf("supe", "supiste", "supo", "supimos", "supisteis", "supieron")
            "querer" -> listOf("quise", "quisiste", "quiso", "quisimos", "quisisteis", "quisieron")
            "poner" -> listOf("puse", "pusiste", "puso", "pusimos", "pusisteis", "pusieron")
            "salir" -> listOf("salí", "saliste", "salió", "salimos", "salisteis", "salieron")
            "venir" -> listOf("vine", "viniste", "vino", "vinimos", "vinisteis", "vinieron")
            else -> when (ending) {
                "ar" -> listOf("${stem}é", "${stem}aste", "${stem}ó", "${stem}amos", "${stem}asteis", "${stem}aron")
                else -> listOf("${stem}í", "${stem}iste", "${stem}ió", "${stem}imos", "${stem}isteis", "${stem}ieron")
            }
        }
    }

    private fun getSpanishImperfect(v: String, stem: String, ending: String): List<String> {
        return when (v) {
            "ser" -> listOf("era", "eras", "era", "éramos", "erais", "eran")
            "ir" -> listOf("iba", "ibas", "iba", "íbamos", "ibais", "iban")
            "ver" -> listOf("veía", "veías", "veía", "veíamos", "veíais", "veían")
            else -> when (ending) {
                "ar" -> listOf("${stem}aba", "${stem}abas", "${stem}aba", "${stem}ábamos", "${stem}abais", "${stem}aban")
                else -> listOf("${stem}ía", "${stem}ías", "${stem}ía", "${stem}íamos", "${stem}íais", "${stem}ían")
            }
        }
    }

    private fun getSpanishFuture(v: String): List<String> {
        val base = when (v) {
            "tener" -> "tendr"
            "hacer" -> "har"
            "decir" -> "dir"
            "poder" -> "podr"
            "poner" -> "pondr"
            "salir" -> "saldr"
            "venir" -> "vendr"
            "saber" -> "sabr"
            "haber" -> "habr"
            "querer" -> "querr"
            else -> v
        }
        return listOf("${base}é", "${base}ás", "${base}á", "${base}emos", "${base}éis", "${base}án")
    }

    private fun getSpanishConditional(v: String): List<String> {
        val base = when (v) {
            "tener" -> "tendr"
            "hacer" -> "har"
            "decir" -> "dir"
            "poder" -> "podr"
            "poner" -> "pondr"
            "salir" -> "saldr"
            "venir" -> "vendr"
            "saber" -> "sabr"
            "haber" -> "habr"
            "querer" -> "querr"
            else -> v
        }
        return listOf("${base}ía", "${base}ías", "${base}ía", "${base}íamos", "${base}íais", "${base}ían")
    }

    private fun getSpanishSubjunctivePresent(v: String, stem: String, ending: String): List<String> {
        return when (v) {
            "ser" -> listOf("sea", "seas", "sea", "seamos", "seáis", "sean")
            "estar" -> listOf("esté", "estés", "esté", "estemos", "estéis", "estén")
            "ir" -> listOf("vaya", "vayas", "vaya", "vayamos", "vayáis", "vayan")
            "tener" -> listOf("tenga", "tengas", "tenga", "tengamos", "tengáis", "tengan")
            "hacer" -> listOf("haga", "hagas", "haga", "hagamos", "hagáis", "hagan")
            "decir" -> listOf("diga", "digas", "diga", "digamos", "digáis", "digan")
            "saber" -> listOf("sepa", "sepas", "sepa", "sepamos", "sepáis", "sepan")
            "dar" -> listOf("dé", "des", "dé", "demos", "deis", "den")
            else -> when (ending) {
                "ar" -> listOf("${stem}e", "${stem}es", "${stem}e", "${stem}emos", "${stem}éis", "${stem}en")
                else -> listOf("${stem}a", "${stem}as", "${stem}a", "${stem}amos", "${stem}áis", "${stem}an")
            }
        }
    }

    private fun getSpanishImperativeTu(v: String, stem: String, ending: String): String {
        return when (v) {
            "decir" -> "di"
            "hacer" -> "haz"
            "ir" -> "ve"
            "poner" -> "pon"
            "salir" -> "sal"
            "ser" -> "sé"
            "tener" -> "ten"
            "venir" -> "ven"
            else -> if (ending == "ar") "${stem}a" else "${stem}e"
        }
    }

    private fun spanishPresentTranslation(pronoun: String, meaning: String): String {
        val root = meaning.removePrefix("to ")
        return when (pronoun) {
            "yo" -> "I $root"
            "tú" -> "you $root"
            "él / ella / usted" -> "he/she ${root}s"
            "nosotros" -> "we $root"
            "vosotros" -> "you all $root"
            else -> "they $root"
        }
    }

    private fun spanishPastTranslation(pronoun: String, meaning: String): String {
        val root = meaning.removePrefix("to ")
        val past = if (root.endsWith("e")) "${root}d" else "${root}ed"
        return "$pronoun $past"
    }

    private fun spanishPhonetic(word: String): String {
        return word.replace("ll", "y")
            .replace("ñ", "ny")
            .replace("j", "h")
            .replace("que", "ke")
            .replace("qui", "kee")
            .replace("ci", "see")
            .replace("ce", "seh")
    }

    // ==========================================
    // GERMAN CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateGermanConjugations(verb: String, meaning: String): VerbTableData {
        val stem = if (verb.endsWith("en")) verb.dropLast(2) else if (verb.endsWith("n")) verb.dropLast(1) else verb
        val isIrregular = verb in listOf("sein", "haben", "werden", "können", "müssen", "wollen", "sollen", "dürfen", "wissen", "geben", "gehen", "kommen", "sehen", "sprechen", "nehmen")

        val pronouns = listOf("ich", "du", "er / sie / es", "wir", "ihr", "sie / Sie")

        val participle = when (verb) {
            "sein" -> "gewesen"
            "haben" -> "gehabt"
            "werden" -> "geworden"
            "gehen" -> "gegangen"
            "kommen" -> "gekommen"
            "sehen" -> "gesehen"
            "sprechen" -> "gesprochen"
            "nehmen" -> "genommen"
            "geben" -> "gegeben"
            "wissen" -> "gewusst"
            else -> if (verb.startsWith("be") || verb.startsWith("ver") || verb.startsWith("er") || verb.startsWith("ent")) "${stem}t" else "ge${stem}t"
        }

        val aux = if (verb in listOf("sein", "werden", "gehen", "kommen", "fahren", "reisen", "bleiben")) "sein" else "haben"

        // 1. Präsens (Present)
        val presForms = when (verb) {
            "sein" -> listOf("bin", "bist", "ist", "sind", "seid", "sind")
            "haben" -> listOf("habe", "hast", "hat", "haben", "habt", "haben")
            "werden" -> listOf("werde", "wirst", "wird", "werden", "werdet", "werden")
            "können" -> listOf("kann", "kannst", "kann", "können", "könnt", "können")
            "müssen" -> listOf("muss", "musst", "muss", "müssen", "müsst", "müssen")
            "wollen" -> listOf("will", "willst", "will", "wollen", "wollt", "wollen")
            "wissen" -> listOf("weiß", "weißt", "weiß", "wissen", "wisst", "wissen")
            "sehen" -> listOf("sehe", "siehst", "sieht", "sehen", "seht", "sehen")
            "sprechen" -> listOf("spreche", "sprichst", "spricht", "sprechen", "sprecht", "sprechen")
            "geben" -> listOf("gebe", "gibst", "gibt", "geben", "gebt", "geben")
            else -> listOf("${stem}e", "${stem}st", "${stem}t", "${stem}en", "${stem}t", "${stem}en")
        }

        val presentTense = TenseConjugation(
            tenseName = "Präsens (Present)",
            tenseCategory = "Indicative",
            description = "Describes actions happening now, universal statements, and planned future actions.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = presForms[idx]
                ConjugatedForm(p, f, translation = "$p ${meaning.removePrefix("to ")}", exampleSentence = "$p $f heute sehr gut.")
            }
        )

        // 2. Präteritum (Simple Past)
        val pretForms = when (verb) {
            "sein" -> listOf("war", "warst", "war", "waren", "wart", "waren")
            "haben" -> listOf("hatte", "hattest", "hatte", "hatten", "hattet", "hatten")
            "werden" -> listOf("wurde", "wurdest", "wurde", "wurden", "wurdet", "wurden")
            "können" -> listOf("konnte", "konntest", "konnte", "konnten", "konntet", "konnten")
            "müssen" -> listOf("musste", "musstest", "musste", "mussten", "musstet", "mussten")
            "gehen" -> listOf("ging", "gingst", "ging", "gingen", "gingt", "gingen")
            "kommen" -> listOf("kam", "kamst", "kam", "kamen", "kamt", "kamen")
            "sehen" -> listOf("sah", "sahst", "sah", "sahen", "saht", "sahen")
            else -> listOf("${stem}te", "${stem}test", "${stem}te", "${stem}ten", "${stem}tet", "${stem}ten")
        }

        val preteriteTense = TenseConjugation(
            tenseName = "Präteritum (Simple Past)",
            tenseCategory = "Indicative",
            description = "Written narrative past, storytelling, and formal reporting.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = pretForms[idx]
                ConjugatedForm(p, f, translation = "$p ${meaning.removePrefix("to ")} (past)", exampleSentence = "Gestern $f $p am Abend.")
            }
        )

        // 3. Perfekt (Present Perfect / Conversational Past)
        val auxForms = if (aux == "sein") {
            listOf("bin", "bist", "ist", "sind", "seid", "sind")
        } else {
            listOf("habe", "hast", "hat", "haben", "habt", "haben")
        }

        val perfectTense = TenseConjugation(
            tenseName = "Perfekt (Conversational Past)",
            tenseCategory = "Compound",
            description = "The most common past tense in spoken German for completed actions.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = "${auxForms[idx]} ... $participle"
                ConjugatedForm(p, f, translation = "$p have ${meaning.removePrefix("to ")}", exampleSentence = "$p ${auxForms[idx]} das bereits $participle.")
            }
        )

        // 4. Futur I (Future)
        val werdenForms = listOf("werde", "wirst", "wird", "werden", "werdet", "werden")
        val futureTense = TenseConjugation(
            tenseName = "Futur I (Future)",
            tenseCategory = "Indicative",
            description = "Future intentions, promises, and predictions.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = "${werdenForms[idx]} $verb"
                ConjugatedForm(p, f, translation = "$p will ${meaning.removePrefix("to ")}", exampleSentence = "$p ${werdenForms[idx]} bald $verb.")
            }
        )

        // 5. Konjunktiv II (Subjunctive / Conditional)
        val konj2Forms = when (verb) {
            "sein" -> listOf("wäre", "wärest", "wäre", "wären", "wäret", "wären")
            "haben" -> listOf("hätte", "hättest", "hätte", "hätten", "hättet", "hätten")
            "können" -> listOf("könnte", "könntest", "könnte", "könnten", "könntet", "könnten")
            else -> listOf("würde $verb", "würdest $verb", "würde $verb", "würden $verb", "würdet $verb", "würden $verb")
        }

        val konjunktivTense = TenseConjugation(
            tenseName = "Konjunktiv II (Hypothetical / Polite)",
            tenseCategory = "Subjunctive",
            description = "Wishes, contrary-to-fact hypotheses, and polite requests ('would do').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = konj2Forms[idx]
                ConjugatedForm(p, f, translation = "$p would ${meaning.removePrefix("to ")}", exampleSentence = "Wenn möglich, $f $p gerne.")
            }
        )

        // 6. Imperativ (Commands)
        val imperativeTense = TenseConjugation(
            tenseName = "Imperativ (Commands)",
            tenseCategory = "Imperative",
            description = "Direct orders, instructions, and invitations.",
            forms = listOf(
                ConjugatedForm("du", when (verb) { "sein" -> "sei!"; "geben" -> "gib!"; "sehen" -> "sieh!"; else -> "$stem!" }, translation = "Do ${meaning.removePrefix("to ")}!"),
                ConjugatedForm("ihr", "${stem}t!", translation = "You all ${meaning.removePrefix("to ")}!"),
                ConjugatedForm("Sie (formal)", "$verb Sie!", translation = "Please ${meaning.removePrefix("to ")}!"),
                ConjugatedForm("wir", "$verb wir!", translation = "Let's ${meaning.removePrefix("to ")}!")
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "de",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Unregelmäßig (Stark)" else "Regelmäßig (Schwach)",
            auxiliaryVerb = aux,
            gerund = "${verb}d",
            gerundPhonetic = "${verb}d",
            pastParticiple = participle,
            pastParticiplePhonetic = participle,
            tenses = listOf(presentTense, preteriteTense, perfectTense, futureTense, konjunktivTense, imperativeTense)
        )
    }

    // ==========================================
    // FRENCH CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateFrenchConjugations(verb: String, meaning: String): VerbTableData {
        val pronouns = listOf("je", "tu", "il / elle / on", "nous", "vous", "ils / elles")
        val ending = when {
            verb.endsWith("er") -> "er"
            verb.endsWith("ir") -> "ir"
            else -> "re"
        }
        val stem = if (verb.length > 2) verb.dropLast(2) else verb
        val isIrregular = verb in listOf("être", "avoir", "aller", "faire", "dire", "pouvoir", "voir", "savoir", "vouloir", "venir", "prendre")

        val participle = when (verb) {
            "être" -> "été"
            "avoir" -> "eu"
            "faire" -> "fait"
            "dire" -> "dit"
            "prendre" -> "pris"
            "voir" -> "vu"
            "pouvoir" -> "pu"
            "vouloir" -> "voulu"
            "venir" -> "venu"
            else -> when (ending) {
                "er" -> "${stem}é"
                "ir" -> "${stem}i"
                else -> "${stem}u"
            }
        }

        val aux = if (verb in listOf("être", "aller", "venir", "partir", "arriver", "entrer", "sortir", "rester", "tomber")) "être" else "avoir"

        // 1. Présent
        val presForms = when (verb) {
            "être" -> listOf("suis", "es", "est", "sommes", "êtes", "sont")
            "avoir" -> listOf("ai", "as", "a", "avons", "avez", "ont")
            "aller" -> listOf("vais", "vas", "va", "allons", "allez", "vont")
            "faire" -> listOf("fais", "fais", "fait", "faisons", "faites", "font")
            "dire" -> listOf("dis", "dis", "dit", "disons", "dites", "disent")
            "pouvoir" -> listOf("peux", "peux", "peut", "pouvons", "pouvez", "peuvent")
            "vouloir" -> listOf("veux", "veux", "veut", "voulons", "voulez", "veulent")
            "prendre" -> listOf("prends", "prends", "prend", "prenons", "prenez", "prennent")
            "venir" -> listOf("viens", "viens", "vient", "venons", "venez", "viennent")
            else -> when (ending) {
                "er" -> listOf("${stem}e", "${stem}es", "${stem}e", "${stem}ons", "${stem}ez", "${stem}ent")
                "ir" -> listOf("${stem}is", "${stem}is", "${stem}it", "${stem}issons", "${stem}issez", "${stem}issent")
                else -> listOf("${stem}s", "${stem}s", "${stem}", "${stem}ons", "${stem}ez", "${stem}ent")
            }
        }

        val presentTense = TenseConjugation(
            tenseName = "Présent (Present)",
            tenseCategory = "Indicative",
            description = "Current actions, permanent states, habits, and immediate future.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = presForms[idx]
                val adjustedP = if (p == "je" && (f.startsWith("a") || f.startsWith("e") || f.startsWith("i") || f.startsWith("o") || f.startsWith("u") || f.startsWith("é"))) "j'" else p
                ConjugatedForm(adjustedP, f, translation = "$p ${meaning.removePrefix("to ")}", exampleSentence = "$adjustedP $f tous les jours.")
            }
        )

        // 2. Passé Composé
        val auxForms = if (aux == "être") {
            listOf("suis", "es", "est", "sommes", "êtes", "sont")
        } else {
            listOf("ai", "as", "a", "avons", "avez", "ont")
        }

        val passeComposeTense = TenseConjugation(
            tenseName = "Passé Composé (Past)",
            tenseCategory = "Compound",
            description = "Completed past events in conversational and modern French.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = "${auxForms[idx]} $participle"
                val adjustedP = if (p == "je" && aux == "avoir") "j'" else p
                ConjugatedForm(adjustedP, f, translation = "$p ${meaning.removePrefix("to ")} (past)", exampleSentence = "$adjustedP $f hier soir.")
            }
        )

        // 3. Imparfait
        val impForms = listOf("${stem}ais", "${stem}ais", "${stem}ait", "${stem}ions", "${stem}iez", "${stem}aient")
        val imparfaitTense = TenseConjugation(
            tenseName = "Imparfait (Imperfect)",
            tenseCategory = "Indicative",
            description = "Descriptions, habits, and ongoing past states ('used to' / 'was doing').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = impForms[idx]
                ConjugatedForm(p, f, translation = "$p used to ${meaning.removePrefix("to ")}", exampleSentence = "Quand j'étais jeune, $p $f souvent.")
            }
        )

        // 4. Futur Simple
        val futStem = when (verb) {
            "être" -> "ser"
            "avoir" -> "aur"
            "aller" -> "ir"
            "faire" -> "fer"
            "pouvoir" -> "pourr"
            "vouloir" -> "voudr"
            else -> verb.removeSuffix("e")
        }
        val futForms = listOf("${futStem}ai", "${futStem}as", "${futStem}a", "${futStem}ons", "${futStem}ez", "${futStem}ont")
        val futureTense = TenseConjugation(
            tenseName = "Futur Simple (Future)",
            tenseCategory = "Indicative",
            description = "Future occurrences and formal predictions.",
            forms = pronouns.mapIndexed { idx, p ->
                val f = futForms[idx]
                ConjugatedForm(p, f, translation = "$p will ${meaning.removePrefix("to ")}", exampleSentence = "Demain, $p $f sans aucun doute.")
            }
        )

        // 5. Conditionnel Présent
        val condForms = listOf("${futStem}ais", "${futStem}ais", "${futStem}ait", "${futStem}ions", "${futStem}iez", "${futStem}aient")
        val conditionalTense = TenseConjugation(
            tenseName = "Conditionnel Présent",
            tenseCategory = "Indicative",
            description = "Polite requests, advice, and hypothetical situations ('would do').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = condForms[idx]
                ConjugatedForm(p, f, translation = "$p would ${meaning.removePrefix("to ")}", exampleSentence = "Si possible, $p $f avec plaisir.")
            }
        )

        // 6. Subjonctif Présent
        val subjForms = listOf("${stem}e", "${stem}es", "${stem}e", "${stem}ions", "${stem}iez", "${stem}ent")
        val subjonctifTense = TenseConjugation(
            tenseName = "Subjonctif Présent",
            tenseCategory = "Subjunctive",
            description = "Necessity, emotion, wish, and uncertainty ('Il faut que...').",
            forms = pronouns.mapIndexed { idx, p ->
                val f = subjForms[idx]
                ConjugatedForm("que $p", f, translation = "(that) $p ${meaning.removePrefix("to ")}", exampleSentence = "Il faut que $p $f dès maintenant.")
            }
        )

        // 7. Impératif
        val imperativeTense = TenseConjugation(
            tenseName = "Impératif (Commands)",
            tenseCategory = "Imperative",
            description = "Direct orders and suggestions.",
            forms = listOf(
                ConjugatedForm("tu", if (ending == "er") "${stem}e!" else "${stem}s!", translation = "Do ${meaning.removePrefix("to ")}!"),
                ConjugatedForm("nous", "${stem}ons!", translation = "Let's ${meaning.removePrefix("to ")}!"),
                ConjugatedForm("vous", "${stem}ez!", translation = "You all ${meaning.removePrefix("to ")}!")
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "fr",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Irrégulier" else "-$ending Régulier",
            auxiliaryVerb = aux,
            gerund = "en ${stem}ant",
            pastParticiple = participle,
            tenses = listOf(presentTense, passeComposeTense, imparfaitTense, futureTense, conditionalTense, subjonctifTense, imperativeTense)
        )
    }

    // ==========================================
    // ITALIAN CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateItalianConjugations(verb: String, meaning: String): VerbTableData {
        val pronouns = listOf("io", "tu", "lui / lei", "noi", "voi", "loro")
        val ending = when {
            verb.endsWith("are") -> "are"
            verb.endsWith("ere") -> "ere"
            else -> "ire"
        }
        val stem = if (verb.length > 3) verb.dropLast(3) else verb
        val isIrregular = verb in listOf("essere", "avere", "andare", "fare", "dire", "potere", "volere", "venire", "sapere", "vedere")

        val presForms = when (verb) {
            "essere" -> listOf("sono", "sei", "è", "siamo", "siete", "sono")
            "avere" -> listOf("ho", "hai", "ha", "abbiamo", "avete", "hanno")
            "andare" -> listOf("vado", "vai", "va", "andiamo", "andate", "vanno")
            "fare" -> listOf("faccio", "fai", "fa", "facciamo", "fate", "fanno")
            "dire" -> listOf("dico", "dici", "dice", "diciamo", "dite", "dicono")
            "potere" -> listOf("posso", "puoi", "può", "possiamo", "potete", "possono")
            "volere" -> listOf("voglio", "vuoi", "vuole", "vogliamo", "volete", "vogliono")
            else -> when (ending) {
                "are" -> listOf("${stem}o", "${stem}i", "${stem}a", "${stem}iamo", "${stem}ate", "${stem}ano")
                "ere" -> listOf("${stem}o", "${stem}i", "${stem}e", "${stem}iamo", "${stem}ete", "${stem}ono")
                else -> listOf("${stem}o", "${stem}i", "${stem}e", "${stem}iamo", "${stem}ite", "${stem}ono")
            }
        }

        val participle = when (verb) {
            "essere" -> "stato"
            "fare" -> "fatto"
            "dire" -> "detto"
            "vedere" -> "visto"
            else -> when (ending) {
                "are" -> "${stem}ato"
                "ere" -> "${stem}uto"
                else -> "${stem}ito"
            }
        }

        val aux = if (verb in listOf("essere", "andare", "venire", "partire", "arrivare", "uscire", "restare")) "essere" else "avere"

        val presentTense = TenseConjugation(
            tenseName = "Presente (Present)",
            tenseCategory = "Indicative",
            description = "Current actions, habits, and near future.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, presForms[idx], translation = "$p ${meaning.removePrefix("to ")}", exampleSentence = "$p ${presForms[idx]} ogni giorno.")
            }
        )

        val passProssimoForms = if (aux == "essere") {
            listOf("sono", "sei", "è", "siamo", "siete", "sono")
        } else {
            listOf("ho", "hai", "ha", "abbiamo", "avete", "hanno")
        }

        val passProssimoTense = TenseConjugation(
            tenseName = "Passato Prossimo (Past)",
            tenseCategory = "Compound",
            description = "Completed actions in the past with current relevance.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, "${passProssimoForms[idx]} $participle", translation = "$p ${meaning.removePrefix("to ")} (past)", exampleSentence = "$p ${passProssimoForms[idx]} $participle ieri.")
            }
        )

        val futStem = if (ending == "are") "${stem}er" else stem
        val futForms = listOf("${futStem}ò", "${futStem}ai", "${futStem}à", "${futStem}emo", "${futStem}ete", "${futStem}anno")
        val futureTense = TenseConjugation(
            tenseName = "Futuro Semplice (Future)",
            tenseCategory = "Indicative",
            description = "Future events, plans, and conjectures.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, futForms[idx], translation = "$p will ${meaning.removePrefix("to ")}", exampleSentence = "Domani, $p ${futForms[idx]} sicuramente.")
            }
        )

        val condForms = listOf("${futStem}ei", "${futStem}esti", "${futStem}ebbe", "${futStem}emmo", "${futStem}este", "${futStem}ebbero")
        val conditionalTense = TenseConjugation(
            tenseName = "Condizionale Presente",
            tenseCategory = "Indicative",
            description = "Wishes, polite forms, and conditional possibilities ('would do').",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, condForms[idx], translation = "$p would ${meaning.removePrefix("to ")}", exampleSentence = "Se potessi, $p ${condForms[idx]} volentieri.")
            }
        )

        val subjForms = when (ending) {
            "are" -> listOf("${stem}i", "${stem}i", "${stem}i", "${stem}iamo", "${stem}iate", "${stem}ino")
            else -> listOf("${stem}a", "${stem}a", "${stem}a", "${stem}iamo", "${stem}iate", "${stem}ano")
        }
        val subjunctiveTense = TenseConjugation(
            tenseName = "Congiuntivo Presente (Subjunctive)",
            tenseCategory = "Subjunctive",
            description = "Doubts, feelings, wishes, and opinions.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm("che $p", subjForms[idx], translation = "(that) $p ${meaning.removePrefix("to ")}", exampleSentence = "Spero che $p ${subjForms[idx]} presto.")
            }
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "it",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Irregolare" else "Regolare in -$ending",
            auxiliaryVerb = aux,
            gerund = if (ending == "are") "${stem}ando" else "${stem}endo",
            pastParticiple = participle,
            tenses = listOf(presentTense, passProssimoTense, futureTense, conditionalTense, subjunctiveTense)
        )
    }

    // ==========================================
    // PORTUGUESE CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generatePortugueseConjugations(verb: String, meaning: String): VerbTableData {
        val pronouns = listOf("eu", "você / tu", "ele / ela", "nós", "vocês", "eles / elas")
        val ending = when {
            verb.endsWith("ar") -> "ar"
            verb.endsWith("er") -> "er"
            else -> "ir"
        }
        val stem = if (verb.length > 2) verb.dropLast(2) else verb
        val isIrregular = verb in listOf("ser", "estar", "ir", "ter", "fazer", "dizer", "poder", "ver", "vir", "saber")

        val presForms = when (verb) {
            "ser" -> listOf("sou", "é", "é", "somos", "são", "são")
            "estar" -> listOf("estou", "está", "está", "estamos", "estão", "estão")
            "ir" -> listOf("vou", "vai", "vai", "vamos", "vão", "vão")
            "ter" -> listOf("tenho", "tem", "tem", "temos", "têm", "têm")
            "fazer" -> listOf("faço", "faz", "faz", "fazemos", "fazem", "fazem")
            else -> when (ending) {
                "ar" -> listOf("${stem}o", "${stem}a", "${stem}a", "${stem}amos", "${stem}am", "${stem}am")
                "er" -> listOf("${stem}o", "${stem}e", "${stem}e", "${stem}emos", "${stem}em", "${stem}em")
                else -> listOf("${stem}o", "${stem}e", "${stem}e", "${stem}imos", "${stem}em", "${stem}em")
            }
        }

        val participle = if (ending == "ar") "${stem}ado" else "${stem}ido"

        val presentTense = TenseConjugation(
            tenseName = "Presente do Indicativo (Present)",
            tenseCategory = "Indicative",
            description = "Current routines, general truths, and ongoing actions.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, presForms[idx], translation = "$p ${meaning.removePrefix("to ")}", exampleSentence = "$p ${presForms[idx]} todos os dias.")
            }
        )

        val pretForms = when (ending) {
            "ar" -> listOf("${stem}ei", "${stem}ou", "${stem}ou", "${stem}amos", "${stem}aram", "${stem}aram")
            "er" -> listOf("${stem}i", "${stem}eu", "${stem}eu", "${stem}emos", "${stem}eram", "${stem}eram")
            else -> listOf("${stem}i", "${stem}iu", "${stem}iu", "${stem}imos", "${stem}iram", "${stem}iram")
        }

        val preteriteTense = TenseConjugation(
            tenseName = "Pretérito Perfeito (Past Simple)",
            tenseCategory = "Indicative",
            description = "Specific actions completed in the past.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, pretForms[idx], translation = "$p ${meaning.removePrefix("to ")} (past)", exampleSentence = "Ontem, $p ${pretForms[idx]} muito bem.")
            }
        )

        val futForms = listOf("${verb}ei", "${verb}á", "${verb}á", "${verb}emos", "${verb}ão", "${verb}ão")
        val futureTense = TenseConjugation(
            tenseName = "Futuro do Presente (Future)",
            tenseCategory = "Indicative",
            description = "Future events and definite intentions.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, futForms[idx], translation = "$p will ${meaning.removePrefix("to ")}", exampleSentence = "Amanhã, $p ${futForms[idx]} com certeza.")
            }
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "pt",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Irregular" else "Regular -$ending",
            auxiliaryVerb = "ter",
            gerund = if (ending == "ar") "${stem}ando" else if (ending == "er") "${stem}endo" else "${stem}indo",
            pastParticiple = participle,
            tenses = listOf(presentTense, preteriteTense, futureTense)
        )
    }

    // ==========================================
    // JAPANESE CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateJapaneseConjugations(verb: String, meaning: String): VerbTableData {
        val root = verb.split(" ")[0].split("(")[0]

        val politePresent = if (root.endsWith("る")) root.dropLast(1) + "ます" else root + "ます"
        val plainNegative = if (root.endsWith("る")) root.dropLast(1) + "ない" else root + "ない"
        val politePast = if (root.endsWith("る")) root.dropLast(1) + "ました" else root + "ました"
        val plainPast = if (root.endsWith("る")) root.dropLast(1) + "た" else root + "た"
        val teForm = if (root.endsWith("る")) root.dropLast(1) + "て" else root + "て"
        val progressive = "$teForm いる"
        val potential = if (root.endsWith("る")) root.dropLast(1) + "られる" else root + "える"
        val volitional = if (root.endsWith("る")) root.dropLast(1) + "ましょう" else root + "ましょう"

        val tenses = listOf(
            TenseConjugation(
                tenseName = "Polite Present (丁寧語 現在形)",
                tenseCategory = "Indicative",
                description = "Standard polite form used in daily respectful conversations.",
                forms = listOf(
                    ConjugatedForm("肯定 (Affirmative)", politePresent, translation = meaning, exampleSentence = "毎日、$politePresent。"),
                    ConjugatedForm("否定 (Negative)", politePresent.dropLast(2) + "ません", translation = "do not ${meaning.removePrefix("to ")}", exampleSentence = "今日は、$politePresent をしません。")
                )
            ),
            TenseConjugation(
                tenseName = "Polite Past (丁寧語 過去形)",
                tenseCategory = "Indicative",
                description = "Polite completed past actions.",
                forms = listOf(
                    ConjugatedForm("肯定 (Affirmative)", politePast, translation = "did ${meaning.removePrefix("to ")}", exampleSentence = "昨日、$politePast。"),
                    ConjugatedForm("否定 (Negative)", politePresent.dropLast(2) + "ませんでした", translation = "did not ${meaning.removePrefix("to ")}", exampleSentence = "昨日は、$politePast でした。")
                )
            ),
            TenseConjugation(
                tenseName = "Te-Form & Continuous (て形・進行形)",
                tenseCategory = "Aspect",
                description = "Connecting actions and ongoing state ('is doing').",
                forms = listOf(
                    ConjugatedForm("て形 (Connecting)", teForm, translation = "and... / by doing", exampleSentence = "$teForm、それから進みます。"),
                    ConjugatedForm("現在進行 (Ongoing)", progressive, translation = "is currently ${meaning.removePrefix("to ")}ing", exampleSentence = "今、$progressive。")
                )
            ),
            TenseConjugation(
                tenseName = "Potential & Volitional (可能形・意志形)",
                tenseCategory = "Modal",
                description = "Ability ('can do') and invitations ('let's do!').",
                forms = listOf(
                    ConjugatedForm("可能 (Can do)", potential, translation = "can ${meaning.removePrefix("to ")}", exampleSentence = "日本語で、$potential。"),
                    ConjugatedForm("意志 (Let's do!)", volitional, translation = "let's ${meaning.removePrefix("to ")}!", exampleSentence = "一緒に、$volitional！")
                )
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "ja",
            englishMeaning = meaning,
            regularType = if (root.endsWith("する")) "サ行変格 (Suru)" else if (root.endsWith("る")) "一段 (Ichidan)" else "五段 (Godan)",
            auxiliaryVerb = "です / ます",
            gerund = teForm,
            pastParticiple = plainPast,
            tenses = tenses
        )
    }

    // ==========================================
    // ENGLISH CONJUGATIONS (All Using Forms)
    // ==========================================
    private fun generateEnglishConjugations(verb: String, meaning: String): VerbTableData {
        val isIrregular = verb in listOf("be", "have", "do", "go", "see", "come", "take", "say", "make", "know", "get", "give", "think", "find")
        val thirdPerson = when {
            verb == "be" -> "is"
            verb == "have" -> "has"
            verb == "do" -> "does"
            verb == "go" -> "goes"
            verb.endsWith("ch") || verb.endsWith("sh") || verb.endsWith("ss") || verb.endsWith("x") -> "${verb}es"
            verb.endsWith("y") && !verb.endsWith("ay") && !verb.endsWith("ey") && !verb.endsWith("oy") -> verb.dropLast(1) + "ies"
            else -> "${verb}s"
        }

        val past = when (verb) {
            "be" -> "was / were"
            "have" -> "had"
            "do" -> "did"
            "go" -> "went"
            "see" -> "saw"
            "come" -> "came"
            "take" -> "took"
            "say" -> "said"
            "make" -> "made"
            "know" -> "knew"
            "get" -> "got"
            "give" -> "gave"
            else -> if (verb.endsWith("e")) "${verb}d" else "${verb}ed"
        }

        val pastParticiple = when (verb) {
            "be" -> "been"
            "have" -> "had"
            "do" -> "done"
            "go" -> "gone"
            "see" -> "seen"
            "come" -> "come"
            "take" -> "taken"
            "know" -> "known"
            "give" -> "given"
            else -> past
        }

        val gerund = when {
            verb == "be" -> "being"
            verb.endsWith("ie") -> verb.dropLast(2) + "ying"
            verb.endsWith("e") && !verb.endsWith("ee") -> verb.dropLast(1) + "ing"
            else -> "${verb}ing"
        }

        val pronouns = listOf("I", "you", "he / she / it", "we", "they")

        val presForms = listOf(verb, verb, thirdPerson, verb, verb)
        val presentTense = TenseConjugation(
            tenseName = "Present Simple",
            tenseCategory = "Indicative",
            description = "General truths, routines, and permanent situations.",
            forms = pronouns.mapIndexed { idx, p ->
                ConjugatedForm(p, presForms[idx], translation = "$p ${presForms[idx]}", exampleSentence = "$p ${presForms[idx]} every day.")
            }
        )

        val pastTense = TenseConjugation(
            tenseName = "Past Simple",
            tenseCategory = "Indicative",
            description = "Finished actions in the past.",
            forms = pronouns.map { p ->
                ConjugatedForm(p, past, translation = "$p $past", exampleSentence = "$p $past yesterday.")
            }
        )

        val continuousTense = TenseConjugation(
            tenseName = "Present Continuous",
            tenseCategory = "Continuous",
            description = "Actions happening right now or temporary situations.",
            forms = listOf(
                ConjugatedForm("I", "am $gerund", translation = "I am ${meaning.removePrefix("to ")}ing", exampleSentence = "I am $gerund right now."),
                ConjugatedForm("you", "are $gerund", translation = "you are ${meaning.removePrefix("to ")}ing", exampleSentence = "You are $gerund right now."),
                ConjugatedForm("he / she / it", "is $gerund", translation = "he is ${meaning.removePrefix("to ")}ing", exampleSentence = "He is $gerund right now."),
                ConjugatedForm("we", "are $gerund", translation = "we are ${meaning.removePrefix("to ")}ing", exampleSentence = "We are $gerund together."),
                ConjugatedForm("they", "are $gerund", translation = "they are ${meaning.removePrefix("to ")}ing", exampleSentence = "They are $gerund right now.")
            )
        )

        val perfectTense = TenseConjugation(
            tenseName = "Present Perfect",
            tenseCategory = "Compound",
            description = "Past experiences and past actions with present result.",
            forms = pronouns.map { p ->
                val aux = if (p == "he / she / it") "has" else "have"
                ConjugatedForm(p, "$aux $pastParticiple", translation = "$p $aux $pastParticiple", exampleSentence = "$p $aux already $pastParticiple.")
            }
        )

        val futureTense = TenseConjugation(
            tenseName = "Future Simple (will)",
            tenseCategory = "Indicative",
            description = "Spontaneous decisions, predictions, and promises.",
            forms = pronouns.map { p ->
                ConjugatedForm(p, "will $verb", translation = "$p will $verb", exampleSentence = "$p will $verb tomorrow.")
            }
        )

        return VerbTableData(
            infinitive = "to $verb",
            languageCode = "en",
            englishMeaning = meaning,
            regularType = if (isIrregular) "Irregular" else "Regular (-ed)",
            auxiliaryVerb = "have / do",
            gerund = gerund,
            pastParticiple = pastParticiple,
            tenses = listOf(presentTense, pastTense, continuousTense, perfectTense, futureTense)
        )
    }

    // ==========================================
    // MANDARIN & KOREAN CONJUGATIONS (Aspects)
    // ==========================================
    private fun generateChineseConjugations(verb: String, meaning: String): VerbTableData {
        val root = verb.split(" ")[0].split("(")[0]
        val tenses = listOf(
            TenseConjugation(
                tenseName = "Basic & Habitual (基础/现在习惯)",
                tenseCategory = "Aspect",
                description = "General statements, routines, and present facts.",
                forms = listOf(
                    ConjugatedForm("肯定 (Affirmative)", root, translation = meaning, exampleSentence = "我每天都$root。"),
                    ConjugatedForm("否定 (Negative)", "不$root", translation = "do not $meaning", exampleSentence = "他不想$root。")
                )
            ),
            TenseConjugation(
                tenseName = "Completed Aspect (完成态 - 了)",
                tenseCategory = "Aspect",
                description = "Completed action in past or immediate completion.",
                forms = listOf(
                    ConjugatedForm("肯定 (Completed)", "$root 了", translation = "did $meaning", exampleSentence = "我已经$root 了。"),
                    ConjugatedForm("否定 (Haven't done)", "没$root", translation = "haven't $meaning", exampleSentence = "我还没有$root。")
                )
            ),
            TenseConjugation(
                tenseName = "Progressive Aspect (进行态 - 在/正在)",
                tenseCategory = "Aspect",
                description = "Actions in progress right now ('in the middle of doing').",
                forms = listOf(
                    ConjugatedForm("进行 (Ongoing)", "在$root / 正在$root", translation = "currently doing $meaning", exampleSentence = "我们正在$root 呢。")
                )
            ),
            TenseConjugation(
                tenseName = "Future & Intention (将要 / 想)",
                tenseCategory = "Aspect",
                description = "Plans, intentions, and future events.",
                forms = listOf(
                    ConjugatedForm("将来 (Will do)", "会$root / 将要$root", translation = "will $meaning", exampleSentence = "明天我会$root。"),
                    ConjugatedForm("意愿 (Want to)", "想$root / 要$root", translation = "want to $meaning", exampleSentence = "我很想$root。")
                )
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "zh",
            englishMeaning = meaning,
            regularType = "Aspect-based (态助词)",
            auxiliaryVerb = "是 / 有",
            gerund = "在$root",
            pastParticiple = "$root 了",
            tenses = tenses
        )
    }

    private fun generateKoreanConjugations(verb: String, meaning: String): VerbTableData {
        val root = verb.split(" ")[0].split("(")[0].removeSuffix("다")
        val tenses = listOf(
            TenseConjugation(
                tenseName = "Present Polite (해요체)",
                tenseCategory = "Indicative",
                description = "Everyday polite conversation form.",
                forms = listOf(
                    ConjugatedForm("존댓말 (Polite Present)", "${root}아요/어요", translation = meaning, exampleSentence = "저는 매일 ${root}어요."),
                    ConjugatedForm("격식체 (Formal Present)", "${root}습니다", translation = meaning, exampleSentence = "오늘 열심히 ${root}습니다.")
                )
            ),
            TenseConjugation(
                tenseName = "Past Tense (과거형)",
                tenseCategory = "Indicative",
                description = "Completed past actions.",
                forms = listOf(
                    ConjugatedForm("과거 (Polite Past)", "${root}았어요/었어요", translation = "did $meaning", exampleSentence = "어제 ${root}었어요."),
                    ConjugatedForm("격식 과거 (Formal Past)", "${root}았습니다/었습니다", translation = "did $meaning (formal)", exampleSentence = "다 함께 ${root}었습니다.")
                )
            ),
            TenseConjugation(
                tenseName = "Future & Intention (미래형/의지)",
                tenseCategory = "Modal",
                description = "Will do / planning to do.",
                forms = listOf(
                    ConjugatedForm("미래 (Will do)", "${root}을/ㄹ 거예요", translation = "will $meaning", exampleSentence = "내일 꼭 ${root}을 거예요."),
                    ConjugatedForm("의지 (Intention)", "${root}겠어요", translation = "will surely $meaning", exampleSentence = "제가 직접 ${root}겠습니다.")
                )
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "ko",
            englishMeaning = meaning,
            regularType = "Polite Levels (해요체 / 하십시오체)",
            auxiliaryVerb = "하다 / 있다",
            gerund = "${root}고 있다",
            pastParticiple = "${root}은/ㄴ",
            tenses = tenses
        )
    }

    // ==========================================
    // SLAVIC VERB STRUCTURE & CONJUGATIONS
    // ==========================================
    private data class SlavicVerbForms(
        val pres: List<String>,
        val pastM: String,
        val pastF: String,
        val pastN: String,
        val pastPl: String,
        val fut: List<String>,
        val impSg: String,
        val impPl: String,
        val gerund: String,
        val participle: String,
        val typeDesc: String
    )

    // ==========================================
    // UKRAINIAN CONJUGATIONS (Українські дієвідміни)
    // ==========================================
    private fun generateUkrainianConjugations(verb: String, meaning: String): VerbTableData {
        val cleanVerb = verb.trim().lowercase()
        val isReflexive = cleanVerb.endsWith("ся") || cleanVerb.endsWith("сь")
        val base = if (isReflexive) cleanVerb.removeSuffix("ся").removeSuffix("сь") else cleanVerb

        val knownUkrVerbs = mapOf(
            "бути" to SlavicVerbForms(
                pres = listOf("є", "є", "є", "є", "є", "є"),
                pastM = "був", pastF = "була", pastN = "було", pastPl = "були",
                fut = listOf("буду", "будеш", "буде", "будемо", "будете", "будуть"),
                impSg = "будь", impPl = "будьте",
                gerund = "будучи", participle = "бувалий",
                typeDesc = "Неправильне дієслово (бути)"
            ),
            "робити" to SlavicVerbForms(
                pres = listOf("роблю", "робиш", "робить", "робимо", "робите", "роблять"),
                pastM = "робив", pastF = "робила", pastN = "робило", pastPl = "робили",
                fut = listOf("буду робити", "будеш робити", "буде робити", "будемо робити", "будете робити", "будуть робити"),
                impSg = "роби", impPl = "робіть",
                gerund = "роблячи", participle = "зроблений",
                typeDesc = "2-ге дієвідмінювання (-ити)"
            ),
            "говорити" to SlavicVerbForms(
                pres = listOf("говорю", "говориш", "говорить", "говоримо", "говорите", "говорять"),
                pastM = "говорив", pastF = "говорила", pastN = "говорило", pastPl = "говорили",
                fut = listOf("буду говорити", "будеш говорити", "буде говорити", "будемо говорити", "будете говорити", "будуть говорити"),
                impSg = "говори", impPl = "говоріть",
                gerund = "говорячи", participle = "сказаний",
                typeDesc = "2-ге дієвідмінювання (-ити)"
            ),
            "знати" to SlavicVerbForms(
                pres = listOf("знаю", "знаєш", "знає", "знаємо", "знаєте", "знають"),
                pastM = "знав", pastF = "знала", pastN = "знало", pastPl = "знали",
                fut = listOf("буду знати", "будеш знати", "буде знати", "будемо знати", "будете знати", "будуть знати"),
                impSg = "знай", impPl = "знайте",
                gerund = "знаючи", participle = "знаний",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "йти" to SlavicVerbForms(
                pres = listOf("йду", "йдеш", "йде", "йдемо", "йдете", "йдуть"),
                pastM = "йшов", pastF = "йшла", pastN = "йшло", pastPl = "йшли",
                fut = listOf("буду йти", "будеш йти", "буде йти", "будемо йти", "будете йти", "будуть йти"),
                impSg = "йди", impPl = "йдіть",
                gerund = "йдучи", participle = "пройдений",
                typeDesc = "Неправильне дієслово (йти)"
            ),
            "ходити" to SlavicVerbForms(
                pres = listOf("ходжу", "ходиш", "ходить", "ходимо", "ходите", "ходять"),
                pastM = "ходив", pastF = "ходила", pastN = "ходило", pastPl = "ходили",
                fut = listOf("буду ходити", "будеш ходити", "буде ходити", "будемо ходити", "будете ходити", "будуть ходити"),
                impSg = "ходи", impPl = "ходіть",
                gerund = "ходячи", participle = "пройдений",
                typeDesc = "2-ге дієвідмінювання (д / дж)"
            ),
            "бачити" to SlavicVerbForms(
                pres = listOf("бачу", "бачиш", "бачить", "бачимо", "бачите", "бачать"),
                pastM = "бачив", pastF = "бачила", pastN = "бачило", pastPl = "бачили",
                fut = listOf("буду бачити", "будеш бачити", "буде бачити", "будемо бачити", "будете бачити", "будуть бачити"),
                impSg = "бач", impPl = "бачте",
                gerund = "бачачи", participle = "бачений",
                typeDesc = "2-ге дієвідмінювання (ч / ч)"
            ),
            "думати" to SlavicVerbForms(
                pres = listOf("думаю", "думаєш", "думає", "думаємо", "думаєте", "думають"),
                pastM = "думав", pastF = "думала", pastN = "думало", pastPl = "думали",
                fut = listOf("буду думати", "будеш думати", "буде думати", "будемо думати", "будете думати", "будуть думати"),
                impSg = "думай", impPl = "думайте",
                gerund = "думаючи", participle = "продуманий",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "хотіти" to SlavicVerbForms(
                pres = listOf("хочу", "хочеш", "хоче", "хочемо", "хочете", "хочуть"),
                pastM = "хотів", pastF = "хотіла", pastN = "хотіло", pastPl = "хотіли",
                fut = listOf("буду хотіти", "будеш хотіти", "буде хотіти", "будемо хотіти", "будете хотіти", "будуть хотіти"),
                impSg = "хочи", impPl = "хочіть",
                gerund = "хотячи", participle = "бажаний",
                typeDesc = "Різновідмінюване дієслово"
            ),
            "могти" to SlavicVerbForms(
                pres = listOf("можу", "можеш", "може", "можемо", "можете", "можуть"),
                pastM = "міг", pastF = "могла", pastN = "могло", pastPl = "могли",
                fut = listOf("зможу", "зможеш", "зможе", "зможемо", "зможете", "зможуть"),
                impSg = "можи", impPl = "можіть",
                gerund = "можучи", participle = "можливий",
                typeDesc = "1-ше дієвідмінювання (г / ж)"
            ),
            "жити" to SlavicVerbForms(
                pres = listOf("живу", "живеш", "живе", "живемо", "живете", "живуть"),
                pastM = "жив", pastF = "жила", pastN = "жило", pastPl = "жили",
                fut = listOf("буду жити", "будеш жити", "буде жити", "будемо жити", "будете жити", "будуть жити"),
                impSg = "живи", impPl = "живіть",
                gerund = "живучи", participle = "прожитий",
                typeDesc = "1-ше дієвідмінювання"
            ),
            "писати" to SlavicVerbForms(
                pres = listOf("пишу", "пишеш", "пише", "пишемо", "пишете", "пишуть"),
                pastM = "писав", pastF = "писала", pastN = "писало", pastPl = "писали",
                fut = listOf("буду писати", "будеш писати", "буде писати", "будемо писати", "будете писати", "будуть писати"),
                impSg = "пиши", impPl = "пишіть",
                gerund = "пишучи", participle = "написаний",
                typeDesc = "1-ше дієвідмінювання (с / ш)"
            ),
            "читати" to SlavicVerbForms(
                pres = listOf("читаю", "читаєш", "читає", "читаємо", "читаєте", "читають"),
                pastM = "читав", pastF = "читала", pastN = "читало", pastPl = "читали",
                fut = listOf("буду читати", "будеш читати", "буде читати", "будемо читати", "будете читати", "будуть читати"),
                impSg = "читай", impPl = "читайте",
                gerund = "читаючи", participle = "прочитаний",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "любити" to SlavicVerbForms(
                pres = listOf("люблю", "любиш", "любить", "любимо", "любите", "люблять"),
                pastM = "любив", pastF = "любила", pastN = "любило", pastPl = "любили",
                fut = listOf("буду любити", "будеш любити", "буде любити", "будемо любити", "будете любити", "будуть любити"),
                impSg = "люби", impPl = "любіть",
                gerund = "люблячи", participle = "улюблений",
                typeDesc = "2-ге дієвідмінювання (вставний л)"
            ),
            "чути" to SlavicVerbForms(
                pres = listOf("чую", "чуєш", "чує", "чуємо", "чуєте", "чують"),
                pastM = "чув", pastF = "чула", pastN = "чуло", pastPl = "чули",
                fut = listOf("буду чути", "будеш чути", "буде чути", "будемо чути", "будете чути", "будуть чути"),
                impSg = "чуй", impPl = "чуйте",
                gerund = "чуючи", participle = "почутий",
                typeDesc = "1-ше дієвідмінювання"
            ),
            "їсти" to SlavicVerbForms(
                pres = listOf("їм", "їси", "їсть", "їмо", "їсте", "їдять"),
                pastM = "їв", pastF = "їла", pastN = "їло", pastPl = "їли",
                fut = listOf("буду їсти", "будеш їсти", "буде їсти", "будемо їсти", "будете їсти", "будуть їсти"),
                impSg = "їж", impPl = "їжте",
                gerund = "їдячи", participle = "з'їдений",
                typeDesc = "Атематичне дієслово"
            ),
            "пити" to SlavicVerbForms(
                pres = listOf("п'ю", "п'єш", "п'є", "п'ємо", "п'єте", "п'ють"),
                pastM = "пив", pastF = "пила", pastN = "пило", pastPl = "пили",
                fut = listOf("буду пити", "будеш пити", "буде пити", "будемо пити", "будете пити", "будуть пити"),
                impSg = "пий", impPl = "пийте",
                gerund = "п'ючи", participle = "випитий",
                typeDesc = "1-ше дієвідмінювання"
            ),
            "спати" to SlavicVerbForms(
                pres = listOf("сплю", "спиш", "спить", "спимо", "спите", "сплять"),
                pastM = "спав", pastF = "спала", pastN = "спало", pastPl = "спали",
                fut = listOf("буду спати", "будеш спати", "буде спати", "будемо спати", "будете спати", "будуть спати"),
                impSg = "спи", impPl = "спите",
                gerund = "сплячи", participle = "виспаний",
                typeDesc = "2-ге дієвідмінювання (вставний л)"
            ),
            "мати" to SlavicVerbForms(
                pres = listOf("маю", "маєш", "має", "маємо", "маєте", "мають"),
                pastM = "мав", pastF = "мала", pastN = "мало", pastPl = "мали",
                fut = listOf("буду мати", "будеш мати", "буде мати", "будемо мати", "будете мати", "будуть мати"),
                impSg = "май", impPl = "майте",
                gerund = "маючи", participle = "нажитий",
                typeDesc = "1-ше дієвідмінювання"
            ),
            "розуміти" to SlavicVerbForms(
                pres = listOf("розумію", "розумієш", "розуміє", "розуміємо", "розумієте", "розуміють"),
                pastM = "розумів", pastF = "розуміла", pastN = "розуміло", pastPl = "розуміли",
                fut = listOf("буду розуміти", "будеш розуміти", "буде розуміти", "будемо розуміти", "будете розуміти", "будуть розуміти"),
                impSg = "розумій", impPl = "розумійте",
                gerund = "розуміючи", participle = "зрозумілий",
                typeDesc = "1-ше дієвідмінювання (-іти)"
            ),
            "працювати" to SlavicVerbForms(
                pres = listOf("працюю", "працюєш", "працює", "працюємо", "працюєте", "працюють"),
                pastM = "працював", pastF = "працювала", pastN = "працювало", pastPl = "працювали",
                fut = listOf("буду працювати", "будеш працювати", "буде працювати", "будемо працювати", "будете працювати", "будуть працювати"),
                impSg = "працюй", impPl = "працюйте",
                gerund = "працюючи", participle = "опрацьований",
                typeDesc = "1-ше дієвідмінювання (-увати)"
            ),
            "вчити" to SlavicVerbForms(
                pres = listOf("вчу", "вчиш", "вчить", "вчимо", "вчите", "вчать"),
                pastM = "вчив", pastF = "вчила", pastN = "вчило", pastPl = "вчили",
                fut = listOf("буду вчити", "будеш вчити", "буде вчити", "будемо вчити", "будете вчити", "будуть вчити"),
                impSg = "вчи", impPl = "вчіть",
                gerund = "вчачи", participle = "вивчений",
                typeDesc = "2-ге дієвідмінювання (-ити)"
            ),
            "брати" to SlavicVerbForms(
                pres = listOf("беру", "береш", "бере", "беремо", "берете", "беруть"),
                pastM = "брав", pastF = "брала", pastN = "брало", pastPl = "брали",
                fut = listOf("буду брати", "будеш брати", "буде брати", "будемо брати", "будете брати", "будуть брати"),
                impSg = "бери", impPl = "беріть",
                gerund = "беручи", participle = "узятий",
                typeDesc = "1-ше дієвідмінювання"
            ),
            "дати" to SlavicVerbForms(
                pres = listOf("дам", "даси", "дасть", "дамо", "дасте", "дадуть"),
                pastM = "дав", pastF = "дала", pastN = "дало", pastPl = "дали",
                fut = listOf("дам", "даси", "дасть", "дамо", "дасте", "дадуть"),
                impSg = "дай", impPl = "дайте",
                gerund = "даючи", participle = "даний",
                typeDesc = "Атематичне дієслово"
            ),
            "казати" to SlavicVerbForms(
                pres = listOf("кажу", "кажеш", "каже", "кажемо", "кажете", "кажуть"),
                pastM = "казав", pastF = "казала", pastN = "казало", pastPl = "казали",
                fut = listOf("буду казати", "будеш казати", "буде казати", "будемо казати", "будете казати", "будуть казати"),
                impSg = "кажи", impPl = "кажіть",
                gerund = "кажучи", participle = "сказаний",
                typeDesc = "1-ше дієвідмінювання (з / ж)"
            ),
            "допомагати" to SlavicVerbForms(
                pres = listOf("допомагаю", "допомагаєш", "допомагає", "допомагаємо", "допомагаєте", "допомагають"),
                pastM = "допомагав", pastF = "допомагала", pastN = "допомагало", pastPl = "допомагали",
                fut = listOf("буду допомагати", "будеш допомагати", "буде допомагати", "будемо допомагати", "будете допомагати", "будуть допомагати"),
                impSg = "допомагай", impPl = "допомагайте",
                gerund = "допомагаючи", participle = "наданий",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "чекати" to SlavicVerbForms(
                pres = listOf("чекаю", "чекаєш", "чекає", "чекаємо", "чекаєте", "чекають"),
                pastM = "чекав", pastF = "чекала", pastN = "чекало", pastPl = "чекали",
                fut = listOf("буду чекати", "будеш чекати", "буде чекати", "будемо чекати", "будете чекати", "будуть чекати"),
                impSg = "чекай", impPl = "чекайте",
                gerund = "чекаючи", participle = "очікуваний",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "шукати" to SlavicVerbForms(
                pres = listOf("шукаю", "шукаєш", "шукає", "шукаємо", "шукаєте", "шукають"),
                pastM = "шукав", pastF = "шукала", pastN = "шукало", pastPl = "шукали",
                fut = listOf("буду шукати", "будеш шукати", "буде шукати", "будемо шукати", "будете шукати", "будуть шукати"),
                impSg = "шукай", impPl = "шукайте",
                gerund = "шукаючи", participle = "знайдений",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            ),
            "купувати" to SlavicVerbForms(
                pres = listOf("купую", "купуєш", "купує", "купуємо", "купуєте", "купують"),
                pastM = "купував", pastF = "купувала", pastN = "купувало", pastPl = "купували",
                fut = listOf("буду купувати", "будеш купувати", "буде купувати", "будемо купувати", "будете купувати", "будуть купувати"),
                impSg = "купуй", impPl = "купуйте",
                gerund = "купуючи", participle = "куплений",
                typeDesc = "1-ше дієвідмінювання (-увати)"
            ),
            "слухати" to SlavicVerbForms(
                pres = listOf("слухаю", "слухаєш", "слухає", "слухаємо", "слухаєте", "слухають"),
                pastM = "слухав", pastF = "слухала", pastN = "слухало", pastPl = "слухали",
                fut = listOf("буду слухати", "будеш слухати", "буде слухати", "будемо слухати", "будете слухати", "будуть слухати"),
                impSg = "слухай", impPl = "слухайте",
                gerund = "слухаючи", participle = "послуханий",
                typeDesc = "1-ше дієвідмінювання (-ати)"
            )
        )

        val verbData = knownUkrVerbs[base] ?: run {
            // Algorithmic fallback based on Ukrainian grammar rules
            when {
                base.endsWith("увати") || base.endsWith("ювати") -> {
                    val stem = base.removeSuffix("увати").removeSuffix("ювати")
                    SlavicVerbForms(
                        pres = listOf("${stem}ую", "${stem}уєш", "${stem}ує", "${stem}уємо", "${stem}уєте", "${stem}ують"),
                        pastM = "${stem}ував", pastF = "${stem}увала", pastN = "${stem}увало", pastPl = "${stem}ували",
                        fut = listOf("буду $cleanVerb", "будеш $cleanVerb", "буде $cleanVerb", "будемо $cleanVerb", "будете $cleanVerb", "будуть $cleanVerb"),
                        impSg = "${stem}уй", impPl = "${stem}уйте",
                        gerund = "${stem}уючи", participle = "${stem}ований",
                        typeDesc = "1-ше дієвідмінювання (-увати)"
                    )
                }
                base.endsWith("ати") || base.endsWith("яти") -> {
                    val stem = base.removeSuffix("ти")
                    SlavicVerbForms(
                        pres = listOf("${stem}ю", "${stem}єш", "${stem}є", "${stem}ємо", "${stem}єте", "${stem}ють"),
                        pastM = "${stem}в", pastF = "${stem}ла", pastN = "${stem}ло", pastPl = "${stem}ли",
                        fut = listOf("буду $cleanVerb", "будеш $cleanVerb", "буде $cleanVerb", "будемо $cleanVerb", "будете $cleanVerb", "будуть $cleanVerb"),
                        impSg = "${stem}й", impPl = "${stem}йте",
                        gerund = "${stem}ючи", participle = "${stem}ний",
                        typeDesc = "1-ше дієвідмінювання (-ати)"
                    )
                }
                base.endsWith("ити") || base.endsWith("іти") -> {
                    val stem = base.removeSuffix("ити").removeSuffix("іти")
                    SlavicVerbForms(
                        pres = listOf("${stem}ю", "${stem}иш", "${stem}ить", "${stem}имо", "${stem}ите", "${stem}ять"),
                        pastM = "${stem}ив", pastF = "${stem}ила", pastN = "${stem}ило", pastPl = "${stem}или",
                        fut = listOf("буду $cleanVerb", "будеш $cleanVerb", "буде $cleanVerb", "будемо $cleanVerb", "будете $cleanVerb", "будуть $cleanVerb"),
                        impSg = "${stem}и", impPl = "${stem}іть",
                        gerund = "${stem}ячи", participle = "${stem}ений",
                        typeDesc = "2-ге дієвідмінювання (-ити/-іти)"
                    )
                }
                else -> {
                    val stem = base.removeSuffix("ти")
                    SlavicVerbForms(
                        pres = listOf("${stem}у", "${stem}еш", "${stem}е", "${stem}емо", "${stem}ете", "${stem}уть"),
                        pastM = "${stem}в", pastF = "${stem}ла", pastN = "${stem}ло", pastPl = "${stem}ли",
                        fut = listOf("буду $cleanVerb", "будеш $cleanVerb", "буде $cleanVerb", "будемо $cleanVerb", "будете $cleanVerb", "будуть $cleanVerb"),
                        impSg = "${stem}и", impPl = "${stem}іть",
                        gerund = "${stem}учи", participle = "${stem}ений",
                        typeDesc = "Дієвідмінювання дієслова"
                    )
                }
            }
        }

        val pronouns = listOf("я", "ти", "він / вона / воно", "ми", "ви", "вони")

        val presentTense = TenseConjugation(
            tenseName = "Теперішній час (Present Tense)",
            tenseCategory = "Indicative",
            description = "Описує дії, що відбуваються в момент мовлення, постійні стани або регулярні процеси.",
            forms = pronouns.mapIndexed { idx, p ->
                val form = verbData.pres[idx]
                ConjugatedForm(
                    pronoun = p,
                    form = form,
                    phonetic = ukrainianPhonetic(form),
                    translation = "$p $meaning",
                    exampleSentence = "$p $form щодня."
                )
            }
        )

        val pastTense = TenseConjugation(
            tenseName = "Минулий час (Past Tense)",
            tenseCategory = "Indicative",
            description = "Описує дії, завершені або тривалі в минулому. Змінюється за родами (ч., ж., ср.) та числами.",
            forms = listOf(
                ConjugatedForm("я (ч. / ж.)", "${verbData.pastM} / ${verbData.pastF}", ukrainianPhonetic(verbData.pastM), "I $meaning (past)", "Я вже це робив / робила."),
                ConjugatedForm("ти (ч. / ж.)", "${verbData.pastM} / ${verbData.pastF}", ukrainianPhonetic(verbData.pastM), "you $meaning (past)", "Ти вчора успішно ${verbData.pastM}."),
                ConjugatedForm("він (Masculine)", verbData.pastM, ukrainianPhonetic(verbData.pastM), "he $meaning", "Він вчора ${verbData.pastM}."),
                ConjugatedForm("вона (Feminine)", verbData.pastF, ukrainianPhonetic(verbData.pastF), "she $meaning", "Вона вчора ${verbData.pastF}."),
                ConjugatedForm("воно (Neuter)", verbData.pastN, ukrainianPhonetic(verbData.pastN), "it $meaning", "Воно вже ${verbData.pastN}."),
                ConjugatedForm("ми / ви / вони (Plural)", verbData.pastPl, ukrainianPhonetic(verbData.pastPl), "we / you / they $meaning", "Ми разом учора ${verbData.pastPl}.")
            )
        )

        val futureTense = TenseConjugation(
            tenseName = "Майбутній час (Future Tense)",
            tenseCategory = "Indicative",
            description = "Дії, які відбудуться в майбутньому (складена аналітична форма: бути + інфінітив).",
            forms = pronouns.mapIndexed { idx, p ->
                val form = verbData.fut[idx]
                ConjugatedForm(
                    pronoun = p,
                    form = form,
                    phonetic = ukrainianPhonetic(form),
                    translation = "will $meaning",
                    exampleSentence = "$p обов'язково $form."
                )
            }
        )

        val imperativeTense = TenseConjugation(
            tenseName = "Наказовий спосіб (Imperative)",
            tenseCategory = "Imperative",
            description = "Спонукання до дії, заклик або ввічливе прохання.",
            forms = listOf(
                ConjugatedForm("ти (Informal)", "${verbData.impSg}!", ukrainianPhonetic(verbData.impSg), "do $meaning!", "${verbData.impSg}, будь ласка!"),
                ConjugatedForm("ми (Заклик / Інклюзив)", "${verbData.impPl.removeSuffix("те").removeSuffix("ть")}мо!", ukrainianPhonetic(verbData.impPl), "let's $meaning!", "Давайте разом ${verbData.impPl.removeSuffix("те").removeSuffix("ть")}мо!"),
                ConjugatedForm("ви (Formal / Plural)", "${verbData.impPl}!", ukrainianPhonetic(verbData.impPl), "please $meaning!", "${verbData.impPl}, будь ласка!")
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "uk",
            englishMeaning = meaning,
            regularType = verbData.typeDesc,
            auxiliaryVerb = "бути (буду, будеш...)",
            gerund = verbData.gerund,
            gerundPhonetic = ukrainianPhonetic(verbData.gerund),
            pastParticiple = verbData.participle,
            pastParticiplePhonetic = ukrainianPhonetic(verbData.participle),
            tenses = listOf(presentTense, pastTense, futureTense, imperativeTense)
        )
    }

    // ==========================================
    // RUSSIAN CONJUGATIONS (Русские спряжения)
    // ==========================================
    private fun generateRussianConjugations(verb: String, meaning: String): VerbTableData {
        val cleanVerb = verb.trim().lowercase()
        val isReflexive = cleanVerb.endsWith("ся") || cleanVerb.endsWith("сь")
        val base = if (isReflexive) cleanVerb.removeSuffix("ся").removeSuffix("сь") else cleanVerb

        val knownRuVerbs = mapOf(
            "быть" to SlavicVerbForms(
                pres = listOf("есть", "есть", "есть", "есть", "есть", "есть"),
                pastM = "был", pastF = "была", pastN = "было", pastPl = "были",
                fut = listOf("буду", "будешь", "будет", "будем", "будете", "будут"),
                impSg = "будь", impPl = "будьте",
                gerund = "будучи", participle = "бывший",
                typeDesc = "Неправильный глагол (быть)"
            ),
            "делать" to SlavicVerbForms(
                pres = listOf("делаю", "делаешь", "делает", "делаем", "делаете", "делают"),
                pastM = "делал", pastF = "делала", pastN = "делало", pastPl = "делали",
                fut = listOf("буду делать", "будешь делать", "будет делать", "будем делать", "будете делать", "будут делать"),
                impSg = "делай", impPl = "делайте",
                gerund = "делая", participle = "сделанный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "говорить" to SlavicVerbForms(
                pres = listOf("говорю", "говоришь", "говорит", "говорим", "говорите", "говорят"),
                pastM = "говорил", pastF = "говорила", pastN = "говорило", pastPl = "говорили",
                fut = listOf("буду говорить", "будешь говорить", "будет говорить", "будем говорить", "будете говорить", "будут говорить"),
                impSg = "говори", impPl = "говорите",
                gerund = "говоря", participle = "сказанный",
                typeDesc = "2-е спряжение (-ить)"
            ),
            "знать" to SlavicVerbForms(
                pres = listOf("знаю", "знаешь", "знает", "знаем", "знаете", "знают"),
                pastM = "знал", pastF = "знала", pastN = "знало", pastPl = "знали",
                fut = listOf("буду знать", "будешь знать", "будет знать", "будем знать", "будете знать", "будут знать"),
                impSg = "знай", impPl = "знайте",
                gerund = "зная", participle = "знаемый",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "идти" to SlavicVerbForms(
                pres = listOf("иду", "идёшь", "идёт", "идём", "идёте", "идут"),
                pastM = "шёл", pastF = "шла", pastN = "шло", pastPl = "шли",
                fut = listOf("буду идти", "будешь идти", "будет идти", "будем идти", "будете идти", "будут идти"),
                impSg = "иди", impPl = "идите",
                gerund = "идя", participle = "пройденный",
                typeDesc = "Неправильный глагол (идти)"
            ),
            "ходить" to SlavicVerbForms(
                pres = listOf("хожу", "ходишь", "ходит", "ходим", "ходите", "ходят"),
                pastM = "ходил", pastF = "ходила", pastN = "ходило", pastPl = "ходили",
                fut = listOf("буду ходить", "будешь ходить", "будет ходить", "будем ходить", "будете ходить", "будут ходить"),
                impSg = "ходи", impPl = "ходите",
                gerund = "ходя", participle = "пройденный",
                typeDesc = "2-е спряжение (д / ж)"
            ),
            "видеть" to SlavicVerbForms(
                pres = listOf("вижу", "видишь", "видит", "видим", "видите", "видят"),
                pastM = "видел", pastF = "видела", pastN = "видело", pastPl = "видели",
                fut = listOf("буду видеть", "будешь видеть", "будет видеть", "будем видеть", "будете видеть", "будут видеть"),
                impSg = "смотри", impPl = "смотрите",
                gerund = "видя", participle = "виденный",
                typeDesc = "2-е спряжение (исключение на -еть)"
            ),
            "думать" to SlavicVerbForms(
                pres = listOf("думаю", "думаешь", "думает", "думаем", "думаете", "думают"),
                pastM = "думал", pastF = "думала", pastN = "думало", pastPl = "думали",
                fut = listOf("буду думать", "будешь думать", "будет думать", "будем думать", "будете думать", "будут думать"),
                impSg = "думай", impPl = "думайте",
                gerund = "думая", participle = "продуманный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "хотеть" to SlavicVerbForms(
                pres = listOf("хочу", "хочешь", "хочет", "хотим", "хотите", "хотят"),
                pastM = "хотел", pastF = "хотела", pastN = "хотело", pastPl = "хотели",
                fut = listOf("буду хотеть", "будешь хотеть", "будет хотеть", "будем хотеть", "будете хотеть", "будут хотеть"),
                impSg = "хоти", impPl = "хотите",
                gerund = "хотя", participle = "желанный",
                typeDesc = "Разноспрягаемый глагол"
            ),
            "мочь" to SlavicVerbForms(
                pres = listOf("могу", "можешь", "может", "можем", "можете", "могут"),
                pastM = "мог", pastF = "могла", pastN = "могло", pastPl = "могли",
                fut = listOf("смогу", "сможешь", "сможет", "сможем", "сможете", "смогут"),
                impSg = "моги", impPl = "могите",
                gerund = "моля", participle = "возможный",
                typeDesc = "1-е спряжение (г / ж)"
            ),
            "жить" to SlavicVerbForms(
                pres = listOf("живу", "живёшь", "живёт", "живём", "живёте", "живут"),
                pastM = "жил", pastF = "жила", pastN = "жило", pastPl = "жили",
                fut = listOf("буду жить", "будешь жить", "будет жить", "будем жить", "будете жить", "будут жить"),
                impSg = "живи", impPl = "живите",
                gerund = "живя", participle = "прожитый",
                typeDesc = "1-е спряжение"
            ),
            "писать" to SlavicVerbForms(
                pres = listOf("пишу", "пишешь", "пишет", "пишем", "пишете", "пишут"),
                pastM = "писал", pastF = "писала", pastN = "писало", pastPl = "писали",
                fut = listOf("буду писать", "будешь писать", "будет писать", "будем писать", "будете писать", "будут писать"),
                impSg = "пиши", impPl = "пишите",
                gerund = "пиша", participle = "написанный",
                typeDesc = "1-е спряжение (с / ш)"
            ),
            "читать" to SlavicVerbForms(
                pres = listOf("читаю", "читаешь", "читает", "читаем", "читаете", "читают"),
                pastM = "читал", pastF = "читала", pastN = "читало", pastPl = "читали",
                fut = listOf("буду читать", "будешь читать", "будет читать", "будем читать", "будете читать", "будут читать"),
                impSg = "читай", impPl = "читайте",
                gerund = "читая", participle = "прочитанный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "любить" to SlavicVerbForms(
                pres = listOf("люблю", "любишь", "любит", "любим", "любите", "любят"),
                pastM = "любил", pastF = "любила", pastN = "любило", pastPl = "любили",
                fut = listOf("буду любить", "будешь любить", "будет любить", "будем любить", "будете любить", "будут любить"),
                impSg = "люби", impPl = "любите",
                gerund = "любя", participle = "любимый",
                typeDesc = "2-е спряжение (вставная л)"
            ),
            "слышать" to SlavicVerbForms(
                pres = listOf("слышу", "слышишь", "слышит", "слышим", "слышите", "слышат"),
                pastM = "слышал", pastF = "слышала", pastN = "слышало", pastPl = "слышали",
                fut = listOf("буду слышать", "будешь слышать", "будет слышать", "будем слышать", "будете слышать", "будут слышать"),
                impSg = "слушай", impPl = "слушайте",
                gerund = "слыша", participle = "услышанный",
                typeDesc = "2-е спряжение (исключение на -ать)"
            ),
            "есть" to SlavicVerbForms(
                pres = listOf("ем", "ешь", "ест", "едим", "едите", "едят"),
                pastM = "ел", pastF = "ела", pastN = "ело", pastPl = "ели",
                fut = listOf("буду есть", "будешь есть", "будет есть", "будем есть", "будете есть", "будут есть"),
                impSg = "ешь", impPl = "ешьте",
                gerund = "едя", participle = "съеденный",
                typeDesc = "Разноспрягаемый (древний) глагол"
            ),
            "пить" to SlavicVerbForms(
                pres = listOf("пью", "пьёшь", "пьёт", "пьём", "пьёте", "пьют"),
                pastM = "пил", pastF = "пила", pastN = "пило", pastPl = "пили",
                fut = listOf("буду пить", "будешь пить", "будет пить", "будем пить", "будете пить", "будут пить"),
                impSg = "пей", impPl = "пейте",
                gerund = "пия", participle = "выпитый",
                typeDesc = "1-е спряжение"
            ),
            "спать" to SlavicVerbForms(
                pres = listOf("сплю", "спишь", "спит", "спим", "спите", "спят"),
                pastM = "спал", pastF = "спала", pastN = "спало", pastPl = "спали",
                fut = listOf("буду спать", "будешь спать", "будет спать", "будем спать", "будете спать", "будут спать"),
                impSg = "спи", impPl = "спите",
                gerund = "спя", participle = "выспанный",
                typeDesc = "2-е спряжение (вставная л)"
            ),
            "иметь" to SlavicVerbForms(
                pres = listOf("имею", "имеешь", "имеет", "имеем", "имеете", "имеют"),
                pastM = "имел", pastF = "имела", pastN = "имело", pastPl = "имели",
                fut = listOf("буду иметь", "будешь иметь", "будет иметь", "будем иметь", "будете иметь", "будут иметь"),
                impSg = "имей", impPl = "имейте",
                gerund = "имея", participle = "имеющийся",
                typeDesc = "1-е спряжение (-еть)"
            ),
            "понимать" to SlavicVerbForms(
                pres = listOf("понимаю", "понимаешь", "понимает", "понимаем", "понимаете", "понимают"),
                pastM = "понимал", pastF = "понимала", pastN = "понимало", pastPl = "понимали",
                fut = listOf("буду понимать", "будешь понимать", "будет понимать", "будем понимать", "будете понимать", "будут понимать"),
                impSg = "понимай", impPl = "понимайте",
                gerund = "понимая", participle = "понятый",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "работать" to SlavicVerbForms(
                pres = listOf("работаю", "работаешь", "работает", "работаем", "работаете", "работают"),
                pastM = "работал", pastF = "работала", pastN = "работало", pastPl = "работали",
                fut = listOf("буду работать", "будешь работать", "будет работать", "будем работать", "будете работать", "будут работать"),
                impSg = "работай", impPl = "работайте",
                gerund = "работая", participle = "отработанный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "учить" to SlavicVerbForms(
                pres = listOf("учу", "учишь", "учит", "учим", "учите", "учат"),
                pastM = "учил", pastF = "учила", pastN = "учило", pastPl = "учили",
                fut = listOf("буду учить", "будешь учить", "будет учить", "будем учить", "будете учить", "будут учить"),
                impSg = "учи", impPl = "учите",
                gerund = "уча", participle = "выученный",
                typeDesc = "2-е спряжение (-ить)"
            ),
            "брать" to SlavicVerbForms(
                pres = listOf("беру", "берёшь", "берёт", "берём", "берёте", "берут"),
                pastM = "брал", pastF = "брала", pastN = "брало", pastPl = "брали",
                fut = listOf("буду брать", "будешь брать", "будет брать", "будем брать", "будете брать", "будут брать"),
                impSg = "бери", impPl = "берите",
                gerund = "беря", participle = "взятый",
                typeDesc = "1-е спряжение"
            ),
            "дать" to SlavicVerbForms(
                pres = listOf("дам", "дашь", "даст", "дадим", "дадите", "дадут"),
                pastM = "дал", pastF = "дала", pastN = "дало", pastPl = "дали",
                fut = listOf("дам", "дашь", "даст", "дадим", "дадите", "дадут"),
                impSg = "дай", impPl = "дайте",
                gerund = "давая", participle = "данный",
                typeDesc = "Разноспрягаемый глагол"
            ),
            "сказать" to SlavicVerbForms(
                pres = listOf("скажу", "скажешь", "скажет", "скажем", "скажете", "скажут"),
                pastM = "сказал", pastF = "сказала", pastN = "сказало", pastPl = "сказали",
                fut = listOf("скажу", "скажешь", "скажет", "скажем", "скажете", "скажут"),
                impSg = "скажи", impPl = "скажите",
                gerund = "сказав", participle = "сказанный",
                typeDesc = "1-е спряжение (з / ж)"
            ),
            "помогать" to SlavicVerbForms(
                pres = listOf("помогаю", "помогаешь", "помогает", "помогаем", "помогаете", "помогают"),
                pastM = "помогал", pastF = "помогала", pastN = "помогало", pastPl = "помогали",
                fut = listOf("буду помогать", "будешь помогать", "будет помогать", "будем помогать", "будете помогать", "будут помогать"),
                impSg = "помогай", impPl = "помогайте",
                gerund = "помогая", participle = "оказанный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "ждать" to SlavicVerbForms(
                pres = listOf("жду", "ждёшь", "ждёт", "ждём", "ждёте", "ждут"),
                pastM = "ждал", pastF = "ждала", pastN = "ждало", pastPl = "ждали",
                fut = listOf("буду ждать", "будешь ждать", "будет ждать", "будем ждать", "будете ждать", "будут ждать"),
                impSg = "жди", impPl = "ждите",
                gerund = "ждя", participle = "жданный",
                typeDesc = "1-е спряжение"
            ),
            "искать" to SlavicVerbForms(
                pres = listOf("ищу", "ищешь", "ищет", "ищем", "ищете", "ищут"),
                pastM = "искал", pastF = "искала", pastN = "искало", pastPl = "искали",
                fut = listOf("буду искать", "будешь искать", "будет искать", "будем искать", "будете искать", "будут искать"),
                impSg = "ищи", impPl = "ищите",
                gerund = "ища", participle = "найденный",
                typeDesc = "1-е спряжение (ск / щ)"
            ),
            "покупать" to SlavicVerbForms(
                pres = listOf("покупаю", "покупаешь", "покупает", "покупаем", "покупаете", "покупают"),
                pastM = "покупал", pastF = "покупала", pastN = "покупало", pastPl = "покупали",
                fut = listOf("буду покупать", "будешь покупать", "будет покупать", "будем покупать", "будете покупать", "будут покупать"),
                impSg = "покупай", impPl = "покупайте",
                gerund = "покупая", participle = "купленный",
                typeDesc = "1-е спряжение (-ать)"
            ),
            "слушать" to SlavicVerbForms(
                pres = listOf("слушаю", "слушаешь", "слушает", "слушаем", "слушаете", "слушают"),
                pastM = "слушал", pastF = "слушала", pastN = "слушало", pastPl = "слушали",
                fut = listOf("буду слушать", "будешь слушать", "будет слушать", "будем слушать", "будете слушать", "будут слушать"),
                impSg = "слушай", impPl = "слушайте",
                gerund = "слушая", participle = "послушанный",
                typeDesc = "1-е спряжение (-ать)"
            )
        )

        val verbData = knownRuVerbs[base] ?: run {
            // Algorithmic fallback for regular Russian verbs
            when {
                base.endsWith("овать") || base.endsWith("евать") -> {
                    val stem = base.removeSuffix("овать").removeSuffix("евать")
                    SlavicVerbForms(
                        pres = listOf("${stem}ую", "${stem}уешь", "${stem}ует", "${stem}уем", "${stem}уете", "${stem}уют"),
                        pastM = "${stem}овал", pastF = "${stem}овала", pastN = "${stem}овало", pastPl = "${stem}овали",
                        fut = listOf("буду $cleanVerb", "будешь $cleanVerb", "будет $cleanVerb", "будем $cleanVerb", "будете $cleanVerb", "будут $cleanVerb"),
                        impSg = "${stem}уй", impPl = "${stem}уйте",
                        gerund = "${stem}уя", participle = "${stem}ованный",
                        typeDesc = "1-е спряжение (-овать)"
                    )
                }
                base.endsWith("ать") || base.endsWith("ять") -> {
                    val stem = base.removeSuffix("ть")
                    SlavicVerbForms(
                        pres = listOf("${stem}ю", "${stem}ешь", "${stem}ет", "${stem}ем", "${stem}ете", "${stem}ют"),
                        pastM = "${stem}л", pastF = "${stem}ла", pastN = "${stem}ло", pastPl = "${stem}ли",
                        fut = listOf("буду $cleanVerb", "будешь $cleanVerb", "будет $cleanVerb", "будем $cleanVerb", "будете $cleanVerb", "будут $cleanVerb"),
                        impSg = "${stem}й", impPl = "${stem}йте",
                        gerund = "${stem}я", participle = "${stem}нный",
                        typeDesc = "1-е спряжение (-ать)"
                    )
                }
                base.endsWith("ить") || base.endsWith("еть") -> {
                    val stem = base.removeSuffix("ить").removeSuffix("еть")
                    SlavicVerbForms(
                        pres = listOf("${stem}ю", "${stem}ишь", "${stem}ит", "${stem}им", "${stem}ите", "${stem}ят"),
                        pastM = "${stem}ил", pastF = "${stem}ила", pastN = "${stem}ило", pastPl = "${stem}или",
                        fut = listOf("буду $cleanVerb", "будешь $cleanVerb", "будет $cleanVerb", "будем $cleanVerb", "будете $cleanVerb", "будут $cleanVerb"),
                        impSg = "${stem}и", impPl = "${stem}ите",
                        gerund = "${stem}я", participle = "${stem}енный",
                        typeDesc = "2-е спряжение (-ить/-еть)"
                    )
                }
                else -> {
                    val stem = base.removeSuffix("ть")
                    SlavicVerbForms(
                        pres = listOf("${stem}у", "${stem}ешь", "${stem}ет", "${stem}ем", "${stem}ете", "${stem}ут"),
                        pastM = "${stem}л", pastF = "${stem}ла", pastN = "${stem}ло", pastPl = "${stem}ли",
                        fut = listOf("буду $cleanVerb", "будешь $cleanVerb", "будет $cleanVerb", "будем $cleanVerb", "будете $cleanVerb", "будут $cleanVerb"),
                        impSg = "${stem}и", impPl = "${stem}ите",
                        gerund = "${stem}я", participle = "${stem}енный",
                        typeDesc = "Спряжение глагола"
                    )
                }
            }
        }

        val pronouns = listOf("я", "ты", "он / она / оно", "мы", "вы", "они")

        val presentTense = TenseConjugation(
            tenseName = "Настоящее время (Present Tense)",
            tenseCategory = "Indicative",
            description = "Описывает действия, происходящие в момент речи, привычные действия или факты.",
            forms = pronouns.mapIndexed { idx, p ->
                val form = verbData.pres[idx]
                ConjugatedForm(
                    pronoun = p,
                    form = form,
                    phonetic = russianPhonetic(form),
                    translation = "$p $meaning",
                    exampleSentence = "$p $form каждый день."
                )
            }
        )

        val pastTense = TenseConjugation(
            tenseName = "Прошедшее время (Past Tense)",
            tenseCategory = "Indicative",
            description = "Описывает действия, совершённые в прошлом. Изменяется по родам (м., ж., ср.) и числам.",
            forms = listOf(
                ConjugatedForm("я (м. / ж.)", "${verbData.pastM} / ${verbData.pastF}", russianPhonetic(verbData.pastM), "I $meaning (past)", "Я уже это делал / делала."),
                ConjugatedForm("ты (м. / ж.)", "${verbData.pastM} / ${verbData.pastF}", russianPhonetic(verbData.pastM), "you $meaning (past)", "Ты вчера ${verbData.pastM}."),
                ConjugatedForm("он (Masculine)", verbData.pastM, russianPhonetic(verbData.pastM), "he $meaning", "Он вчера ${verbData.pastM}."),
                ConjugatedForm("она (Feminine)", verbData.pastF, russianPhonetic(verbData.pastF), "she $meaning", "Она вчера ${verbData.pastF}."),
                ConjugatedForm("оно (Neuter)", verbData.pastN, russianPhonetic(verbData.pastN), "it $meaning", "Оно уже ${verbData.pastN}."),
                ConjugatedForm("мы / вы / они (Plural)", verbData.pastPl, russianPhonetic(verbData.pastPl), "we / you / they $meaning", "Мы вчера вместе ${verbData.pastPl}.")
            )
        )

        val futureTense = TenseConjugation(
            tenseName = "Будущее время (Future Tense)",
            tenseCategory = "Indicative",
            description = "Действия, которые произойдут в будущем (быть + инфинитив несовершенного вида).",
            forms = pronouns.mapIndexed { idx, p ->
                val form = verbData.fut[idx]
                ConjugatedForm(
                    pronoun = p,
                    form = form,
                    phonetic = russianPhonetic(form),
                    translation = "will $meaning",
                    exampleSentence = "$p обязательно $form."
                )
            }
        )

        val imperativeTense = TenseConjugation(
            tenseName = "Повелительное наклонение (Imperative)",
            tenseCategory = "Imperative",
            description = "Побуждение к действию, вежливая просьба или приказ.",
            forms = listOf(
                ConjugatedForm("ты (Неформально)", "${verbData.impSg}!", russianPhonetic(verbData.impSg), "do $meaning!", "${verbData.impSg}, пожалуйста!"),
                ConjugatedForm("мы (Совместное действие)", "давайте $cleanVerb!", russianPhonetic("давайте"), "let's $meaning!", "Давайте вместе $cleanVerb!"),
                ConjugatedForm("вы (Вежливо / Мн.ч.)", "${verbData.impPl}!", russianPhonetic(verbData.impPl), "please $meaning!", "${verbData.impPl}, пожалуйста!")
            )
        )

        return VerbTableData(
            infinitive = verb,
            languageCode = "ru",
            englishMeaning = meaning,
            regularType = verbData.typeDesc,
            auxiliaryVerb = "быть (буду, будешь...)",
            gerund = verbData.gerund,
            gerundPhonetic = russianPhonetic(verbData.gerund),
            pastParticiple = verbData.participle,
            pastParticiplePhonetic = russianPhonetic(verbData.participle),
            tenses = listOf(presentTense, pastTense, futureTense, imperativeTense)
        )
    }

    private fun ukrainianPhonetic(text: String): String {
        return text.map { ch ->
            when (ch) {
                'а' -> "a"
                'б' -> "b"
                'в' -> "v"
                'г' -> "h"
                'ґ' -> "g"
                'д' -> "d"
                'е' -> "e"
                'є' -> "ye"
                'ж' -> "zh"
                'з' -> "z"
                'и' -> "y"
                'і' -> "i"
                'ї' -> "yi"
                'й' -> "y"
                'к' -> "k"
                'л' -> "l"
                'м' -> "m"
                'н' -> "n"
                'о' -> "o"
                'п' -> "p"
                'р' -> "r"
                'с' -> "s"
                'т' -> "t"
                'у' -> "u"
                'ф' -> "f"
                'х' -> "kh"
                'ц' -> "ts"
                'ч' -> "ch"
                'ш' -> "sh"
                'щ' -> "shch"
                'ь' -> "'"
                'ю' -> "yu"
                'я' -> "ya"
                else -> ch.toString()
            }
        }.joinToString("")
    }

    private fun russianPhonetic(text: String): String {
        return text.map { ch ->
            when (ch) {
                'а' -> "a"
                'б' -> "b"
                'в' -> "v"
                'г' -> "g"
                'д' -> "d"
                'е' -> "ye"
                'ё' -> "yo"
                'ж' -> "zh"
                'з' -> "z"
                'и' -> "i"
                'й' -> "y"
                'к' -> "k"
                'л' -> "l"
                'м' -> "m"
                'н' -> "n"
                'о' -> "o"
                'п' -> "p"
                'р' -> "r"
                'с' -> "s"
                'т' -> "t"
                'у' -> "u"
                'ф' -> "f"
                'х' -> "kh"
                'ц' -> "ts"
                'ч' -> "ch"
                'ш' -> "sh"
                'щ' -> "shch"
                'ъ' -> ""
                'ы' -> "y"
                'ь' -> "'"
                'э' -> "e"
                'ю' -> "yu"
                'я' -> "ya"
                else -> ch.toString()
            }
        }.joinToString("")
    }
}
