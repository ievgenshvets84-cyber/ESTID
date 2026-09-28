package com.example.data.vocabulary.phrases

import com.example.data.vocabulary.PhraseCategory
import com.example.data.vocabulary.PhraseItem

object SituationalPhrasesDataPart4 {

    fun getPart4PhrasesForLanguage(targetLang: String, nativeLang: String): List<PhraseItem> {
        val t = targetLang.lowercase()
        val n = nativeLang.lowercase()

        return PART4_LIST.mapIndexed { index, entry ->
            val phraseText = when (t) {
                "es" -> entry.es
                "fr" -> entry.fr
                "de" -> entry.de
                "it" -> entry.it
                "pt" -> entry.pt
                "en" -> entry.en
                "ru" -> entry.ru
                "uk" -> entry.uk
                "ja" -> entry.ja
                "zh" -> entry.zh
                "ko" -> entry.ko
                else -> entry.en
            }

            val phoneticText = when (t) {
                "es" -> entry.phoneticEs.ifBlank { "/$phraseText/" }
                "fr" -> entry.phoneticFr.ifBlank { "/$phraseText/" }
                "it" -> entry.phoneticIt.ifBlank { "/$phraseText/" }
                "pt" -> entry.phoneticPt.ifBlank { "/$phraseText/" }
                "ru" -> entry.phoneticRu.ifBlank { phraseText }
                "uk" -> entry.phoneticUk.ifBlank { phraseText }
                "ja" -> entry.phoneticJa.ifBlank { phraseText }
                "zh" -> entry.phoneticZh.ifBlank { phraseText }
                "ko" -> entry.phoneticKo.ifBlank { phraseText }
                else -> "/$phraseText/"
            }

            val meaningText = when (n) {
                "de" -> entry.de
                "en" -> entry.en
                "es" -> entry.es
                "fr" -> entry.fr
                "it" -> entry.it
                "pt" -> entry.pt
                "ru" -> entry.ru
                "uk" -> entry.uk
                "ja" -> entry.ja
                "zh" -> entry.zh
                "ko" -> entry.ko
                else -> entry.en
            }

            val literalText = when (n) {
                "de" -> "Bedeutung: ${entry.de}"
                "en" -> "Meaning: ${entry.en}"
                "uk" -> "Значення: ${entry.uk}"
                "ru" -> "Значение: ${entry.ru}"
                else -> entry.en
            }

            val exampleSent = if (entry.exTargetPrefix.isNotBlank()) {
                "${entry.exTargetPrefix} $phraseText"
            } else {
                phraseText
            }

            val exampleTrans = if (n == "de") {
                if (entry.exDe.isNotBlank()) entry.exDe else entry.de
            } else {
                if (entry.exEn.isNotBlank()) entry.exEn else entry.en
            }

            PhraseItem(
                id = "${t}_core4_${entry.id}_${index + 86}",
                languageCode = t,
                phrase = phraseText,
                phonetic = phoneticText,
                literalMeaning = literalText,
                meaning = meaningText,
                category = entry.category,
                isIdiom = false,
                exampleSentence = exampleSent,
                exampleTranslation = exampleTrans,
                culturalTip = ""
            )
        }
    }

    private val PART4_LIST = listOf(
        // Additional 25 Essential Everyday & Travel Expressions
        CorePhraseEntry("x1", PhraseCategory.TRAVEL,
            "An welchem Gate boardet mein Flug?", "Which gate is my flight boarding at?",
            "¿En qué puerta de embarque sale mi vuelo?", "À quelle porte embarque mon vol ?",
            "A quale gate imbarca il mio volo?", "Em qual portão embarca o meu voo?",
            "У какого выхода на посадку мой рейс?", "Біля якого виходу на посадку мій рейс?",
            "私の便の搭乗ゲートは何番ですか？", "我的航班在哪个登机口登机？", "제 비행기는 몇 번 탑승구에서 탑승하나요?",
            phoneticEs = "en keh PWEHR-tah deh em-BAHR-keh SAH-leh mee VWEH-loh?", phoneticFr = "ah kell pohrt ahm-bahrk mohn vohl?",
            phoneticJa = "Watashi no bin no tōjō gēto wa nanban desu ka?", phoneticZh = "Wǒ de hángbān zài nǎge dēngjīkǒu dēngjī?", phoneticKo = "Je bihaenggineun myeot beon tapseunggueseo tapseunghanayo?"),

        CorePhraseEntry("x2", PhraseCategory.TRAVEL,
            "Gibt es einen Shuttleservice zum Flughafen?", "Is there an airport shuttle service?",
            "¿Hay servicio de transporte al aeropuerto?", "Y a-t-il une navette pour l'aéroport ?",
            "C'è un servizio navetta per l'aeroporto?", "Há serviço de traslado para o aeroporto?",
            "Есть ли трансфер в аэропорт?", "Чи є трансфер до аеропорту?",
            "空港へのシャトルバスはありますか？", "有去机场的班车服务吗？", "공항까지 가는 셔틀버스가 있나요?",
            phoneticEs = "eye sehr-VEE-syoh deh trahns-POHR-teh ahl ah-eh-roh-PWEHR-toh?", phoneticFr = "ee ah-teel oon nah-vet poor lah-ay-roh-pohr?",
            phoneticJa = "Kūkō e no shatorubasu wa arimasu ka?", phoneticZh = "Yǒu qù jīchǎng de bānchē fúwù ma?", phoneticKo = "Gonghangkkaji ganeun syeoteulbeoseuga innayo?"),

        CorePhraseEntry("x3", PhraseCategory.DINING,
            "Bringen Sie mir bitte die Weinkarte.", "Please bring me the wine list.",
            "Tráigame la carta de vinos, por favor.", "Apportez-moi la carte des vins, s'il vous plaît.",
            "Mi porti la carta dei vini, per favore.", "Traga-me a carta de vinhos, por favor.",
            "Принесите мне винную карту, пожалуйста.", "Принесіть мені карту вин, будь ласка.",
            "ワインリストを持ってきていただけますか？", "请拿一份酒水单给我看，谢谢。", "와인 리스트 좀 가져다주시겠어요?",
            phoneticEs = "TRY-gah-meh lah KAHR-tah deh VEE-nohs", phoneticFr = "ah-pohr-tay mwah lah kahrt day van",
            phoneticJa = "Wain risuto o motte kite itadakemasu ka?", phoneticZh = "Qǐng ná yī fèn jiǔshuǐ dān gěi wǒ kàn", phoneticKo = "Wain riseuteu jom gajyeodajusigesseoyo?"),

        CorePhraseEntry("x4", PhraseCategory.DINING,
            "Können wir getrennt bezahlen?", "Can we pay separately?",
            "¿Podemos pagar por separado?", "Pouvons-nous payer séparément ?",
            "Possiamo pagare separatamente?", "Podemos pagar separados?",
            "Мы можем заплатить раздельно?", "Чи можемо ми розрахуватися окремо?",
            "お会計を別々にできますか？", "我们可以分开结账吗？", "따로따로 계산할 수 있을까요?",
            phoneticEs = "poh-DEH-mohs pah-GAHR pohr seh-pah-RAH-doh?", phoneticFr = "poo-vohn-noo pay-yay say-pah-ray-mahn?",
            phoneticJa = "Okaikei o betsubetsu ni dekimasu ka?", phoneticZh = "Wǒmen kěyǐ fēnkāi jiézhàng ma?", phoneticKo = "Ttarottaro gyesanhal su isseulkkayo?"),

        CorePhraseEntry("x5", PhraseCategory.DAILY,
            "Wie ist das Wetter heute draußen?", "How is the weather outside today?",
            "¿Cómo está el clima hoy afuera?", "Quel temps fait-il dehors aujourd'hui ?",
            "Che tempo fa fuori oggi?", "Como está o tempo lá fora hoje?",
            "Какая сегодня погода на улице?", "Яка сьогодні погода на вулиці?",
            "今日の外の天気はどうですか？", "今天外面的天气怎么样？", "오늘 바깥 날씨는 어떤가요?",
            phoneticEs = "KOH-moh ess-TAH ehl KLEE-mah oy ah-FWEH-rah?", phoneticFr = "kell tahn feh-teel duh-ohr oh-zhoor-dwee?",
            phoneticJa = "Kyō no soto no tenki wa dō desu ka?", phoneticZh = "Jīntiān wàimiàn de tiānqì zěnme yàng?", phoneticKo = "Oneul bakkat nalssineun eotteongayo?"),

        CorePhraseEntry("x6", PhraseCategory.DAILY,
            "Es regnet in Strömen, nimm einen Schirm!", "It's pouring rain, take an umbrella!",
            "¡Está lloviendo a cántaros, lleva paraguas!", "Il pleut des cordes, prends un parapluie !",
            "Piove a catinelle, prendi l'ombrello!", "Está chovendo a cântaros, leve um guarda-chuva!",
            "Льет как из ведра, возьми зонт!", "Ллє як з відра, візьми парасольку!",
            "土砂降りだから、傘を持って行ってね！", "外面下着倾盆大雨，带把伞吧！", "비가 억수같이 쏟아지네요, 우산 챙기세요!",
            phoneticEs = "ess-TAH yoh-VYEHN-doh ah KAHN-tah-rohs", phoneticFr = "eel pluh day kohrd, prahn un pah-rah-plwee",
            phoneticJa = "Doshaburi dakara, kasa o motte itte ne!", phoneticZh = "Wàimiàn xiàzhe qīngpén dàyǔ, dài bǎ sǎn ba!", phoneticKo = "Biga eoksugati ssodajineyo, usan chaenggiseyo!"),

        CorePhraseEntry("x7", PhraseCategory.DAILY,
            "Können Sie ein Foto von uns machen?", "Could you take a photo of us, please?",
            "¿Podría tomarnos una foto, por favor?", "Pourriez-vous nous prendre en photo, s'il vous plaît ?",
            "Ci potrebbe fare una foto, per favore?", "Poderia tirar uma foto nossa, por favor?",
            "Не могли бы вы сфотографировать нас?", "Чи не могли б ви сфотографувати нас, будь ласка?",
            "私たちの写真を撮っていただけますか？", "您能帮我们拍张合影吗？", "저희 사진 한 장만 찍어주시겠어요?",
            phoneticEs = "poh-DREE-ah toh-MAHR-nohs OO-nah FOH-toh?", phoneticFr = "poo-ryay-voo noo prahndr ahn foh-toh?",
            phoneticJa = "Watashitachi no shashin o totte itadakemasu ka?", phoneticZh = "Nín néng bāng wǒmen pāi zhāng héyǐng ma?", phoneticKo = "Jeohui sajin han jangman jjigeojusigesseoyo?"),

        CorePhraseEntry("x8", PhraseCategory.FEELINGS,
            "Ich bin absolut begeistert davon!", "I am absolutely thrilled about this!",
            "¡Estoy absolutamente fascinado con esto!", "Je suis absolument enthousiaste à ce sujet !",
            "Ne sono assolutamente entusiasta!", "Estou absolutamente encantado com isso!",
            "Я в абсолютном восторге от этого!", "Я у повному захваті від цього!",
            "とても感激しています！", "我对此感到无比兴奋与激动！", "정말 감탄스럽고 벅차오르네요!",
            phoneticEs = "ess-TOY ahb-soh-LOO-tah-mehn-teh fah-see-NAH-doh", phoneticFr = "zhuh swee ahb-soh-loo-mahn ahn-too-zyahst",
            phoneticJa = "Totemo kangeki shite imasu!", phoneticZh = "Wǒ duì cǐ gǎndào wúbǐ xīngfèn!", phoneticKo = "Jeongmal gamtanseureopgo beokchaoreuneyo!"),

        CorePhraseEntry("x9", PhraseCategory.BUSINESS,
            "Ich werde mich umgehend darum kümmern.", "I will attend to this matter right away.",
            "Me ocuparé de este asunto de inmediato.", "Je vais m'en occuper immédiatement.",
            "Me ne occuperò immediatamente.", "Cuidarei deste assunto imediatamente.",
            "Я немедленно займусь этим вопросом.", "Я негайно займуся цим питанням.",
            "至急この件の対応にあたります。", "我马上就会着手处理这件事。", "지금 즉시 이 문제를 처리하겠습니다.",
            phoneticEs = "meh oh-koo-pah-REH deh EHS-teh ah-SOON-toh", phoneticFr = "zhuh vay mahn oh-koo-pay ee-may-dyaht-mahn",
            phoneticJa = "Shikyū kono ken no taiō ni atarimasu", phoneticZh = "Wǒ mǎshàng jiù huì zhuóshǒu chǔlǐ zhè jiàn shì", phoneticKo = "Jigeum jeuksi i munje-reul cheorihagesseumnida"),

        CorePhraseEntry("x10", PhraseCategory.BUSINESS,
            "Haben Sie die Unterlagen bereits erhalten?", "Have you already received the documents?",
            "¿Ha recibido ya todos los documentos?", "Avez-vous déjà reçu tous les documents ?",
            "Ha già ricevuto tutti i documenti?", "Já recebeu todos os documentos?",
            "Вы уже получили все необходимые документы?", "Ви вже отримали всі необхідні документи?",
            "資料はすでにお手元に届きましたか？", "您已经收到相关文件了吗？", "서류는 이미 전달받으셨나요?",
            phoneticEs = "ah reh-see-BEE-doh YAH TOH-dohs lohs doh-koo-MEHN-tohs?", phoneticFr = "ah-vay-voo day-zhah ruh-soo lay doh-koo-mahn?",
            phoneticJa = "Shiryō wa sude ni otemoto ni todokimashita ka?", phoneticZh = "Nín yǐjīng shōudào xiāngguān wénjiànle ma?", phoneticKo = "Seoryuneun imi jeondalbadeusyeonnayo?"),

        CorePhraseEntry("x11", PhraseCategory.EMERGENCY,
            "Ich habe mich am Fuß verletzt.", "I injured my foot.",
            "Me he lastimado el pie.", "Je me suis blessé au pied.",
            "Mi sono fatto male al piede.", "Machuquei o meu pé.",
            "Я повредил ногу.", "Я поранив ногу.",
            "足を怪我してしまいました。", "我的脚受伤了。", "발을 다쳤습니다.",
            phoneticEs = "meh eh lahs-tee-MAH-doh ehl pyeh", phoneticFr = "zhuh muh swee bleh-say oh pyay",
            phoneticJa = "Ashi o kega shite shimaimashita", phoneticZh = "Wǒ de jiǎo shòushāngle", phoneticKo = "Bareul dachyeosseumnida"),

        CorePhraseEntry("x12", PhraseCategory.DAILY,
            "Das macht überhaupt nichts aus.", "It doesn't matter at all.",
            "No tiene la menor importancia, no te preocupes.", "Cela n'a pas la moindre importance.",
            "Non ha alcuna importanza, tranquillo.", "Não tem a menor importância, fique tranquilo.",
            "Это совсем не страшно, пустяки.", "Це дрібниці, зовсім не хвилюйтеся.",
            "全然大したことないですよ、お気になさらず。", "完全没关系，小事一桩。", "전혀 상관없어요, 신경 쓰지 마세요.",
            phoneticEs = "noh TYEH-neh lah meh-NOHR eem-pohr-TAHN-syah", phoneticFr = "suh-lah nah pah lah mwandr an-pohr-tahnss",
            phoneticJa = "Zenzen taishita koto nai desu yo", phoneticZh = "Wánquán méiguānxì, xiǎoshì yī zhuāng", phoneticKo = "Jeonhyeo sang-gwaneopseoyo"),

        CorePhraseEntry("x13", PhraseCategory.TRAVEL,
            "Wo befindet sich der nächste Taxistand?", "Where is the nearest taxi stand?",
            "¿Dónde se ubica la parada de taxis más próxima?", "Où est la station de taxis la plus proche ?",
            "Dov'è il posteggio taxi più vicino?", "Onde fica o ponto de táxi mais próximo?",
            "Где ближайшая стоянка такси?", "Де розташована найближча стоянка таксі?",
            "一番近いタクシー乗り場はどこですか？", "最近的出租车站立在何处？", "가장 가까운 택시 승강장은 어디에 있나요?",
            phoneticEs = "DOHN-deh seh oo-BEE-kah lah pah-RAH-dah?", phoneticFr = "oo ay lah stah-syohn duh tahk-see?",
            phoneticJa = "Ichiban chikai takushī noriba wa doko desu ka?", phoneticZh = "Zuìjìn de chūzūchē zhàn zài héchù?", phoneticKo = "Gajang gakkaun taeksi seunggangjang-eun eodi-ingayo?"),

        CorePhraseEntry("x14", PhraseCategory.DINING,
            "Ich bin gegen Nüsse und Erdnüsse allergisch.", "I am allergic to tree nuts and peanuts.",
            "Soy alérgico a los frutos secos y cacahuetes.", "Je suis allergique aux fruits à coque et cacahuètes.",
            "Sono allergico alle noci e alle arachidi.", "Sou alérgico a nozes e amendoim.",
            "У меня аллергия на орехи и арахис.", "У мене алергія на горіхи та арахіс.",
            "ナッツ類とピーナッツにアレルギーがあります。", "我对坚果和花生严重过敏。", "견과류와 땅콩 알레르기가 있습니다.",
            phoneticEs = "soy ah-LEHR-hee-koh ah lohs FROO-tohs SEH-kohs", phoneticFr = "zhuh swee ah-lehr-zheek oh frwee ah kohk",
            phoneticJa = "Nattsurui to pīnattsu ni arerugī ga arimasu", phoneticZh = "Wǒ duì jiānguǒ hé huāshēng yánzhòng guòmǐn", phoneticKo = "Gyeongwaryuwa ttangkong allereugiga isseumnida"),

        CorePhraseEntry("x15", PhraseCategory.DAILY,
            "Kommen Sie gut nach Hause!", "Get home safely!",
            "¡Que llegue muy bien a su casa!", "Rentrez bien chez vous !",
            "Torni a casa sano e salvo!", "Chegue bem em casa!",
            "Благополучно добраться домой!", "Безпечно дістатися вам додому!",
            "お気をつけてお帰りくださいね！", "回家路上注意安全，祝平安！", "조심히 잘 들어가세요!"),

        CorePhraseEntry("x16", PhraseCategory.BUSINESS,
            "Ich schätze Ihre wertvolle Zeit sehr.", "I really appreciate your valuable time.",
            "Aprecio muchísimo su valioso tiempo.", "J'apprécie énormément votre temps précieux.",
            "Apprezzo moltissimo il suo tempo prezioso.", "Agradeço muito pelo seu tempo precioso.",
            "Я очень ценю ваше драгоценное время.", "Я дуже ціную ваш дорогоцінний час.",
            "貴重なお時間をいただき、深く感謝いたします。", "非常感激您抽出的宝贵时间。", "소중한 시간을 내어주셔서 깊이 감사드립니다."),

        CorePhraseEntry("x17", PhraseCategory.FEELINGS,
            "Das hat mich positiv überrascht!", "That positively surprised me!",
            "¡Eso me sorprendió gratamente!", "Cela m'a agréablement surpris !",
            "Questo mi ha sorpreso molto piacevolmente!", "Isso me surpreendeu muito positivamente!",
            "Это меня приятно удивило!", "Це мене приємно здивувало!",
            "うれしい驚きでした！", "这真让我感到惊喜万分！", "기분 좋은 놀라움이네요!"),

        CorePhraseEntry("x18", PhraseCategory.TRAVEL,
            "Ist das Frühstück im Zimmerpreis enthalten?", "Is breakfast included in the room rate?",
            "¿El desayuno está incluido en el precio?", "Le petit-déjeuner est-il compris dans le prix ?",
            "La colazione è inclusa nel prezzo della camera?", "O café da manhã está incluso na diária?",
            "Завтрак включен в стоимость номера?", "Чи сніданок входить у вартість номера?",
            "朝食は宿泊料金に含まれていますか？", "早餐包含在房费里面吗？", "조식이 객실 요금에 포함되어 있나요?"),

        CorePhraseEntry("x19", PhraseCategory.EMERGENCY,
            "Halt! Nicht anfassen! Das ist gefährlich!", "Stop! Do not touch that! It is dangerous!",
            "¡Alto! ¡No toque eso! ¡Es peligroso!", "Arrêtez ! Ne touchez pas à ça ! C'est dangereux !",
            "Fermo! Non toccare quello! È pericoloso!", "Pare! Não toque nisso! É perigoso!",
            "Стой! Не трогай это! Это опасно!", "Стій! Не чіпай цього! Це небезпечно!",
            "止まって！それに触らないで！危険です！", "停下！千万别碰！那很危险！", "멈추세요! 그거 만지지 마세요! 위험합니다!"),

        CorePhraseEntry("x20", PhraseCategory.DAILY,
            "Alles Gute zum Geburtstag! Feiere schön!", "Happy birthday! Have a great celebration!",
            "¡Feliz cumpleaños! ¡Pásalo genial!", "Joyeux anniversaire ! Fête bien ça !",
            "Buon compleanno! Festeggia alla grande!", "Feliz aniversário! Aproveite muito!",
            "С днем рождения! Отличного праздника!", "З днем народження! Чудового свята!",
            "お誕生日おめでとう！素敵な一日を！", "祝你生日快乐！好好庆祝一下！", "생일 축하해요! 오늘 하루 신나게 보내세요!")
    )
}
