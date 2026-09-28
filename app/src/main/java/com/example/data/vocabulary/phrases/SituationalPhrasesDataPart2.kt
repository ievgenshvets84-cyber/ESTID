package com.example.data.vocabulary.phrases

import com.example.data.vocabulary.PhraseCategory
import com.example.data.vocabulary.PhraseItem

object SituationalPhrasesDataPart2 {

    fun getPart2PhrasesForLanguage(targetLang: String, nativeLang: String): List<PhraseItem> {
        val t = targetLang.lowercase()
        val n = nativeLang.lowercase()

        return PART2_LIST.mapIndexed { index, entry ->
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
                id = "${t}_core2_${entry.id}_${index + 16}",
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

    private val PART2_LIST = listOf(
        // === 3. DINING & CAFE (15) ===
        CorePhraseEntry("fd1", PhraseCategory.DINING,
            "Einen Tisch für zwei Personen, bitte.", "A table for two, please.",
            "Una mesa para dos personas, por favor.", "Une table pour deux personnes, s'il vous plaît.",
            "Un tavolo per due persone, per favore.", "Uma mesa para duas pessoas, por favor.",
            "Столик на двоих, пожалуйста.", "Столик на двох, будь ласка.",
            "二名用の席をお願いします。", "请给我们安排一张两人的桌子。", "두 명 자리 부탁드립니다.",
            phoneticEs = "OO-nah MEH-sah PAH-rah dohs pehr-SOH-nahs", phoneticFr = "oon tahbl poor duh pehr-suhn",
            phoneticJa = "Nimei-yō no seki o onegai shimasu", phoneticZh = "Qǐng gěi wǒmen ānpái yī zhāng liǎng rén de zhuōzi", phoneticKo = "Du myeong jari butakdeurimnida"),

        CorePhraseEntry("fd2", PhraseCategory.DINING,
            "Könnten wir bitte die Speisekarte haben?", "Could we have the menu, please?",
            "¿Podríamos ver el menú, por favor?", "Pourrions-nous avoir la carte, s'il vous plaît ?",
            "Potremmo avere il menu, per favore?", "Poderíamos ver o cardápio, por favor?",
            "Можно нам меню, пожалуйста?", "Чи можна нам меню, будь ласка?",
            "メニューを見せていただけますか？", "请给我们看下菜单可以吗？", "메뉴판 좀 보여주시겠어요?",
            phoneticEs = "poh-DREE-ah-mohs vehr ehl meh-NOO, pohr fah-VOHR?", phoneticFr = "poo-ryohn-noo ah-vwahr lah kahrt?",
            phoneticJa = "Menyū o misete itadakemasu ka?", phoneticZh = "Qǐng gěi wǒmen kàn xià càidān kěyǐ ma?", phoneticKo = "Menyupan jom boyeojusigesseoyo?"),

        CorePhraseEntry("fd3", PhraseCategory.DINING,
            "Was können Sie uns heute empfehlen?", "What do you recommend today?",
            "¿Qué nos recomienda usted para hoy?", "Que nous recommandez-vous aujourd'hui ?",
            "Cosa ci consiglia per oggi?", "O que o senhor nos recomenda hoje?",
            "Что вы порекомендуете сегодня?", "Що ви можете порекомендувати сьогодні?",
            "本日のおすすめは何ですか？", "请问今天有什么招牌菜推荐？", "오늘 추천해주실 만한 메뉴가 있나요?",
            phoneticEs = "keh nohs reh-koh-MYEHN-dah oos-TEHD PAH-rah oy?", phoneticFr = "kuh noo ruh-koh-mahn-day-voo oh-zhoor-dwee?",
            phoneticJa = "Honjitsu no osusume wa nan desu ka?", phoneticZh = "Qǐngwèn jīntiān yǒu shénme zhāopái cài tuījiàn?", phoneticKo = "Oneul chucheonhaejusil manhan menyuga innayo?"),

        CorePhraseEntry("fd4", PhraseCategory.DINING,
            "Haben Sie vegetarische oder vegane Optionen?", "Do you have vegetarian or vegan options?",
            "¿Tienen platos vegetarianos o veganos?", "Avez-vous des options végétariennes ou véganes ?",
            "Avete piatti vegetariani o vegani?", "Vocês têm opções vegetarianas ou veganas?",
            "У вас есть вегетарианские или веганские блюда?", "Чи є у вас вегетаріанські або веганські страви?",
            "ベジタリアンやヴィーガン向けの料理はありますか？", "请问有素食或纯素餐点吗？", "채식주의자나 비건 메뉴가 있나요?",
            phoneticEs = "TYEH-nehn PLAH-tohs veh-heh-tah-RYAH-nohs?", phoneticFr = "ah-vay-voo day zohp-syohn vay-zhay-tah-ryenn?",
            phoneticJa = "Bejitarian ya vīgan muke no ryōri wa arimasu ka?", phoneticZh = "Qǐngwèn yǒu sùshí huò chún sù cāndiǎn ma?", phoneticKo = "Chaesikjuuijana bigeon menyuga innayo?"),

        CorePhraseEntry("fd5", PhraseCategory.DINING,
            "Guten Appetit! Lass es dir schmecken!", "Enjoy your meal! Bon appétit!",
            "¡Buen provecho! ¡Que lo disfrutes!", "Bon appétit ! Régalez-vous !",
            "Buon appetito! Goditi il pranzo!", "Bom apetite! Aproveite a refeição!",
            "Приятного аппетита! Наслаждайтесь!", "Смачного! Насолоджуйтесь трапезою!",
            "いただきます！美味しく召し上がれ！", "请慢用！祝您胃口好！", "맛있게 드세요! 식사 맛있게 하세요!",
            phoneticEs = "bwehn proh-VEH-choh! keh loh dees-FROO-tess!", phoneticFr = "bohn ah-pay-tee! ray-gah-lay-voo!",
            phoneticJa = "Itadakimasu! Oishiku meshagare!", phoneticZh = "Qǐng màn yòng! Zhù nín wèikǒu hǎo!", phoneticKo = "Mas-issge deuseyo! Siksa mas-issge haseyo!"),

        CorePhraseEntry("fd6", PhraseCategory.DINING,
            "Zum Wohl! Auf unsere Gesundheit!", "Cheers! To our health!",
            "¡Salud! ¡Por nosotros!", "Santé ! À notre santé !",
            "Salute! Alla nostra!", "Saúde! A nós!",
            "За здоровье! Ваше здоровье!", "Будьмо! За наше здоров'я!",
            "乾杯！健康を祝して！", "干杯！为健康干杯！", "건배! 우리의 건강을 위하여!",
            phoneticEs = "sah-LOOD! pohr noh-SOH-trohs!", phoneticFr = "sahn-tay! ah nohtr sahn-tay!",
            phoneticJa = "Kanpai! Kenkō o shukushite!", phoneticZh = "Gānbēi! Wèi jiànkāng gānbēi!", phoneticKo = "Geonbae! Uriui geon-gang-eul wihayeo!"),

        CorePhraseEntry("fd7", PhraseCategory.DINING,
            "Das Essen war absolut vorzüglich, danke!", "The meal was absolutely delicious, thank you!",
            "¡La comida estuvo deliciosa, muchísimas gracias!", "Le repas était absolument délicieux, merci !",
            "Il cibo era delizioso, grazie mille!", "A refeição estava absolutamente deliciosa, obrigado!",
            "Еда была невероятно вкусной, спасибо!", "Їжа була надзвичайно смачною, щиро дякую!",
            "お料理がとても美味しかったです、ごちそうさまでした！", "菜品非常美味，多谢款待！", "음식이 정말 맛있었습니다, 잘 먹었습니다!",
            phoneticEs = "lah koh-MEE-dah ess-TOO-voh deh-lee-SYOH-sah", phoneticFr = "luh ruh-pah ay-teh ahb-soh-loo-mahn day-lee-syuh",
            phoneticJa = "Oryōri ga totemo oishikatta desu, gochisōsama deshita!", phoneticZh = "Càipǐn fēicháng měiwèi, duōxiè kuǎndài!", phoneticKo = "Eumsigi jeongmal mas-isseosseumnida, jal meogeosseumnida!"),

        CorePhraseEntry("fd8", PhraseCategory.DINING,
            "Könnten wir bitte zahlen? Die Rechnung, bitte.", "Could we have the bill, please?",
            "¿Nos trae la cuenta, por favor?", "L'addition, s'il vous plaît.",
            "Il conto, per favore.", "A conta, por favor.",
            "Можно счет, пожалуйста?", "Рахунок, будь ласка.",
            "お会計をお願いします。", "买单，请给我账单。", "계산서 부탁드립니다.",
            phoneticEs = "nohs TRAH-eh lah KWEHN-tah, pohr fah-VOHR?", phoneticFr = "lah-dee-syohn, seel voo pleh",
            phoneticJa = "Okaikei o onegai shimasu", phoneticZh = "Mǎidān, qǐng gěi wǒ zhàngdān", phoneticKo = "Gyesanseo butakdeurimnida"),

        CorePhraseEntry("fd9", PhraseCategory.DINING,
            "Kann ich hier mit Karte bezahlen?", "Can I pay by credit card here?",
            "¿Se puede pagar con tarjeta de crédito aquí?", "Puis-je payer par carte bancaire ici ?",
            "Posso pagare con carta di credito qui?", "Posso pagar com cartão de crédito aqui?",
            "Можно ли здесь оплатить банковской картой?", "Чи можна тут розрахуватися банківською карткою?",
            "クレジットカードで支払えますか？", "这里可以用信用卡付款吗？", "여기서 신용카드로 결제할 수 있나요?",
            phoneticEs = "seh PWEH-deh pah-GAHR kohn tahr-HEH-tah?", phoneticFr = "pweezh pay-yay pahr kahrt bahn-kehr?",
            phoneticJa = "Kurejitto kādo de shiharaemasu ka?", phoneticZh = "Zhèlǐ kěyǐ yòng xìnyòngkǎ fùkuǎn ma?", phoneticKo = "Yeogiseo sin-yongkadeuro gyeoljehal su innayo?"),

        CorePhraseEntry("fd10", PhraseCategory.DINING,
            "Stimmt so, der Rest ist für Sie!", "Keep the change, thank you!",
            "Está bien así, ¡quédese con el cambio!", "Gardez la monnaie, c'est pour vous !",
            "Tenga il resto, è per lei!", "Fique com o troco, é seu!",
            "Сдачи не нужно, это вам!", "Решти не треба, це для вас!",
            "お釣りは取っておいてください！", "不用找零钱了，这是给您的小费！", "거스름돈은 가지세요!",
            phoneticEs = "ess-TAH byehn ah-SEE, KEH-deh-seh kohn ehl KAHM-byoh", phoneticFr = "gahr-day lah muh-nay, say poor voo",
            phoneticJa = "Otsuri wa totte oite kudasai!", phoneticZh = "Bùyòng zhǎo língqiánle, zhè shì gěi nín de xiǎofèi!", phoneticKo = "Geoseureumdoneun gajiseyo!"),

        // === 4. SHOPPING (10) ===
        CorePhraseEntry("sh1", PhraseCategory.DAILY,
            "Wie viel kostet dieser Artikel hier?", "How much does this item cost?",
            "¿Cuánto cuesta este artículo aquí?", "Combien coûte cet article ici ?",
            "Quanto costa questo articolo qui?", "Quanto custa este item aqui?",
            "Сколько стоит этот товар?", "Скільки коштує цей товар?",
            "これはいくらですか？", "这件商品多少钱？", "이 상품은 얼마인가요?",
            phoneticEs = "KWAHN-toh KWEHS-tah EHS-teh ahr-TEE-koo-loh?", phoneticFr = "kohn-byan koot set ahr-teekl?",
            phoneticJa = "Kore wa ikura desu ka?", phoneticZh = "Zhè jiàn shāngpǐn duōshǎo qián?", phoneticKo = "I sangpumeun eolma-ingayo?"),

        CorePhraseEntry("sh2", PhraseCategory.DAILY,
            "Wo befindet sich die Umkleidekabine?", "Where is the fitting room?",
            "¿Dónde están los probadores?", "Où se trouvent les cabines d'essayage ?",
            "Dove si trovano i camerini?", "Onde ficam os provadores?",
            "Где находится примерочная?", "Де розташована примірочна?",
            "試着室はどこにありますか？", "试衣间在哪里？", "탈의실은 어디에 있나요?",
            phoneticEs = "DOHN-deh ess-TAHN lohs proh-bah-DOH-ress?", phoneticFr = "oo suh troov lay kah-been deh-say-yahzh?",
            phoneticJa = "Shichakushitsu wa doko ni arimasu ka?", phoneticZh = "Shìyījiān zài nǎlǐ?", phoneticKo = "Tar-uisir-eun eodie innayo?"),

        CorePhraseEntry("sh3", PhraseCategory.DAILY,
            "Haben Sie das in einer größeren Größe?", "Do you have this in a larger size?",
            "¿Tiene esto en una talla más grande?", "Avez-vous cela dans une taille plus grande ?",
            "Ha questo in una taglia più grande?", "Tem este modelo em um tamanho maior?",
            "У вас есть это большего размера?", "Чи маєте ви це більшого розміру?",
            "これのもっと大きいサイズはありますか？", "请问有更大一号的尺码吗？", "이것보다 더 큰 사이즈가 있나요?",
            phoneticEs = "TYEH-neh EHS-toh en OO-nah TAH-yah mahs GRAHN-deh?", phoneticFr = "ah-vay-voo suh-lah dahn zoon tie ploo grahnd?",
            phoneticJa = "Kore no motto ōkii saizu wa arimasu ka?", phoneticZh = "Qǐngwèn yǒu gèng dà yī hào de chǐmǎ ma?", phoneticKo = "Igeotboda deo keun saijeuga innayo?"),

        CorePhraseEntry("sh4", PhraseCategory.DAILY,
            "Ich schaue mich nur ein wenig um, danke!", "I am just browsing, thank you!",
            "Solo estoy mirando un poco, ¡gracias!", "Je regarde juste un peu, merci !",
            "Sto solo dando un'occhiata, grazie!", "Estou só olhando, obrigado!",
            "Я просто смотрю, спасибо!", "Я просто оглядаюся, дякую!",
            "見ているだけです、ありがとうございます！", "我只是随便看看，谢谢！", "그냥 구경하는 중이에요, 감사합니다!",
            phoneticEs = "SOH-loh ess-TOY mee-RAHN-doh, GRAH-syahs!", phoneticFr = "zhuh ruh-gahrd zhoost un puh, mehr-see!",
            phoneticJa = "Mite iru dake desu, arigatō gozaimasu!", phoneticZh = "Wǒ zhǐshì suíbiàn kàn kàn, xièxiè!", phoneticKo = "Geunyang gugyeonghaneun jung-ieyo, gamsahamnida!"),

        CorePhraseEntry("sh5", PhraseCategory.DAILY,
            "Kann ich bitte eine Quittung bekommen?", "Could I please have a receipt?",
            "¿Me puede dar el recibo, por favor?", "Puis-je avoir un reçu, s'il vous plaît ?",
            "Posso avere lo scontrino, per favore?", "Pode me dar o comprovante, por favor?",
            "Можно чек, пожалуйста?", "Можна чек, будь ласка?",
            "レシートをいただけますか？", "能给我一张发票吗？", "영수증 좀 주시겠어요?",
            phoneticEs = "meh PWEH-deh dahr ehl reh-SEE-boh, pohr fah-VOHR?", phoneticFr = "pweezh ah-vwahr un ruh-soo, seel voo pleh?",
            phoneticJa = "Reshīto o itadakemasu ka?", phoneticZh = "Néng gěi wǒ yī zhāng fāpiào ma?", phoneticKo = "Yeongsujeung jom jusigesseoyo?"),

        // === 5. FEELINGS, REACTIONS & OPINIONS (15) ===
        CorePhraseEntry("fe1", PhraseCategory.FEELINGS,
            "Ich stimme Ihnen vollkommen zu.", "I completely agree with you.",
            "Estoy totalmente de acuerdo con usted.", "Je suis tout à fait d'accord avec vous.",
            "Sono perfettamente d'accordo con lei.", "Concordo plenamente com o senhor.",
            "Я полностью с вами согласен.", "Я повністю з вами згоден.",
            "あなたのご意見に完全に同意します。", "我完全赞同您的观点。", "당신의 의견에 전적으로 동의합니다.",
            phoneticEs = "ess-TOY toh-tahl-MEHN-teh deh ah-KWEHR-doh", phoneticFr = "zhuh swee too tah feh dah-kohr ah-vek voo",
            phoneticJa = "Anata no go-iken ni kanzen ni dōi shimasu", phoneticZh = "Wǒ wánquán zàntóng nín de guāndiǎn", phoneticKo = "Dangsin-ui uigyeon-e jeonjeog-euro dong'uihamnida"),

        CorePhraseEntry("fe2", PhraseCategory.FEELINGS,
            "Das klingt absolut fantastisch!", "That sounds absolutely fantastic!",
            "¡Eso suena absolutamente fantástico!", "Cela semble tout à fait fantastique !",
            "Sembra davvero meraviglioso!", "Isso soa absolutamente fantástico!",
            "Это звучит просто потрясающе!", "Це звучить просто дивовижно!",
            "それは本当に素晴らしいですね！", "听起来太棒了！", "정말 환상적으로 들리네요!",
            phoneticEs = "EH-soh SWEH-nah ahb-soh-LOO-tah-mehn-teh fahn-TAHS-tee-koh", phoneticFr = "suh-lah sahmbl fahntahsteek",
            phoneticJa = "Sore wa hontō ni subarashii desu ne!", phoneticZh = "Tīng qǐlái tài bàngle!", phoneticKo = "Jeongmal hwansangjeog-euro deullineyo!"),

        CorePhraseEntry("fe3", PhraseCategory.FEELINGS,
            "Ich freue mich schon riesig darauf!", "I am really looking forward to it!",
            "¡Tengo muchísimas ganas de que llegue!", "J'ai vraiment hâte d'y être !",
            "Non vedo l'ora che arrivi quel momento!", "Estou ansioso por isso!",
            "Я с нетерпением жду этого!", "Я з нетерпінням чекаю на це!",
            "とても楽しみにしています！", "我非常期待那一刻！", "정말 기대하고 있어요!",
            phoneticEs = "TEHN-goh moo-CHEE-see-mahs GAH-nahs", phoneticFr = "zhay vray-mahn aht dee ehtr",
            phoneticJa = "Totemo tanoshimi ni shite imasu!", phoneticZh = "Wǒ fēicháng qīdài nà yīkè!", phoneticKo = "Jeongmal gidaehago isseoyo!"),

        CorePhraseEntry("fe4", PhraseCategory.FEELINGS,
            "Mach dir keine Sorgen, alles wird gut!", "Don't worry, everything will be alright!",
            "¡No te preocupes, todo saldrá muy bien!", "Ne t'inquiète pas, tout va bien se passer !",
            "Non ti preoccupare, andrà tutto bene!", "Não se preocupe, tudo vai dar certo!",
            "Не волнуйся, всё будет хорошо!", "Не хвилюйся, все буде добре!",
            "心配しないで、すべてうまくいきますよ！", "别担心，一切都会好起来的！", "걱정 마세요, 다 잘 될 거예요!",
            phoneticEs = "noh teh preh-oh-KOO-pess, TOH-doh sahl-DRAH MOOY byehn", phoneticFr = "nuh tan-kee-eht pah, too vah byan suh pahss-ay",
            phoneticJa = "Shinpai shinaide, subete umaku ikimasu yo!", phoneticZh = "Bié dānxīn, yīqiè dōuhuì hǎo qǐlái de!", phoneticKo = "Geokjeong maseyo, da jal doel geoyeyo!"),

        CorePhraseEntry("fe5", PhraseCategory.FEELINGS,
            "Wie schade! Das tut mir sehr leid.", "What a pity! I am so sorry to hear that.",
            "¡Qué lástima! Lo lamento muchísimo.", "Quel dommage ! J'en suis sincèrement désolé.",
            "Che peccato! Mi dispiace tantissimo.", "Que pena! Sinto muitíssimo por isso.",
            "Как жаль! Мне очень жаль это слышать.", "Як прикро! Мені дуже шкода це чути.",
            "残念ですね、お気の毒に思います。", "真遗憾！听到这个我很抱歉。", "정말 안타깝네요, 유감입니다.",
            phoneticEs = "keh LAHS-tee-mah! loh lah-MEHN-toh moo-CHEE-see-moh", phoneticFr = "kell dohm-mahzh! zhahn swee say-roh-zohlay",
            phoneticJa = "Zannen desu ne, okinodoku ni omoimasu", phoneticZh = "Zhēn yíhàn! Tīng dào zhège wǒ hěn bàoqiàn", phoneticKo = "Jeongmal antakkapneyo, yugam-imnida"),

        CorePhraseEntry("fe6", PhraseCategory.FEELINGS,
            "Ich drücke dir ganz fest die Daumen!", "Fingers crossed for you! Best of luck!",
            "¡Te deseo muchísima suerte, cruzo los dedos!", "Je croise les doigts très fort pour toi !",
            "Incrocio le dita per te, buona fortuna!", "Estou torcendo muito por você!",
            "Держу за тебя кулачки! Удачи!", "Тримаю за тебе кулаки! Бажаю успіху!",
            "成功を祈って応援しています！", "祝你好运，为你加油祈祷！", "행운을 빌어요, 응원할게요!",
            phoneticEs = "teh deh-SEH-oh moo-CHEE-see-mah SWEHR-teh", phoneticFr = "zhuh krwahz lay dwah tray fohr poor twah",
            phoneticJa = "Seikō o inotte ōen shite imasu!", phoneticZh = "Zhù nǐ hǎoyùn, wèi nǐ jiāyóu qídǎo!", phoneticKo = "Haeng'un-eul bireoyo, eung-wonhalgeyo!"),

        // === 6. WORK & BUSINESS (10) ===
        CorePhraseEntry("bu1", PhraseCategory.BUSINESS,
            "Lassen Sie uns einen Termin für morgen vereinbaren.", "Let's schedule a meeting for tomorrow.",
            "Agendemos una reunión para el día de mañana.", "Fixons un rendez-vous pour demain.",
            "Fissiamo un incontro per domani.", "Vamos agendar uma reunião para amanhã.",
            "Давайте назначим встречу на завтра.", "Давайте домовимося про зустріч на завтра.",
            "明日のミーティングの日程を決めましょう。", "我们安排明天开个会吧。", "내일 회의 일정을 잡읍시다.",
            phoneticEs = "ah-hehn-DEH-mohs OO-nah reh-oo-NYOHN PAH-rah mah-NYAH-nah", phoneticFr = "feek-sohn un rahn-day-voo poor duh-man",
            phoneticJa = "Ashita no mītingu no nittei o kimemashō", phoneticZh = "Wǒmen ānpái míngtiān kāi gè huì ba", phoneticKo = "Naeil hoeui iljeong-eul jabeupsida"),

        CorePhraseEntry("bu2", PhraseCategory.BUSINESS,
            "Könnten Sie mir die Details per E-Mail zusenden?", "Could you send me the details via email?",
            "¿Podría enviarme los detalles por correo electrónico?", "Pourriez-vous m'envoyer les détails par courriel ?",
            "Potrebbe inviarmi i dettagli via e-mail?", "Poderia me enviar os detalhes por e-mail?",
            "Не могли бы вы прислать подробности по почте?", "Чи не могли б ви надіслати деталі електронною поштою?",
            "詳細をメールで送っていただけますか？", "您能把详细资料发邮件给我吗？", "세부 사항을 이메일로 보내주시겠어요?",
            phoneticEs = "poh-DREE-ah en-vee-AHR-meh lohs deh-TAH-yes pohr kohr-REH-oh", phoneticFr = "poo-ryay-voo mahn-vwah-yay lay day-tie pahr koor-yel",
            phoneticJa = "Shōsai o mēru de okutte itadakemasu ka?", phoneticZh = "Nín néng bǎ xiángxì zīliào fā yóujiàn gěi wǒ ma?", phoneticKo = "Sebu sahang-eul imeillo bonaejusigesseoyo?"),

        CorePhraseEntry("bu3", PhraseCategory.BUSINESS,
            "Was ist die genaue Frist für dieses Projekt?", "What is the deadline for this project?",
            "¿Cuál es la fecha límite de este proyecto?", "Quelle est la date limite pour ce projet ?",
            "Qual è la scadenza precisa per questo progetto?", "Qual é o prazo limite para este projeto?",
            "Каков крайний срок для этого проекта?", "Який дедлайн для цього проєкту?",
            "このプロジェクトの締め切りはいつですか？", "这个项目的截止日期是几号？", "이 프로젝트의 마감 기한은 언제인가요?",
            phoneticEs = "kwahl ess lah FEH-chah LEE-mee-teh deh EHS-teh proh-YEK-toh?", phoneticFr = "kell ay lah daht lee-meet poor suh proh-zhay?",
            phoneticJa = "Kono purojekuto no shimekiri wa itsu desu ka?", phoneticZh = "Zhège xiàngmù de jiézhǐ rìqī shì jǐ hào?", phoneticKo = "I peurojekteu-ui magam gihaneun eonje-ingayo?"),

        CorePhraseEntry("bu4", PhraseCategory.BUSINESS,
            "Vielen Dank für Ihre schnelle Rückmeldung!", "Thank you for your prompt response!",
            "¡Muchísimas gracias por su pronta respuesta!", "Merci beaucoup pour votre réponse si rapide !",
            "Grazie mille per la sua tempestiva risposta!", "Muito obrigado pela sua resposta rápida!",
            "Большое спасибо за ваш оперативный ответ!", "Щиро дякую за вашу оперативну відповідь!",
            "迅速なご返信、誠にありがとうございます！", "非常感谢您及时的回复！", "신속한 답변에 진심으로 감사드립니다!",
            phoneticEs = "MOO-chahs GRAH-syahs pohr soo PROHN-tah reh-SPWEHS-tah", phoneticFr = "mehr-see boh-koo poor vohtr ray-pohnss see rah-peed",
            phoneticJa = "Jinsoku na go-henshin, makoto ni arigatō gozaimasu!", phoneticZh = "Fēicháng gǎnxiè nín jíshí de huífù!", phoneticKo = "Sinsokan dapbyeon-e jinsimeuro gamsadeurimnida!"),

        // === 7. EMERGENCY & HEALTH (10) ===
        CorePhraseEntry("em1", PhraseCategory.EMERGENCY,
            "Bitte helfen Sie mir! Es ist ein Notfall!", "Please help me! It is an emergency!",
            "¡Por favor, ayúdeme! ¡Es una emergencia!", "Aidez-moi s'il vous plaît ! C'est une urgence !",
            "Aiuto, per favore! È un'emergenza!", "Por favor, me ajude! É uma emergência!",
            "Пожалуйста, помогите мне! Это экстренный случай!", "Будь ласка, допоможіть мені! Це екстрений випадок!",
            "助けてください！緊急事態です！", "请救救我！这是紧急情况！", "도와주세요! 긴급 상황입니다!",
            phoneticEs = "pohr fah-VOHR, ah-YOO-deh-meh! ess OO-nah eh-mehr-HEHN-syah!", phoneticFr = "ay-day mwah seel voo pleh! say toon oor-zhahnss!",
            phoneticJa = "Tasukete kudasai! Kinkyū jitai desu!", phoneticZh = "Qǐng jiù jiù wǒ! Zhè shì jǐnjí qíngkuàng!", phoneticKo = "Dowajuseyo! Gingeup sanghwang-imnida!"),

        CorePhraseEntry("em2", PhraseCategory.EMERGENCY,
            "Rufen Sie sofort einen Krankenwagen!", "Call an ambulance immediately!",
            "¡Llame a una ambulancia inmediatamente!", "Appelez une ambulance immédiatement !",
            "Chiami un'ambulanza immediatamente!", "Chame uma ambulância imediatamente!",
            "Немедленно вызовите скорую помощь!", "Негайно викличте швидку допомогу!",
            "すぐに救急車を呼んでください！", "请立刻叫救护车！", "지금 즉시 구급차를 불러주세요!",
            phoneticEs = "YAH-meh ah OO-nah ahm-boo-LAHN-syah!", phoneticFr = "ah-play zoon ahm-boo-lahnss ee-may-dyaht-mahn!",
            phoneticJa = "Sugu ni kyūkyūsha o yonde kudasai!", phoneticZh = "Qǐng lìkè jiào jiùhùchē!", phoneticKo = "Jigeum jeuksi gugupchareul bulleojuseyo!"),

        CorePhraseEntry("em3", PhraseCategory.EMERGENCY,
            "Wo befindet sich das nächste Krankenhaus?", "Where is the nearest hospital?",
            "¿Dónde queda el hospital más cercano?", "Où se trouve l'hôpital le plus proche ?",
            "Dov'è l'ospedale più vicino?", "Onde fica o hospital mais próximo?",
            "Где находится ближайшая больница?", "Де розташована найближча лікарня?",
            "一番近い病院はどこにありますか？", "最近的医院在哪个位置？", "가장 가까운 병원은 어디인가요?",
            phoneticEs = "DOHN-deh KEH-dah ehl ohs-pee-TAHL mahs sehr-KAH-noh?", phoneticFr = "oo suh troov loh-pee-tahl luh ploo prohsh?",
            phoneticJa = "Ichiban chikai byōin wa doko ni arimasu ka?", phoneticZh = "Zuìjìn de yīyuàn zài nǎge wèizhì?", phoneticKo = "Gajang gakkaun byeong-won-eun eodi-ingayo?"),

        CorePhraseEntry("em4", PhraseCategory.EMERGENCY,
            "Ich habe starke Schmerzen und brauche einen Arzt.", "I have severe pain and need a doctor.",
            "Tengo un dolor muy fuerte y necesito un médico.", "J'ai une forte douleur et j'ai besoin d'un médecin.",
            "Ho un forte dolore e ho bisogno di un medico.", "Estou com muita dor e preciso de um médico.",
            "У меня сильная боль, мне нужен врач.", "У мене сильний біль, мені потрібен лікар.",
            "激しい痛みがあり、医師に診てもらいたいです。", "我痛得很厉害，需要看医生。", "통증이 너무 심해서 의사의 진료가 필요합니다.",
            phoneticEs = "TEHN-goh oon doh-LOHR MOOY FWEHR-teh", phoneticFr = "zhay oon fohrt doo-luhr ay zhay buh-zwan dun mayd-san",
            phoneticJa = "Hageshii itami ga ari, ishi ni mite moraitai desu", phoneticZh = "Wǒ tòng de hěn lìhài, xūyào kàn yīshēng", phoneticKo = "Tongjeung-i neomu simhaeseo uisa-ui jinryoga piryohamnida"),

        CorePhraseEntry("em5", PhraseCategory.EMERGENCY,
            "Pass auf! Sei vorsichtig!", "Watch out! Be careful!",
            "¡Cuidado! ¡Ten mucha precaución!", "Attention ! Sois très prudent !",
            "Attento! Fai molta attenzione!", "Cuidado! Fique atento!",
            "Осторожно! Будь внимателен!", "Обережно! Будь дуже уважним!",
            "気をつけて！危ないよ！", "当心！小心一点！", "조심하세요! 위험해요!",
            phoneticEs = "kwee-DAH-doh! tehn MOO-chah preh-kow-SYOHN!", phoneticFr = "ah-tahn-syohn! swah tray proo-dahn!",
            phoneticJa = "Ki o tsukete! Abunai yo!", phoneticZh = "Dāngxīn! Xiǎoxīn yīdiǎn!", phoneticKo = "Josimhaseyo! Wiheomhaeyo!")
    )
}
