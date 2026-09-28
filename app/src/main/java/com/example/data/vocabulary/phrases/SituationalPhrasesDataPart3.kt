package com.example.data.vocabulary.phrases

import com.example.data.vocabulary.PhraseCategory
import com.example.data.vocabulary.PhraseItem

object SituationalPhrasesDataPart3 {

    fun getPart3PhrasesForLanguage(targetLang: String, nativeLang: String): List<PhraseItem> {
        val t = targetLang.lowercase()
        val n = nativeLang.lowercase()

        return PART3_LIST.mapIndexed { index, entry ->
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
                id = "${t}_core3_${entry.id}_${index + 61}",
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

    private val PART3_LIST = listOf(
        // Everyday Communications & Practical Interactions (25)
        CorePhraseEntry("p1", PhraseCategory.DAILY,
            "Kein Problem, das kriegen wir hin!", "No problem, we can manage that!",
            "¡No hay ningún problema, lo solucionamos!", "Pas de problème, on va s'en sortir !",
            "Nessun problema, ce la faremo!", "Sem problema, damos um jeito nisso!",
            "Никаких проблем, мы справимся!", "Жодних проблем, ми впораємося!",
            "問題ありません、何とかできますよ！", "没问题，我们可以搞定的！", "문제없어요, 해결할 수 있어요!",
            phoneticEs = "noh eye neen-GOON proh-BLEH-mah", phoneticFr = "pah duh proh-blem, ohn vah sahn sohr-teer",
            phoneticJa = "Mondai arimasen, nantoka dekimasu yo!", phoneticZh = "Méi wèntí, wǒmen kěyǐ gǎodìng de!", phoneticKo = "Munje-eopseoyo, haegyeolhal su isseoyo!"),

        CorePhraseEntry("p2", PhraseCategory.DAILY,
            "Ich verstehe das vollkommen.", "I completely understand that.",
            "Lo entiendo perfectamente.", "Je comprends tout à fait.",
            "Capisco perfettamente.", "Entendo perfeitamente.",
            "Я прекрасно это понимаю.", "Я чудово це розумію.",
            "よく理解できます。", "我完全理解这一点。", "충분히 이해합니다.",
            phoneticEs = "loh en-TYEHN-doh pehr-fek-tah-MEHN-teh", phoneticFr = "zhuh kohm-prahn too tah feh",
            phoneticJa = "Yoku rikai dekimasu", phoneticZh = "Wǒ wánquán lǐjiě zhè yīdiǎn", phoneticKo = "Chungbunhi ihaehamnida"),

        CorePhraseEntry("p3", PhraseCategory.TRAVEL,
            "Wann kommt der nächste Bus an?", "When does the next bus arrive?",
            "¿A qué hora llega el próximo autobús?", "À quelle heure arrive le prochain bus ?",
            "A che ora arriva il prossimo autobus?", "A que horas chega o próximo ônibus?",
            "Когда прибудет следующий автобус?", "Коли прибуває наступний автобус?",
            "次のバスは何時に到着しますか？", "下一班公交车什么时候到？", "다음 버스는 몇 시에 도착하나요?",
            phoneticEs = "ah keh OH-rah YEH-gah ehl PROHK-see-moh ow-toh-BOOS?", phoneticFr = "ah kell uhr ah-reev luh proh-shan boos?",
            phoneticJa = "Tsugi no basu wa nanji ni tōchaku shimasu ka?", phoneticZh = "Xià yī bān gōngjiāochē shénme shíhòu dào?", phoneticKo = "Daeum beoseuneun myeot sie dochakhanayo?"),

        CorePhraseEntry("p4", PhraseCategory.TRAVEL,
            "Gibt es hier in der Nähe eine Apotheke?", "Is there a pharmacy nearby?",
            "¿Hay una farmacia cerca de aquí?", "Y a-t-il une pharmacie près d'ici ?",
            "C'è una farmacia qui vicino?", "Tem alguma farmácia por perto?",
            "Есть ли поблизости аптека?", "Чи є поблизу аптека?",
            "この近くに薬局はありますか？", "这附近有药店吗？", "이 근처에 약국이 있나요?",
            phoneticEs = "eye OO-nah fahr-MAH-syah SEHR-kah deh ah-KEE?", phoneticFr = "ee ah-teel oon fahr-mah-see preh dee-see?",
            phoneticJa = "Kono chikaku ni yakkyoku wa arimasu ka?", phoneticZh = "Zhè fùjìn yǒu yàodiàn ma?", phoneticKo = "I geuncheoe yakgugi innayo?"),

        CorePhraseEntry("p5", PhraseCategory.DINING,
            "Könnte ich bitte ein Glas stilles Wasser haben?", "Could I have a glass of still water, please?",
            "¿Me da un vaso de agua sin gas, por favor?", "Puis-je avoir un verre d'eau plate, s'il vous plaît ?",
            "Posso avere un bicchiere d'acqua naturale, per favore?", "Poderia me trazer um copo de água sem gás, por favor?",
            "Можно мне стакан негазированной воды, пожалуйста?", "Можна мені склянку негазованої води, будь ласка?",
            "お水を一杯いただけますか？", "请给我一杯不含气的水，谢谢。", "생수 한 잔 부탁드립니다.",
            phoneticEs = "meh dah oon VAH-soh deh AH-gwah seen gahs?", phoneticFr = "pweezh ah-vwahr un vehr doh plaht?",
            phoneticJa = "Omizu o ippai itadakemasu ka?", phoneticZh = "Qǐng gěi wǒ yī bēi bù hán qì de shuǐ", phoneticKo = "Saengsu han jan butakdeurimnida"),

        CorePhraseEntry("p6", PhraseCategory.DINING,
            "Kann man das zum Mitnehmen bekommen?", "Can I have this for takeout / to go?",
            "¿Se puede pedir esto para llevar?", "Est-ce possible de l'emporter ?",
            "Si può avere da asporto?", "Poderia ser para viagem?",
            "Можно ли взять это с собой навынос?", "Чи можна взяти це з собою на винос?",
            "テイクアウト（持ち帰り）にできますか？", "可以打包带走吗？", "포장(테이크아웃) 가능한가요?",
            phoneticEs = "seh PWEH-deh peh-DEER EHS-toh PAH-rah yeh-VAHR?", phoneticFr = "ess poh-see-bluh duh lahn-pohr-tay?",
            phoneticJa = "Teikuauto ni dekimasu ka?", phoneticZh = "Kěyǐ dǎbāo dài zǒu ma?", phoneticKo = "Pojang ganeunghangayo?"),

        CorePhraseEntry("p7", PhraseCategory.BUSINESS,
            "Geben Sie mir bitte bis heute Abend Bescheid.", "Please let me know by this evening.",
            "Por favor, avíseme antes de esta tarde.", "Veuillez me tenir informé d'ici ce soir.",
            "Per favore, mi faccia sapere entro stasera.", "Por favor, me avise até hoje à noite.",
            "Пожалуйста, дайте мне знать до сегодняшнего вечера.", "Будь ласка, повідомте мене до сьогоднішнього вечора.",
            "今晩までにお知らせいただけますと幸いです。", "请在今晚之前通知我。", "오늘 저녁까지 알려주시기 바랍니다.",
            phoneticEs = "pohr fah-VOHR, ah-VEE-seh-meh AHN-tess deh EHS-tah TAHR-deh", phoneticFr = "vuh-yay muh tuh-neer an-fohr-may dee-see suh swahr",
            phoneticJa = "Konban made ni oshirase itadakemasu to saiwai desu", phoneticZh = "Qǐng zài jīnwǎn zhīqián tōngzhī wǒ", phoneticKo = "Oneul jeonyeokkkaji allyeojusigi baramnida"),

        CorePhraseEntry("p8", PhraseCategory.BUSINESS,
            "Lassen Sie uns eine kurze Kaffeepause machen.", "Let's take a short coffee break.",
            "Hagamos una breve pausa para el café.", "Faisons une courte pause café.",
            "Facciamo una breve pausa caffè.", "Vamos fazer uma pequena pausa para o café.",
            "Давайте сделаем небольшой перерыв на кофе.", "Зробімо коротку перерву на каву.",
            "少しコーヒーブレイクを取りましょう。", "我们休息一下，喝杯咖啡吧。", "잠깐 커피 한 잔 마시며 쉬어갑시다.",
            phoneticEs = "ah-GAH-mohs OO-nah BREH-veh POW-sah PAH-rah ehl kah-FEH", phoneticFr = "feh-zohn zoon koort pohz kah-fay",
            phoneticJa = "Sukoshi kōhī bureiku o torimashō", phoneticZh = "Wǒmen xiūxī yīxià, hē bēi kāfēi ba", phoneticKo = "Jamkkan keopi han jan masimyeo swieogapsida"),

        CorePhraseEntry("p9", PhraseCategory.FEELINGS,
            "Ich bin wirklich stolz auf deine Leistung!", "I am truly proud of your achievement!",
            "¡Estoy verdaderamente orgulloso de tu logro!", "Je suis vraiment fier de ce que tu as accompli !",
            "Sono davvero orgoglioso del tuo risultato!", "Estou verdadeiramente orgulhoso do que você conquistou!",
            "Я искренне горжусь твоим достижением!", "Я щиро пишаюся твоїм досягненням!",
            "あなたの成果を本当に誇りに思います！", "我真为你取得的成绩感到骄傲！", "당신의 성취가 정말 자랑스럽습니다!",
            phoneticEs = "ess-TOY vehr-dah-deh-rah-MEHN-teh ohr-goo-YOH-soh", phoneticFr = "zhuh swee vray-mahn fyehr duh suh kuh too ah ah-kohm-plee",
            phoneticJa = "Anata no seika o hontō ni hokori ni omoimasu!", phoneticZh = "Wǒ zhēn wèi nǐ qǔdé de chéngjì gǎndào jiāo'ào!", phoneticKo = "Dangsin-ui seongchwiga jeongmal jarangseoreopseumnida!"),

        CorePhraseEntry("p10", PhraseCategory.FEELINGS,
            "Das ist ein hervorragender Vorschlag!", "That is an outstanding suggestion!",
            "¡Es una propuesta verdaderamente excelente!", "C'est une excellente suggestion !",
            "È un'ottima proposta!", "É uma sugestão excelente!",
            "Это великолепное предложение!", "Це чудова пропозиція!",
            "それは素晴らしい提案ですね！", "这是一个非常出色的建议！", "정말 훌륭한 제안입니다!",
            phoneticEs = "ess OO-nah proh-PWEHS-tah vehr-dah-deh-rah-MEHN-teh ek-seh-LEHN-teh", phoneticFr = "say toon ek-seh-lahnt soo-zhes-tyohn",
            phoneticJa = "Sore wa subarashii teian desu ne!", phoneticZh = "Zhè shì yīgè fēicháng chūsè de jiànyì!", phoneticKo = "Jeongmal hullyunghan jean-imnida!"),

        CorePhraseEntry("p11", PhraseCategory.EMERGENCY,
            "Ich habe meine Geldbörse verloren.", "I have lost my wallet.",
            "He perdido mi cartera con mis documentos.", "J'ai perdu mon portefeuille.",
            "Ho perso il mio portafoglio.", "Perdi a minha carteira.",
            "Я потерял свой кошелек с документами.", "Я загубив свій гаманець з документами.",
            "財布を紛失してしまいました。", "我的钱包丢了。", "지갑을 분실했습니다.",
            phoneticEs = "eh pehr-DEE-doh mee kahr-TEH-rah", phoneticFr = "zhay pehr-doo mohn pohrt-fuh-yee",
            phoneticJa = "Saifu o funshitsu shite shimaimashita", phoneticZh = "Wǒ de qiánbāo diūle", phoneticKo = "Jigab-eul bunsilhaesseumnida"),

        CorePhraseEntry("p12", PhraseCategory.DAILY,
            "Wie sagt man das in Ihrer Sprache?", "How do you say that in your language?",
            "¿Cómo se dice eso en su idioma?", "Comment dit-on cela dans votre langue ?",
            "Come si dice questo nella sua lingua?", "Como se diz isso na sua língua?",
            "Как это сказать на вашем языке?", "Як це сказати вашою мовою?",
            "あなたの言葉では何と言いますか？", "在您的母语中这句话怎么说？", "당신의 언어로는 이것을 뭐라고 하나요?",
            phoneticEs = "KOH-moh seh DEE-seh EH-soh en soo ee-DYOH-mah?", phoneticFr = "koh-mahn dee-tohn suh-lah dahn vohtr lahng?",
            phoneticJa = "Anata no kotoba dewa nan to iimasu ka?", phoneticZh = "Zài nín de mǔyǔ zhōng zhè jù huà zěnme shuō?", phoneticKo = "Dangsin-ui eoneoroneun igeoseul mworago hanayo?"),

        CorePhraseEntry("p13", PhraseCategory.TRAVEL,
            "Gibt es einen Aufzug in diesem Gebäude?", "Is there an elevator in this building?",
            "¿Hay un ascensor en este edificio?", "Y a-t-il un ascenseur dans cet immeuble ?",
            "C'è un ascensore in questo edificio?", "Tem elevador neste prédio?",
            "В этом здании есть лифт?", "Чи є в цій будівлі ліфт?",
            "この建物にエレベーターはありますか？", "这栋楼里有电梯吗？", "이 건물에 엘리베이터가 있나요?",
            phoneticEs = "eye oon ahs-sehn-SOHR en EHS-teh eh-dee-FEE-syoh?", phoneticFr = "ee ah-teel un ah-sahn-suhr dahn set ee-muhbl?",
            phoneticJa = "Kono tatemono ni erebētā wa arimasu ka?", phoneticZh = "Zhè dòng lóu lǐ yǒu diàntī ma?", phoneticKo = "I geonmure ellibeiteoga innayo?"),

        CorePhraseEntry("p14", PhraseCategory.DINING,
            "Ich hätte gerne einen Kaffee mit Milch.", "I would like a coffee with milk, please.",
            "Quisiera un café con leche, por favor.", "Je voudrais un café au lait, s'il vous plaît.",
            "Vorrei un caffè macchiato, per favore.", "Gostaria de um café com leite, por favor.",
            "Я хотел бы кофе с молоком, пожалуйста.", "Я б хотів каву з молоком, будь ласка.",
            "カフェオレを一杯いただけますか？", "我想要一杯加奶咖啡，谢谢。", "따뜻한 카페라떼 한 잔 부탁드립니다.",
            phoneticEs = "kee-SYEH-rah oon kah-FEH kohn LEH-cheh", phoneticFr = "zhuh voo-dreh un kah-fay oh leh",
            phoneticJa = "Kafeore o ippai itadakemasu ka?", phoneticZh = "Wǒ xiǎng yào yī bēi jiā nǎi kāfēi", phoneticKo = "Ttatteut-han kaperatte han jan butakdeurimnida"),

        CorePhraseEntry("p15", PhraseCategory.BUSINESS,
            "Ich melde mich später bei Ihnen.", "I will get back to you later.",
            "Me comunicaré con usted más tarde.", "Je reviendrai vers vous plus tard.",
            "La ricontatterò più tardi.", "Entro em contato com o senhor mais tarde.",
            "Я свяжусь с вами чуть позже.", "Я зв'яжуся з вами пізніше.",
            "後ほど改めてご連絡いたします。", "我稍后会与您联系。", "나중에 다시 연락드리겠습니다.",
            phoneticEs = "meh koh-moo-nee-kah-REH kohn oos-TEHD mahs TAHR-deh", phoneticFr = "zhuh ruh-vyan-dray vehr voo ploo tahr",
            phoneticJa = "Nochihodo aratamete gorenraku itashimasu", phoneticZh = "Wǒ shāohòu huì yǔ nín liánxì", phoneticKo = "Najung-e dasi yeonlakdeurigesseumnida"),

        CorePhraseEntry("p16", PhraseCategory.DAILY,
            "Gute Besserung! Werde schnell wieder gesund!", "Get well soon! Wishing you a speedy recovery!",
            "¡Que te mejores pronto! ¡Recupérate bien!", "Bon rétablissement ! Reviens vite en forme !",
            "Buona guarigione! Riprenditi presto!", "Melhoras! Fique bom logo!",
            "Скорейшего выздоровления! Поправляйся!", "Швидкого одужання! Одужуй скоріше!",
            "お大事に！早く良くなってくださいね！", "祝您早日康复，多注意身体！", "쾌차하시길 바랍니다! 빨리 나으세요!",
            phoneticEs = "keh teh meh-HOH-ress PROHN-toh!", phoneticFr = "bohn ray-tah-bleess-mahn!",
            phoneticJa = "Odaiji ni! Hayaku yoku natte kudasai ne!", phoneticZh = "Zhù nín zǎorì kāngfù!", phoneticKo = "Kwaechahasigil baramnida! Ppalli naeuseyo!"),

        CorePhraseEntry("p17", PhraseCategory.FEELINGS,
            "Ich fühle mich heute voller Energie!", "I feel full of energy today!",
            "¡Hoy me siento con muchísima energía!", "Je me sens plein d'énergie aujourd'hui !",
            "Oggi mi sento pieno di energia!", "Hoje estou cheio de energia!",
            "Сегодня я чувствую себя полным сил!", "Сьогодні я почуваюся сповненим енергії!",
            "今日はエネルギーに満ちあふれています！", "我今天感觉活力充沛！", "오늘 컨디션이 최고예요, 활력이 넘쳐요!",
            phoneticEs = "oy meh SYEHN-toh kohn moo-CHEE-see-mah eh-nehr-HEE-ah", phoneticFr = "zhuh muh sahn plan day-nehr-zhee",
            phoneticJa = "Kyō wa enerugī ni michiafurete imasu!", phoneticZh = "Wǒ jīntiān gǎnjué huólì chōngpèi!", phoneticKo = "Oneul keondisyeon-i choegoyeyo!"),

        CorePhraseEntry("p18", PhraseCategory.TRAVEL,
            "Kann ich hier mein Gepäck aufbewahren lassen?", "Can I leave my luggage here for storage?",
            "¿Puedo dejar mi equipaje aquí?", "Puis-je laisser mes bagages ici ?",
            "Posso lasciare i miei bagagli qui?", "Posso deixar minhas malas guardadas aqui?",
            "Могу ли я оставить здесь свой багаж?", "Чи можу я залишити тут свій багаж на зберігання?",
            "ここに荷物を預けることはできますか？", "请问我可以把行李寄存在这里吗？", "여기에 짐을 보관할 수 있나요?",
            phoneticEs = "PWEH-doh deh-HAHR mee eh-kee-PAH-heh ah-KEE?", phoneticFr = "pweezh leh-say may bah-gahzh ee-see?",
            phoneticJa = "Koko ni nimotsu o azukeru koto wa dekimasu ka?", phoneticZh = "Qǐngwèn wǒ kěyǐ bǎ xínglǐ jìcún zài zhèlǐ ma?", phoneticKo = "Yeogie jimeul bogwanhal su innayo?"),

        CorePhraseEntry("p19", PhraseCategory.DINING,
            "Haben Sie auch glutenfreie Produkte?", "Do you also carry gluten-free items?",
            "¿Tienen también productos sin gluten?", "Avez-vous aussi des produits sans gluten ?",
            "Avete anche prodotti senza glutine?", "Vocês também têm produtos sem glúten?",
            "У вас есть продукты без глютена?", "Чи є у вас продукти без глютену?",
            "グルテンフリーのメニューはありますか？", "请问有无麸质的餐品提供吗？", "글루텐 프리 제품도 있나요?",
            phoneticEs = "TYEH-nehn tahm-BYEHN proh-DOOK-tohs seen GLOO-tehn?", phoneticFr = "ah-vay-voo oh-see day proh-dwee sahn gloo-ten?",
            phoneticJa = "Gurutenfurī no menyū wa arimasu ka?", phoneticZh = "Qǐngwèn yǒu wú fūzhì de cānpǐn tígōng ma?", phoneticKo = "Geulluten peuri jepumdo innayo?"),

        CorePhraseEntry("p20", PhraseCategory.EMERGENCY,
            "Ich habe mich verlaufen und mein Akku ist leer.", "I am lost and my phone battery is dead.",
            "Me he perdido y la batería de mi teléfono se ha agotado.", "Je me suis égaré et ma batterie est à plat.",
            "Mi sono perso e la batteria del mio telefono è scarica.", "Estou perdido e a bateria do meu celular acabou.",
            "Я заблудился, и у меня разрядился телефон.", "Я заблукав, і в мене розрядився телефон.",
            "道に迷い、スマホの充電も切れてしまいました。", "我迷路了，而且手机电量用完了。", "길을 잃었는데 휴대폰 배터리도 방전되었어요.",
            phoneticEs = "meh eh pehr-DEE-doh ee lah bah-teh-REE-ah seh ah ah-goh-TAH-doh", phoneticFr = "zhuh muh swee ay-gah-ray",
            phoneticJa = "Michi ni mayoi, sumaho no jūden mo kirete shimaimashita", phoneticZh = "Wǒ mílùle, érqiě shǒujī diànliàng yòng wánle", phoneticKo = "Gireul ireonneunde hyudaepon baeteorido bangjeondoeeosseoyo"),

        CorePhraseEntry("p21", PhraseCategory.DAILY,
            "Darf ich Ihnen behilflich sein?", "May I offer you some assistance?",
            "¿Puedo ayudarle en algo?", "Puis-je vous être utile en quelque chose ?",
            "Posso esserle d'aiuto in qualche modo?", "Posso ser útil em alguma coisa?",
            "Могу ли я вам чем-нибудь помочь?", "Чи можу я вам чимось допомогти?",
            "何かお手伝いできることはありますか？", "请问我有什么可以帮到您的吗？", "무엇을 도와드릴까요?",
            phoneticEs = "PWEH-doh ah-yoo-DAHR-leh en AHL-goh?", phoneticFr = "pweezh voo zehtr oo-teel?",
            phoneticJa = "Nanika otetsudai dekiru koto wa arimasu ka?", phoneticZh = "Qǐngwèn wǒ yǒu shénme kěyǐ bāng dào nín de ma?", phoneticKo = "Mueoseul dowadeurilkkayo?"),

        CorePhraseEntry("p22", PhraseCategory.TRAVEL,
            "Wie lange dauert die Fahrt dorthin?", "How long does the journey take?",
            "¿Cuánto tiempo dura el trayecto hasta allí?", "Combien de temps dure le trajet jusqu'à là-bas ?",
            "Quanto tempo dura il tragitto fino a lì?", "Quanto tempo dura a viagem até lá?",
            "Сколько времени занимает дорога туда?", "Скільки часу займає дорога туди?",
            "そこまで行くのにどれくらい時間がかかりますか？", "到那里大概需要多长时间？", "그곳까지 가는 데 시간이 얼마나 걸리나요?",
            phoneticEs = "KWAHN-toh TYEHM-poh DOO-rah ehl trah-YEK-toh?", phoneticFr = "kohn-byan duh tahn door luh trah-zhay?",
            phoneticJa = "Soko made iku no ni dore kurai jikan ga kakarimasu ka?", phoneticZh = "Dào nàlǐ dàgài xūyào duō cháng shíjiān?", phoneticKo = "Geugotkkaji ganeun de sigani eolmana geollinayo?"),

        CorePhraseEntry("p23", PhraseCategory.DINING,
            "Könnten Sie das bitte für mich aufwärmen?", "Could you please warm this up for me?",
            "¿Podría calentar esto para mí, por favor?", "Pourriez-vous réchauffer cela, s'il vous plaît ?",
            "Potrebbe riscaldare questo, per favore?", "Poderia esquentar isso para mim, por favor?",
            "Не могли бы вы подогреть это для меня?", "Чи не могли б ви підігріти це для мене?",
            "これを温め直していただけますか？", "您能帮我把这个加热一下吗？", "이것 좀 데워주실 수 있나요?",
            phoneticEs = "poh-DREE-ah kah-lehn-TAHR EHS-toh PAH-rah mee?", phoneticFr = "poo-ryay-voo ray-shoh-fay suh-lah?",
            phoneticJa = "Kore o atatame naoshite itadakemasu ka?", phoneticZh = "Nín néng bāng wǒ bǎ zhège jiārè yīxià ma?", phoneticKo = "Igeot jom dewojusil su innayo?"),

        CorePhraseEntry("p24", PhraseCategory.FEELINGS,
            "Ich habe vollstes Vertrauen in dich!", "I have complete trust and confidence in you!",
            "¡Tengo plena confianza en tus capacidades!", "J'ai une confiance absolue en toi !",
            "Ho piena fiducia in te!", "Tenho total confiança em você!",
            "Я полностью верю в твои силы!", "Я повністю вірю в твої сили!",
            "あなたのことを心から信頼しています！", "我对你充满绝对的信心！", "당신을 전적으로 신뢰합니다!",
            phoneticEs = "TEHN-goh PLEH-nah kohn-FYAHN-sah en tees kah-pah-see-DAH-dess", phoneticFr = "zhay oon kohn-fyahnss ahb-soh-loo",
            phoneticJa = "Anata no koto o kokoro kara shinrai shite imasu!", phoneticZh = "Wǒ duì nǐ chōngmǎn juéduì de xìnxīn!", phoneticKo = "Dangsineul jeonjeog-euro sinroehamnida!"),

        CorePhraseEntry("p25", PhraseCategory.BUSINESS,
            "Ich wünsche Ihnen eine erfolgreiche Woche!", "Wishing you a productive and successful week!",
            "¡Le deseo una semana sumamente productiva!", "Je vous souhaite une semaine fructueuse !",
            "Le auguro una settimana molto produttiva!", "Desejo-lhe uma semana muito produtiva e cheia de sucesso!",
            "Желаю вам продуктивной и успешной недели!", "Бажаю вам продуктивного та успішного тижня!",
            "実り多く素晴らしい一週間をお過ごしください！", "祝您度过富有成效且充实的一周！", "알차고 생산적인 한 주 보내시길 바랍니다!",
            phoneticEs = "leh deh-SEH-oh OO-nah seh-MAH-nah proh-dook-TEE-vah", phoneticFr = "zhuh voo sweht oon suh-mehn frook-too-uhz",
            phoneticJa = "Minori ōku subarashii isshūkan o osugoshi kudasai!", phoneticZh = "Zhù nín dùguò fùyǒu chéngxiào de yīzhōu!", phoneticKo = "Alchago saengsanjeogin han ju bonaesigil baramnida!")
    )
}
