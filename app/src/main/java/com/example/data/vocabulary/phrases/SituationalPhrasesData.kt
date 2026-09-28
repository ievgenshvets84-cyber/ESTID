package com.example.data.vocabulary.phrases

import com.example.data.vocabulary.PhraseCategory
import com.example.data.vocabulary.PhraseItem

data class CorePhraseEntry(
    val id: String,
    val category: PhraseCategory,
    val de: String,
    val en: String,
    val es: String,
    val fr: String,
    val it: String,
    val pt: String,
    val ru: String,
    val uk: String,
    val ja: String,
    val zh: String,
    val ko: String,
    val phoneticEs: String = "",
    val phoneticFr: String = "",
    val phoneticIt: String = "",
    val phoneticPt: String = "",
    val phoneticRu: String = "",
    val phoneticUk: String = "",
    val phoneticJa: String = "",
    val phoneticZh: String = "",
    val phoneticKo: String = "",
    val exTargetPrefix: String = "",
    val exDe: String = "",
    val exEn: String = ""
)

object SituationalPhrasesData {

    fun getCorePhrasesForLanguage(targetLang: String, nativeLang: String): List<PhraseItem> {
        val t = targetLang.lowercase()
        val n = nativeLang.lowercase()

        return CORE_LIST.mapIndexed { index, entry ->
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
                "de" -> "Direkte Bedeutung: ${entry.de}"
                "en" -> "Literal meaning: ${entry.en}"
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
                id = "${t}_core_${entry.id}_${index + 1}",
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

    private val CORE_LIST = listOf(
        // === 1. DAILY & GREETINGS (15) ===
        CorePhraseEntry("d1", PhraseCategory.DAILY,
            "Guten Morgen! Wie geht es Ihnen?", "Good morning! How are you?",
            "¡Buenos días! ¿Cómo está usted?", "Bonjour ! Comment allez-vous ?",
            "Buongiorno! Come sta?", "Bom dia! Como vai o senhor?",
            "Доброе утро! Как ваши дела?", "Доброго ранку! Як ваші справи?",
            "おはようございます、お元気ですか？", "早上好！您好吗？", "좋은 아침입니다! 어떻게 지내세요?",
            phoneticEs = "BWEH-nos DEE-as, KOH-moh ess-TAH?", phoneticFr = "bon-zhoor, koh-mahn tah-lay-voo?",
            phoneticJa = "Ohayō gozaimasu, ogenki desu ka?", phoneticZh = "Zǎoshang hǎo! Nín hǎo ma?", phoneticKo = "Joeun achimimnida! Eotteoke jinaeseyo?"),

        CorePhraseEntry("d2", PhraseCategory.DAILY,
            "Sehr erfreut, Sie kennenzulernen!", "Pleased to meet you!",
            "¡Mucho gusto en conocerle!", "Ravi de faire votre connaissance !",
            "Molto piacere di conoscerla!", "Muito prazer em conhecê-lo!",
            "Очень приятно познакомиться!", "Дуже приємно познайомитися!",
            "はじめまして、お会いできて光栄です。", "很高兴认识您！", "만나서 반갑습니다!",
            phoneticEs = "MOO-choh GOOS-toh en koh-noh-SEHR-leh", phoneticFr = "rah-vee duh fehr vohtr koh-neh-sahnss",
            phoneticJa = "Hajimemashite, oaidekite kōei desu", phoneticZh = "Hěn gāoxìng rènshí nín!", phoneticKo = "Mannaseo bangapseumnida!"),

        CorePhraseEntry("d3", PhraseCategory.DAILY,
            "Mir geht es gut, danke! Und Ihnen?", "I am doing well, thank you! And you?",
            "Estoy bien, ¡gracias! ¿Y usted?", "Je vais bien, merci ! Et vous ?",
            "Sto bene, grazie! E lei?", "Estou bem, obrigado! E o senhor?",
            "У меня все хорошо, спасибо! А у вас?", "У мене все добре, дякую! А у вас?",
            "元気です、ありがとうございます！あなたは？", "我很好，谢谢！您呢？", "잘 지내고 있어요, 감사합니다! 당신은요?",
            phoneticEs = "ess-TOY byehn, GRAH-syahs! ee oos-TEHD?", phoneticFr = "zhuh vay byan, mehr-see! ay voo?",
            phoneticJa = "Genki desu, arigatō gozaimasu! Anata wa?", phoneticZh = "Wǒ hěn hǎo, xièxiè! Nín ne?", phoneticKo = "Jal jinaego isseoyo, gamsahamnida! Dangsineunyo?"),

        CorePhraseEntry("d4", PhraseCategory.DAILY,
            "Ich wünsche Ihnen einen schönen Tag!", "Have a wonderful day!",
            "¡Que tenga un excelente día!", "Passez une excellente journée !",
            "Le auguro una splendida giornata!", "Tenha um excelente dia!",
            "Желаю вам прекрасного дня!", "Бажаю вам чудового дня!",
            "良い一日をお過ごしください！", "祝您度过愉快的一天！", "좋은 하루 보내세요!",
            phoneticEs = "keh TEHN-gah oon ex-seh-LEHN-teh DEE-ah", phoneticFr = "pah-say oon ex-seh-lahnt zhoor-nay",
            phoneticJa = "Yoi ichinichi o osugoshi kudasai!", phoneticZh = "Zhù nín dùguò yúkuài de yītiān!", phoneticKo = "Joeun haru bonaeseyo!"),

        CorePhraseEntry("d5", PhraseCategory.DAILY,
            "Bis bald, pass auf dich auf!", "See you soon, take care!",
            "¡Hasta pronto, cuídate mucho!", "À bientôt, prends bien soin de toi !",
            "A presto, abbi cura di te!", "Até logo, cuide-se bem!",
            "До скорого, береги себя!", "До скорої зустрічі, бережи себе!",
            "またね、体に気をつけて！", "一会儿见，多保重！", "곧 봐요, 몸조심하세요!",
            phoneticEs = "AHS-tah PROHN-toh, KWEE-dah-teh MOO-choh", phoneticFr = "ah byan-toh, prahn byan swan duh twah",
            phoneticJa = "Mata ne, karada ni ki o tsukete!", phoneticZh = "Yīhuǐ'er jiàn, duō bǎozhòng!", phoneticKo = "Got bwayo, momjosimhaseyo!"),

        CorePhraseEntry("d6", PhraseCategory.DAILY,
            "Wie heißen Sie? Mein Name ist...", "What is your name? My name is...",
            "¿Cómo se llama usted? Me llamo...", "Comment vous appelez-vous ? Je m'appelle...",
            "Come si chiama? Mi chiamo...", "Como se chama? Meu nome é...",
            "Как вас зовут? Меня зовут...", "Як вас звати? Мене звати...",
            "お名前は何ですか？私は…と申します。", "请问您贵姓？我叫……", "이름이 무엇인가요? 제 이름은...입니다.",
            phoneticEs = "KOH-moh seh YAH-mah oos-TEHD? meh YAH-moh...", phoneticFr = "koh-mahn voo zah-play-voo? zhuh mah-pell...",
            phoneticJa = "Onamae wa nan desu ka? Watashi wa... to mōshimasu", phoneticZh = "Qǐngwèn nín guìxìng? Wǒ jiào...", phoneticKo = "Ireumi mueosingayo? Je ireumeun...imnida"),

        CorePhraseEntry("d7", PhraseCategory.DAILY,
            "Woher kommen Sie?", "Where are you from?",
            "¿De dónde es usted?", "D'où venez-vous ?",
            "Di dove sei / è?", "De onde você é?",
            "Откуда вы приехали?", "Звідки ви родом?",
            "ご出身はどちらですか？", "您来自哪里？", "어디 출신이신가요?",
            phoneticEs = "deh DOHN-deh ess oos-TEHD?", phoneticFr = "doo vuh-nay-voo?",
            phoneticJa = "Goshusshin wa dochira desu ka?", phoneticZh = "Nín láizì nǎlǐ?", phoneticKo = "Eodi chulsin-isingayo?"),

        CorePhraseEntry("d8", PhraseCategory.DAILY,
            "Sprechen Sie Englisch oder Deutsch?", "Do you speak English or German?",
            "¿Habla usted inglés o alemán?", "Parlez-vous anglais ou allemand ?",
            "Parla inglese o tedesco?", "Fala inglês ou alemão?",
            "Вы говорите по-английски или по-немецки?", "Ви розмовляєте англійською чи німецькою?",
            "英語かドイツ語を話せますか？", "您会说英语或德语吗？", "영어 또는 독일어를 하시나요?",
            phoneticEs = "AH-blah oos-TEHD een-GLEHS oh ah-leh-MAHN?", phoneticFr = "par-lay-voo ahn-glay oo ahl-mahn?",
            phoneticJa = "Eigo ka doitsugo o hanasemasu ka?", phoneticZh = "Nín huì shuō yīngyǔ huò déyǔ ma?", phoneticKo = "Yeong'eo ttoneun dogireoreul hasinayo?"),

        CorePhraseEntry("d9", PhraseCategory.DAILY,
            "Könnten Sie das bitte wiederholen?", "Could you please repeat that?",
            "¿Podría repetir eso, por favor?", "Pourriez-vous répéter, s'il vous plaît ?",
            "Potrebbe ripetere, per favore?", "Poderia repetir isso, por favor?",
            "Не могли бы вы повторить, пожалуйста?", "Чи не могли б ви повторити, будь ласка?",
            "もう一度繰り返していただけますか？", "请问您能重复一遍吗？", "다시 한번 말씀해 주시겠어요?",
            phoneticEs = "poh-DREE-ah reh-peh-TEER EH-soh, pohr fah-VOHR?", phoneticFr = "poo-ryay-voo ray-pay-tay, seel voo pleh?",
            phoneticJa = "Mō ichido kurikaeshite itadakemasu ka?", phoneticZh = "Qǐngwèn nín néng chóngfù yībiàn ma?", phoneticKo = "Dasi hanbeon malsseumhae jusigesseoyo?"),

        CorePhraseEntry("d10", PhraseCategory.DAILY,
            "Könnten Sie bitte etwas langsamer sprechen?", "Could you speak a bit slower, please?",
            "¿Podría hablar un poco más despacio?", "Pourriez-vous parler plus lentement ?",
            "Potrebbe parlare più lentamente, per favore?", "Poderia falar mais devagar, por favor?",
            "Пожалуйста, говорите немного медленнее.", "Будь ласка, говоріть трішки повільніше.",
            "もう少しゆっくり話していただけますか？", "请说得稍慢一点好吗？", "조금만 천천히 말씀해 주시겠어요?",
            phoneticEs = "poh-DREE-ah ah-BLAHR oon POH-koh mahs des-PAH-syoh?", phoneticFr = "poo-ryay-voo par-lay ploo lahn-tuh-mahn?",
            phoneticJa = "Mō sukoshi yukkuri hanashite itadakemasu ka?", phoneticZh = "Qǐng shuō de shāo màn yīdiǎn hǎo ma?", phoneticKo = "Jogeumman cheoncheonhi malsseumhae jusigesseoyo?"),

        CorePhraseEntry("d11", PhraseCategory.DAILY,
            "Vielen Dank für Ihre freundliche Unterstützung!", "Thank you very much for your kind support!",
            "¡Muchas gracias por su amable ayuda!", "Merci beaucoup pour votre aimable aide !",
            "Mille grazie per il suo gentile aiuto!", "Muito obrigado pela sua amável ajuda!",
            "Большое спасибо за вашу добрую помощь!", "Щиро дякую за вашу люб'язну допомогу!",
            "親切なサポートを本当にありがとうございます！", "非常感谢您的热情帮助！", "친절하게 도와주셔서 정말 감사합니다!",
            phoneticEs = "MOO-chahs GRAH-syahs pohr soo ah-MAH-bleh ah-YOO-dah", phoneticFr = "mehr-see boh-koo poor vohtr eh-mahbl ed",
            phoneticJa = "Shinsetsu na sapōto o hontō ni arigatō gozaimasu!", phoneticZh = "Fēicháng gǎnxiè nín de rèqíng bāngzhù!", phoneticKo = "Chinjeolhage dowajusyeoseo jeongmal gamsahamnida!"),

        CorePhraseEntry("d12", PhraseCategory.DAILY,
            "Gern geschehen! Keine Ursache!", "You are very welcome! Don't mention it!",
            "¡De nada! ¡No hay de qué!", "De rien ! Il n'y a pas de quoi !",
            "Prego! Di niente!", "De nada! Não há de quê!",
            "Не за что! Пожалуйста!", "Будь ласка! Нема за що!",
            "どういたしまして！お気になさらず！", "不客气！不用谢！", "천만에요! 별말씀을요!",
            phoneticEs = "deh NAH-dah! noh eye deh keh!", phoneticFr = "duh ryan! eel nya pah duh kwah!",
            phoneticJa = "Dōitashimashite! Oki ni nasarazu!", phoneticZh = "Bù kèqì! Bùyòng xiè!", phoneticKo = "Cheonmaneyo! Byeolmalsseumeullyo!"),

        CorePhraseEntry("d13", PhraseCategory.DAILY,
            "Entschuldigen Sie die Störung, bitte.", "Excuse me for troubling you.",
            "Disculpe la molestia, por favor.", "Excusez-moi de vous déranger.",
            "Mi scusi per il disturbo, per favore.", "Desculpe o incômodo, por favor.",
            "Извините за беспокойство, пожалуйста.", "Вибачте за турботу, будь ласка.",
            "お邪魔して申し訳ありません。", "打扰一下，请见谅。", "실례지만 번거롭게 해드려 죄송합니다.",
            phoneticEs = "dees-KOOL-peh lah moh-LESS-tyah, pohr fah-VOHR", phoneticFr = "ex-koo-zay mwah duh voo day-rahn-zhay",
            phoneticJa = "Ojama shite mōshiwake arimasen", phoneticZh = "Dǎrǎo yīxià, qǐng jiànliàng", phoneticKo = "Sillyejiman beongeoropge haedeuryeo joesonghamnida"),

        CorePhraseEntry("d14", PhraseCategory.DAILY,
            "Herzlichen Glückwunsch zu diesem Erfolg!", "Congratulations on this success!",
            "¡Muchas felicitaciones por este gran logro!", "Félicitations chaleureuses pour cette réussite !",
            "Congratulazioni di cuore per questo successo!", "Parabéns de coração por esse sucesso!",
            "Сердечно поздравляю с этим успехом!", "Щиро вітаю з цим великим успіхом!",
            "この度の成功、心よりお祝い申し上げます！", "衷心祝贺您取得的成功！", "이번 성공을 진심으로 축하드립니다!",
            phoneticEs = "MOO-chahs feh-lee-see-tah-SYOH-ness pohr EHS-teh grahn LOH-groh", phoneticFr = "fay-lee-see-tah-syohn shahl-uh-ruhz",
            phoneticJa = "Konotabi no seikō, kokoro yori oiwai mōshiagemasu!", phoneticZh = "Zhōngxīn zhùhè nín qǔdé de chénggōng!", phoneticKo = "Ibeon seonggong-eul jinsimeuro chukhadeurimnida!"),

        CorePhraseEntry("d15", PhraseCategory.DAILY,
            "Gute Nacht und schlaf gut!", "Good night and sleep well!",
            "¡Buenas noches y que duermas bien!", "Bonne nuit et dors bien !",
            "Buonanotte e dormi bene!", "Boa noite e durma bem!",
            "Доброй ночи и приятных снов!", "Добраніч і солодких снів!",
            "おやすみなさい、良い夢を！", "晚安，祝您做个好梦！", "안녕히 주무시고 좋은 꿈 꾸세요!",
            phoneticEs = "BWEH-nahs NOH-chess ee keh DWEHR-mahs byehn", phoneticFr = "bohn nwee ay dohr byan",
            phoneticJa = "Oyasuminasai, yoi yume o!", phoneticZh = "Wǎn'ān, zhù nín zuò gè hǎomèng!", phoneticKo = "Annyeonghi jumusigo joeun kkum kkuseyo!"),

        // === 2. TRAVEL & TRANSPORT (15) ===
        CorePhraseEntry("t1", PhraseCategory.TRAVEL,
            "Wo befindet sich der Hauptbahnhof?", "Where is the main train station located?",
            "¿Dónde se encuentra la estación central de trenes?", "Où se trouve la gare centrale ?",
            "Dov'è la stazione ferroviaria principale?", "Onde fica a estação ferroviária principal?",
            "Где находится главный железнодорожный вокзал?", "Де розташований центральний залізничний вокзал?",
            "中央駅はどこにありますか？", "中央火车站位于哪里？", "중앙 기차역은 어디에 있나요?",
            phoneticEs = "DOHN-deh seh en-KWEHN-trah lah ess-tah-SYOHN sehn-TRAHL?", phoneticFr = "oo suh troov lah gahr sahn-trahl?",
            phoneticJa = "Chūō-eki wa doko ni arimasu ka?", phoneticZh = "Zhōngyāng huǒchēzhàn wèiyú nǎlǐ?", phoneticKo = "Jung'ang gichayeog-eun eodie innayo?"),

        CorePhraseEntry("t2", PhraseCategory.TRAVEL,
            "Wie komme ich am schnellsten ins Stadtzentrum?", "How do I get to the city center fastest?",
            "¿Cómo llego al centro de la ciudad más rápido?", "Comment aller au centre-ville le plus vite ?",
            "Come arrivo al centro città più rapidamente?", "Como chego ao centro da cidade mais rápido?",
            "Как быстрее всего добраться до центра города?", "Як найшвидше дістатися центру міста?",
            "市内中心部へ一番早く行くにはどうすればいいですか？", "去市中心最快的方式是什么？", "시내 중심가로 가장 빨리 가는 방법은 무엇인가요?",
            phoneticEs = "KOH-moh YEH-goh ahl SEHN-troh deh lah syoo-DAHD?", phoneticFr = "koh-mahn ah-lay oh sahn-truh-veel?",
            phoneticJa = "Shinai chūshinbu e ichiban hayaku iku niwa dō sureba ii desu ka?", phoneticZh = "Qù shì zhōngxīn zuì kuài de fāngshì shì shénme?", phoneticKo = "Sinae jungsimgaro gajang ppalli ganeun bangbeob-eun mueosingayo?"),

        CorePhraseEntry("t3", PhraseCategory.TRAVEL,
            "Ist es weit von hier oder kann man zu Fuß gehen?", "Is it far from here, or can I walk?",
            "¿Está lejos de aquí o se puede ir a pie?", "Est-ce loin d'ici ou peut-on y aller à pied ?",
            "È lontano da qui o si può andare a piedi?", "É longe daqui ou dá para ir a pé?",
            "Это далеко отсюда или можно дойти пешком?", "Це далеко звідси чи можна дійти пішки?",
            "ここから遠いですか、それとも歩いて行けますか？", "离这里远吗，还是可以步行到达？", "여기서 먼가요, 아니면 걸어갈 수 있나요?",
            phoneticEs = "ess-TAH LEH-hohs deh ah-KEE oh seh PWEH-deh eer ah pyeh?", phoneticFr = "ess lwan dee-see oo puh-tohn ee ah-lay ah pyay?",
            phoneticJa = "Koko kara tōi desu ka, soretomo aruite ikemasu ka?", phoneticZh = "Lí zhèlǐ yuǎn ma, háishì kěyǐ bùxíng dàodá?", phoneticKo = "Yeogiseo meongayo, animyeon georeogal su innayo?"),

        CorePhraseEntry("t4", PhraseCategory.TRAVEL,
            "Eine Fahrkarte hin und zurück, bitte.", "A round-trip ticket, please.",
            "Un boleto de ida y vuelta, por favor.", "Un billet aller-retour, s'il vous plaît.",
            "Un biglietto di andata e ritorno, per favore.", "Uma passagem de ida e volta, por favor.",
            "Билет туда и обратно, пожалуйста.", "Квиток туди й назад, будь ласка.",
            "往復切符を一枚お願いします。", "请给我一张往返票。", "왕복 티켓 한 장 부탁드립니다.",
            phoneticEs = "oon boh-LEH-toh deh EE-dah ee VWEL-tah, pohr fah-VOHR", phoneticFr = "un bee-yay ah-lay ruh-toor, seel voo pleh",
            phoneticJa = "Ōfuku kippu o ichimai onegai shimasu", phoneticZh = "Qǐng gěi wǒ yī zhāng wǎngfǎn piào", phoneticKo = "Wangbok tiket han jang butakdeurimnida"),

        CorePhraseEntry("t5", PhraseCategory.TRAVEL,
            "Von welchem Gleis fährt der Zug ab?", "Which platform does the train depart from?",
            "¿De qué andén sale el tren?", "De quel quai part le train ?",
            "Da quale binario parte il treno?", "De qual plataforma sai o trem?",
            "С какой платформы отправляется поезд?", "З якої колії вирушає потяг?",
            "その列車は何番線から出発しますか？", "火车从哪个站台发车？", "그 기차는 몇 번 승강장에서 출발하나요?",
            phoneticEs = "deh keh ahn-DEHN SAH-leh ehl trehn?", phoneticFr = "duh kell kay pahr luh tran?",
            phoneticJa = "Sono ressha wa nanban-sen kara shuppatsu shimasu ka?", phoneticZh = "Huǒchē cóng nǎge zhàntái fāchē?", phoneticKo = "Geu gichaneun myeot beon seunggangjang-eseo chulbalhanayo?"),

        CorePhraseEntry("t6", PhraseCategory.TRAVEL,
            "Wo finde ich den nächsten Taxistand?", "Where can I find the nearest taxi stand?",
            "¿Dónde puedo encontrar la parada de taxis más cercana?", "Où puis-je trouver la station de taxis la plus proche ?",
            "Dove posso trovare il posteggio dei taxi più vicino?", "Onde posso encontrar o ponto de táxi mais próximo?",
            "Где находится ближайшая стоянка такси?", "Де розташована найближча стоянка таксі?",
            "一番近いタクシー乗り場はどこですか？", "最近的出租车站立在何处？", "가장 가까운 택시 승강장은 어디에 있나요?",
            phoneticEs = "DOHN-deh PWEH-doh en-kohn-TRAHR lah pah-RAH-dah deh TAHK-sees?", phoneticFr = "oo pweezh troo-vay lah stah-syohn duh tahk-see?",
            phoneticJa = "Ichiban chikai takushī noriba wa doko desu ka?", phoneticZh = "Zuìjìn de chūzūchē zhàn zài héchù?", phoneticKo = "Gajang gakkaun taeksi seunggangjang-eun eodie innayo?"),

        CorePhraseEntry("t7", PhraseCategory.TRAVEL,
            "Ich habe eine Reservierung auf den Namen...", "I have a reservation under the name...",
            "Tengo una reserva a nombre de...", "J'ai une réservation au nom de...",
            "Ho una prenotazione a nome di...", "Tenho uma reserva em nome de...",
            "У меня бронь на имя...", "У мене заброньовано на ім'я...",
            "…の名前で予約しております。", "我有一个预订，名字是……", "...이름으로 예약했습니다.",
            phoneticEs = "TEHN-goh OO-nah reh-SEHR-vah ah NOHM-breh deh...", phoneticFr = "zhay oon ray-zehr-vah-syohn oh nohm duh...",
            phoneticJa = "...no namae de yoyaku shite orimasu", phoneticZh = "Wǒ yǒu yīgè yùdìng, míngzì shì...", phoneticKo = "...ireumeuro yeyakhaesseumnida"),

        CorePhraseEntry("t8", PhraseCategory.TRAVEL,
            "Wie lautet das WLAN-Passwort hier?", "What is the Wi-Fi password here?",
            "¿Cuál es la contraseña del wifi aquí?", "Quel est le mot de passe du Wi-Fi ici ?",
            "Qual è la password del Wi-Fi qui?", "Qual é a senha do Wi-Fi aqui?",
            "Какой здесь пароль от вайфая?", "Який тут пароль від Wi-Fi?",
            "ここのWi-Fiパスワードは何ですか？", "请问这里的无线网络密码是什么？", "여기 와이파이 비밀번호가 무엇인가요?",
            phoneticEs = "kwahl ess lah kohn-trah-SEH-nyah dehl WEE-fee ah-KEE?", phoneticFr = "kell ay luh moh duh pahss doo wee-fee ee-see?",
            phoneticJa = "Koko no waifai pasuwādo wa nan desu ka?", phoneticZh = "Qǐngwèn zhèlǐ de wúxiàn wǎngluò mìmǎ shì shénme?", phoneticKo = "Yeogi waipai bimilbeonhoga mueosingayo?"),

        CorePhraseEntry("t9", PhraseCategory.TRAVEL,
            "Können Sie mir das auf der Karte zeigen?", "Could you show me this on the map?",
            "¿Podría mostrarme esto en el mapa?", "Pourriez-vous me montrer cela sur le plan ?",
            "Potrebbe mostrarmelo sulla mappa?", "Poderia me mostrar isso no mapa?",
            "Не могли бы вы показать мне это на карте?", "Чи не могли б ви показати це на карті?",
            "地図上で教えていただけますか？", "您能在地图上指给我看一下吗？", "지도에서 보여주실 수 있나요?",
            phoneticEs = "poh-DREE-ah mohs-TRAHR-meh EHS-toh en ehl MAH-pah?", phoneticFr = "poo-ryay-voo muh mohn-tray suh-lah soor luh plahn?",
            phoneticJa = "Chizu-jō de oshiete itadakemasu ka?", phoneticZh = "Nín néng zài dìtú shàng zhǐ gěi wǒ kàn yīxià ma?", phoneticKo = "Jido-eseo boyeojusil su innayo?"),

        CorePhraseEntry("t10", PhraseCategory.TRAVEL,
            "Biegen Sie an der Kreuzung rechts ab.", "Turn right at the intersection.",
            "Gire a la derecha en la intersección.", "Tournez à droite au croisement.",
            "Giri a destra all'incrocio.", "Vire à direita no cruzamento.",
            "Поверните направо на перекрестке.", "Поверніть праворуч на перехресті.",
            "交差点を右に曲がってください。", "在十字路口向右转。", "교차로에서 우회전하세요.",
            phoneticEs = "HEE-reh ah lah deh-REH-chah en lah een-tehr-sek-SYOHN", phoneticFr = "toor-nay ah drwaht oh krwahz-mahn",
            phoneticJa = "Kōsaten o migi ni magatte kudasai", phoneticZh = "Zài shízìlùkǒu xiàng yòuzhuǎn", phoneticKo = "Gyocharo-eseo uhoejeonhaseyo"),

        CorePhraseEntry("t11", PhraseCategory.TRAVEL,
            "Wo befindet sich der nächste Geldautomat?", "Where is the nearest ATM located?",
            "¿Dónde hay un cajero automático cercano?", "Où y a-t-il un distributeur de billets ?",
            "Dov'è il bancomat più vicino?", "Onde fica o caixa eletrônico mais próximo?",
            "Где находится ближайший банкомат?", "Де розташований найближчий банкомат?",
            "一番近いATMはどこにありますか？", "最近的自动取款机在哪里？", "가장 가까운 현금 인출기는 어디인가요?",
            phoneticEs = "DOHN-deh eye oon kah-HEH-roh ow-toh-MAH-tee-koh?", phoneticFr = "oo ee ah-teel oon dees-tree-boo-tuhr duh bee-yay?",
            phoneticJa = "Ichiban chikai ētīemu wa doko ni arimasu ka?", phoneticZh = "Zuìjìn de zìdòng qǔkuǎnjī zài nǎlǐ?", phoneticKo = "Gajang gakkaun hyeongeum inchulgi-neun eodi-ingayo?"),

        CorePhraseEntry("t12", PhraseCategory.TRAVEL,
            "Wo kann ich eine lokale SIM-Karte kaufen?", "Where can I buy a local SIM card?",
            "¿Dónde puedo comprar una tarjeta SIM local?", "Où puis-je acheter une carte SIM locale ?",
            "Dove posso acquistare una scheda SIM locale?", "Onde posso comprar um chip SIM local?",
            "Где я могу купить местную SIM-карту?", "Де я можу придбати місцеву SIM-картку?",
            "現地のSIMカードはどこで買えますか？", "我在哪里可以购买当地的电话卡？", "현지 유심 카드는 어디서 구매할 수 있나요?",
            phoneticEs = "DOHN-deh PWEH-doh kohm-PRAHR OO-nah tahr-HEH-tah seem?", phoneticFr = "oo pweezh ahsh-tay oon kahrt seem loh-kahl?",
            phoneticJa = "Genchi no shimu-kādo wa doko de kaemasu ka?", phoneticZh = "Wǒ zài nǎlǐ kěyǐ gòumǎi dāngdì de diànhuà kǎ?", phoneticKo = "Hyeonji yusim kadeuneun eodiseo gumaehal su innayo?"),

        CorePhraseEntry("t13", PhraseCategory.TRAVEL,
            "Ich habe mich verlaufen, können Sie mir helfen?", "I am lost, could you please help me?",
            "Me he perdido, ¿podría ayudarme por favor?", "Je me suis perdu, pouvez-vous m'aider ?",
            "Mi sono perso, mi potrebbe aiutare per favore?", "Estou perdido, poderia me ajudar por favor?",
            "Я заблудился, вы не могли бы мне помочь?", "Я заблукав, ви не могли б мені допомогти?",
            "道に迷ってしまいました、助けていただけますか？", "我迷路了，您可以帮帮我吗？", "길을 잃어버렸는데, 좀 도와주시겠어요?",
            phoneticEs = "meh eh pehr-DEE-doh, poh-DREE-ah ah-yoo-DAHR-meh?", phoneticFr = "zhuh muh swee pehr-doo, poo-vay-voo may-day?",
            phoneticJa = "Michi ni mayotte shimaimashita, tasukete itadakemasu ka?", phoneticZh = "Wǒ mílùle, nín kěyǐ bāng bāng wǒ ma?", phoneticKo = "Gireul ireobeoryeonneunde, jom dowajusigesseoyo?"),

        CorePhraseEntry("t14", PhraseCategory.TRAVEL,
            "Um wie viel Uhr ist der Check-out?", "What time is checkout?",
            "¿A qué hora es el registro de salida?", "À quelle heure est le départ de l'hôtel ?",
            "A che ora è il check-out?", "A que horas é o check-out?",
            "В какое время нужно освободить номер?", "О котрій годині потрібно звільнити номер?",
            "チェックアウトの時間は何時ですか？", "退房时间是几点？", "체크아웃 시간은 몇 시인가요?",
            phoneticEs = "ah keh OH-rah ess ehl reh-HEES-troh deh sah-LEE-dah?", phoneticFr = "ah kell uhr ay luh day-pahr duh loh-tell?",
            phoneticJa = "Chekkuauto no jikan wa nanji desu ka?", phoneticZh = "Tuìfáng shíjiān shì jǐ diǎn?", phoneticKo = "Chekeu-aut siganeun myeot si-ingayo?"),

        CorePhraseEntry("t15", PhraseCategory.TRAVEL,
            "Ich wünsche Ihnen eine sichere und gute Reise!", "Have a safe and pleasant journey!",
            "¡Le deseo un viaje muy seguro y agradable!", "Je vous souhaite un voyage agréable et sans encombre !",
            "Le auguro un viaggio sicuro e piacevole!", "Desejo-lhe uma viagem segura e muito agradável!",
            "Счастливого и безопасного вам пути!", "Щасливої та безпечної вам подорожі!",
            "道中どうぞお気をつけて、良い旅を！", "祝您旅途平安愉快！", "안전하고 즐거운 여행 되세요!",
            phoneticEs = "leh deh-SEH-oh oon VYAH-heh MOOY seh-GOO-roh", phoneticFr = "zhuh voo sweht oon vwah-yahzh ah-gray-ahbl",
            phoneticJa = "Dōchū dōzo oki o tsukete, yoi tabi o!", phoneticZh = "Zhù nín lǚtú píng'ān yúkuài!", phoneticKo = "Anjeonhago jeulgeoun yeohaeng doeseyo!")
    )
}
