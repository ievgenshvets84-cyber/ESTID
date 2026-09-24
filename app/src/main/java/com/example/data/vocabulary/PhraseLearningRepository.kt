package com.example.data.vocabulary

object PhraseLearningRepository {

    fun getPhrases(targetLanguage: String, nativeLanguage: String = "de"): List<PhraseItem> {
        val rawList = when (targetLanguage.lowercase()) {
            "uk" -> getUkrainianPhrases()
            "ru" -> getRussianPhrases()
            "es" -> getSpanishPhrases()
            "fr" -> getFrenchPhrases()
            "de" -> getGermanPhrases()
            "en" -> getEnglishPhrases()
            "it" -> getItalianPhrases()
            "pt" -> getPortuguesePhrases()
            "ja" -> getJapanesePhrases()
            "zh" -> getChinesePhrases()
            "ko" -> getKoreanPhrases()
            else -> getEnglishPhrases()
        }

        // Return list; if nativeLanguage is not German ("de"), localize meanings
        return if (nativeLanguage.lowercase() == "de") {
            rawList
        } else {
            localizePhrases(rawList, nativeLanguage)
        }
    }

    // ==========================================
    // UKRAINIAN PHRASES & IDIOMS (Українські фрази)
    // ==========================================
    private fun getUkrainianPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "uk_idiom_1",
            languageCode = "uk",
            phrase = "Бити байдики",
            phonetic = "Byty baydyky",
            literalMeaning = "Holzstücke zerspalten / Schnitzen ohne Ziel",
            meaning = "Faulenzen, die Hände in den Schoß legen, Däumchen drehen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Досить бити байдики, час братися до роботи!",
            exampleTranslation = "Hör auf zu faulenzen, es ist Zeit sich an die Arbeit zu machen!",
            culturalTip = "Früher schnitzten Handwerker im Winter einfache Holzrohlinge (байдики) für Holzlöffel – eine sehr einfache, fast anstrengungslose Tätigkeit."
        ),
        PhraseItem(
            id = "uk_idiom_2",
            languageCode = "uk",
            phrase = "Водити за ніс",
            phonetic = "Vodyty za nis",
            literalMeaning = "An der Nase herumführen",
            meaning = "Jemanden täuschen, falsche Versprechungen machen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Не вір йому, він просто водить тебе за ніс.",
            exampleTranslation = "Glaube ihm nicht, er führt dich nur an der Nase herum.",
            culturalTip = "Stammt von Jahrmarktsbären, die an einem Nasenring geführt wurden, um Kunststücke zu zeigen."
        ),
        PhraseItem(
            id = "uk_idiom_3",
            languageCode = "uk",
            phrase = "Як кіт наплакав",
            phonetic = "Yak kit naplakav",
            literalMeaning = "Wie eine Katze geweint hat",
            meaning = "Verschwindend wenig, fast gar nichts",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Грошей у мене залишилося як кіт наплакав.",
            exampleTranslation = "Ich habe nur noch verschwindend wenig Geld übrig.",
            culturalTip = "Katzen weinen im biologischen Sinne keine Tränen – daher bedeutet es eine minimale Menge."
        ),
        PhraseItem(
            id = "uk_idiom_4",
            languageCode = "uk",
            phrase = "Брати ноги в руки",
            phonetic = "Braty nohy v ruky",
            literalMeaning = "Die Beine in die Hände nehmen",
            meaning = "Sich beeilen, schleunigst losrennen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Поїзд вирушає через десять хвилин, бери ноги в руки!",
            exampleTranslation = "Der Zug fährt in zehn Minuten ab, nimm die Beine in die Hand!",
            culturalTip = "Ein sehr populärer volkstümlicher Ausdruck in der Ukraine für höchste Eile."
        ),
        PhraseItem(
            id = "uk_idiom_5",
            languageCode = "uk",
            phrase = "Зробити з мухи слона",
            phonetic = "Zrobyty z mukhy slona",
            literalMeaning = "Aus einer Fliege einen Elefanten machen",
            meaning = "Eine Kleinigkeit maßlos übertreiben",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Не хвилюйся так сильно, ти робиш з мухи слона.",
            exampleTranslation = "Mach dir nicht so viele Sorgen, du machst aus einer Mücke einen Elefanten.",
            culturalTip = "Existiert in vielen slawischen Sprachen und betont Gelassenheit bei kleinen Problemen."
        ),
        PhraseItem(
            id = "uk_idiom_6",
            languageCode = "uk",
            phrase = "Пекти раків",
            phonetic = "Pekty rakiv",
            literalMeaning = "Krebse backen",
            meaning = "Rot anlaufen vor Scham oder Verlegenheit",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Коли його запитали про помилку, він почав пекти раків.",
            exampleTranslation = "Als man ihn nach dem Fehler fragte, lief er knallrot an.",
            culturalTip = "Krebse färben sich beim Kochen intensiv rot – die Metapher für plötzliches Erröten."
        ),
        PhraseItem(
            id = "uk_idiom_7",
            languageCode = "uk",
            phrase = "Тримати язик за зубами",
            phonetic = "Trymaty yazyk za zubamy",
            literalMeaning = "Die Zunge hinter den Zähnen halten",
            meaning = "Ein Geheimnis bewahren, den Mund halten",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Це секрет, тому тримай язик за зубами!",
            exampleTranslation = "Das ist ein Geheimnis, also halte deinen Mund!",
            culturalTip = "Sehr oft verwendet im familiären Kreis, um Diskretion zu fordern."
        ),
        PhraseItem(
            id = "uk_phrase_1",
            languageCode = "uk",
            phrase = "Скільки це коштує?",
            phonetic = "Skil'ky tse koshtuye?",
            literalMeaning = "Wie viel das kostet?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Вибачте, скільки коштує ця кава?",
            exampleTranslation = "Entschuldigung, wie viel kostet dieser Kaffee?"
        ),
        PhraseItem(
            id = "uk_phrase_2",
            languageCode = "uk",
            phrase = "Чи можу я розрахуватися карткою?",
            phonetic = "Chy mozhu ya rozrakhuvatysya kartkoyu?",
            literalMeaning = "Ob kann ich abrechnen mit der Karte?",
            meaning = "Kann ich mit Karte zahlen?",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "Підкажіть, будь ласка, чи можу я розрахуватися карткою?",
            exampleTranslation = "Sagen Sie bitte, kann ich mit Karte zahlen?"
        ),
        PhraseItem(
            id = "uk_phrase_3",
            languageCode = "uk",
            phrase = "Де знаходиться найближча аптека?",
            phonetic = "De znakhodyt'sya nayblyzhcha apteka?",
            literalMeaning = "Wo befindet sich nächste Apotheke?",
            meaning = "Wo ist die nächste Apotheke?",
            category = PhraseCategory.TRAVEL,
            isIdiom = false,
            exampleSentence = "Скажіть, будь ласка, де знаходиться найближча аптека?",
            exampleTranslation = "Können Sie mir bitte sagen, wo die nächste Apotheke ist?"
        ),
        PhraseItem(
            id = "uk_phrase_4",
            languageCode = "uk",
            phrase = "Все буде добре!",
            phonetic = "Vse bude dobre!",
            literalMeaning = "Alles wird gut!",
            meaning = "Alles wird gut! (Zuversicht & Trost)",
            category = PhraseCategory.FEELINGS,
            isIdiom = false,
            exampleSentence = "Не переживай, все буде добре!",
            exampleTranslation = "Mach dir keine Sorgen, alles wird gut!",
            culturalTip = "Ein zutiefst bedeutsamer, herzlicher Ausdruck gegenseitiger Ermutigung in der ukrainischen Kultur."
        ),
        PhraseItem(
            id = "uk_phrase_5",
            languageCode = "uk",
            phrase = "Допоможіть мені, будь ласка!",
            phonetic = "Dopomozhit' meni, bud' laska!",
            literalMeaning = "Helfen Sie mir, bitte!",
            meaning = "Helfen Sie mir bitte!",
            category = PhraseCategory.EMERGENCY,
            isIdiom = false,
            exampleSentence = "Вибачте, допоможіть мені, будь ласка, я загубив квиток.",
            exampleTranslation = "Entschuldigung, helfen Sie mir bitte, ich habe mein Ticket verloren."
        ),
        PhraseItem(
            id = "uk_phrase_6",
            languageCode = "uk",
            phrase = "Радий був познайомитися!",
            phonetic = "Radyy buv poznayomytysya!",
            literalMeaning = "Froh war kennenlernen!",
            meaning = "Freut mich, Sie kennengelernt zu haben!",
            category = PhraseCategory.BUSINESS,
            isIdiom = false,
            exampleSentence = "Дякую за зустріч, радий був познайомитися!",
            exampleTranslation = "Danke für das Treffen, es hat mich sehr gefreut Sie kennenzulernen!"
        )
    )

    // ==========================================
    // RUSSIAN PHRASES & IDIOMS (Русские фразы)
    // ==========================================
    private fun getRussianPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "ru_idiom_1",
            languageCode = "ru",
            phrase = "Вешать лапшу на уши",
            phonetic = "Veshat' lapshu na ushi",
            literalMeaning = "Nudeln auf die Ohren hängen",
            meaning = "Jemanden anlügen, einen Bären aufbinden",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Хватит вешать мне лапшу на уши, я знаю правду!",
            exampleTranslation = "Hör auf mir Nudeln auf die Ohren zu hängen, ich kenne die Wahrheit!",
            culturalTip = "Eines der bekanntesten russischen Idiome. Vermutlich entstanden aus der Gaunersprache des 19. Jahrhunderts."
        ),
        PhraseItem(
            id = "ru_idiom_2",
            languageCode = "ru",
            phrase = "Бить баклуши",
            phonetic = "Bit' baklushi",
            literalMeaning = "Holzklötze schlagen",
            meaning = "Däumchen drehen, faulenzen, Zeit vergeuden",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Он целыми днями бьёт баклуши вместо учёбы.",
            exampleTranslation = "Er faulenzt den ganzen Tag, anstatt zu lernen.",
            culturalTip = "Bischläger (баклуши) waren Holzblöcke für Löffel; dies galt als einfachste Kinderarbeit, daher die Bedeutung 'nichtstun'."
        ),
        PhraseItem(
            id = "ru_idiom_3",
            languageCode = "ru",
            phrase = "Кот наплакал",
            phonetic = "Kot naplakal",
            literalMeaning = "Der Kater hat geweint",
            meaning = "Sehr wenig, verschwindend geringe Menge",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Времени у нас осталось кот наплакал.",
            exampleTranslation = "Wir haben nur noch verschwindend wenig Zeit übrig.",
            culturalTip = "Katzen weinen im biologischen Sinne keine Tränen, daher bedeutet es 'praktisch nichts'."
        ),
        PhraseItem(
            id = "ru_idiom_4",
            languageCode = "ru",
            phrase = "Взять себя в руки",
            phonetic = "Vzyat' sebya v ruki",
            literalMeaning = "Sich selbst in die Hände nehmen",
            meaning = "Sich zusammenreißen, die Fassung wiedererlangen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Успокойся, возьми себя в руки и сосредоточься.",
            exampleTranslation = "Beruhige dich, reiß dich zusammen und konzentriere dich.",
            culturalTip = "Universell genutzt in emotionalen Momenten zur Selbstkontrolle."
        ),
        PhraseItem(
            id = "ru_idiom_5",
            languageCode = "ru",
            phrase = "Делать из мухи слона",
            phonetic = "Delat' iz mukhi slona",
            literalMeaning = "Aus einer Fliege einen Elefanten machen",
            meaning = "Eine Kleinigkeit übertrieben dramatisieren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Не переживай из-за ерунды, не делай из мухи слона.",
            exampleTranslation = "Mach dir keine Sorgen um Nichtigkeiten, mach keine Mücke zum Elefanten.",
            culturalTip = "Geht auf antike griechische Satiren zurück und ist fester Bestandteil des russischen Sprachgebrauchs."
        ),
        PhraseItem(
            id = "ru_idiom_6",
            languageCode = "ru",
            phrase = "Держать язык за зубами",
            phonetic = "Derzhat' yazyk za zubami",
            literalMeaning = "Die Zunge hinter den Zähnen halten",
            meaning = "Den Mund halten, Stillschweigen bewahren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Если хочешь сохранить дружбу, держи язык за зубами.",
            exampleTranslation = "Wenn du die Freundschaft bewahren willst, halte deinen Mund.",
            culturalTip = "Rät zur Klugheit des Schweigens im richtigen Moment."
        ),
        PhraseItem(
            id = "ru_phrase_1",
            languageCode = "ru",
            phrase = "Сколько это стоит?",
            phonetic = "Skol'ko eto stoit?",
            literalMeaning = "Wie viel das kostet?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Скажите, пожалуйста, сколько это стоит?",
            exampleTranslation = "Sagen Sie bitte, wie viel kostet das?"
        ),
        PhraseItem(
            id = "ru_phrase_2",
            languageCode = "ru",
            phrase = "Можно оплатить картой?",
            phonetic = "Mozhno oplatit' kartoy?",
            literalMeaning = "Kann man bezahlen mit Karte?",
            meaning = "Kann man mit Karte zahlen?",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "Подскажите, у вас можно оплатить картой?",
            exampleTranslation = "Können Sie mir sagen, kann man bei Ihnen mit Karte zahlen?"
        ),
        PhraseItem(
            id = "ru_phrase_3",
            languageCode = "ru",
            phrase = "Где находится метро?",
            phonetic = "Gde nakhoditsya metro?",
            literalMeaning = "Wo befindet sich Metro?",
            meaning = "Wo ist die U-Bahn-Station?",
            category = PhraseCategory.TRAVEL,
            isIdiom = false,
            exampleSentence = "Извините, вы не подскажете, где находится метро?",
            exampleTranslation = "Entschuldigung, könnten Sie mir sagen, wo die Metro ist?"
        ),
        PhraseItem(
            id = "ru_phrase_4",
            languageCode = "ru",
            phrase = "Приятно познакомиться!",
            phonetic = "Priyatno poznakomit'sya!",
            literalMeaning = "Angenehm kennenlernen!",
            meaning = "Schön, Sie kennenzulernen!",
            category = PhraseCategory.BUSINESS,
            isIdiom = false,
            exampleSentence = "Меня зовут Анна. Очень приятно познакомиться!",
            exampleTranslation = "Mein Name ist Anna. Sehr angenehm Sie kennenzulernen!"
        ),
        PhraseItem(
            id = "ru_phrase_5",
            languageCode = "ru",
            phrase = "Помогите, пожалуйста!",
            phonetic = "Pomogite, pozhaluysta!",
            literalMeaning = "Helfen Sie, bitte!",
            meaning = "Helfen Sie mir bitte!",
            category = PhraseCategory.EMERGENCY,
            isIdiom = false,
            exampleSentence = "Помогите, пожалуйста, мне нужно вызвать скорую!",
            exampleTranslation = "Helfen Sie bitte, ich muss den Krankenwagen rufen!"
        )
    )

    // ==========================================
    // SPANISH PHRASES & IDIOMS (Frases en español)
    // ==========================================
    private fun getSpanishPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "es_idiom_1",
            languageCode = "es",
            phrase = "Costar un ojo de la cara",
            phonetic = "kos-TAR oon O-kho de la KA-ra",
            literalMeaning = "Ein Auge aus dem Gesicht kosten",
            meaning = "Ein Vermögen kosten, extrem teuer sein",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Ese coche nuevo me costó un ojo de la cara.",
            exampleTranslation = "Dieses neue Auto hat mich ein Vermögen gekostet.",
            culturalTip = "Geht historisch auf Konquistador Diego de Almagro zurück, der bei einer Schlacht ein Auge verlor und sagte: 'Verteidigen kostete ein Auge'."
        ),
        PhraseItem(
            id = "es_idiom_2",
            languageCode = "es",
            phrase = "Tomar el pelo",
            phonetic = "to-MAR el PE-lo",
            literalMeaning = "Am Haar ziehen / nehmen",
            meaning = "Jemanden auf den Arm nehmen, veräppeln",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "¿De verdad ganaste la lotería o me estás tomando el pelo?",
            exampleTranslation = "Hast du wirklich im Lotto gewonnen oder nimmst du mich auf den Arm?",
            culturalTip = "Im alten Spanien galt der Bart als Symbol der Ehre; an ihm zu ziehen war eine freche Respektlosigkeit."
        ),
        PhraseItem(
            id = "es_idiom_3",
            languageCode = "es",
            phrase = "Estar en las nubes",
            phonetic = "es-TAR en las NOO-bes",
            literalMeaning = "In den Wolken sein",
            meaning = "Geistig abwesend sein, träumen, unkonzentriert sein",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "No escuchaste nada de lo que dije porque estabas en las nubes.",
            exampleTranslation = "Du hast nichts gehört von dem was ich sagte, weil du mit dem Kopf in den Wolken warst."
        ),
        PhraseItem(
            id = "es_idiom_4",
            languageCode = "es",
            phrase = "Echar una mano",
            phonetic = "e-CHAR OO-na MA-no",
            literalMeaning = "Eine Hand hinwerfen",
            meaning = "Jemandem zur Hand gehen, Hilfe leisten",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "¿Podrías echarme una mano con estas cajas pesadas?",
            exampleTranslation = "Könntest du mir kurz mit diesen schweren Kisten helfen?"
        ),
        PhraseItem(
            id = "es_idiom_5",
            languageCode = "es",
            phrase = "Tirar la toalla",
            phonetic = "tee-RAR la to-A-lya",
            literalMeaning = "Das Handtuch werfen",
            meaning = "Aufgeben, kapitulieren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "El examen es difícil, pero no voy a tirar la toalla.",
            exampleTranslation = "Die Prüfung ist schwer, aber ich werde nicht das Handtuch werfen."
        ),
        PhraseItem(
            id = "es_phrase_1",
            languageCode = "es",
            phrase = "¿Cuánto cuesta esto?",
            phonetic = "KWAN-to KWES-ta ES-to",
            literalMeaning = "Wie viel kostet dies?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Disculpe, ¿cuánto cuesta esta camiseta?",
            exampleTranslation = "Entschuldigen Sie, wie viel kostet dieses T-Shirt?"
        ),
        PhraseItem(
            id = "es_phrase_2",
            languageCode = "es",
            phrase = "La cuenta, por favor.",
            phonetic = "la KWEN-ta por fa-VOR",
            literalMeaning = "Die Rechnung, bitte.",
            meaning = "Die Rechnung bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "Camarero, ¿nos trae la cuenta, por favor?",
            exampleTranslation = "Kellner, bringen Sie uns bitte die Rechnung?"
        ),
        PhraseItem(
            id = "es_phrase_3",
            languageCode = "es",
            phrase = "¿Dónde está la estación?",
            phonetic = "DON-de es-TA la es-ta-SYON",
            literalMeaning = "Wo ist die Station?",
            meaning = "Wo befindet sich der Bahnhof?",
            category = PhraseCategory.TRAVEL,
            isIdiom = false,
            exampleSentence = "¿Dónde está la estación de tren más cercana?",
            exampleTranslation = "Wo ist der nächste Bahnhof?"
        )
    )

    // ==========================================
    // FRENCH PHRASES & IDIOMS (Expressions françaises)
    // ==========================================
    private fun getFrenchPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "fr_idiom_1",
            languageCode = "fr",
            phrase = "Poser un lapin",
            phonetic = "po-zay uhn la-pan",
            literalMeaning = "Ein Kaninchen absetzen",
            meaning = "Jemanden versetzen, zu einer Verabredung nicht erscheinen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Je l'ai attendue une heure, elle m'a posé un lapin.",
            exampleTranslation = "Ich habe eine Stunde auf sie gewartet, sie hat mich versetzt.",
            culturalTip = "Im 19. Jahrhundert bedeutete es im Argot, die Gunst einer Frau nicht zu bezahlen; heute bedeutet es 'nicht zum Treffen kommen'."
        ),
        PhraseItem(
            id = "fr_idiom_2",
            languageCode = "fr",
            phrase = "Avoir le coup de foudre",
            phonetic = "ah-vwahr luh koo duh foodr",
            literalMeaning = "Vom Blitzschlag getroffen werden",
            meaning = "Liebe auf den ersten Blick verspüren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Dès que je l'ai vue, j'ai eu un coup de foudre.",
            exampleTranslation = "Sobald ich sie sah, war es Liebe auf den ersten Blick."
        ),
        PhraseItem(
            id = "fr_idiom_3",
            languageCode = "fr",
            phrase = "Tomber dans les pommes",
            phonetic = "tohn-bay dahn lay pom",
            literalMeaning = "In die Äpfel fallen",
            meaning = "In Ohnmacht fallen, kollabieren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Il faisait tellement chaud qu'il est tombé dans les pommes.",
            exampleTranslation = "Es war so heiß, dass er in Ohnmacht gefallen ist.",
            culturalTip = "Wahrscheinlich eine lautliche Verballhornung des altfranzösischen Wortes 'pâmer' (in Ohnmacht fallen)."
        ),
        PhraseItem(
            id = "fr_idiom_4",
            languageCode = "fr",
            phrase = "C'est la fin des haricots",
            phonetic = "say la fan day ah-ree-ko",
            literalMeaning = "Das ist das Ende der Bohnen",
            meaning = "Jetzt ist alles vorbei / Hopfen und Malz verloren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "S'il ne vient pas aujourd'hui, c'est la fin des haricots.",
            exampleTranslation = "Wenn er heute nicht kommt, ist alles verloren."
        ),
        PhraseItem(
            id = "fr_phrase_1",
            languageCode = "fr",
            phrase = "Combien ça coûte ?",
            phonetic = "kohn-byan sah koot",
            literalMeaning = "Wie viel das kostet?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Excusez-moi, combien ça coûte ce livre ?",
            exampleTranslation = "Entschuldigung, wie viel kostet dieses Buch?"
        ),
        PhraseItem(
            id = "fr_phrase_2",
            languageCode = "fr",
            phrase = "L'addition, s'il vous plaît.",
            phonetic = "la-dee-syon seel voo pleh",
            literalMeaning = "Die Addition / Rechnung, wenn es Ihnen gefällt.",
            meaning = "Die Rechnung, bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "Monsieur, nous aimerions l'addition, s'il vous plaît.",
            exampleTranslation = "Herr Ober, wir hätten gerne die Rechnung, bitte."
        )
    )

    // ==========================================
    // GERMAN PHRASES & IDIOMS (Deutsche Redewendungen)
    // ==========================================
    private fun getGermanPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "de_idiom_1",
            languageCode = "de",
            phrase = "Da liegt der Hund begraben",
            phonetic = "da leekt der hoont buh-GRAH-ben",
            literalMeaning = "Hier ist der Hund begraben",
            meaning = "Das ist die eigentliche Ursache / der Kern des Problems",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Er will nicht investieren – genau da liegt der Hund begraben.",
            exampleTranslation = "Er will nicht investieren – genau da liegt das eigentliche Problem.",
            culturalTip = "Wahrscheinlich aus dem mittelhochdeutschen 'Hundt' (Bezeichnung für einen Schatzkasten)."
        ),
        PhraseItem(
            id = "de_idiom_2",
            languageCode = "de",
            phrase = "Ich verstehe nur Bahnhof",
            phonetic = "ikh fer-SHTAY-uh noor BAHN-hof",
            literalMeaning = "Ich verstehe nur Bahnhof",
            meaning = "Ich verstehe überhaupt nichts, kein einziges Wort",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Kannst du das nochmals erklären? Ich verstehe nur Bahnhof.",
            exampleTranslation = "Kannst du das noch einmal erklären? Ich kapiere gar nichts.",
            culturalTip = "Entstand um den Ersten Weltkrieg bei Soldaten, die nur noch an die Heimfahrt am Bahnhof denken konnten."
        ),
        PhraseItem(
            id = "de_idiom_3",
            languageCode = "de",
            phrase = "Die Daumen drücken",
            phonetic = "dee DOW-men DROO-ken",
            literalMeaning = "Die Daumen pressen",
            meaning = "Viel Glück und Erfolg wünschen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Morgen hast du die Prüfung? Ich drücke dir ganz fest die Daumen!",
            exampleTranslation = "Morgen hast du deine Prüfung? Ich wünsche dir ganz viel Erfolg!"
        ),
        PhraseItem(
            id = "de_idiom_4",
            languageCode = "de",
            phrase = "Tomaten auf den Augen haben",
            phonetic = "to-MAH-ten owf dayn OW-gen HAH-ben",
            literalMeaning = "Tomaten auf den Augen haben",
            meaning = "Etwas Offensichtliches einfach übersehen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Der Schlüssel liegt doch direkt vor dir! Hast du Tomaten auf den Augen?",
            exampleTranslation = "Der Schlüssel liegt doch direkt vor dir! Siehst du das nicht?"
        ),
        PhraseItem(
            id = "de_phrase_1",
            languageCode = "de",
            phrase = "Wie viel kostet das?",
            phonetic = "vee feel KOS-tet das",
            literalMeaning = "Wie viel kostet das?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Entschuldigen Sie bitte, wie viel kostet dieses Buch?",
            exampleTranslation = "Entschuldigen Sie bitte, wie viel kostet dieses Buch?"
        ),
        PhraseItem(
            id = "de_phrase_2",
            languageCode = "de",
            phrase = "Können Sie mir helfen?",
            phonetic = "KER-nen zee meer HEL-fen",
            literalMeaning = "Können Sie mir helfen?",
            meaning = "Können Sie mir bitte helfen?",
            category = PhraseCategory.EMERGENCY,
            isIdiom = false,
            exampleSentence = "Guten Tag, können Sie mir bitte den Weg zum Bahnhof zeigen?",
            exampleTranslation = "Guten Tag, können Sie mir bitte den Weg zum Bahnhof zeigen?"
        )
    )

    // ==========================================
    // ENGLISH PHRASES & IDIOMS (English expressions)
    // ==========================================
    private fun getEnglishPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "en_idiom_1",
            languageCode = "en",
            phrase = "Piece of cake",
            phonetic = "peess uhv kayk",
            literalMeaning = "Ein Stück Kuchen",
            meaning = "Ein Kinderspiel, kinderleicht, mühelos",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Don't worry about the test, it's going to be a piece of cake.",
            exampleTranslation = "Mach dir keine Sorgen wegen des Tests, das wird ein Kinderspiel.",
            culturalTip = "Entstammt Wettbewerben im 19. Jahrhundert, bei denen Kuchen als einfachster Preis galt."
        ),
        PhraseItem(
            id = "en_idiom_2",
            languageCode = "en",
            phrase = "Break a leg",
            phonetic = "brayk uh leg",
            literalMeaning = "Brich dir ein Bein",
            meaning = "Hals- und Beinbruch! Viel Erfolg!",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "You're going on stage now? Break a leg!",
            exampleTranslation = "Du gehst jetzt auf die Bühne? Hals- und Beinbruch!",
            culturalTip = "Alter Theateraberglaube: Direktes Glückwünschen bringt Unglück, daher wünscht man das Gegenteil."
        ),
        PhraseItem(
            id = "en_idiom_3",
            languageCode = "en",
            phrase = "Bite the bullet",
            phonetic = "bayt thuh BOOL-it",
            literalMeaning = "Auf die Kugel beißen",
            meaning = "In den sauren Apfel beißen, eine harte Notwendigkeit ertragen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "I really hate dental visits, but I just have to bite the bullet.",
            exampleTranslation = "Ich hasse Zahnarztbesuche, aber ich muss wohl in den sauren Apfel beißen."
        ),
        PhraseItem(
            id = "en_idiom_4",
            languageCode = "en",
            phrase = "Once in a blue moon",
            phonetic = "wuhns in uh bloo moon",
            literalMeaning = "Einmal bei blauem Mond",
            meaning = "Extrem selten, alle Jubeljahre einmal",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "He visits his hometown once in a blue moon.",
            exampleTranslation = "Er besucht seine Heimatstadt nur alle Jubeljahre einmal."
        ),
        PhraseItem(
            id = "en_phrase_1",
            languageCode = "en",
            phrase = "How much does this cost?",
            phonetic = "how much duhz this kost",
            literalMeaning = "Wie viel tut dies kosten?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Excuse me, how much does this coffee cost?",
            exampleTranslation = "Entschuldigung, wie viel kostet dieser Kaffee?"
        ),
        PhraseItem(
            id = "en_phrase_2",
            languageCode = "en",
            phrase = "Could you give me a hand?",
            phonetic = "kood yoo giv mee uh hand",
            literalMeaning = "Könntest du mir eine Hand geben?",
            meaning = "Könntest du mir kurz helfen?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Could you give me a hand carrying these groceries?",
            exampleTranslation = "Könntest du mir kurz beim Tragen der Einkäufe helfen?"
        )
    )

    // ==========================================
    // ITALIAN PHRASES & IDIOMS (Modi di dire italiani)
    // ==========================================
    private fun getItalianPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "it_idiom_1",
            languageCode = "it",
            phrase = "In bocca al lupo",
            phonetic = "een BOK-ka al LOO-po",
            literalMeaning = "In das Maul des Wolfes",
            meaning = "Viel Glück! (Antwort: 'Crepi il lupo!' oder 'Viva il lupo!')",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Hai l'esame domani? In bocca al lupo!",
            exampleTranslation = "Hast du morgen die Prüfung? Viel Glück!",
            culturalTip = "Ein alter Jägergruß; die Wölfin trug ihre Jungen im Maul, daher symbolisiert es auch mütterlichen Schutz."
        ),
        PhraseItem(
            id = "it_idiom_2",
            languageCode = "it",
            phrase = "Costare un occhio della testa",
            phonetic = "kos-TA-re oon OK-kyo DEL-la TES-ta",
            literalMeaning = "Ein Auge des Kopfes kosten",
            meaning = "Unbezahlbar teuer sein, ein Vermögen kosten",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Quella cena al ristorante di lusso mi è costata un occhio della testa.",
            exampleTranslation = "Dieses Abendessen im Luxusrestaurant hat mich ein Vermögen gekostet."
        ),
        PhraseItem(
            id = "it_idiom_3",
            languageCode = "it",
            phrase = "Prendere due piccioni con una fava",
            phonetic = "PREN-de-re DOO-ay pee-CHO-nee kon OO-na FA-va",
            literalMeaning = "Zwei Tauben mit einer Bohne fangen",
            meaning = "Zwei Fliegen mit einer Klappe schlagen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Andando a Roma vedrò un cliente e visiterò i musei: due piccioni con una fava.",
            exampleTranslation = "Wenn ich nach Rom fahre, treffe ich einen Kunden und sehe Museen: zwei Fliegen mit einer Klappe."
        ),
        PhraseItem(
            id = "it_phrase_1",
            languageCode = "it",
            phrase = "Il conto, per favore.",
            phonetic = "eel KON-to per fa-VO-re",
            literalMeaning = "Die Rechnung, bitte.",
            meaning = "Die Rechnung bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "Cameriere, ci porta il conto, per favore?",
            exampleTranslation = "Kellner, bringen Sie uns bitte die Rechnung?"
        )
    )

    // ==========================================
    // PORTUGUESE PHRASES & IDIOMS (Expressões portuguesas)
    // ==========================================
    private fun getPortuguesePhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "pt_idiom_1",
            languageCode = "pt",
            phrase = "Custar os olhos da cara",
            phonetic = "koos-TAR ooz O-lyooz da KA-ra",
            literalMeaning = "Die Augen des Gesichts kosten",
            meaning = "Extrem teuer sein, ein Vermögen kosten",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Comprar aquela casa nova custou os olhos da cara.",
            exampleTranslation = "Der Kauf dieses neuen Hauses hat ein Vermögen gekostet."
        ),
        PhraseItem(
            id = "pt_idiom_2",
            languageCode = "pt",
            phrase = "Dar uma mãozinha",
            phonetic = "dar OO-ma mohn-ZEEN-ya",
            literalMeaning = "Ein Händchen geben",
            meaning = "Kurz zur Hand gehen, schnell helfen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Você pode me dar uma mãozinha aqui?",
            exampleTranslation = "Kannst du mir hier kurz zur Hand gehen?"
        ),
        PhraseItem(
            id = "pt_idiom_3",
            languageCode = "pt",
            phrase = "Chutar o balde",
            phonetic = "shoo-TAR oo BAL-jee",
            literalMeaning = "Den Eimer treten",
            meaning = "Alles hinschmeißen, die Geduld verlieren",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "Depois de horas de discussão, ele chutou o balde e foi embora.",
            exampleTranslation = "Nach stundenlanger Diskussion schmiss er alles hin und ging weg."
        ),
        PhraseItem(
            id = "pt_phrase_1",
            languageCode = "pt",
            phrase = "Quanto custa isto?",
            phonetic = "KWAN-too KOOS-ta EESH-too",
            literalMeaning = "Wie viel kostet dies?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "Com licença, quanto custa este café?",
            exampleTranslation = "Entschuldigung, wie viel kostet dieser Kaffee?"
        )
    )

    // ==========================================
    // JAPANESE PHRASES & IDIOMS (日本のことわざ & 慣用句)
    // ==========================================
    private fun getJapanesePhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "ja_idiom_1",
            languageCode = "ja",
            phrase = "猿も木から落ちる",
            phonetic = "Saru mo ki kara ochiru",
            literalMeaning = "Auch Affen fallen vom Baum",
            meaning = "Niemand ist perfekt; auch Experten machen Fehler",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "プロでもミスをする。猿も木から落ちるだよ。",
            exampleTranslation = "Auch Profis machen Fehler. Selbst Affen fallen mal vom Baum.",
            culturalTip = "Klassisches japanisches Kotowaza (Sprichwort), das zu Bescheidenheit und Nachsicht mahnt."
        ),
        PhraseItem(
            id = "ja_idiom_2",
            languageCode = "ja",
            phrase = "猫の手も借りたい",
            phonetic = "Neko no te mo karitai",
            literalMeaning = "Sogar die Pfote einer Katze ausleihen wollen",
            meaning = "Extrem beschäftigt sein, jede noch so kleine Hilfe brauchen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "年末は忙しくて、猫の手も借りたいほどです。",
            exampleTranslation = "Am Jahresende ist es so hektisch, dass wir jede Hilfe gebrauchen könnten."
        ),
        PhraseItem(
            id = "ja_idiom_3",
            languageCode = "ja",
            phrase = "七転び八起き",
            phonetic = "Nana korobi ya oki",
            literalMeaning = "Siebenmal fallen, achtmal aufstehen",
            meaning = "Unbeirrt weitermachen, niemals aufgeben",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "人生は七転び八起きだ。諦めないで！",
            exampleTranslation = "Das Leben besteht aus siebenmal hinfallen und achtmal aufstehen. Gib niemals auf!"
        ),
        PhraseItem(
            id = "ja_phrase_1",
            languageCode = "ja",
            phrase = "これはいくらですか？",
            phonetic = "Kore wa ikura desu ka?",
            literalMeaning = "Dies wie viel ist es?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "すみません、これはいくらですか？",
            exampleTranslation = "Entschuldigung, wie viel kostet das?"
        ),
        PhraseItem(
            id = "ja_phrase_2",
            languageCode = "ja",
            phrase = "お会計をお願いします。",
            phonetic = "O-kaikei o onegai shimasu.",
            literalMeaning = "Die Abrechnung erbitte ich höflich.",
            meaning = "Die Rechnung bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "ごちそうさまでした。お会計をお願いします。",
            exampleTranslation = "Vielen Dank für das Essen. Die Rechnung bitte."
        )
    )

    // ==========================================
    // CHINESE PHRASES & IDIOMS (中文成语与日常短语)
    // ==========================================
    private fun getChinesePhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "zh_idiom_1",
            languageCode = "zh",
            phrase = "一石二鸟",
            phonetic = "Yī shí èr niǎo",
            literalMeaning = "Ein Stein, zwei Vögel",
            meaning = "Zwei Fliegen mit einer Klappe schlagen",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "学习外语既能锻炼大脑又能认识新朋友，真是一石二鸟。",
            exampleTranslation = "Fremdsprachen zu lernen trainiert das Gehirn und bringt neue Freunde – zwei Fliegen mit einer Klappe."
        ),
        PhraseItem(
            id = "zh_idiom_2",
            languageCode = "zh",
            phrase = "马到成功",
            phonetic = "Mǎ dào chéng gōng",
            literalMeaning = "Das Pferd trifft ein und der Erfolg ist da",
            meaning = "Sofortiger, glänzender Erfolg von Anfang an",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "祝你的新项目马到成功！",
            exampleTranslation = "Ich wünsche deinem neuen Projekt sofortigen und vollen Erfolg!",
            culturalTip = "Klassisches vierstelliges Chengyu (成语), das oft zu Neujahr oder bei Geschäftsgründungen gewünscht wird."
        ),
        PhraseItem(
            id = "zh_idiom_3",
            languageCode = "zh",
            phrase = "塞翁失马",
            phonetic = "Sài wēng shī mǎ",
            literalMeaning = "Der alte Grenzer verlor sein Pferd",
            meaning = "Glück im Unglück; ein scheinbarer Verlust kann sich zum Guten wenden",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "别太难过，塞翁失马，焉知非福。",
            exampleTranslation = "Sei nicht traurig, oft ist ein Missgeschick ein verdeckter Glücksfall."
        ),
        PhraseItem(
            id = "zh_phrase_1",
            languageCode = "zh",
            phrase = "这个多少钱？",
            phonetic = "Zhège duōshǎo qián?",
            literalMeaning = "Dieses wie viel Geld?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "请问，这个多少钱？",
            exampleTranslation = "Darf ich fragen, wie viel das kostet?"
        ),
        PhraseItem(
            id = "zh_phrase_2",
            languageCode = "zh",
            phrase = "请给我结账。",
            phonetic = "Qǐng gěi wǒ jiézhàng.",
            literalMeaning = "Bitte gib mir Abrechnung.",
            meaning = "Die Rechnung bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "服务员，请给我结账，可以刷卡吗？",
            exampleTranslation = "Bedienung, die Rechnung bitte, kann man mit Karte zahlen?"
        )
    )

    // ==========================================
    // KOREAN PHRASES & IDIOMS (한국어 관용구 & 회화)
    // ==========================================
    private fun getKoreanPhrases(): List<PhraseItem> = listOf(
        PhraseItem(
            id = "ko_idiom_1",
            languageCode = "ko",
            phrase = "누워서 떡 먹기",
            phonetic = "Nu-wo-seo tteok meok-gi",
            literalMeaning = "Im Liegen Reiskuchen essen",
            meaning = "Ein Kinderspiel, kinderleicht",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "이 문제는 누워서 떡 먹기처럼 쉬워요.",
            exampleTranslation = "Diese Aufgabe ist kinderleicht wie Reiskuchen im Liegen zu essen."
        ),
        PhraseItem(
            id = "ko_idiom_2",
            languageCode = "ko",
            phrase = "발이 넓다",
            phonetic = "Bal-i neolp-da",
            literalMeaning = "Breite Füße haben",
            meaning = "Sehr viele Kontakte haben, gut vernetzt und bekannt sein",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "그 사람은 발이 넓어서 아는 사람이 많아요.",
            exampleTranslation = "Er ist sehr gut vernetzt und kennt unheimlich viele Menschen."
        ),
        PhraseItem(
            id = "ko_idiom_3",
            languageCode = "ko",
            phrase = "시작이 반이다",
            phonetic = "Si-jak-i ban-i-da",
            literalMeaning = "Der Anfang ist die Hälfte",
            meaning = "Ein guter Anfang ist die halbe Miete",
            category = PhraseCategory.IDIOMS,
            isIdiom = true,
            exampleSentence = "망설이지 말고 시작해 보세요. 시작이 반이에요.",
            exampleTranslation = "Zögere nicht und fang einfach an. Ein guter Start ist die halbe Miete."
        ),
        PhraseItem(
            id = "ko_phrase_1",
            languageCode = "ko",
            phrase = "이거 얼마예요?",
            phonetic = "I-geo eol-ma-ye-yo?",
            literalMeaning = "Dieses wie viel ist es?",
            meaning = "Wie viel kostet das?",
            category = PhraseCategory.DAILY,
            isIdiom = false,
            exampleSentence = "저기요, 이거 얼마예요?",
            exampleTranslation = "Entschuldigung, wie viel kostet das hier?"
        ),
        PhraseItem(
            id = "ko_phrase_2",
            languageCode = "ko",
            phrase = "계산해 주세요.",
            phonetic = "Gye-san-hae ju-se-yo.",
            literalMeaning = "Abrechnen tun Sie bitte.",
            meaning = "Die Rechnung bitte!",
            category = PhraseCategory.DINING,
            isIdiom = false,
            exampleSentence = "잘 먹었습니다. 계산해 주세요.",
            exampleTranslation = "Es war sehr lecker. Die Rechnung bitte."
        )
    )

    private fun localizePhrases(list: List<PhraseItem>, nativeLang: String): List<PhraseItem> {
        // If native language is English, keep translations in English
        if (nativeLang.lowercase() == "en") {
            return list.map { item ->
                when (item.id) {
                    "uk_idiom_1" -> item.copy(literalMeaning = "To whittle wood blocks pointlessly", meaning = "To slack off, twiddle one's thumbs, do nothing")
                    "uk_idiom_2" -> item.copy(literalMeaning = "To lead by the nose", meaning = "To deceive someone, make empty promises")
                    "uk_idiom_3" -> item.copy(literalMeaning = "Like a cat cried", meaning = "A tiny amount, next to nothing")
                    "uk_idiom_4" -> item.copy(literalMeaning = "To take feet in hands", meaning = "To hurry up, start running fast")
                    "uk_idiom_5" -> item.copy(literalMeaning = "To make an elephant out of a fly", meaning = "To make a mountain out of a molehill")
                    "uk_phrase_1" -> item.copy(literalMeaning = "How much that costs?", meaning = "How much does this cost?")
                    "uk_phrase_2" -> item.copy(literalMeaning = "Can I pay by card?", meaning = "Can I pay by card?")
                    "uk_phrase_4" -> item.copy(literalMeaning = "Everything will be good!", meaning = "Everything will be alright!")
                    "ru_idiom_1" -> item.copy(literalMeaning = "To hang noodles on ears", meaning = "To tell lies, pull someone's leg")
                    "ru_idiom_2" -> item.copy(literalMeaning = "To strike wood blanks", meaning = "To idle about, twiddle one's thumbs")
                    "ru_phrase_1" -> item.copy(literalMeaning = "How much this costs?", meaning = "How much does this cost?")
                    "es_idiom_1" -> item.copy(literalMeaning = "To cost an eye of the face", meaning = "To cost an arm and a leg")
                    "es_idiom_2" -> item.copy(literalMeaning = "To pull the hair", meaning = "To pull someone's leg")
                    "fr_idiom_1" -> item.copy(literalMeaning = "To plant a rabbit", meaning = "To stand someone up")
                    "fr_idiom_2" -> item.copy(literalMeaning = "Lightning strike", meaning = "Love at first sight")
                    else -> item
                }
            }
        }
        return list
    }
}
