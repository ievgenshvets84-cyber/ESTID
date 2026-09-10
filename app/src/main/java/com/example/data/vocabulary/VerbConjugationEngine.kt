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
}
