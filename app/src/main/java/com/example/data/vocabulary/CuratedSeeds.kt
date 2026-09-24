package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

val SpanishCuratedWords = listOf(
    // Tier 1: A1 Essentials (Ranks 1 - 1000)
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 1, word = "el / la", translation = "the",
        phonetic = "el / lah", partOfSpeech = "article", category = "Grammar Core", tier = 1, cefrLevel = "A1",
        exampleSentence = "El sol brilla en el cielo.", exampleTranslation = "The sun shines in the sky."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 2, word = "ser", translation = "to be (essential nature)",
        phonetic = "sehr", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Ella es una excelente profesora.", exampleTranslation = "She is an excellent teacher."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 3, word = "estar", translation = "to be (state/location)",
        phonetic = "ess-TAHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "¿Dónde estás ahora mismo?", exampleTranslation = "Where are you right now?"
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 4, word = "haber", translation = "to have (auxiliary) / there is",
        phonetic = "ah-BEHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Hay muchas cosas que aprender.", exampleTranslation = "There are many things to learn."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 5, word = "tener", translation = "to have / possess",
        phonetic = "teh-NEHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Tengo muchas ganas de viajar.", exampleTranslation = "I really want to travel."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 6, word = "hacer", translation = "to do / make",
        phonetic = "ah-SEHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "¿Qué haces en tu tiempo libre?", exampleTranslation = "What do you do in your free time?"
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 7, word = "poder", translation = "to be able to / can",
        phonetic = "poh-DEHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "¿Puedo hacerte una pregunta?", exampleTranslation = "Can I ask you a question?"
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 8, word = "decir", translation = "to say / tell",
        phonetic = "deh-SEER", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Dime qué opinas sobre esto.", exampleTranslation = "Tell me what you think about this."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 9, word = "ir", translation = "to go",
        phonetic = "eer", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Vamos al centro de la ciudad.", exampleTranslation = "Let's go to downtown."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 10, word = "ver", translation = "to see / watch",
        phonetic = "behr", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Me alegro mucho de verte.", exampleTranslation = "I'm so glad to see you."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 15, word = "amigo", translation = "friend",
        phonetic = "ah-MEE-goh", partOfSpeech = "noun", category = "Relationships", tier = 1, cefrLevel = "A1",
        exampleSentence = "Un buen amigo siempre te apoya.", exampleTranslation = "A good friend always supports you."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 25, word = "tiempo", translation = "time / weather",
        phonetic = "TYEHM-poh", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "El tiempo pasa volando cuando disfrutas.", exampleTranslation = "Time flies when you are having fun."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 40, word = "casa", translation = "house / home",
        phonetic = "KAH-sah", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Bienvenido a mi humilde casa.", exampleTranslation = "Welcome to my humble home."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 85, word = "entender", translation = "to understand",
        phonetic = "en-ten-DEHR", partOfSpeech = "verb", category = "Communication", tier = 1, cefrLevel = "A1",
        exampleSentence = "Ahora entiendo la situación.", exampleTranslation = "Now I understand the situation."
    ),
    // Tier 2: A2 Everyday Fluency (Ranks 1001 - 2500)
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 1020, word = "costumbre", translation = "habit / custom / tradition",
        phonetic = "kohs-TOOM-breh", partOfSpeech = "noun", category = "Culture & Habits", tier = 2, cefrLevel = "A2",
        exampleSentence = "Es una costumbre local tomar café después de comer.", exampleTranslation = "It's a local custom to have coffee after eating."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 1150, word = "desarrollar", translation = "to develop / evolve",
        phonetic = "deh-sah-rroy-AHR", partOfSpeech = "verb", category = "Growth", tier = 2, cefrLevel = "A2",
        exampleSentence = "Queremos desarrollar nuevas habilidades.", exampleTranslation = "We want to develop new skills."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 1420, word = "paisaje", translation = "landscape / scenery",
        phonetic = "pye-SAH-kheh", partOfSpeech = "noun", category = "Nature & Travel", tier = 2, cefrLevel = "A2",
        exampleSentence = "El paisaje montañoso es espectacular.", exampleTranslation = "The mountainous scenery is spectacular."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 1850, word = "sabiduría", translation = "wisdom",
        phonetic = "sah-bee-doo-REE-ah", partOfSpeech = "noun", category = "Mind & Spirit", tier = 2, cefrLevel = "A2",
        exampleSentence = "Las personas mayores tienen mucha sabiduría.", exampleTranslation = "Older people have great wisdom."
    ),
    // Tier 3: B1 Conversational Competence (Ranks 2501 - 5000)
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 2650, word = "imprescindible", translation = "indispensable / essential",
        phonetic = "eem-preh-seen-DEE-bleh", partOfSpeech = "adjective", category = "Quality & Opinion", tier = 3, cefrLevel = "B1",
        exampleSentence = "La perseverancia es imprescindible para el éxito.", exampleTranslation = "Perseverance is indispensable for success."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 3100, word = "desafío", translation = "challenge / dare",
        phonetic = "deh-sah-FEE-oh", partOfSpeech = "noun", category = "Work & Mindset", tier = 3, cefrLevel = "B1",
        exampleSentence = "Acepto este desafío con optimismo.", exampleTranslation = "I accept this challenge with optimism."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 4200, word = "empatía", translation = "empathy",
        phonetic = "em-pah-TEE-ah", partOfSpeech = "noun", category = "Emotions", tier = 3, cefrLevel = "B1",
        exampleSentence = "La empatía une a las personas.", exampleTranslation = "Empathy brings people together."
    ),
    // Tier 4: B2 Professional & Academic (Ranks 5001 - 7500)
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 5400, word = "paradigma", translation = "paradigm / model",
        phonetic = "pah-rah-DEEG-mah", partOfSpeech = "noun", category = "Academic & Tech", tier = 4, cefrLevel = "B2",
        exampleSentence = "Este nuevo paradigma revoluciona la ciencia.", exampleTranslation = "This new paradigm revolutionizes science."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 6800, word = "vanguardia", translation = "vanguard / forefront",
        phonetic = "bahn-GWAHR-dyah", partOfSpeech = "noun", category = "Innovation", tier = 4, cefrLevel = "B2",
        exampleSentence = "La empresa está a la vanguardia de la tecnología.", exampleTranslation = "The company is at the forefront of technology."
    ),
    // Tier 5: C1/C2 Mastery & Idioms (Ranks 7501 - 10000)
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 7800, word = "efímero", translation = "ephemeral / fleeting",
        phonetic = "eh-FEE-meh-roh", partOfSpeech = "adjective", category = "Poetic & Nuance", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "La belleza de las flores es un placer efímero.", exampleTranslation = "The beauty of flowers is a fleeting pleasure."
    ),
    VocabularyWordEntity(
        languageCode = "es", frequencyRank = 9200, word = "serendipia", translation = "serendipity / pleasant surprise",
        phonetic = "seh-ren-DEE-pyah", partOfSpeech = "noun", category = "Nuance", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Encontrarnos fue una hermosa serendipia.", exampleTranslation = "Meeting each other was a beautiful serendipity."
    )
)

val FrenchCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 1, word = "être", translation = "to be",
        phonetic = "EH-truh", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Je suis ravi de faire votre connaissance.", exampleTranslation = "I am delighted to meet you."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 2, word = "avoir", translation = "to have",
        phonetic = "ah-VWAHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "J'ai une opportunité formidable.", exampleTranslation = "I have a wonderful opportunity."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 3, word = "pouvoir", translation = "can / to be able to",
        phonetic = "poo-VWAHR", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Pouvez-vous m'expliquer cela ?", exampleTranslation = "Can you explain that to me?"
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 4, word = "faire", translation = "to do / to make",
        phonetic = "fehr", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Que fais-tu aujourd'hui ?", exampleTranslation = "What are you doing today?"
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 12, word = "monde", translation = "world / people",
        phonetic = "mohnd", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Le monde est plein de surprises.", exampleTranslation = "The world is full of surprises."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 1100, word = "découverte", translation = "discovery",
        phonetic = "day-koo-VAIRT", partOfSpeech = "noun", category = "Travel & Science", tier = 2, cefrLevel = "A2",
        exampleSentence = "C'est une découverte fascinante.", exampleTranslation = "It's a fascinating discovery."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 3200, word = "bienveillance", translation = "benevolence / goodwill",
        phonetic = "byen-vey-YAHNS", partOfSpeech = "noun", category = "Emotions", tier = 3, cefrLevel = "B1",
        exampleSentence = "Agir avec bienveillance change tout.", exampleTranslation = "Acting with benevolence changes everything."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 6100, word = "incontournable", translation = "unmissable / essential",
        phonetic = "an-kohn-toor-NAHBL", partOfSpeech = "adjective", category = "Media & Arts", tier = 4, cefrLevel = "B2",
        exampleSentence = "Ce musée est une étape incontournable.", exampleTranslation = "This museum is an unmissable stop."
    ),
    VocabularyWordEntity(
        languageCode = "fr", frequencyRank = 8900, word = "dépaysant", translation = "disorienting in an exotic/refreshing way",
        phonetic = "day-pay-ee-ZAHN", partOfSpeech = "adjective", category = "Nuance", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Ce voyage était profondément dépaysant.", exampleTranslation = "This trip was profoundly refreshing and exotic."
    )
)

val GermanCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 1, word = "sein", translation = "to be",
        phonetic = "zyn", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Ich bin sehr glücklich hier zu sein.", exampleTranslation = "I am very happy to be here."
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 2, word = "haben", translation = "to have",
        phonetic = "HAH-ben", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Wir haben genug Zeit zum Üben.", exampleTranslation = "We have enough time to practice."
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 3, word = "können", translation = "can / to be able to",
        phonetic = "KOE-nen", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Kannst du mir bitte helfen?", exampleTranslation = "Can you please help me?"
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 15, word = "die Freude", translation = "joy / delight",
        phonetic = "FROY-duh", partOfSpeech = "noun", category = "Emotions", tier = 1, cefrLevel = "A1",
        exampleSentence = "Mit großer Freude lerne ich Deutsch.", exampleTranslation = "With great joy I learn German."
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 1200, word = "die Erfahrung", translation = "experience",
        phonetic = "er-FAH-roong", partOfSpeech = "noun", category = "Life Skills", tier = 2, cefrLevel = "A2",
        exampleSentence = "Erfahrung ist der beste Lehrmeister.", exampleTranslation = "Experience is the best teacher."
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 3400, word = "die Achtsamkeit", translation = "mindfulness",
        phonetic = "AKHT-zahm-kayt", partOfSpeech = "noun", category = "Mindset", tier = 3, cefrLevel = "B1",
        exampleSentence = "Achtsamkeit hilft gegen Stress.", exampleTranslation = "Mindfulness helps against stress."
    ),
    VocabularyWordEntity(
        languageCode = "de", frequencyRank = 8200, word = "das Fernweh", translation = "longing for distant places / wanderlust",
        phonetic = "FEHRN-vay", partOfSpeech = "noun", category = "German Gems", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Fernweh zieht mich in fremde Länder.", exampleTranslation = "Wanderlust pulls me to foreign lands."
    )
)

val JapaneseCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 1, word = "する", translation = "to do",
        phonetic = "suru", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "日本語を勉強するのが楽しいです。", exampleTranslation = "Studying Japanese is fun."
    ),
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 2, word = "行く", translation = "to go",
        phonetic = "iku", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "明日、京都へ行きます。", exampleTranslation = "I am going to Kyoto tomorrow."
    ),
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 10, word = "友達 (ともだち)", translation = "friend",
        phonetic = "tomodachi", partOfSpeech = "noun", category = "Relationships", tier = 1, cefrLevel = "A1",
        exampleSentence = "友達とカフェで話しました。", exampleTranslation = "I talked with a friend at a cafe."
    ),
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 1500, word = "思い出 (おもいで)", translation = "memory / recollection",
        phonetic = "omoide", partOfSpeech = "noun", category = "Emotions", tier = 2, cefrLevel = "A2",
        exampleSentence = "素晴らしい思い出ができました。", exampleTranslation = "We made wonderful memories."
    ),
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 3800, word = "一期一会 (いちごいちえ)", translation = "once-in-a-lifetime encounter",
        phonetic = "ichigo ichie", partOfSpeech = "idiom", category = "Philosophy", tier = 3, cefrLevel = "B1",
        exampleSentence = "すべての人との出会いは一期一会です。", exampleTranslation = "Every encounter with someone is a once-in-a-lifetime moment."
    ),
    VocabularyWordEntity(
        languageCode = "ja", frequencyRank = 8500, word = "木漏れ日 (こもれび)", translation = "sunlight filtering through trees",
        phonetic = "komorebi", partOfSpeech = "noun", category = "Poetic Japanese", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "森の中で木漏れ日を感じて歩いた。", exampleTranslation = "I walked through the forest feeling the dappled sunlight."
    )
)

val ItalianCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "it", frequencyRank = 1, word = "essere", translation = "to be",
        phonetic = "ESS-seh-reh", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Sono felice di imparare l'italiano.", exampleTranslation = "I am happy to learn Italian."
    ),
    VocabularyWordEntity(
        languageCode = "it", frequencyRank = 2, word = "avere", translation = "to have",
        phonetic = "ah-VEH-reh", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Abbiamo una splendida giornata.", exampleTranslation = "We have a splendid day."
    ),
    VocabularyWordEntity(
        languageCode = "it", frequencyRank = 1200, word = "meraviglia", translation = "wonder / marvel",
        phonetic = "meh-rah-VEE-lyah", partOfSpeech = "noun", category = "Emotions", tier = 2, cefrLevel = "A2",
        exampleSentence = "Roma è piena di meraviglie artistiche.", exampleTranslation = "Rome is full of artistic wonders."
    )
)

val ChineseCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "zh", frequencyRank = 1, word = "是", translation = "to be",
        phonetic = "shì", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "我是语言学习爱好者。", exampleTranslation = "I am a language learning enthusiast."
    ),
    VocabularyWordEntity(
        languageCode = "zh", frequencyRank = 2, word = "有", translation = "to have / there is",
        phonetic = "yǒu", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "今天我们有很多收获。", exampleTranslation = "Today we have gained a lot."
    ),
    VocabularyWordEntity(
        languageCode = "zh", frequencyRank = 1300, word = "进步", translation = "progress / improve",
        phonetic = "jìn bù", partOfSpeech = "noun", category = "Learning", tier = 2, cefrLevel = "A2",
        exampleSentence = "每天积累一点点，就能看到巨大进步。", exampleTranslation = "Accumulating a little every day leads to great progress."
    )
)

val KoreanCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "ko", frequencyRank = 1, word = "하다", translation = "to do",
        phonetic = "ha-da", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "매일 한국어를 연습해요.", exampleTranslation = "I practice Korean every day."
    ),
    VocabularyWordEntity(
        languageCode = "ko", frequencyRank = 2, word = "있다", translation = "to be / to exist / have",
        phonetic = "it-da", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "질문이 있으면 언제든 물어보세요.", exampleTranslation = "If you have questions, please ask anytime."
    ),
    VocabularyWordEntity(
        languageCode = "ko", frequencyRank = 1100, word = "소중하다", translation = "to be precious / valuable",
        phonetic = "so-jung-ha-da", partOfSpeech = "adjective", category = "Emotions", tier = 2, cefrLevel = "A2",
        exampleSentence = "함께하는 이 시간이 참 소중해요.", exampleTranslation = "This time we spend together is truly precious."
    )
)

val PortugueseCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "pt", frequencyRank = 1, word = "ser", translation = "to be (permanent)",
        phonetic = "sehr", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "A vida é uma jornada maravilhosa.", exampleTranslation = "Life is a wonderful journey."
    ),
    VocabularyWordEntity(
        languageCode = "pt", frequencyRank = 2, word = "ter", translation = "to have",
        phonetic = "tehr", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Temos muitos planos para o futuro.", exampleTranslation = "We have many plans for the future."
    ),
    VocabularyWordEntity(
        languageCode = "pt", frequencyRank = 8200, word = "saudade", translation = "deep nostalgic longing",
        phonetic = "sow-DAH-djee", partOfSpeech = "noun", category = "Cultural Gems", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Sinto saudades da minha terra natal.", exampleTranslation = "I miss my homeland with deep longing."
    )
)

val EnglishCuratedWords = listOf(
    VocabularyWordEntity(
        languageCode = "en", frequencyRank = 1, word = "the", translation = "definite article",
        phonetic = "thuh", partOfSpeech = "article", category = "Grammar Core", tier = 1, cefrLevel = "A1",
        exampleSentence = "The world is full of opportunities.", exampleTranslation = "The world is full of opportunities."
    ),
    VocabularyWordEntity(
        languageCode = "en", frequencyRank = 2, word = "be", translation = "exist / state of being",
        phonetic = "bee", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Be kind to yourself and others.", exampleTranslation = "Be kind to yourself and others."
    ),
    VocabularyWordEntity(
        languageCode = "en", frequencyRank = 1200, word = "resilience", translation = "ability to recover quickly",
        phonetic = "rih-ZIL-yenss", partOfSpeech = "noun", category = "Mindset", tier = 2, cefrLevel = "A2",
        exampleSentence = "Resilience enables us to overcome challenges.", exampleTranslation = "Resilience enables us to overcome challenges."
    ),
    VocabularyWordEntity(
        languageCode = "en", frequencyRank = 7900, word = "serendipity", translation = "finding valuable things unexpectedly",
        phonetic = "seh-ren-DIP-ih-tee", partOfSpeech = "noun", category = "Nuance", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Our encounter was pure serendipity.", exampleTranslation = "Our encounter was pure serendipity."
    )
)

val RussianCuratedWords = listOf(
    // Tier 1: A1 Essentials
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 1, word = "быть", translation = "to be / exist",
        phonetic = "byt'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Всё будет хорошо.", exampleTranslation = "Everything will be fine."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 2, word = "делать", translation = "to do / make",
        phonetic = "DYE-lat'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Что ты делаешь сейчас?", exampleTranslation = "What are you doing now?"
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 3, word = "говорить", translation = "to speak / talk",
        phonetic = "ga-va-REET'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Мы говорим по-русски.", exampleTranslation = "We speak Russian."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 4, word = "знать", translation = "to know",
        phonetic = "znat'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Я знаю этот ответ.", exampleTranslation = "I know this answer."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 5, word = "идти", translation = "to go / walk",
        phonetic = "eet-TEE", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Пора идти домой.", exampleTranslation = "It is time to go home."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 6, word = "видеть", translation = "to see",
        phonetic = "VEE-det'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Рад тебя видеть!", exampleTranslation = "Glad to see you!"
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 7, word = "думать", translation = "to think",
        phonetic = "DOO-mat'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "О чём ты думаешь?", exampleTranslation = "What are you thinking about?"
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 8, word = "хотеть", translation = "to want",
        phonetic = "kha-TYET'", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Я хочу выучить язык.", exampleTranslation = "I want to learn the language."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 10, word = "время", translation = "time",
        phonetic = "VRYE-mya", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Время летит быстро.", exampleTranslation = "Time flies fast."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 15, word = "друг", translation = "friend",
        phonetic = "drook", partOfSpeech = "noun", category = "Relationships", tier = 1, cefrLevel = "A1",
        exampleSentence = "Настоящий друг всегда поможет.", exampleTranslation = "A real friend always helps."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 25, word = "дом", translation = "house / home",
        phonetic = "dom", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Добро пожаловать в наш дом.", exampleTranslation = "Welcome to our home."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 85, word = "понимать", translation = "to understand",
        phonetic = "pa-nee-MAT'", partOfSpeech = "verb", category = "Communication", tier = 1, cefrLevel = "A1",
        exampleSentence = "Теперь я всё понимаю.", exampleTranslation = "Now I understand everything."
    ),
    // Tier 2: A2 Everyday Fluency
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 1020, word = "привычка", translation = "habit / routine",
        phonetic = "pree-VYCH-ka", partOfSpeech = "noun", category = "Lifestyle", tier = 2, cefrLevel = "A2",
        exampleSentence = "Хорошая привычка читать каждый день.", exampleTranslation = "It's a good habit to read every day."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 1150, word = "развивать", translation = "to develop / evolve",
        phonetic = "raz-vee-VAT'", partOfSpeech = "verb", category = "Growth", tier = 2, cefrLevel = "A2",
        exampleSentence = "Мы развиваем полезные навыки.", exampleTranslation = "We are developing useful skills."
    ),
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 1850, word = "мудрость", translation = "wisdom",
        phonetic = "MOOD-rast'", partOfSpeech = "noun", category = "Mind & Spirit", tier = 2, cefrLevel = "A2",
        exampleSentence = "Мудрость приходит с опытом.", exampleTranslation = "Wisdom comes with experience."
    ),
    // Tier 3: B1
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 2650, word = "необходимый", translation = "essential / indispensable",
        phonetic = "ne-ab-kha-DEE-my", partOfSpeech = "adjective", category = "Importance", tier = 3, cefrLevel = "B1",
        exampleSentence = "Практика — это необходимый шаг.", exampleTranslation = "Practice is an essential step."
    ),
    // Tier 4: B2
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 5200, word = "взаимопонимание", translation = "mutual understanding",
        phonetic = "vza-ee-ma-pa-nee-MA-nee-ye", partOfSpeech = "noun", category = "Relationships", tier = 4, cefrLevel = "B2",
        exampleSentence = "Взаимопонимание объединяет людей.", exampleTranslation = "Mutual understanding unites people."
    ),
    // Tier 5: C1-C2
    VocabularyWordEntity(
        languageCode = "ru", frequencyRank = 8200, word = "умиротворение", translation = "serenity / peacefulness",
        phonetic = "oo-mee-rat-va-RYE-nee-ye", partOfSpeech = "noun", category = "State of Mind", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "На природе ощущается глубокое умиротворение.", exampleTranslation = "In nature, deep serenity is felt."
    )
)

val UkrainianCuratedWords = listOf(
    // Tier 1: A1 Essentials
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 1, word = "бути", translation = "to be / exist",
        phonetic = "BOO-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Все буде чудово.", exampleTranslation = "Everything will be wonderful."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 2, word = "робити", translation = "to do / make",
        phonetic = "ro-BY-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Що ти зараз робиш?", exampleTranslation = "What are you doing now?"
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 3, word = "говорити", translation = "to speak / talk",
        phonetic = "ho-vo-RY-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Ми говоримо українською.", exampleTranslation = "We speak Ukrainian."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 4, word = "знати", translation = "to know",
        phonetic = "ZNA-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Я знаю цю відповідь.", exampleTranslation = "I know this answer."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 5, word = "йти", translation = "to go / walk",
        phonetic = "yty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Час іти додому.", exampleTranslation = "It's time to go home."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 6, word = "бачити", translation = "to see",
        phonetic = "BA-chy-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Радий тебе бачити!", exampleTranslation = "Glad to see you!"
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 7, word = "думати", translation = "to think",
        phonetic = "DOO-ma-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Про що ти думаєш?", exampleTranslation = "What are you thinking about?"
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 8, word = "хотіти", translation = "to want",
        phonetic = "kho-TEE-ty", partOfSpeech = "verb", category = "Core Verbs", tier = 1, cefrLevel = "A1",
        exampleSentence = "Я хочу вивчити мову.", exampleTranslation = "I want to learn the language."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 10, word = "час", translation = "time",
        phonetic = "chas", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Час летить дуже швидко.", exampleTranslation = "Time flies very fast."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 15, word = "друг", translation = "friend",
        phonetic = "drooh", partOfSpeech = "noun", category = "Relationships", tier = 1, cefrLevel = "A1",
        exampleSentence = "Справжній друг завжди поруч.", exampleTranslation = "A true friend is always near."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 25, word = "дім", translation = "house / home",
        phonetic = "deem", partOfSpeech = "noun", category = "Daily Life", tier = 1, cefrLevel = "A1",
        exampleSentence = "Ласкаво просимо до нашого дому.", exampleTranslation = "Welcome to our home."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 85, word = "розуміти", translation = "to understand",
        phonetic = "ro-zoo-MEE-ty", partOfSpeech = "verb", category = "Communication", tier = 1, cefrLevel = "A1",
        exampleSentence = "Тепер я все розумію.", exampleTranslation = "Now I understand everything."
    ),
    // Tier 2: A2 Everyday Fluency
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 1020, word = "звичка", translation = "habit / routine",
        phonetic = "ZVYCH-ka", partOfSpeech = "noun", category = "Lifestyle", tier = 2, cefrLevel = "A2",
        exampleSentence = "Корисна звичка читати щодня.", exampleTranslation = "It is a useful habit to read every day."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 1150, word = "розвивати", translation = "to develop / evolve",
        phonetic = "roz-vy-VA-ty", partOfSpeech = "verb", category = "Growth", tier = 2, cefrLevel = "A2",
        exampleSentence = "Ми розвиваємо нові навички.", exampleTranslation = "We are developing new skills."
    ),
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 1850, word = "мудрість", translation = "wisdom",
        phonetic = "MOO-drist'", partOfSpeech = "noun", category = "Mind & Spirit", tier = 2, cefrLevel = "A2",
        exampleSentence = "Мудрість приходить з досвідом.", exampleTranslation = "Wisdom comes with experience."
    ),
    // Tier 3: B1
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 2650, word = "необхідний", translation = "essential / indispensable",
        phonetic = "ne-ob-KHEED-ny", partOfSpeech = "adjective", category = "Importance", tier = 3, cefrLevel = "B1",
        exampleSentence = "Практика — це необхідний крок.", exampleTranslation = "Practice is an essential step."
    ),
    // Tier 4: B2
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 5200, word = "взаєморозуміння", translation = "mutual understanding",
        phonetic = "vza-ye-mo-ro-zoo-MEE-nnya", partOfSpeech = "noun", category = "Relationships", tier = 4, cefrLevel = "B2",
        exampleSentence = "Взаєморозуміння будує міцні мости.", exampleTranslation = "Mutual understanding builds strong bridges."
    ),
    // Tier 5: C1-C2
    VocabularyWordEntity(
        languageCode = "uk", frequencyRank = 8200, word = "натхнення", translation = "inspiration / illumination",
        phonetic = "nat-KHNEN-nya", partOfSpeech = "noun", category = "Creativity", tier = 5, cefrLevel = "C1-C2",
        exampleSentence = "Щоденна праця приносить справжнє натхнення.", exampleTranslation = "Daily work brings true inspiration."
    )
)
