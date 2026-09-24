package com.example.data.vocabulary

object VocabularyMultilingualLexicon {
    data class LexiconEntry(
        val key: String,
        val pos: String,
        val cefr: String,
        val cat: String,
        val translations: Map<String, String>
    )

    val rawEntries: List<LexiconEntry> by lazy {
        RAW_CHUNKS.flatMap { chunk ->
            chunk.trim().lines().filter { it.isNotBlank() }.map { line ->
                val parts = line.split('|')
                val key = parts[0]
                val pos = parts.getOrElse(1) { "noun" }
                val cefr = parts.getOrElse(2) { "A1" }
                val cat = parts.getOrElse(3) { "General" }
                val transPairs = parts.getOrElse(4) { "" }.split(';')
                val transMap = mutableMapOf<String, String>()
                for (p in transPairs) {
                    val colonIdx = p.indexOf(':')
                    if (colonIdx > 0) {
                        val lang = p.substring(0, colonIdx)
                        val value = p.substring(colonIdx + 1)
                        transMap[lang] = value
                    }
                }
                LexiconEntry(key, pos, cefr, cat, transMap)
            }
        }
    }

    val entries: Map<String, Map<String, String>> by lazy {
        rawEntries.associate { it.key.lowercase() to it.translations }
    }

    private const val CHUNK_0 = """
I|pronoun|A1|Everyday Life|de:ich;en:I;es:yo;fr:je;it:io;ja:私;ko:나;pt:eu;ru:я;uk:я;zh:我
you|pronoun|A1|Everyday Life|de:du;en:you;es:tú;fr:tu;it:tu;ja:あなた;ko:너;pt:você;ru:ты;uk:ти;zh:你
he|pronoun|A1|Everyday Life|de:er;en:he;es:él;fr:il;it:lui;ja:彼;ko:그;pt:ele;ru:он;uk:він;zh:他
she|pronoun|A1|Everyday Life|de:sie;en:she;es:ella;fr:elle;it:lei;ja:彼女;ko:그녀;pt:ela;ru:она;uk:вона;zh:她
we|pronoun|A1|Everyday Life|de:wir;en:we;es:nosotros;fr:nous;it:noi;ja:私たち;ko:우리;pt:nós;ru:мы;uk:ми;zh:我们
they|pronoun|A1|Everyday Life|de:sie;en:they;es:ellos;fr:ils;it:loro;ja:彼ら;ko:그들;pt:eles;ru:они;uk:вони;zh:他们
yes|adverb|A1|Everyday Life|de:ja;en:yes;es:sí;fr:oui;it:sì;ja:はい;ko:네;pt:sim;ru:да;uk:так;zh:是
no|adverb|A1|Everyday Life|de:nein;en:no;es:no;fr:non;it:no;ja:いいえ;ko:아니오;pt:não;ru:нет;uk:ні;zh:不
please|adverb|A1|Everyday Life|de:bitte;en:please;es:por favor;fr:s'il vous plaît;it:per favore;ja:お願いします;ko:부탁합니다;pt:por favor;ru:пожалуйста;uk:будь ласка;zh:请
thank you|adverb|A1|Everyday Life|de:danke;en:thank you;es:gracias;fr:merci;it:grazie;ja:ありがとう;ko:감사합니다;pt:obrigado;ru:спасибо;uk:дякую;zh:谢谢
hello|adverb|A1|Everyday Life|de:hallo;en:hello;es:hola;fr:bonjour;it:ciao;ja:こんにちは;ko:안녕하세요;pt:olá;ru:привет;uk:привіт;zh:你好
goodbye|adverb|A1|Everyday Life|de:auf Wiedersehen;en:goodbye;es:adiós;fr:au revoir;it:arrivederci;ja:さようなら;ko:안녕히 가세요;pt:adeus;ru:до свидания;uk:до побачення;zh:再见
to be|verb|A1|Action Verbs|de:sein;en:to be;es:ser;fr:être;it:essere;ja:である;ko:이다;pt:ser;ru:быть;uk:бути;zh:是
to have|verb|A1|Action Verbs|de:haben;en:to have;es:tener;fr:avoir;it:avere;ja:持っている;ko:가지다;pt:ter;ru:иметь;uk:мати;zh:有
to do|verb|A1|Action Verbs|de:machen;en:to do;es:hacer;fr:faire;it:fare;ja:する;ko:하다;pt:fazer;ru:делать;uk:робити;zh:做
to go|verb|A1|Action Verbs|de:gehen;en:to go;es:ir;fr:aller;it:andare;ja:行く;ko:가다;pt:ir;ru:идти;uk:йти;zh:去
to come|verb|A1|Action Verbs|de:kommen;en:to come;es:venir;fr:venir;it:venire;ja:来る;ko:오다;pt:vir;ru:приходить;uk:приходити;zh:来
to see|verb|A1|Action Verbs|de:sehen;en:to see;es:ver;fr:voir;it:vedere;ja:見る;ko:보다;pt:ver;ru:видеть;uk:бачити;zh:看
to hear|verb|A1|Action Verbs|de:hören;en:to hear;es:oír;fr:entendre;it:sentire;ja:聞く;ko:듣다;pt:ouvir;ru:слышать;uk:чути;zh:听
to eat|verb|A1|Action Verbs|de:essen;en:to eat;es:comer;fr:manger;it:mangiare;ja:食べる;ko:먹다;pt:comer;ru:есть;uk:їсти;zh:吃
to drink|verb|A1|Action Verbs|de:trinken;en:to drink;es:beber;fr:boire;it:bere;ja:飲む;ko:마시다;pt:beber;ru:пить;uk:пити;zh:喝
to sleep|verb|A1|Action Verbs|de:schlafen;en:to sleep;es:dormir;fr:dormir;it:dormire;ja:眠る;ko:자다;pt:dormir;ru:спать;uk:спати;zh:睡觉
to speak|verb|A1|Action Verbs|de:sprechen;en:to speak;es:hablar;fr:parler;it:parlare;ja:話す;ko:말하다;pt:falar;ru:говорить;uk:говорити;zh:说话
to read|verb|A1|Action Verbs|de:lesen;en:to read;es:leer;fr:lire;it:leggere;ja:読む;ko:읽다;pt:ler;ru:читать;uk:читати;zh:读
to write|verb|A1|Action Verbs|de:schreiben;en:to write;es:escribir;fr:écrire;it:scrivere;ja:書く;ko:쓰다;pt:escrever;ru:писать;uk:писати;zh:写
to buy|verb|A1|Action Verbs|de:kaufen;en:to buy;es:comprar;fr:acheter;it:comprare;ja:買う;ko:사다;pt:comprar;ru:покупать;uk:купувати;zh:买
to understand|verb|A1|Action Verbs|de:verstehen;en:to understand;es:entender;fr:comprendre;it:capire;ja:理解する;ko:이해하다;pt:entender;ru:понимать;uk:розуміти;zh:明白
to know|verb|A1|Action Verbs|de:wissen;en:to know;es:saber;fr:savoir;it:sapere;ja:知る;ko:알다;pt:saber;ru:знать;uk:знати;zh:知道
to want|verb|A1|Action Verbs|de:wollen;en:to want;es:querer;fr:vouloir;it:volere;ja:欲しい;ko:원하다;pt:querer;ru:хотеть;uk:хотіти;zh:想要
to live|verb|A1|Action Verbs|de:leben;en:to live;es:vivir;fr:vivre;it:vivere;ja:生きる;ko:살다;pt:viver;ru:жить;uk:жити;zh:生活
to work|verb|A1|Action Verbs|de:arbeiten;en:to work;es:trabajar;fr:travailler;it:lavorare;ja:働く;ko:일하다;pt:trabalhar;ru:работать;uk:працювати;zh:工作
to open|verb|A1|Action Verbs|de:öffnen;en:to open;es:abrir;fr:ouvrir;it:aprire;ja:開ける;ko:열다;pt:abrir;ru:открывать;uk:відкривати;zh:打开
to close|verb|A1|Action Verbs|de:schließen;en:to close;es:cerrar;fr:fermer;it:chiudere;ja:閉める;ko:닫다;pt:fechar;ru:закрывать;uk:закривати;zh:关闭
to give|verb|A1|Action Verbs|de:geben;en:to give;es:dar;fr:donner;it:dare;ja:与える;ko:주다;pt:dar;ru:давать;uk:давати;zh:给
to take|verb|A1|Action Verbs|de:nehmen;en:to take;es:tomar;fr:prendre;it:prendere;ja:取る;ko:받다;pt:tomar;ru:брать;uk:брати;zh:拿
to help|verb|A1|Action Verbs|de:helfen;en:to help;es:ayudar;fr:aider;it:aiutare;ja:助ける;ko:돕다;pt:ajudar;ru:помогать;uk:допомагати;zh:帮助
to learn|verb|A1|Action Verbs|de:lernen;en:to learn;es:aprender;fr:apprendre;it:imparare;ja:学ぶ;ko:배우다;pt:aprender;ru:учить;uk:вчити;zh:学习
to play|verb|A1|Action Verbs|de:spielen;en:to play;es:jugar;fr:jouer;it:giocare;ja:遊ぶ;ko:놀다;pt:jogar;ru:играть;uk:грати;zh:玩
to wait|verb|A1|Action Verbs|de:warten;en:to wait;es:esperar;fr:attendre;it:aspettare;ja:待つ;ko:기다리다;pt:esperar;ru:ждать;uk:чекати;zh:等待
to think|verb|A1|Action Verbs|de:denken;en:to think;es:pensar;fr:penser;it:pensare;ja:考える;ko:생각하다;pt:pensar;ru:думать;uk:думати;zh:思考
to find|verb|A1|Action Verbs|de:finden;en:to find;es:encontrar;fr:trouver;it:trovare;ja:見つける;ko:찾다;pt:encontrar;ru:находить;uk:знаходити;zh:找到
to call|verb|A1|Action Verbs|de:rufen;en:to call;es:llamar;fr:appeler;it:chiamare;ja:呼ぶ;ko:부르다;pt:chamar;ru:звонить;uk:дзвонити;zh:呼叫
to ask|verb|A1|Action Verbs|de:fragen;en:to ask;es:preguntar;fr:demander;it:chiedere;ja:尋ねる;ko:묻다;pt:perguntar;ru:спрашивать;uk:запитувати;zh:询问
to answer|verb|A1|Action Verbs|de:antworten;en:to answer;es:responder;fr:répondre;it:rispondere;ja:答える;ko:대답하다;pt:responder;ru:отвечать;uk:відповідати;zh:回答
to pay|verb|A1|Action Verbs|de:bezahlen;en:to pay;es:pagar;fr:payer;it:pagare;ja:支払う;ko:지불하다;pt:pagar;ru:платить;uk:платити;zh:支付
to start|verb|A1|Action Verbs|de:beginnen;en:to start;es:empezar;fr:commencer;it:iniziare;ja:始める;ko:시작하다;pt:começar;ru:начинать;uk:починати;zh:开始
to finish|verb|A1|Action Verbs|de:beenden;en:to finish;es:terminar;fr:finir;it:finire;ja:終える;ko:끝내다;pt:terminar;ru:заканчивать;uk:закінчувати;zh:结束
person|noun|A1|Society & Culture|de:die Person;en:person;es:la persona;fr:la personne;it:la persona;ja:人;ko:사람;pt:a pessoa;ru:человек;uk:людина;zh:人
man|noun|A1|Society & Culture|de:der Mann;en:man;es:el hombre;fr:l'homme;it:l'uomo;ja:男性;ko:남자;pt:o homem;ru:мужчина;uk:чоловік;zh:男人
woman|noun|A1|Society & Culture|de:die Frau;en:woman;es:la mujer;fr:la femme;it:la donna;ja:女性;ko:여자;pt:a mulher;ru:женщина;uk:жінка;zh:女人
child|noun|A1|Society & Culture|de:das Kind;en:child;es:el niño;fr:l'enfant;it:il bambino;ja:子供;ko:아이;pt:a criança;ru:ребёнок;uk:дитина;zh:孩子
friend|noun|A1|Society & Culture|de:der Freund;en:friend;es:el amigo;fr:l'ami;it:l'amico;ja:友達;ko:친구;pt:o amigo;ru:друг;uk:друг;zh:朋友
family|noun|A1|Society & Culture|de:die Familie;en:family;es:la familia;fr:la famille;it:la famiglia;ja:家族;ko:가족;pt:a família;ru:семья;uk:сім'я;zh:家庭
house|noun|A1|Everyday Life|de:das Haus;en:house;es:la casa;fr:la maison;it:la casa;ja:家;ko:집;pt:a casa;ru:дом;uk:будинок;zh:房屋
room|noun|A1|Everyday Life|de:das Zimmer;en:room;es:la habitación;fr:la chambre;it:la stanza;ja:部屋;ko:방;pt:o quarto;ru:комната;uk:кімната;zh:房间
door|noun|A1|Everyday Life|de:die Tür;en:door;es:la puerta;fr:la porte;it:la porta;ja:ドア;ko:문;pt:a porta;ru:дверь;uk:двері;zh:门
window|noun|A1|Everyday Life|de:das Fenster;en:window;es:la ventana;fr:la fenêtre;it:la finestra;ja:窓;ko:창문;pt:a janela;ru:окно;uk:вікно;zh:窗户
street|noun|A1|Travel & Exploration|de:die Straße;en:street;es:la calle;fr:la rue;it:la strada;ja:通り;ko:거리;pt:a rua;ru:улица;uk:вулиця;zh:街道
city|noun|A1|Travel & Exploration|de:die Stadt;en:city;es:la ciudad;fr:la ville;it:la città;ja:街;ko:도시;pt:a cidade;ru:город;uk:місто;zh:城市
country|noun|A1|Travel & Exploration|de:das Land;en:country;es:el país;fr:le pays;it:il paese;ja:国;ko:나라;pt:o país;ru:страна;uk:країна;zh:国家
world|noun|A1|Nature & Environment|de:die Welt;en:world;es:el mundo;fr:le monde;it:il mondo;ja:世界;ko:세계;pt:o mundo;ru:мир;uk:світ;zh:世界
school|noun|A1|Society & Culture|de:die Schule;en:school;es:la escuela;fr:l'école;it:la scuola;ja:学校;ko:학교;pt:a escola;ru:школа;uk:школа;zh:学校
water|noun|A1|Everyday Life|de:das Wasser;en:water;es:el agua;fr:l'eau;it:l'acqua;ja:水;ko:물;pt:a água;ru:вода;uk:вода;zh:水
bread|noun|A1|Everyday Life|de:das Brot;en:bread;es:el pan;fr:le pain;it:il pane;ja:パン;ko:빵;pt:o pão;ru:хлеб;uk:хліб;zh:面包
food|noun|A1|Everyday Life|de:das Essen;en:food;es:la comida;fr:la nourriture;it:il cibo;ja:食べ物;ko:음식;pt:a comida;ru:еда;uk:їжа;zh:食物
coffee|noun|A1|Everyday Life|de:der Kaffee;en:coffee;es:el café;fr:le café;it:il caffè;ja:コーヒー;ko:커피;pt:o café;ru:кофе;uk:кава;zh:咖啡
tea|noun|A1|Everyday Life|de:der Tee;en:tea;es:el té;fr:le thé;it:il tè;ja:お茶;ko:차;pt:o chá;ru:чай;uk:чай;zh:茶
apple|noun|A1|Everyday Life|de:der Apfel;en:apple;es:la manzana;fr:la pomme;it:la mela;ja:リンゴ;ko:사과;pt:a maçã;ru:яблоко;uk:яблуко;zh:苹果
time|noun|A1|Everyday Life|de:die Zeit;en:time;es:el tiempo;fr:le temps;it:il tempo;ja:時間;ko:시간;pt:o tempo;ru:время;uk:час;zh:时间
day|noun|A1|Everyday Life|de:der Tag;en:day;es:el día;fr:le jour;it:il giorno;ja:日;ko:날;pt:o dia;ru:день;uk:день;zh:天
night|noun|A1|Everyday Life|de:die Nacht;en:night;es:la noche;fr:la nuit;it:la notte;ja:夜;ko:밤;pt:a noite;ru:ночь;uk:ніч;zh:夜晚
morning|noun|A1|Everyday Life|de:der Morgen;en:morning;es:la mañana;fr:le matin;it:la mattina;ja:朝;ko:아침;pt:a manhã;ru:утро;uk:ранок;zh:早晨
evening|noun|A1|Everyday Life|de:der Abend;en:evening;es:la tarde;fr:le soir;it:la sera;ja:晩;ko:저녁;pt:a noite;ru:вечер;uk:вечір;zh:傍晚
year|noun|A1|Everyday Life|de:das Jahr;en:year;es:el año;fr:l'année;it:l'anno;ja:年;ko:년;pt:o ano;ru:год;uk:рік;zh:年
month|noun|A1|Everyday Life|de:der Monat;en:month;es:el mes;fr:le mois;it:il mese;ja:月;ko:월;pt:o mês;ru:месяц;uk:місяць;zh:月
week|noun|A1|Everyday Life|de:die Woche;en:week;es:la semana;fr:la semaine;it:la settimana;ja:週;ko:주;pt:a semana;ru:неделя;uk:тиждень;zh:周
hour|noun|A1|Everyday Life|de:die Stunde;en:hour;es:la hora;fr:l'heure;it:l'ora;ja:時間;ko:시간;pt:a hora;ru:час;uk:година;zh:小时
minute|noun|A1|Everyday Life|de:die Minute;en:minute;es:el minuto;fr:la minute;it:il minuto;ja:分;ko:분;pt:o minuto;ru:минута;uk:хвилина;zh:分钟
money|noun|A1|Everyday Life|de:das Geld;en:money;es:el dinero;fr:l'argent;it:il denaro;ja:お金;ko:돈;pt:o dinheiro;ru:деньги;uk:гроші;zh:钱
car|noun|A1|Travel & Exploration|de:das Auto;en:car;es:el coche;fr:la voiture;it:l'auto;ja:車;ko:차;pt:o carro;ru:машина;uk:машина;zh:汽车
bus|noun|A1|Travel & Exploration|de:der Bus;en:bus;es:el autobús;fr:le bus;it:l'autobus;ja:バス;ko:버스;pt:o ônibus;ru:автобус;uk:автобус;zh:公交车
train|noun|A1|Travel & Exploration|de:der Zug;en:train;es:el tren;fr:le train;it:il treno;ja:電車;ko:기차;pt:o trem;ru:поезд;uk:поїзд;zh:火车
book|noun|A1|Everyday Life|de:das Buch;en:book;es:el libro;fr:le livre;it:il libro;ja:本;ko:책;pt:o livro;ru:книга;uk:книга;zh:书
letter|noun|A1|Everyday Life|de:der Brief;en:letter;es:la carta;fr:la lettre;it:la lettera;ja:手紙;ko:편지;pt:a carta;ru:письмо;uk:лист;zh:信
word|noun|A1|Everyday Life|de:das Wort;en:word;es:la palabra;fr:le mot;it:la parola;ja:言葉;ko:단어;pt:a palavra;ru:слово;uk:слово;zh:词
language|noun|A1|Society & Culture|de:die Sprache;en:language;es:el idioma;fr:la langue;it:la lingua;ja:言語;ko:언어;pt:o idioma;ru:язык;uk:мова;zh:语言
phone|noun|A1|Everyday Life|de:das Telefon;en:phone;es:el teléfono;fr:le téléphone;it:il telefono;ja:電話;ko:전화;pt:o telefone;ru:телефон;uk:телефон;zh:电话
table|noun|A1|Everyday Life|de:der Tisch;en:table;es:la mesa;fr:la table;it:il tavolo;ja:机;ko:탁자;pt:a mesa;ru:стол;uk:стіл;zh:桌子
chair|noun|A1|Everyday Life|de:der Stuhl;en:chair;es:la silla;fr:la chaise;it:la sedia;ja:椅子;ko:의자;pt:a cadeira;ru:стул;uk:стілець;zh:椅子
sun|noun|A1|Nature & Environment|de:die Sonne;en:sun;es:el sol;fr:le soleil;it:il sole;ja:太陽;ko:태양;pt:o sol;ru:солнце;uk:сонце;zh:太阳
rain|noun|A1|Nature & Environment|de:der Regen;en:rain;es:la lluvia;fr:la pluie;it:la pioggia;ja:雨;ko:비;pt:a chuva;ru:дождь;uk:дощ;zh:雨
good|adjective|A1|Descriptions & Quality|de:gut;en:good;es:bueno;fr:bon;it:buono;ja:良い;ko:좋은;pt:bom;ru:хороший;uk:хороший;zh:好
bad|adjective|A1|Descriptions & Quality|de:schlecht;en:bad;es:malo;fr:mauvais;it:cattivo;ja:悪い;ko:나쁜;pt:mau;ru:плохой;uk:поганий;zh:坏
big|adjective|A1|Descriptions & Quality|de:groß;en:big;es:grande;fr:grand;it:grande;ja:大きい;ko:큰;pt:grande;ru:большой;uk:великий;zh:大
small|adjective|A1|Descriptions & Quality|de:klein;en:small;es:pequeño;fr:petit;it:piccolo;ja:小さい;ko:작은;pt:pequeno;ru:маленький;uk:маленький;zh:小
new|adjective|A1|Descriptions & Quality|de:neu;en:new;es:nuevo;fr:nouveau;it:nuovo;ja:新しい;ko:새로운;pt:novo;ru:новый;uk:новий;zh:新
old|adjective|A1|Descriptions & Quality|de:alt;en:old;es:viejo;fr:vieux;it:vecchio;ja:古い;ko:오래된;pt:velho;ru:старый;uk:старий;zh:老
young|adjective|A1|Descriptions & Quality|de:jung;en:young;es:joven;fr:jeune;it:giovane;ja:若い;ko:젊은;pt:jovem;ru:молодой;uk:молодий;zh:年轻
beautiful|adjective|A1|Descriptions & Quality|de:schön;en:beautiful;es:hermoso;fr:beau;it:bello;ja:美しい;ko:아름다운;pt:bonito;ru:красивый;uk:гарний;zh:美丽
warm|adjective|A1|Descriptions & Quality|de:warm;en:warm;es:cálido;fr:chaud;it:caldo;ja:暖かい;ko:따뜻한;pt:quente;ru:тёплый;uk:теплий;zh:温暖
"""

    private const val CHUNK_1 = """
cold|adjective|A1|Descriptions & Quality|de:kalt;en:cold;es:frío;fr:froid;it:freddo;ja:寒い;ko:차가운;pt:frio;ru:холодный;uk:холодний;zh:寒冷
happy|adjective|A1|Thoughts & Emotions|de:glücklich;en:happy;es:feliz;fr:heureux;it:felice;ja:幸せな;ko:행복한;pt:feliz;ru:счастливый;uk:щасливий;zh:快乐
fast|adjective|A1|Descriptions & Quality|de:schnell;en:fast;es:rápido;fr:rapide;it:veloce;ja:速い;ko:빠른;pt:rápido;ru:быстрый;uk:швидкий;zh:快
slow|adjective|A1|Descriptions & Quality|de:langsam;en:slow;es:lento;fr:lent;it:lento;ja:遅い;ko:느린;pt:lento;ru:медленный;uk:повільний;zh:慢
easy|adjective|A1|Descriptions & Quality|de:einfach;en:easy;es:fácil;fr:facile;it:facile;ja:簡単;ko:쉬운;pt:fácil;ru:простой;uk:простий;zh:简单
difficult|adjective|A1|Descriptions & Quality|de:schwierig;en:difficult;es:difícil;fr:difficile;it:difficile;ja:難しい;ko:어려운;pt:difícil;ru:трудный;uk:складний;zh:困难
important|adjective|A1|Descriptions & Quality|de:wichtig;en:important;es:importante;fr:important;it:importante;ja:重要;ko:중요한;pt:importante;ru:важный;uk:важливий;zh:重要
clean|adjective|A1|Descriptions & Quality|de:sauber;en:clean;es:limpio;fr:propre;it:pulito;ja:清潔;ko:깨끗한;pt:limpo;ru:чистый;uk:чистий;zh:干净
now|adverb|A1|Everyday Life|de:jetzt;en:now;es:ahora;fr:maintenant;it:ora;ja:今;ko:지금;pt:agora;ru:сейчас;uk:зараз;zh:现在
today|adverb|A1|Everyday Life|de:heute;en:today;es:hoy;fr:aujourd'hui;it:oggi;ja:今日;ko:오늘;pt:hoje;ru:сегодня;uk:сьогодні;zh:今天
tomorrow|adverb|A1|Everyday Life|de:morgen;en:tomorrow;es:mañana;fr:demain;it:domani;ja:明日;ko:내일;pt:amanhã;ru:завтра;uk:завтра;zh:明天
yesterday|adverb|A1|Everyday Life|de:gestern;en:yesterday;es:ayer;fr:hier;it:ieri;ja:昨日;ko:어제;pt:ontem;ru:вчера;uk:вчора;zh:昨天
always|adverb|A1|Everyday Life|de:immer;en:always;es:siempre;fr:toujours;it:sempre;ja:いつも;ko:항상;pt:sempre;ru:всегда;uk:завжди;zh:总是
never|adverb|A1|Everyday Life|de:nie;en:never;es:nunca;fr:jamais;it:mai;ja:決して;ko:결코;pt:nunca;ru:никогда;uk:ніколи;zh:从不
often|adverb|A1|Everyday Life|de:oft;en:often;es:a menudo;fr:souvent;it:spesso;ja:よく;ko:자주;pt:frequentemente;ru:часто;uk:часто;zh:经常
very|adverb|A1|Everyday Life|de:sehr;en:very;es:muy;fr:très;it:molto;ja:とても;ko:매우;pt:muito;ru:очень;uk:дуже;zh:非常
here|adverb|A1|Everyday Life|de:hier;en:here;es:aquí;fr:ici;it:qui;ja:ここ;ko:여기;pt:aqui;ru:здесь;uk:тут;zh:这里
there|adverb|A1|Everyday Life|de:dort;en:there;es:allí;fr:là;it:lì;ja:そこ;ko:거기;pt:lá;ru:там;uk:там;zh:那里
to arrive|verb|A2|Travel & Exploration|de:ankommen;en:to arrive;es:llegar;fr:arriver;it:arrivare;ja:着く;ko:도착하다;pt:chegar;ru:прибывать;uk:прибувати;zh:到达
to leave|verb|A2|Travel & Exploration|de:verlassen;en:to leave;es:salir;fr:partir;it:partire;ja:去る;ko:떠나다;pt:partir;ru:уходить;uk:залишати;zh:离开
to travel|verb|A2|Travel & Exploration|de:reisen;en:to travel;es:viajar;fr:voyager;it:viaggiare;ja:旅行する;ko:여행하다;pt:viajar;ru:путешествовать;uk:подорожувати;zh:旅行
to stay|verb|A2|Travel & Exploration|de:bleiben;en:to stay;es:quedarse;fr:rester;it:rimanere;ja:滞在する;ko:머물다;pt:ficar;ru:оставаться;uk:залишатися;zh:停留
to visit|verb|A2|Travel & Exploration|de:besuchen;en:to visit;es:visitar;fr:visiter;it:visitare;ja:訪れる;ko:방문하다;pt:visitar;ru:посещать;uk:відвідувати;zh:参观
to invite|verb|A2|Society & Culture|de:einladen;en:to invite;es:invitar;fr:inviter;it:invitare;ja:招待する;ko:초대하다;pt:convidar;ru:приглашать;uk:запрошувати;zh:邀请
to order|verb|A2|Everyday Life|de:bestellen;en:to order;es:pedir;fr:commander;it:ordinare;ja:注文する;ko:주문하다;pt:pedir;ru:заказывать;uk:замовляти;zh:订购
to choose|verb|A2|Action Verbs|de:wählen;en:to choose;es:elegir;fr:choisir;it:scegliere;ja:選ぶ;ko:선택하다;pt:escolher;ru:выбирать;uk:вибирати;zh:选择
to explain|verb|A2|Action Verbs|de:erklären;en:to explain;es:explicar;fr:expliquer;it:spiegare;ja:説明する;ko:설명하다;pt:explicar;ru:объяснять;uk:пояснювати;zh:解释
to remember|verb|A2|Thoughts & Emotions|de:erinnern;en:to remember;es:recordar;fr:se souvenir;it:ricordare;ja:思い出す;ko:기억하다;pt:lembrar;ru:помнить;uk:пам'ятати;zh:记得
to forget|verb|A2|Thoughts & Emotions|de:vergessen;en:to forget;es:olvidar;fr:oublier;it:dimenticare;ja:忘れる;ko:잊어버리다;pt:esquecer;ru:забывать;uk:забувати;zh:忘记
to lose|verb|A2|Action Verbs|de:verlieren;en:to lose;es:perder;fr:perdre;it:perdere;ja:失う;ko:잃다;pt:perder;ru:терять;uk:втрачати;zh:丢失
to win|verb|A2|Action Verbs|de:gewinnen;en:to win;es:ganar;fr:gagner;it:vincere;ja:勝つ;ko:이기다;pt:ganhar;ru:выигрывать;uk:вигравати;zh:获胜
to try|verb|A2|Action Verbs|de:versuchen;en:to try;es:intentar;fr:essayer;it:provare;ja:試す;ko:시도하다;pt:tentar;ru:пробовать;uk:пробувати;zh:尝试
to change|verb|A2|Action Verbs|de:ändern;en:to change;es:cambiar;fr:changer;it:cambiare;ja:変える;ko:바꾸다;pt:mudar;ru:менять;uk:змінювати;zh:改变
to move|verb|A2|Action Verbs|de:bewegen;en:to move;es:mover;fr:bouger;it:muovere;ja:動く;ko:움직이다;pt:mover;ru:двигаться;uk:рухатися;zh:移动
to clean|verb|A2|Everyday Life|de:putzen;en:to clean;es:limpiar;fr:nettoyer;it:pulire;ja:掃除する;ko:청소하다;pt:limpar;ru:чистить;uk:чистити;zh:打扫
to cook|verb|A2|Everyday Life|de:kochen;en:to cook;es:cocinar;fr:cuisiner;it:cucinare;ja:料理する;ko:요리하다;pt:cozinhar;ru:готовить;uk:готувати;zh:做饭
to wash|verb|A2|Everyday Life|de:waschen;en:to wash;es:lavar;fr:laver;it:lavare;ja:洗う;ko:씻다;pt:lavar;ru:мыть;uk:мити;zh:洗
to wear|verb|A2|Everyday Life|de:tragen;en:to wear;es:llevar;fr:porter;it:indossare;ja:着る;ko:입다;pt:vestir;ru:носить;uk:носити;zh:穿戴
to prepare|verb|A2|Action Verbs|de:vorbereiten;en:to prepare;es:preparar;fr:préparer;it:preparare;ja:準備する;ko:준비하다;pt:preparar;ru:готовить;uk:готувати;zh:准备
to believe|verb|A2|Thoughts & Emotions|de:glauben;en:to believe;es:creer;fr:croire;it:credere;ja:信じる;ko:믿다;pt:acreditar;ru:верить;uk:вірити;zh:相信
to hope|verb|A2|Thoughts & Emotions|de:hoffen;en:to hope;es:esperar;fr:espérer;it:sperare;ja:願う;ko:바라다;pt:esperar;ru:надеяться;uk:сподіватися;zh:希望
to feel|verb|A2|Thoughts & Emotions|de:fühlen;en:to feel;es:sentir;fr:ressentir;it:sentire;ja:感じる;ko:느끼다;pt:sentir;ru:чувствовать;uk:відчувати;zh:感觉
to stop|verb|A2|Action Verbs|de:stoppen;en:to stop;es:parar;fr:arrêter;it:fermare;ja:止まる;ko:멈추다;pt:parar;ru:останавливать;uk:зупиняти;zh:停止
to continue|verb|A2|Action Verbs|de:fortsetzen;en:to continue;es:continuar;fr:continuer;it:continuare;ja:続ける;ko:계속하다;pt:continuar;ru:продолжать;uk:продовжувати;zh:继续
to send|verb|A2|Action Verbs|de:senden;en:to send;es:enviar;fr:envoyer;it:inviare;ja:送る;ko:보내다;pt:enviar;ru:отправлять;uk:надсилати;zh:发送
to receive|verb|A2|Action Verbs|de:erhalten;en:to receive;es:recibir;fr:recevoir;it:ricevere;ja:受け取る;ko:받다;pt:receber;ru:получать;uk:отримувати;zh:接收
to bring|verb|A2|Action Verbs|de:bringen;en:to bring;es:traer;fr:apporter;it:portare;ja:持ってくる;ko:가져오다;pt:trazer;ru:приносить;uk:приносити;zh:带来
to meet|verb|A2|Society & Culture|de:treffen;en:to meet;es:conocer;fr:rencontrer;it:incontrare;ja:会う;ko:만나다;pt:encontrar;ru:встречать;uk:зустрічати;zh:遇见
airport|noun|A2|Travel & Exploration|de:der Flughafen;en:airport;es:el aeropuerto;fr:l'aéroport;it:l'aeroporto;ja:空港;ko:공항;pt:o aeroporto;ru:аэропорт;uk:аеропорт;zh:机场
station|noun|A2|Travel & Exploration|de:der Bahnhof;en:station;es:la estación;fr:la gare;it:la stazione;ja:駅;ko:기차역;pt:a estação;ru:вокзал;uk:вокзал;zh:车站
hotel|noun|A2|Travel & Exploration|de:das Hotel;en:hotel;es:el hotel;fr:l'hôtel;it:l'hotel;ja:ホテル;ko:호텔;pt:o hotel;ru:отель;uk:готель;zh:酒店
restaurant|noun|A2|Everyday Life|de:das Restaurant;en:restaurant;es:el restaurante;fr:le restaurant;it:il ristorante;ja:レストラン;ko:식당;pt:o restaurante;ru:ресторан;uk:ресторан;zh:餐厅
menu|noun|A2|Everyday Life|de:die Speisekarte;en:menu;es:el menú;fr:le menu;it:il menu;ja:メニュー;ko:메뉴;pt:o cardápio;ru:меню;uk:меню;zh:菜单
bill|noun|A2|Everyday Life|de:die Rechnung;en:bill;es:la cuenta;fr:l'addition;it:il conto;ja:お会計;ko:계산서;pt:a conta;ru:счёт;uk:рахунок;zh:账单
market|noun|A2|Everyday Life|de:der Markt;en:market;es:el mercado;fr:le marché;it:il mercato;ja:市場;ko:시장;pt:o mercado;ru:рынок;uk:ринок;zh:市场
doctor|noun|A2|Society & Culture|de:der Arzt;en:doctor;es:el médico;fr:le médecin;it:il medico;ja:医者;ko:의사;pt:o médico;ru:врач;uk:лікар;zh:医生
hospital|noun|A2|Society & Culture|de:das Krankenhaus;en:hospital;es:el hospital;fr:l'hôpital;it:l'ospedale;ja:病院;ko:병원;pt:o hospital;ru:больница;uk:лікарня;zh:医院
medicine|noun|A2|Everyday Life|de:die Medizin;en:medicine;es:la medicina;fr:le médicament;it:la medicina;ja:薬;ko:약;pt:o remédio;ru:лекарство;uk:ліки;zh:药物
ticket|noun|A2|Travel & Exploration|de:die Fahrkarte;en:ticket;es:el billete;fr:le billet;it:il biglietto;ja:切符;ko:표;pt:o bilhete;ru:билет;uk:квиток;zh:票
journey|noun|A2|Travel & Exploration|de:die Reise;en:journey;es:el viaje;fr:le voyage;it:il viaggio;ja:旅;ko:여행;pt:a viagem;ru:поездка;uk:подорож;zh:旅行
vacation|noun|A2|Travel & Exploration|de:der Urlaub;en:vacation;es:las vacaciones;fr:les vacances;it:le vacanze;ja:休暇;ko:휴가;pt:as férias;ru:отпуск;uk:відпустка;zh:假期
luggage|noun|A2|Travel & Exploration|de:das Gepäck;en:luggage;es:el equipaje;fr:les bagages;it:il bagaglio;ja:荷物;ko:짐;pt:a bagagem;ru:багаж;uk:багаж;zh:行李
weather|noun|A2|Nature & Environment|de:das Wetter;en:weather;es:el tiempo;fr:la météo;it:il tempo;ja:天気;ko:날씨;pt:o clima;ru:погода;uk:погода;zh:天气
snow|noun|A2|Nature & Environment|de:der Schnee;en:snow;es:la nieve;fr:la neige;it:la neve;ja:雪;ko:눈;pt:a neve;ru:снег;uk:сніг;zh:雪
summer|noun|A2|Nature & Environment|de:der Sommer;en:summer;es:el verano;fr:l'été;it:l'estate;ja:夏;ko:여름;pt:o verão;ru:лето;uk:літо;zh:夏天
winter|noun|A2|Nature & Environment|de:der Winter;en:winter;es:el invierno;fr:l'hiver;it:l'inverno;ja:冬;ko:겨울;pt:o inverno;ru:зима;uk:зима;zh:冬天
clothes|noun|A2|Everyday Life|de:die Kleidung;en:clothes;es:la ropa;fr:les vêtements;it:i vestiti;ja:服;ko:옷;pt:as roupas;ru:одежда;uk:одяг;zh:衣服
shoes|noun|A2|Everyday Life|de:die Schuhe;en:shoes;es:los zapatos;fr:les chaussures;it:le scarpe;ja:靴;ko:신발;pt:os sapatos;ru:обувь;uk:взуття;zh:鞋子
price|noun|A2|Everyday Life|de:der Preis;en:price;es:el precio;fr:le prix;it:il prezzo;ja:価格;ko:가격;pt:o preço;ru:цена;uk:ціна;zh:价格
music|noun|A2|Society & Culture|de:die Musik;en:music;es:la música;fr:la musique;it:la musica;ja:音楽;ko:음악;pt:a música;ru:музыка;uk:музика;zh:音乐
film|noun|A2|Society & Culture|de:der Film;en:film;es:la película;fr:le film;it:il film;ja:映画;ko:영화;pt:o filme;ru:фильм;uk:фільм;zh:电影
party|noun|A2|Society & Culture|de:die Party;en:party;es:la fiesta;fr:la fête;it:la festa;ja:パーティー;ko:파티;pt:a festa;ru:вечеринка;uk:вечірка;zh:聚会
gift|noun|A2|Everyday Life|de:das Geschenk;en:gift;es:el regalo;fr:le cadeau;it:il regalo;ja:プレゼント;ko:선물;pt:o presente;ru:подарок;uk:подарунок;zh:礼物
neighbor|noun|A2|Society & Culture|de:der Nachbar;en:neighbor;es:el vecino;fr:le voisin;it:il vicino;ja:隣人;ko:이웃;pt:o vizinho;ru:сосед;uk:сусід;zh:邻居
colleague|noun|A2|Professional & Tech|de:der Kollege;en:colleague;es:el colega;fr:le collègue;it:il collega;ja:同僚;ko:동료;pt:o colega;ru:коллега;uk:колега;zh:同事
problem|noun|A2|Everyday Life|de:das Problem;en:problem;es:el problema;fr:le problème;it:il problema;ja:問題;ko:문제;pt:o problema;ru:проблема;uk:проблема;zh:问题
solution|noun|A2|Everyday Life|de:die Lösung;en:solution;es:la solución;fr:la solution;it:la soluzione;ja:解決策;ko:해결책;pt:a solução;ru:решение;uk:рішення;zh:解决方案
nature|noun|A2|Nature & Environment|de:die Natur;en:nature;es:la naturaleza;fr:la nature;it:la natura;ja:自然;ko:자연;pt:a natureza;ru:природа;uk:природа;zh:大自然
dog|noun|A2|Nature & Environment|de:der Hund;en:dog;es:el perro;fr:le chien;it:il cane;ja:犬;ko:개;pt:o cão;ru:собака;uk:собака;zh:狗
cat|noun|A2|Nature & Environment|de:die Katze;en:cat;es:el gato;fr:le chat;it:il gatto;ja:猫;ko:고양이;pt:o gato;ru:кошка;uk:кішка;zh:猫
flower|noun|A2|Nature & Environment|de:die Blume;en:flower;es:la flor;fr:la fleur;it:il fiore;ja:花;ko:꽃;pt:a flor;ru:цветок;uk:квітка;zh:花朵
sea|noun|A2|Nature & Environment|de:das Meer;en:sea;es:el mar;fr:la mer;it:il mare;ja:海;ko:바다;pt:o mar;ru:море;uk:море;zh:大海
mountain|noun|A2|Nature & Environment|de:der Berg;en:mountain;es:la montaña;fr:la montagne;it:la montagna;ja:山;ko:산;pt:a montanha;ru:гора;uk:гора;zh:山峰
interesting|adjective|A2|Descriptions & Quality|de:interessant;en:interesting;es:interesante;fr:intéressant;it:interessante;ja:面白い;ko:흥미로운;pt:interessante;ru:интересный;uk:цікавий;zh:有趣
boring|adjective|A2|Descriptions & Quality|de:langweilig;en:boring;es:aburrido;fr:ennuyeux;it:noioso;ja:退屈な;ko:지루한;pt:chato;ru:скучный;uk:нудний;zh:无聊
friendly|adjective|A2|Thoughts & Emotions|de:freundlich;en:friendly;es:amable;fr:amical;it:amichevole;ja:親切な;ko:친절한;pt:amigável;ru:дружелюбный;uk:дружній;zh:友好
funny|adjective|A2|Thoughts & Emotions|de:lustig;en:funny;es:divertido;fr:drôle;it:divertente;ja:面白い;ko:재미있는;pt:engraçado;ru:смешной;uk:смішний;zh:有趣
safe|adjective|A2|Descriptions & Quality|de:sicher;en:safe;es:seguro;fr:sûr;it:sicuro;ja:安全な;ko:안전한;pt:seguro;ru:безопасный;uk:безпечний;zh:安全
healthy|adjective|A2|Descriptions & Quality|de:gesund;en:healthy;es:sano;fr:sain;it:sano;ja:健康な;ko:건강한;pt:saudável;ru:здоровый;uk:здоровий;zh:健康
useful|adjective|A2|Descriptions & Quality|de:nützlich;en:useful;es:útil;fr:utile;it:utile;ja:役に立つ;ko:유용한;pt:útil;ru:полезный;uk:корисний;zh:有用
tired|adjective|A2|Thoughts & Emotions|de:müde;en:tired;es:cansado;fr:fatigué;it:stanco;ja:疲れた;ko:피곤한;pt:cansado;ru:уставший;uk:втомлений;zh:疲倦
possible|adjective|A2|Descriptions & Quality|de:möglich;en:possible;es:posible;fr:possible;it:possibile;ja:可能;ko:가능한;pt:possível;ru:возможный;uk:можливий;zh:可能
popular|adjective|A2|Descriptions & Quality|de:beliebt;en:popular;es:popular;fr:populaire;it:popolare;ja:人気のある;ko:인기 있는;pt:popular;ru:популярный;uk:популярний;zh:受欢迎
modern|adjective|A2|Descriptions & Quality|de:modern;en:modern;es:moderno;fr:moderne;it:moderno;ja:現代的;ko:현대적인;pt:moderno;ru:современный;uk:сучасний;zh:现代
delicious|adjective|A2|Descriptions & Quality|de:lecker;en:delicious;es:delicioso;fr:délicieux;it:delizioso;ja:美味しい;ko:맛있는;pt:delicioso;ru:вкусный;uk:смачний;zh:美味
comfortable|adjective|A2|Descriptions & Quality|de:bequem;en:comfortable;es:cómodo;fr:confortable;it:comodo;ja:快適な;ko:편안한;pt:confortável;ru:удобный;uk:зручний;zh:舒适
bright|adjective|A2|Descriptions & Quality|de:hell;en:bright;es:brillante;fr:lumineux;it:luminoso;ja:明るい;ko:밝은;pt:brilhante;ru:яркий;uk:яскравий;zh:明亮
strong|adjective|A2|Descriptions & Quality|de:stark;en:strong;es:fuerte;fr:fort;it:forte;ja:強い;ko:강한;pt:forte;ru:сильный;uk:сильний;zh:强壮
perhaps|adverb|A2|Everyday Life|de:vielleicht;en:perhaps;es:quizás;fr:peut-être;it:forse;ja:たぶん;ko:아마도;pt:talvez;ru:может быть;uk:можливо;zh:也许
almost|adverb|A2|Everyday Life|de:fast;en:almost;es:casi;fr:presque;it:quasi;ja:ほとんど;ko:거의;pt:quase;ru:почти;uk:майже;zh:几乎
"""

    private const val CHUNK_2 = """
especially|adverb|A2|Everyday Life|de:besonders;en:especially;es:especialmente;fr:surtout;it:soprattutto;ja:特に;ko:특히;pt:especialmente;ru:особенно;uk:особливо;zh:特别
soon|adverb|A2|Everyday Life|de:bald;en:soon;es:pronto;fr:bientôt;it:presto;ja:まもなく;ko:곧;pt:em breve;ru:скоро;uk:скоро;zh:很快
clearly|adverb|A2|Everyday Life|de:deutlich;en:clearly;es:claramente;fr:clairement;it:chiaramente;ja:はっきりと;ko:분명히;pt:claramente;ru:ясно;uk:чітко;zh:清楚
usually|adverb|A2|Everyday Life|de:gewöhnlich;en:usually;es:usualmente;fr:d'habitude;it:di solito;ja:普段;ko:보통;pt:geralmente;ru:обычно;uk:зазвичай;zh:通常
suddenly|adverb|A2|Everyday Life|de:plötzlich;en:suddenly;es:de repente;fr:soudain;it:all'improvviso;ja:突然;ko:갑자기;pt:de repente;ru:внезапно;uk:раптово;zh:突然
enough|adverb|A2|Everyday Life|de:genug;en:enough;es:suficiente;fr:assez;it:abbastanza;ja:十分に;ko:충분히;pt:bastante;ru:достаточно;uk:достатньо;zh:足够
to decide|verb|B1|Thoughts & Emotions|de:entscheiden;en:to decide;es:decidir;fr:décider;it:decidere;ja:決める;ko:결정하다;pt:decidir;ru:решать;uk:вирішувати;zh:决定
to develop|verb|B1|Action Verbs|de:entwickeln;en:to develop;es:desarrollar;fr:développer;it:sviluppare;ja:発展させる;ko:발전시키다;pt:desenvolver;ru:развивать;uk:розвивати;zh:发展
to achieve|verb|B1|Action Verbs|de:erreichen;en:to achieve;es:lograr;fr:atteindre;it:raggiungere;ja:達成する;ko:달성하다;pt:alcançar;ru:достигать;uk:досягати;zh:达到
to support|verb|B1|Society & Culture|de:unterstützen;en:to support;es:apoyar;fr:soutenir;it:supportare;ja:支える;ko:지지하다;pt:apoiar;ru:поддерживать;uk:підтримувати;zh:支持
to consider|verb|B1|Thoughts & Emotions|de:überlegen;en:to consider;es:considerar;fr:considérer;it:considerare;ja:考慮する;ko:고려하다;pt:considerar;ru:рассматривать;uk:розглядати;zh:考虑
to suggest|verb|B1|Action Verbs|de:vorschlagen;en:to suggest;es:sugerir;fr:suggérer;it:suggerire;ja:提案する;ko:제안하다;pt:sugerir;ru:предлагать;uk:пропонувати;zh:建议
to compare|verb|B1|Action Verbs|de:vergleichen;en:to compare;es:comparar;fr:comparer;it:confrontare;ja:比較する;ko:비교하다;pt:comparar;ru:сравнивать;uk:порівнювати;zh:比较
to agree|verb|B1|Thoughts & Emotions|de:zustimmen;en:to agree;es:estar de acuerdo;fr:être d'accord;it:essere d'accordo;ja:同意する;ko:동의하다;pt:concordar;ru:соглашаться;uk:погоджуватися;zh:同意
to discuss|verb|B1|Society & Culture|de:diskutieren;en:to discuss;es:discutir;fr:discuter;it:discutere;ja:議論する;ko:토론하다;pt:discutir;ru:обсуждать;uk:обговорювати;zh:讨论
to avoid|verb|B1|Action Verbs|de:vermeiden;en:to avoid;es:evitar;fr:éviter;it:evitare;ja:避ける;ko:피하다;pt:evitar;ru:избегать;uk:уникати;zh:避免
to improve|verb|B1|Action Verbs|de:verbessern;en:to improve;es:mejorar;fr:améliorer;it:migliorare;ja:改善する;ko:개선하다;pt:melhorar;ru:улучшать;uk:покращувати;zh:改进
to protect|verb|B1|Nature & Environment|de:schützen;en:to protect;es:proteger;fr:protéger;it:proteggere;ja:守る;ko:보호하다;pt:proteger;ru:защищать;uk:захищати;zh:保护
to discover|verb|B1|Travel & Exploration|de:entdecken;en:to discover;es:descubrir;fr:découvrir;it:scoprire;ja:発見する;ko:발견하다;pt:descobrir;ru:открывать;uk:відкривати;zh:发现
to expect|verb|B1|Thoughts & Emotions|de:erwarten;en:to expect;es:esperar;fr:attendre;it:aspettarsi;ja:期待する;ko:기대하다;pt:esperar;ru:ожидать;uk:очікувати;zh:期待
to participate|verb|B1|Society & Culture|de:teilnehmen;en:to participate;es:participar;fr:participer;it:partecipare;ja:参加する;ko:참여하다;pt:participar;ru:участвовать;uk:брати участь;zh:参加
to manage|verb|B1|Professional & Tech|de:verwalten;en:to manage;es:gestionar;fr:gérer;it:gestire;ja:管理する;ko:관리하다;pt:gerenciar;ru:руководить;uk:керувати;zh:管理
to organize|verb|B1|Professional & Tech|de:organisieren;en:to organize;es:organizar;fr:organiser;it:organizzare;ja:組織する;ko:조직하다;pt:organizar;ru:организовывать;uk:організовувати;zh:组织
to allow|verb|B1|Society & Culture|de:erlauben;en:to allow;es:permitir;fr:permettre;it:permettere;ja:許可する;ko:허용하다;pt:permitir;ru:разрешать;uk:дозволяти;zh:允许
to share|verb|B1|Society & Culture|de:teilen;en:to share;es:compartir;fr:partager;it:condividere;ja:共有する;ko:공유하다;pt:compartilhar;ru:делиться;uk:ділитися;zh:分享
to trust|verb|B1|Thoughts & Emotions|de:vertrauen;en:to trust;es:confiar;fr:faire confiance;it:fidarsi;ja:信頼する;ko:신뢰하다;pt:confiar;ru:доверять;uk:довіряти;zh:信任
to appreciate|verb|B1|Thoughts & Emotions|de:schätzen;en:to appreciate;es:apreciar;fr:apprécier;it:apprezzare;ja:感謝する;ko:인정하다;pt:apreciar;ru:ценить;uk:цінувати;zh:欣赏
decision|noun|B1|Thoughts & Emotions|de:die Entscheidung;en:decision;es:la decisión;fr:la décision;it:la decisione;ja:決定;ko:결정;pt:a decisão;ru:решение;uk:рішення;zh:决定
development|noun|B1|Professional & Tech|de:die Entwicklung;en:development;es:el desarrollo;fr:le développement;it:lo sviluppo;ja:開発;ko:개발;pt:o desenvolvimento;ru:развитие;uk:розвиток;zh:发展
experience|noun|B1|Thoughts & Emotions|de:die Erfahrung;en:experience;es:la experiencia;fr:l'expérience;it:l'esperienza;ja:経験;ko:경험;pt:a experiência;ru:опыт;uk:досвід;zh:经验
relationship|noun|B1|Society & Culture|de:die Beziehung;en:relationship;es:la relación;fr:la relation;it:la relazione;ja:関係;ko:관계;pt:o relacionamento;ru:отношения;uk:відносини;zh:关系
possibility|noun|B1|Thoughts & Emotions|de:die Möglichkeit;en:possibility;es:la posibilidad;fr:la possibilité;it:la possibilità;ja:可能性;ko:가능성;pt:a possibilidade;ru:возможность;uk:можливість;zh:可能性
opinion|noun|B1|Thoughts & Emotions|de:die Meinung;en:opinion;es:la opinión;fr:l'avis;it:l'opinione;ja:意見;ko:의견;pt:a opinião;ru:мнение;uk:думка;zh:意见
goal|noun|B1|Thoughts & Emotions|de:das Ziel;en:goal;es:el objetivo;fr:l'objectif;it:l'obiettivo;ja:目標;ko:목표;pt:o objetivo;ru:цель;uk:мета;zh:目标
reason|noun|B1|Thoughts & Emotions|de:der Grund;en:reason;es:la razón;fr:la raison;it:il motivo;ja:理由;ko:이유;pt:a razão;ru:причина;uk:причина;zh:原因
result|noun|B1|Professional & Tech|de:das Ergebnis;en:result;es:el resultado;fr:le résultat;it:il risultato;ja:結果;ko:결과;pt:o resultado;ru:результат;uk:результат;zh:结果
future|noun|B1|Everyday Life|de:die Zukunft;en:future;es:el futuro;fr:l'avenir;it:il futuro;ja:未来;ko:미래;pt:o futuro;ru:будущее;uk:майбутнє;zh:未来
culture|noun|B1|Society & Culture|de:die Kultur;en:culture;es:la cultura;fr:la culture;it:la cultura;ja:文化;ko:문화;pt:a cultura;ru:культура;uk:культура;zh:文化
economy|noun|B1|Professional & Tech|de:die Wirtschaft;en:economy;es:la economía;fr:l'économie;it:l'economia;ja:経済;ko:경제;pt:a economia;ru:экономика;uk:економіка;zh:经济
education|noun|B1|Society & Culture|de:die Bildung;en:education;es:la educación;fr:l'éducation;it:l'istruzione;ja:教育;ko:교육;pt:a educação;ru:образование;uk:освіта;zh:教育
success|noun|B1|Professional & Tech|de:der Erfolg;en:success;es:el éxito;fr:le succès;it:il successo;ja:成功;ko:성공;pt:o sucesso;ru:успех;uk:успіх;zh:成功
society|noun|B1|Society & Culture|de:die Gesellschaft;en:society;es:la sociedad;fr:la société;it:la società;ja:社会;ko:사회;pt:a sociedade;ru:общество;uk:суспільство;zh:社会
truth|noun|B1|Thoughts & Emotions|de:die Wahrheit;en:truth;es:la verdad;fr:la vérité;it:la verità;ja:真実;ko:진실;pt:a verdade;ru:правда;uk:правда;zh:真理
advantage|noun|B1|Everyday Life|de:der Vorteil;en:advantage;es:la ventaja;fr:l'avantage;it:il vantaggio;ja:利点;ko:장점;pt:a vantagem;ru:преимущество;uk:перевага;zh:优势
gratitude|noun|B1|Thoughts & Emotions|de:die Dankbarkeit;en:gratitude;es:la gratitud;fr:la gratitude;it:la gratitudine;ja:感謝;ko:감사;pt:a gratidão;ru:благодарность;uk:вдячність;zh:感激
freedom|noun|B1|Society & Culture|de:die Freiheit;en:freedom;es:la libertad;fr:la liberté;it:la libertà;ja:自由;ko:자유;pt:a liberdade;ru:свобода;uk:свобода;zh:自由
knowledge|noun|B1|Thoughts & Emotions|de:das Wissen;en:knowledge;es:el conocimiento;fr:la connaissance;it:la conoscenza;ja:知識;ko:지식;pt:o conhecimento;ru:знания;uk:знання;zh:知识
challenge|noun|B1|Professional & Tech|de:die Herausforderung;en:challenge;es:el desafío;fr:le défi;it:la sfida;ja:挑戦;ko:도전;pt:o desafio;ru:вызов;uk:виклик;zh:挑战
necessary|adjective|B1|Descriptions & Quality|de:notwendig;en:necessary;es:necesario;fr:nécessaire;it:necessario;ja:必要な;ko:필요한;pt:necessário;ru:необходимый;uk:необхідний;zh:必要
responsible|adjective|B1|Thoughts & Emotions|de:verantwortlich;en:responsible;es:responsable;fr:responsable;it:responsabile;ja:責任ある;ko:책임감 있는;pt:responsável;ru:ответственный;uk:відповідальний;zh:负责
independent|adjective|B1|Society & Culture|de:unabhängig;en:independent;es:independiente;fr:indépendant;it:indipendente;ja:独立した;ko:독립적인;pt:independente;ru:независимый;uk:незалежний;zh:独立
patient|adjective|B1|Thoughts & Emotions|de:geduldig;en:patient;es:paciente;fr:patient;it:paziente;ja:忍耐強い;ko:인내심 있는;pt:paciente;ru:терпеливый;uk:терплячий;zh:耐心
flexible|adjective|B1|Descriptions & Quality|de:flexibel;en:flexible;es:flexible;fr:flexible;it:flessibile;ja:柔軟な;ko:유연한;pt:flexível;ru:гибкий;uk:гнучкий;zh:灵活
honest|adjective|B1|Thoughts & Emotions|de:ehrlich;en:honest;es:honesto;fr:honnête;it:onesto;ja:正直な;ko:정직한;pt:honesto;ru:честный;uk:чесний;zh:诚实
creative|adjective|B1|Descriptions & Quality|de:kreativ;en:creative;es:creativo;fr:créatif;it:creativo;ja:創造的な;ko:창의적인;pt:criativo;ru:творческий;uk:творчий;zh:有创意
reliable|adjective|B1|Descriptions & Quality|de:zuverlässig;en:reliable;es:fiable;fr:fiable;it:affidabile;ja:信頼できる;ko:신뢰할 수 있는;pt:confiável;ru:надёжный;uk:надійний;zh:可靠
calm|adjective|B1|Thoughts & Emotions|de:ruhig;en:calm;es:tranquilo;fr:calme;it:calmo;ja:穏やかな;ko:차분한;pt:calmo;ru:спокойный;uk:спокійний;zh:平静
brave|adjective|B1|Thoughts & Emotions|de:mutig;en:brave;es:valiente;fr:courageux;it:coraggioso;ja:勇敢な;ko:용감한;pt:corajoso;ru:смелый;uk:сміливий;zh:勇敢
certainly|adverb|B1|Everyday Life|de:sicherlich;en:certainly;es:ciertamente;fr:certainement;it:certamente;ja:確かに;ko:분명히;pt:certamente;ru:безусловно;uk:безумовно;zh:必定
finally|adverb|B1|Everyday Life|de:schließlich;en:finally;es:finalmente;fr:finalement;it:finalmente;ja:ついに;ko:마침내;pt:finalmente;ru:наконец;uk:нарешті;zh:终于
however|adverb|B1|Everyday Life|de:jedoch;en:however;es:sin embargo;fr:cependant;it:tuttavia;ja:しかしながら;ko:그러나;pt:no entanto;ru:однако;uk:проте;zh:然而
definitely|adverb|B1|Everyday Life|de:definitiv;en:definitely;es:definitivamente;fr:définitivement;it:decisamente;ja:絶対に;ko:확실히;pt:definitivamente;ru:определённо;uk:безумовно;zh:绝对
completely|adverb|B1|Everyday Life|de:völlig;en:completely;es:completamente;fr:complètement;it:completamente;ja:完全に;ko:완전히;pt:completamente;ru:полностью;uk:повністю;zh:完全
to analyze|verb|B2|Professional & Tech|de:analysieren;en:to analyze;es:analizar;fr:analyser;it:analizzare;ja:分析する;ko:분석하다;pt:analisar;ru:анализировать;uk:аналізувати;zh:分析
to interpret|verb|B2|Professional & Tech|de:interpretieren;en:to interpret;es:interpretar;fr:interpréter;it:interpretare;ja:解釈する;ko:해석하다;pt:interpretar;ru:толковать;uk:тлумачити;zh:解释
to implement|verb|B2|Professional & Tech|de:umsetzen;en:to implement;es:implementar;fr:mettre en œuvre;it:implementare;ja:実装する;ko:구현하다;pt:implementar;ru:внедрять;uk:впроваджувати;zh:实施
to coordinate|verb|B2|Professional & Tech|de:koordinieren;en:to coordinate;es:coordinar;fr:coordonner;it:coordinare;ja:調整する;ko:조정하다;pt:coordenar;ru:координировать;uk:координувати;zh:协调
to optimize|verb|B2|Professional & Tech|de:optimieren;en:to optimize;es:optimizar;fr:optimiser;it:ottimizzare;ja:最適化する;ko:최적화하다;pt:otimizar;ru:оптимизировать;uk:оптимізувати;zh:优化
to evaluate|verb|B2|Professional & Tech|de:bewerten;en:to evaluate;es:evaluar;fr:évaluer;it:valutare;ja:評価する;ko:평가하다;pt:avaliar;ru:оценивать;uk:оцінювати;zh:评估
to justify|verb|B2|Thoughts & Emotions|de:rechtfertigen;en:to justify;es:justificar;fr:justifier;it:giustificare;ja:正当化する;ko:정당화하다;pt:justificar;ru:обосновывать;uk:обґрунтовувати;zh:证明合理
to establish|verb|B2|Professional & Tech|de:etablieren;en:to establish;es:establecer;fr:établir;it:stabilire;ja:確立する;ko:확립하다;pt:estabelecer;ru:устанавливать;uk:встановлювати;zh:确立
to distinguish|verb|B2|Thoughts & Emotions|de:unterscheiden;en:to distinguish;es:distinguir;fr:distinguer;it:distinguere;ja:区別する;ko:구별하다;pt:distinguir;ru:различать;uk:розрізняти;zh:区分
to prioritize|verb|B2|Professional & Tech|de:priorisieren;en:to prioritize;es:priorizar;fr:prioriser;it:dare priorità;ja:優先順位をつける;ko:우선시하다;pt:priorizar;ru:расставлять приоритеты;uk:визначати пріоритети;zh:优先处理
strategy|noun|B2|Professional & Tech|de:die Strategie;en:strategy;es:la estrategia;fr:la stratégie;it:la strategia;ja:戦略;ko:전략;pt:a estratégia;ru:стратегия;uk:стратегія;zh:策略
concept|noun|B2|Thoughts & Emotions|de:das Konzept;en:concept;es:el concepto;fr:le concept;it:il concetto;ja:概念;ko:개념;pt:o conceito;ru:концепция;uk:концепція;zh:概念
innovation|noun|B2|Professional & Tech|de:die Innovation;en:innovation;es:la innovación;fr:l'innovation;it:l'innovazione;ja:革新;ko:혁신;pt:a inovação;ru:инновация;uk:інновація;zh:创新
perspective|noun|B2|Thoughts & Emotions|de:die Perspektive;en:perspective;es:la perspectiva;fr:la perspective;it:la prospettiva;ja:視点;ko:관점;pt:a perspectiva;ru:перспектива;uk:перспектива;zh:观点
responsibility|noun|B2|Thoughts & Emotions|de:die Verantwortung;en:responsibility;es:la responsabilidad;fr:la responsabilité;it:la responsabilità;ja:責任;ko:책임;pt:a responsabilidade;ru:ответственность;uk:відповідальність;zh:责任
efficiency|noun|B2|Professional & Tech|de:die Effizienz;en:efficiency;es:la eficiencia;fr:l'efficacité;it:l'efficienza;ja:効率;ko:효율;pt:a eficiência;ru:эффективность;uk:ефективність;zh:效率
sustainability|noun|B2|Nature & Environment|de:die Nachhaltigkeit;en:sustainability;es:la sostenibilidad;fr:la durabilité;it:la sostenibilità;ja:持続可能性;ko:지속 가능성;pt:a sustentabilidade;ru:устойчивость;uk:стійкість;zh:可持续性
compromise|noun|B2|Society & Culture|de:der Kompromiss;en:compromise;es:el compromiso;fr:le compromis;it:il compromesso;ja:妥協;ko:타협;pt:o compromisso;ru:компромисс;uk:компроміс;zh:妥协
significant|adjective|B2|Descriptions & Quality|de:bedeutend;en:significant;es:significativo;fr:significatif;it:significativo;ja:重要な;ko:중요한;pt:significativo;ru:значительный;uk:значний;zh:显著的
complex|adjective|B2|Descriptions & Quality|de:komplex;en:complex;es:complejo;fr:complexe;it:complesso;ja:複雑な;ko:복잡한;pt:complexo;ru:сложный;uk:складний;zh:复杂的
sustainable|adjective|B2|Nature & Environment|de:nachhaltig;en:sustainable;es:sostenible;fr:durable;it:sostenibile;ja:持続可能な;ko:지속 가능한;pt:sustentável;ru:устойчивый;uk:стійкий;zh:可持续的
precise|adjective|B2|Descriptions & Quality|de:präzise;en:precise;es:preciso;fr:précis;it:preciso;ja:正確な;ko:정확한;pt:preciso;ru:точный;uk:точний;zh:精确的
authentic|adjective|B2|Descriptions & Quality|de:authentisch;en:authentic;es:auténtico;fr:authentique;it:autentico;ja:本物の;ko:진정한;pt:autêntico;ru:подлинный;uk:автентичний;zh:真实的
profound|adjective|B2|Thoughts & Emotions|de:tiefgründig;en:profound;es:profundo;fr:profond;it:profondo;ja:深遠な;ko:심오한;pt:profundo;ru:глубокий;uk:глибокий;zh:深刻的
consequently|adverb|B2|Everyday Life|de:infolgedessen;en:consequently;es:por lo tanto;fr:par conséquent;it:di conseguenza;ja:その結果;ko:따라서;pt:consequentemente;ru:следовательно;uk:отже;zh:因此
nonetheless|adverb|B2|Everyday Life|de:dennoch;en:nonetheless;es:no obstante;fr:néanmoins;it:nondimeno;ja:それにもかかわらず;ko:그럼에도 불구하고;pt:não obstante;ru:тем не менее;uk:тим не менше;zh:尽管如此
predominantly|adverb|B2|Everyday Life|de:überwiegend;en:predominantly;es:predominantemente;fr:principalement;it:prevalentemente;ja:主に;ko:주로;pt:predominantemente;ru:преимущественно;uk:переважно;zh:主要地
to differentiate|verb|C1|Thoughts & Emotions|de:differenzieren;en:to differentiate;es:diferenciar;fr:différencier;it:differenziare;ja:識別する;ko:구별하다;pt:diferenciar;ru:дифференцировать;uk:диференціювати;zh:区分
to scrutinize|verb|C1|Professional & Tech|de:hinterfragen;en:to scrutinize;es:escrutar;fr:scruter;it:scrutare;ja:吟味する;ko:면밀히 조사하다;pt:escrutinar;ru:тщательно проверять;uk:ретельно перевіряти;zh:细察
to synthesize|verb|C1|Professional & Tech|de:synthetisieren;en:to synthesize;es:sintetizar;fr:synthétiser;it:sintetizzare;ja:統合する;ko:종합하다;pt:sintetizar;ru:синтезировать;uk:синтезувати;zh:综合
to substantiate|verb|C1|Professional & Tech|de:untermauern;en:to substantiate;es:fundamentar;fr:étayer;it:sostanziare;ja:実証する;ko:입증하다;pt:substanciar;ru:обосновывать;uk:підтверджувати;zh:证实
to reconcile|verb|C1|Society & Culture|de:versöhnen;en:to reconcile;es:conciliar;fr:réconcilier;it:riconciliare;ja:和解させる;ko:화해시키다;pt:reconciliar;ru:примирять;uk:примиряти;zh:调和
nuance|noun|C1|Thoughts & Emotions|de:die Nuance;en:nuance;es:el matiz;fr:la nuance;it:la sfumatura;ja:ニュアンス;ko:뉘앙스;pt:a nuance;ru:нюанс;uk:нюанс;zh:细微差别
consensus|noun|C1|Society & Culture|de:der Konsens;en:consensus;es:el consenso;fr:le consensus;it:il consenso;ja:合意;ko:합의;pt:o consenso;ru:консенсус;uk:консенсус;zh:共识
resilience|noun|C1|Thoughts & Emotions|de:die Widerstandskraft;en:resilience;es:la resiliencia;fr:la résilience;it:la resilienza;ja:回復力;ko:회복 탄력성;pt:a resiliência;ru:устойчивость;uk:стійкість;zh:复原力
paradigm|noun|C1|Professional & Tech|de:das Paradigma;en:paradigm;es:el paradigma;fr:le paradigme;it:il paradigma;ja:パラダイム;ko:패러다임;pt:o paradigma;ru:парадигма;uk:парадигма;zh:范式
ambiguity|noun|C1|Thoughts & Emotions|de:die Mehrdeutigkeit;en:ambiguity;es:la ambigüedad;fr:l'ambiguïté;it:l'ambiguità;ja:曖昧さ;ko:모호성;pt:a ambiguidade;ru:двусмысленность;uk:неоднозначність;zh:模棱两可
"""

    private const val CHUNK_3 = """
subtle|adjective|C1|Descriptions & Quality|de:subtil;en:subtle;es:sutil;fr:subtil;it:sottile;ja:繊細な;ko:미묘한;pt:sutil;ru:тонкий;uk:тонкий;zh:微妙的
meticulous|adjective|C1|Descriptions & Quality|de:akribisch;en:meticulous;es:meticuloso;fr:méticuleux;it:meticoloso;ja:綿密な;ko:꼼꼼한;pt:meticuloso;ru:дотошный;uk:ретельний;zh:一丝不苟的
plausible|adjective|C1|Thoughts & Emotions|de:plausibel;en:plausible;es:plausible;fr:plausible;it:plausibile;ja:もっともらしい;ko:그럴듯한;pt:plausível;ru:правдоподобный;uk:правдоподібний;zh:看似合理的
eloquent|adjective|C1|Society & Culture|de:wortgewandt;en:eloquent;es:elocuente;fr:éloquent;it:eloquente;ja:雄弁な;ko:달변의;pt:eloquente;ru:красноречивый;uk:красномовний;zh:雄辩的
inevitably|adverb|C1|Everyday Life|de:unweigerlich;en:inevitably;es:inevitablemente;fr:inévitablement;it:inevitabilmente;ja:必然的に;ko:필연적으로;pt:inevitavelmente;ru:неизбежно;uk:неминуче;zh:不可避免地
inherently|adverb|C1|Thoughts & Emotions|de:von Natur aus;en:inherently;es:intrínsecamente;fr:intrinsèquement;it:intrinsecamente;ja:本質的に;ko:본질적으로;pt:intrinsecamente;ru:по сути;uk:за своєю суттю;zh:固执地/固有地
to fluctuate|verb|C2|Professional & Tech|de:schwanken;en:to fluctuate;es:fluctuar;fr:fluctuer;it:fluttuare;ja:変動する;ko:변동하다;pt:flutuar;ru:колебаться;uk:коливатися;zh:波动
to corroborate|verb|C2|Professional & Tech|de:bekräftigen;en:to corroborate;es:corroborar;fr:corroborer;it:corroborare;ja:裏付ける;ko:확증하다;pt:corroborar;ru:подтверждать;uk:підтверджувати;zh:证实
to delineate|verb|C2|Professional & Tech|de:skizzieren;en:to delineate;es:delinear;fr:délimiter;it:delineare;ja:描写する;ko:기술하다;pt:delinear;ru:очерчивать;uk:окреслювати;zh:描绘
dichotomy|noun|C2|Thoughts & Emotions|de:die Dichotomie;en:dichotomy;es:la dicotomía;fr:la dichotomie;it:la dicotomia;ja:二分法;ko:이분법;pt:a dicotomia;ru:дихотомия;uk:дихотомія;zh:对立/二分法
epiphany|noun|C2|Thoughts & Emotions|de:die Erleuchtung;en:epiphany;es:la epifanía;fr:l'épiphanie;it:l'epifania;ja:ひらめき;ko:깨달음;pt:a epifania;ru:озарение;uk:прозріння;zh:顿悟
serendipity|noun|C2|Thoughts & Emotions|de:glücklicher Zufall;en:serendipity;es:la serenpidad;fr:le hasard heureux;it:la serendipità;ja:思わぬ発見;ko:우연한 행운;pt:a serendipidade;ru:счастливая случайность;uk:щаслива випадковість;zh:机缘凑巧
apex|noun|C2|Descriptions & Quality|de:der Höhepunkt;en:apex;es:el ápice;fr:l'apogée;it:l'apice;ja:頂点;ko:정점;pt:o ápice;ru:вершина;uk:вершина;zh:巅峰
exquisite|adjective|C2|Descriptions & Quality|de:erlesen;en:exquisite;es:exquisito;fr:exquis;it:squisito;ja:精巧な;ko:정교한;pt:requintado;ru:изящный;uk:вишуканий;zh:精美的
succinct|adjective|C2|Society & Culture|de:prägnant;en:succinct;es:sucinto;fr:succinct;it:succinto;ja:簡潔な;ko:간결한;pt:sucinto;ru:лаконичный;uk:лаконічний;zh:简明扼要的
quintessential|adjective|C2|Descriptions & Quality|de:vollendet;en:quintessential;es:arquetípico;fr:quintessencié;it:quintessenziale;ja:典型的な;ko:전형적인;pt:típico;ru:типичнейший;uk:найбільш типовий;zh:典范的
ephemeral|adjective|C2|Descriptions & Quality|de:flüchtig;en:ephemeral;es:efímero;fr:éphémère;it:effimero;ja:つかの間の;ko:덧없는;pt:efêmero;ru:мимолетный;uk:минущий;zh:短暂的
unequivocally|adverb|C2|Thoughts & Emotions|de:unmissverständlich;en:unequivocally;es:inequívocamente;fr:sans équivoque;it:inequivocabilmente;ja:明白に;ko:명백하게;pt:inequivocamente;ru:недвусмысленно;uk:недвозначно;zh:明确无疑地
seamlessly|adverb|C2|Professional & Tech|de:nahtlos;en:seamlessly;es:sin problemas;fr:harmonieusement;it:perfettamente;ja:円滑に;ko:매끄럽게;pt:perfeitamente;ru:плавно;uk:безперешкодно;zh:无缝地
milk|noun|A1|Everyday Life|de:die Milch;en:milk;es:la leche;fr:le lait;it:il latte;ja:牛乳;ko:우유;pt:o leite;ru:молоко;uk:молоко;zh:牛奶
sugar|noun|A1|Everyday Life|de:der Zucker;en:sugar;es:el azúcar;fr:le sucre;it:lo zucchero;ja:砂糖;ko:설탕;pt:o açúcar;ru:сахар;uk:цукор;zh:糖
salt|noun|A1|Everyday Life|de:das Salz;en:salt;es:la sal;fr:le sel;it:il sale;ja:塩;ko:소금;pt:o sal;ru:соль;uk:сіль;zh:盐
fruit|noun|A1|Everyday Life|de:das Obst;en:fruit;es:la fruta;fr:le fruit;it:la frutta;ja:果物;ko:과일;pt:a fruta;ru:фрукты;uk:фрукти;zh:水果
vegetable|noun|A1|Everyday Life|de:das Gemüse;en:vegetable;es:la verdura;fr:le légume;it:la verdura;ja:野菜;ko:야채;pt:o legume;ru:овощи;uk:овочі;zh:蔬菜
kitchen|noun|A1|Everyday Life|de:die Küche;en:kitchen;es:la cocina;fr:la cuisine;it:la cucina;ja:キッチン;ko:주방;pt:a cozinha;ru:кухня;uk:кухня;zh:厨房
bathroom|noun|A1|Everyday Life|de:das Badezimmer;en:bathroom;es:el baño;fr:la salle de bain;it:il bagno;ja:浴室;ko:욕실;pt:o banheiro;ru:ванная комната;uk:ванна кімната;zh:浴室
bed|noun|A1|Everyday Life|de:das Bett;en:bed;es:la cama;fr:le lit;it:il letto;ja:ベッド;ko:침대;pt:a cama;ru:кровать;uk:ліжко;zh:床
garden|noun|A1|Nature & Environment|de:der Garten;en:garden;es:el jardín;fr:le jardin;it:il giardino;ja:庭;ko:정원;pt:o jardim;ru:сад;uk:сад;zh:花园
tree|noun|A1|Nature & Environment|de:der Baum;en:tree;es:el árbol;fr:l'arbre;it:l'albero;ja:木;ko:나무;pt:a árvore;ru:дерево;uk:дерево;zh:树
bird|noun|A1|Nature & Environment|de:der Vogel;en:bird;es:el pájaro;fr:l'oiseau;it:l'uccello;ja:鳥;ko:새;pt:o pássaro;ru:птица;uk:птах;zh:鸟
sky|noun|A1|Nature & Environment|de:der Himmel;en:sky;es:el cielo;fr:le ciel;it:il cielo;ja:空;ko:하늘;pt:o céu;ru:небо;uk:небо;zh:天空
moon|noun|A1|Nature & Environment|de:der Mond;en:moon;es:la luna;fr:la lune;it:la luna;ja:月;ko:달;pt:a lua;ru:луна;uk:місяць;zh:月亮
star|noun|A1|Nature & Environment|de:der Stern;en:star;es:la estrella;fr:l'étoile;it:la stella;ja:星;ko:별;pt:a estrela;ru:звезда;uk:зірка;zh:星星
wind|noun|A1|Nature & Environment|de:der Wind;en:wind;es:el viento;fr:le vent;it:il vento;ja:風;ko:바람;pt:o vento;ru:ветер;uk:вітер;zh:风
afternoon|noun|A1|Everyday Life|de:der Nachmittag;en:afternoon;es:la tarde;fr:l'après-midi;it:il pomeriggio;ja:午後;ko:오후;pt:a tarde;ru:день;uk:день;zh:下午
midnight|noun|A1|Everyday Life|de:die Mitternacht;en:midnight;es:la medianoche;fr:le minuit;it:la mezzanotte;ja:真夜中;ko:자정;pt:a meia-noite;ru:полночь;uk:північ;zh:午夜
clock|noun|A1|Everyday Life|de:die Uhr;en:clock;es:el reloj;fr:l'horloge;it:l'orologio;ja:時計;ko:시계;pt:o relógio;ru:часы;uk:годинник;zh:钟表
key|noun|A1|Everyday Life|de:der Schlüssel;en:key;es:la llave;fr:la clé;it:la chiave;ja:鍵;ko:열쇠;pt:a chave;ru:ключ;uk:ключ;zh:钥匙
bag|noun|A1|Everyday Life|de:die Tasche;en:bag;es:la bolsa;fr:le sac;it:la borsa;ja:バッグ;ko:가방;pt:a bolsa;ru:сумка;uk:сумка;zh:包
coat|noun|A1|Everyday Life|de:der Mantel;en:coat;es:el abrigo;fr:le manteau;it:il cappotto;ja:コート;ko:코트;pt:o casaco;ru:пальто;uk:пальто;zh:大衣
hat|noun|A1|Everyday Life|de:der Hut;en:hat;es:el sombrero;fr:le chapeau;it:il cappello;ja:帽子;ko:모자;pt:o chapéu;ru:шляпа;uk:капелюх;zh:帽子
pen|noun|A1|Everyday Life|de:der Stift;en:pen;es:el bolígrafo;fr:le stylo;it:la penna;ja:ペン;ko:펜;pt:a caneta;ru:ручка;uk:ручка;zh:笔
paper|noun|A1|Everyday Life|de:das Papier;en:paper;es:el papel;fr:le papier;it:la carta;ja:紙;ko:종이;pt:o papel;ru:бумага;uk:папір;zh:纸
newspaper|noun|A1|Everyday Life|de:die Zeitung;en:newspaper;es:el periódico;fr:le journal;it:il giornale;ja:新聞;ko:신문;pt:o jornal;ru:газета;uk:газета;zh:报纸
picture|noun|A1|Everyday Life|de:das Bild;en:picture;es:el cuadro;fr:le tableau;it:il quadro;ja:絵;ko:그림;pt:o quadro;ru:картина;uk:картина;zh:画
color|noun|A1|Descriptions & Quality|de:die Farbe;en:color;es:el color;fr:la couleur;it:il colore;ja:色;ko:색상;pt:a cor;ru:цвет;uk:колір;zh:颜色
red|adjective|A1|Descriptions & Quality|de:rot;en:red;es:rojo;fr:rouge;it:rosso;ja:赤;ko:빨간색;pt:vermelho;ru:красный;uk:червоний;zh:红色
blue|adjective|A1|Descriptions & Quality|de:blau;en:blue;es:azul;fr:bleu;it:blu;ja:青;ko:파란색;pt:azul;ru:синий;uk:синій;zh:蓝色
green|adjective|A1|Descriptions & Quality|de:grün;en:green;es:verde;fr:vert;it:verde;ja:緑;ko:초록색;pt:verde;ru:зелёный;uk:зелений;zh:绿色
yellow|adjective|A1|Descriptions & Quality|de:gelb;en:yellow;es:amarillo;fr:jaune;it:giallo;ja:黄色;ko:노란색;pt:amarelo;ru:жёлтый;uk:жовтий;zh:黄色
white|adjective|A1|Descriptions & Quality|de:weiß;en:white;es:blanco;fr:blanc;it:bianco;ja:白;ko:흰색;pt:branco;ru:белый;uk:білий;zh:白色
black|adjective|A1|Descriptions & Quality|de:schwarz;en:black;es:negro;fr:noir;it:nero;ja:黒;ko:검은색;pt:preto;ru:чёрный;uk:чорний;zh:黑色
to build|verb|A2|Action Verbs|de:bauen;en:to build;es:construir;fr:construire;it:costruire;ja:建てる;ko:짓다;pt:construir;ru:строить;uk:будувати;zh:建造
to cut|verb|A2|Action Verbs|de:schneiden;en:to cut;es:cortar;fr:couper;it:tagliare;ja:切る;ko:자르다;pt:cortar;ru:резать;uk:різати;zh:切
to draw|verb|A2|Action Verbs|de:zeichnen;en:to draw;es:dibujar;fr:dessiner;it:disegnare;ja:描く;ko:그리다;pt:desenhar;ru:рисовать;uk:малювати;zh:绘画
to sing|verb|A2|Action Verbs|de:singen;en:to sing;es:cantar;fr:chanter;it:cantare;ja:歌う;ko:노래하다;pt:cantar;ru:петь;uk:співати;zh:唱歌
to dance|verb|A2|Action Verbs|de:tanzen;en:to dance;es:bailar;fr:danser;it:ballare;ja:踊る;ko:춤추다;pt:dançar;ru:танцевать;uk:танцювати;zh:跳舞
to smile|verb|A2|Thoughts & Emotions|de:lächeln;en:to smile;es:sonreír;fr:sourire;it:sorridere;ja:微笑む;ko:미소 짓다;pt:sorrir;ru:улыбаться;uk:посміхатися;zh:微笑
to laugh|verb|A2|Thoughts & Emotions|de:lachen;en:to laugh;es:reír;fr:rire;it:ridere;ja:笑う;ko:웃다;pt:rir;ru:смеяться;uk:сміятися;zh:笑
to cry|verb|A2|Thoughts & Emotions|de:weinen;en:to cry;es:llorar;fr:pleurer;it:piangere;ja:泣く;ko:울다;pt:chorar;ru:плакать;uk:плакати;zh:哭泣
to carry|verb|A2|Action Verbs|de:tragen;en:to carry;es:llevar;fr:porter;it:portare;ja:運ぶ;ko:나르다;pt:carregar;ru:носить;uk:носити;zh:搬运
to pull|verb|A2|Action Verbs|de:ziehen;en:to pull;es:tirar;fr:tirer;it:tirare;ja:引く;ko:당기다;pt:puxar;ru:тянуть;uk:тягти;zh:拉
to push|verb|A2|Action Verbs|de:drücken;en:to push;es:empujar;fr:pousser;it:spingere;ja:押す;ko:밀다;pt:empurrar;ru:толкать;uk:штовхати;zh:推
to throw|verb|A2|Action Verbs|de:werfen;en:to throw;es:lanzar;fr:lancer;it:lanciare;ja:投げる;ko:던지다;pt:jogar;ru:бросать;uk:кидати;zh:扔
to catch|verb|A2|Action Verbs|de:fangen;en:to catch;es:atrapar;fr:attraper;it:afferrare;ja:捕まえる;ko:잡다;pt:pegar;ru:ловить;uk:ловити;zh:抓住
to fly|verb|A2|Action Verbs|de:fliegen;en:to fly;es:volar;fr:voler;it:volare;ja:飛ぶ;ko:날다;pt:voar;ru:летать;uk:літати;zh:飞
to drive|verb|A2|Action Verbs|de:fahren;en:to drive;es:conducir;fr:conduire;it:guidare;ja:運転する;ko:운전하다;pt:dirigir;ru:водить;uk:керувати;zh:驾驶
to swim|verb|A2|Action Verbs|de:schwimmen;en:to swim;es:nadar;fr:nager;it:nuotare;ja:泳ぐ;ko:수영하다;pt:nadar;ru:плавать;uk:плавати;zh:游泳
to run|verb|A2|Action Verbs|de:laufen;en:to run;es:correr;fr:courir;it:correre;ja:走る;ko:달리다;pt:correr;ru:бегать;uk:бігати;zh:跑步
to fall|verb|A2|Action Verbs|de:fallen;en:to fall;es:caer;fr:tomber;it:cadere;ja:落ちる;ko:떨어지다;pt:cair;ru:падать;uk:падати;zh:跌倒
to stand|verb|A2|Action Verbs|de:stehen;en:to stand;es:estar de pie;fr:être debout;it:stare in piedi;ja:立つ;ko:서다;pt:ficar de pé;ru:стоять;uk:стояти;zh:站立
to sit|verb|A2|Action Verbs|de:sitzen;en:to sit;es:sentarse;fr:s'asseoir;it:sedersi;ja:座る;ko:앉다;pt:sentar-se;ru:сидеть;uk:сидіти;zh:坐
memory|noun|B1|Thoughts & Emotions|de:die Erinnerung;en:memory;es:el recuerdo;fr:le souvenir;it:il ricordo;ja:記憶;ko:기억;pt:a lembrança;ru:память;uk:пам'ять;zh:记忆
habit|noun|B1|Everyday Life|de:die Gewohnheit;en:habit;es:el hábito;fr:l'habitude;it:l'abitudine;ja:習慣;ko:습관;pt:o hábito;ru:привычка;uk:звичка;zh:习惯
progress|noun|B1|Professional & Tech|de:der Fortschritt;en:progress;es:el progreso;fr:le progrès;it:il progresso;ja:進歩;ko:진보;pt:o progresso;ru:прогресс;uk:прогрес;zh:进步
danger|noun|B1|Everyday Life|de:die Gefahr;en:danger;es:el peligro;fr:le danger;it:il pericolo;ja:危険;ko:위험;pt:o perigo;ru:опасность;uk:небезпека;zh:危险
attention|noun|B1|Thoughts & Emotions|de:die Aufmerksamkeit;en:attention;es:la atención;fr:l'attention;it:l'attenzione;ja:注意;ko:주의;pt:a atenção;ru:внимание;uk:увага;zh:注意
difference|noun|B1|Thoughts & Emotions|de:der Unterschied;en:difference;es:la diferencia;fr:la différence;it:la differenza;ja:違い;ko:차이;pt:a diferença;ru:различие;uk:різниця;zh:差异
community|noun|B1|Society & Culture|de:die Gemeinschaft;en:community;es:la comunidad;fr:la communauté;it:la comunità;ja:共同体;ko:공동체;pt:a comunidade;ru:сообщество;uk:спільнота;zh:社区
quality|noun|B1|Descriptions & Quality|de:die Qualität;en:quality;es:la calidad;fr:la qualité;it:la qualità;ja:品質;ko:품질;pt:a qualidade;ru:качество;uk:якість;zh:质量
condition|noun|B1|Everyday Life|de:die Bedingung;en:condition;es:la condición;fr:la condition;it:la condizione;ja:条件;ko:조건;pt:a condição;ru:условие;uk:умова;zh:条件
choice|noun|B1|Thoughts & Emotions|de:die Wahl;en:choice;es:la elección;fr:le choix;it:la scelta;ja:選択;ko:선택;pt:a escolha;ru:выбор;uk:вибір;zh:选择
imagination|noun|B1|Thoughts & Emotions|de:die Fantasie;en:imagination;es:la imaginación;fr:l'imagination;it:l'immaginazione;ja:想像力;ko:상상력;pt:a imaginação;ru:воображение;uk:уява;zh:想象力
attitude|noun|B1|Thoughts & Emotions|de:die Einstellung;en:attitude;es:la actitud;fr:l'attitude;it:l'atteggiamento;ja:態度;ko:태도;pt:a atitude;ru:отношение;uk:ставлення;zh:态度
purpose|noun|B1|Thoughts & Emotions|de:der Zweck;en:purpose;es:el propósito;fr:le but;it:lo scopo;ja:目的;ko:목적;pt:o propósito;ru:цель;uk:мета;zh:目的
curious|adjective|B1|Thoughts & Emotions|de:neugierig;en:curious;es:curioso;fr:curieux;it:curioso;ja:好奇心旺盛な;ko:호기심 많은;pt:curioso;ru:любопытный;uk:допитливий;zh:好奇的
polite|adjective|B1|Society & Culture|de:höflich;en:polite;es:educado;fr:poli;it:educato;ja:丁寧な;ko:공손한;pt:educado;ru:вежливый;uk:ввічливий;zh:礼貌的
fair|adjective|B1|Society & Culture|de:fair;en:fair;es:justo;fr:juste;it:giusto;ja:公平な;ko:공정한;pt:justo;ru:справедливый;uk:справедливий;zh:公平的
active|adjective|B1|Everyday Life|de:aktiv;en:active;es:activo;fr:actif;it:attivo;ja:積極的な;ko:활동적인;pt:ativo;ru:активный;uk:активний;zh:积极的
serious|adjective|B1|Thoughts & Emotions|de:ernst;en:serious;es:serio;fr:sérieux;it:serio;ja:真面目な;ko:심각한;pt:sério;ru:серьёзный;uk:серйозний;zh:严肃的
positive|adjective|B1|Thoughts & Emotions|de:positiv;en:positive;es:positivo;fr:positif;it:positivo;ja:前向きな;ko:긍정적인;pt:positivo;ru:позитивный;uk:позитивний;zh:积极的
negative|adjective|B1|Thoughts & Emotions|de:negativ;en:negative;es:negativo;fr:négatif;it:negativo;ja:否定的な;ko:부정적인;pt:negativo;ru:негативный;uk:негативний;zh:消极的
suitable|adjective|B1|Descriptions & Quality|de:geeignet;en:suitable;es:adecuado;fr:approprié;it:adatto;ja:適切な;ko:적합한;pt:adequado;ru:подходящий;uk:підходящий;zh:合适的
rarely|adverb|B1|Everyday Life|de:selten;en:rarely;es:raramente;fr:rarement;it:raramente;ja:滅多に;ko:드물게;pt:raramente;ru:редко;uk:рідко;zh:很少
perfectly|adverb|B1|Descriptions & Quality|de:perfekt;en:perfectly;es:perfectamente;fr:parfaitement;it:perfettamente;ja:完璧に;ko:완벽하게;pt:perfeitamente;ru:идеально;uk:ідеально;zh:完美地
recently|adverb|B1|Everyday Life|de:kürzlich;en:recently;es:recientemente;fr:récemment;it:recentemente;ja:最近;ko:최근에;pt:recentemente;ru:недавно;uk:нещодавно;zh:最近
naturally|adverb|B1|Everyday Life|de:natürlich;en:naturally;es:naturalmente;fr:naturellement;it:naturalmente;ja:当然;ko:자연스럽게;pt:naturalmente;ru:естественно;uk:природно;zh:自然地
structure|noun|B2|Professional & Tech|de:die Struktur;en:structure;es:la estructura;fr:la structure;it:la struttura;ja:構造;ko:구조;pt:a estrutura;ru:структура;uk:структура;zh:结构
debate|noun|B2|Society & Culture|de:die Debatte;en:debate;es:el debate;fr:le débat;it:il dibattito;ja:討論;ko:토론;pt:o debate;ru:дебаты;uk:дебати;zh:辩论
analysis|noun|B2|Professional & Tech|de:die Analyse;en:analysis;es:el análisis;fr:l'analyse;it:l'analisi;ja:分析;ko:분석;pt:a análise;ru:анализ;uk:аналіз;zh:分析
"""

    private const val CHUNK_4 = """
competence|noun|B2|Professional & Tech|de:die Kompetenz;en:competence;es:la competencia;fr:la compétence;it:la competenza;ja:能力;ko:역량;pt:a competência;ru:компетентность;uk:компетентність;zh:能力
criteria|noun|B2|Professional & Tech|de:das Kriterium;en:criteria;es:el criterio;fr:le critère;it:il criterio;ja:基準;ko:기준;pt:o critério;ru:критерий;uk:критерій;zh:标准
argument|noun|B2|Thoughts & Emotions|de:das Argument;en:argument;es:el argumento;fr:l'argument;it:l'argomento;ja:論点;ko:논거;pt:o argumento;ru:аргумент;uk:аргумент;zh:论据
hypothesis|noun|B2|Professional & Tech|de:die Hypothese;en:hypothesis;es:la hipótesis;fr:l'hypothèse;it:l'ipotesi;ja:仮説;ko:가설;pt:a hipótese;ru:гипотеза;uk:гіпотеза;zh:假设
priority|noun|B2|Professional & Tech|de:die Priorität;en:priority;es:la prioridad;fr:la priorité;it:la priorità;ja:優先順位;ko:우선순위;pt:a prioridade;ru:приоритет;uk:пріоритет;zh:优先事项
principle|noun|B2|Thoughts & Emotions|de:das Prinzip;en:principle;es:el principio;fr:le principe;it:il principio;ja:原則;ko:원칙;pt:o princípio;ru:принцип;uk:принцип;zh:原则
initiative|noun|B2|Society & Culture|de:die Initiative;en:initiative;es:la iniciativa;fr:l'initiative;it:l'iniziativa;ja:主導権;ko:주도권;pt:a iniciativa;ru:инициатива;uk:ініціатива;zh:倡议
perception|noun|B2|Thoughts & Emotions|de:die Wahrnehmung;en:perception;es:la percepción;fr:la perception;it:la percezione;ja:知覚;ko:인식;pt:a percepção;ru:восприятие;uk:сприйняття;zh:知觉
tension|noun|B2|Society & Culture|de:die Spannung;en:tension;es:la tensión;fr:la tension;it:la tensione;ja:緊張;ko:긴장;pt:a tensão;ru:напряжение;uk:напруження;zh:紧张
aspect|noun|B2|Thoughts & Emotions|de:der Aspekt;en:aspect;es:el aspecto;fr:l'aspect;it:l'aspetto;ja:側面;ko:측면;pt:o aspecto;ru:аспект;uk:аспект;zh:方面
correlation|noun|B2|Professional & Tech|de:der Zusammenhang;en:correlation;es:la correlación;fr:la corrélation;it:la correlazione;ja:相関関係;ko:상관관계;pt:a correlação;ru:взаимосвязь;uk:взаємозв'язок;zh:相关性
interaction|noun|B2|Society & Culture|de:die Interaktion;en:interaction;es:la interacción;fr:l'interaction;it:l'interazione;ja:相互作用;ko:상호작용;pt:a interação;ru:взаимодействие;uk:взаємодія;zh:互动
obligation|noun|B2|Society & Culture|de:die Verpflichtung;en:obligation;es:la obligación;fr:l'obligation;it:l'obbligo;ja:義務;ko:의무;pt:a obrigação;ru:обязательство;uk:зобов'язання;zh:义务
dynamic|noun|B2|Professional & Tech|de:die Dynamik;en:dynamic;es:la dinámica;fr:la dynamique;it:la dinamica;ja:ダイナミクス;ko:역학;pt:a dinâmica;ru:динамика;uk:динаміка;zh:动态
transition|noun|B2|Professional & Tech|de:der Übergang;en:transition;es:la transición;fr:la transition;it:la transizione;ja:移行;ko:과도기;pt:a transição;ru:переход;uk:перехід;zh:过渡
complexity|noun|B2|Professional & Tech|de:die Komplexität;en:complexity;es:la complejidad;fr:la complexité;it:la complessità;ja:複雑さ;ko:복잡성;pt:a complexidade;ru:сложность;uk:складність;zh:复杂性
scope|noun|B2|Professional & Tech|de:der Umfang;en:scope;es:el alcance;fr:la portée;it:l'ambito;ja:範囲;ko:범위;pt:o escopo;ru:масштаб;uk:обсяг;zh:范围
framework|noun|B2|Professional & Tech|de:der Rahmen;en:framework;es:el marco;fr:le cadre;it:il quadro;ja:枠組み;ko:체계;pt:o quadro;ru:структура;uk:рамки;zh:框架
impact|noun|B2|Thoughts & Emotions|de:die Auswirkung;en:impact;es:el impacto;fr:l'impact;it:l'impatto;ja:影響;ko:영향;pt:o impacto;ru:влияние;uk:вплив;zh:影响
diversity|noun|B2|Society & Culture|de:die Vielfalt;en:diversity;es:la diversidad;fr:la diversité;it:la diversità;ja:多様性;ko:다양성;pt:a diversidade;ru:разнообразие;uk:різноманітність;zh:多样性
equilibrium|noun|B2|Nature & Environment|de:das Gleichgewicht;en:equilibrium;es:el equilibrio;fr:l'équilibre;it:l'equilibrio;ja:均衡;ko:균형;pt:o equilíbrio;ru:равновесие;uk:рівновага;zh:平衡
infrastructure|noun|B2|Professional & Tech|de:die Infrastruktur;en:infrastructure;es:la infraestructura;fr:l'infrastructure;it:l'infrastruttura;ja:社会基盤;ko:기반 시설;pt:a infraestrutura;ru:инфраструктура;uk:інфраструктура;zh:基础设施
credibility|noun|B2|Thoughts & Emotions|de:die Glaubwürdigkeit;en:credibility;es:la credibilidad;fr:la crédibilité;it:la credibilità;ja:信頼性;ko:신뢰도;pt:a credibilidade;ru:достоверность;uk:достовірність;zh:公信力
thorough|adjective|B2|Descriptions & Quality|de:gründlich;en:thorough;es:minucioso;fr:minutieux;it:minuzioso;ja:徹底的な;ko:철저한;pt:minucioso;ru:тщательный;uk:ретельний;zh:透彻的
distinct|adjective|B2|Descriptions & Quality|de:ausgeprägt;en:distinct;es:distinto;fr:distinct;it:distinto;ja:明確な;ko:뚜렷한;pt:distinto;ru:отчетливый;uk:виразний;zh:明显的
transparent|adjective|B2|Society & Culture|de:transparent;en:transparent;es:transparente;fr:transparent;it:trasparente;ja:透明な;ko:투명한;pt:transparente;ru:прозрачный;uk:прозорий;zh:透明的
remarkable|adjective|B2|Descriptions & Quality|de:bemerkenswert;en:remarkable;es:notable;fr:remarquable;it:notevole;ja:注目すべき;ko:주목할 만한;pt:notável;ru:замечательный;uk:чудовий;zh:引人注目的
consistent|adjective|B2|Descriptions & Quality|de:beständig;en:consistent;es:coherente;fr:cohérent;it:coerente;ja:一貫した;ko:일관된;pt:coerente;ru:последовательный;uk:послідовний;zh:始终如一的
objective|adjective|B2|Thoughts & Emotions|de:objektiv;en:objective;es:objetivo;fr:objectif;it:obiettivo;ja:客観的な;ko:객관적인;pt:objetivo;ru:объективный;uk:об'єктивний;zh:客观的
subjective|adjective|B2|Thoughts & Emotions|de:subjektiv;en:subjective;es:subjetivo;fr:subjectif;it:soggettivo;ja:主観的な;ko:주관적인;pt:subjetivo;ru:субъективный;uk:суб'єктивний;zh:主观的
crucial|adjective|B2|Descriptions & Quality|de:entscheidend;en:crucial;es:crucial;fr:crucial;it:cruciale;ja:決定的な;ko:결정적인;pt:crucial;ru:решающий;uk:вирішальний;zh:至关重要的
substantial|adjective|B2|Descriptions & Quality|de:erheblich;en:substantial;es:sustancial;fr:substantiel;it:sostanziale;ja:実質的な;ko:상당한;pt:substancial;ru:существенный;uk:істотний;zh:实质性的
rigorous|adjective|B2|Professional & Tech|de:streng;en:rigorous;es:riguroso;fr:rigoureux;it:rigoroso;ja:厳格な;ko:엄격한;pt:rigoroso;ru:строгий;uk:суворий;zh:严密的
versatile|adjective|B2|Descriptions & Quality|de:vielseitig;en:versatile;es:versátil;fr:polyvalent;it:versatile;ja:多才な;ko:다재다능한;pt:versátil;ru:универсальный;uk:універсальний;zh:多才多艺的
intricate|adjective|B2|Descriptions & Quality|de:verschlungen;en:intricate;es:intrincado;fr:complexe;it:intricato;ja:入り組んだ;ko:복잡한;pt:intrincado;ru:запутанный;uk:заплутаний;zh:错综复杂的
precisely|adverb|B2|Descriptions & Quality|de:genau;en:precisely;es:precisamente;fr:précisément;it:precisamente;ja:正確に;ko:정확히;pt:precisamente;ru:точно;uk:точно;zh:精确地
premise|noun|C1|Thoughts & Emotions|de:die Prämisse;en:premise;es:la premisa;fr:la prémisse;it:la premessa;ja:前提;ko:전제;pt:a premissa;ru:предпосылка;uk:передумова;zh:前提
rhetoric|noun|C1|Society & Culture|de:die Rhetorik;en:rhetoric;es:la retórica;fr:la rhétorique;it:la retorica;ja:修辞学;ko:수사학;pt:a retórica;ru:риторика;uk:риторика;zh:修辞
integrity|noun|C1|Thoughts & Emotions|de:die Integrität;en:integrity;es:la integridad;fr:l'intégrité;it:l'integrità;ja:高潔;ko:진실성;pt:a integridade;ru:честность;uk:чесність;zh:正直/完整
legacy|noun|C1|Society & Culture|de:das Erbe;en:legacy;es:el legado;fr:le legs;it:l'eredità;ja:遺産;ko:유산;pt:o legado;ru:наследие;uk:спадщина;zh:遗产
sovereignty|noun|C1|Society & Culture|de:die Souveränität;en:sovereignty;es:la soberanía;fr:la souveraineté;it:la sovranità;ja:主権;ko:주권;pt:a soberania;ru:суверенитет;uk:суверенітет;zh:主权
catalyst|noun|C1|Nature & Environment|de:der Katalysator;en:catalyst;es:el catalizador;fr:le catalyseur;it:il catalizzatore;ja:触媒;ko:촉매;pt:o catalisador;ru:катализатор;uk:каталізатор;zh:催化剂
zenith|noun|C1|Nature & Environment|de:der Zenit;en:zenith;es:el cenit;fr:le zénith;it:lo zenit;ja:頂点;ko:정점;pt:o zênite;ru:зенит;uk:зеніт;zh:顶点
fragile|adjective|C1|Descriptions & Quality|de:zerbrechlich;en:fragile;es:frágil;fr:fragile;it:fragile;ja:壊れやすい;ko:취약한;pt:frágil;ru:хрупкий;uk:крихкий;zh:脆弱的
volatile|adjective|C1|Descriptions & Quality|de:flüchtig;en:volatile;es:volátil;fr:volatil;it:volatile;ja:変動しやすい;ko:휘발성의;pt:volátil;ru:изменчивый;uk:мінливий;zh:挥发性/不稳定的
tangible|adjective|C1|Descriptions & Quality|de:greifbar;en:tangible;es:tangible;fr:tangible;it:tangibile;ja:明白な;ko:유형의;pt:tangível;ru:осязаемый;uk:відчутний;zh:有形的
implicit|adjective|C1|Thoughts & Emotions|de:implizit;en:implicit;es:implícito;fr:implicite;it:implicito;ja:暗黙の;ko:암시적인;pt:implícito;ru:подразумеваемый;uk:неявний;zh:含蓄的
explicit|adjective|C1|Thoughts & Emotions|de:explizit;en:explicit;es:explícito;fr:explicite;it:esplicito;ja:明白な;ko:명시적인;pt:explícito;ru:явный;uk:явний;zh:明确的
exhaustive|adjective|C1|Descriptions & Quality|de:erschöpfend;en:exhaustive;es:exhaustivo;fr:exhaustif;it:esaustivo;ja:徹底的な;ko:철저한;pt:exaustivo;ru:исчерпывающий;uk:вичерпний;zh:详尽的
multifaceted|adjective|C1|Descriptions & Quality|de:facettenreich;en:multifaceted;es:multifacético;fr:aux multiples facettes;it:sfaccettato;ja:多面的な;ko:다면적인;pt:multifacetado;ru:многогранный;uk:багатогранний;zh:多层面的
perpetual|adjective|C1|Descriptions & Quality|de:ewig;en:perpetual;es:perpetuo;fr:perpétuel;it:perpetuo;ja:永続的な;ko:영구적인;pt:perpétuo;ru:вечный;uk:вічний;zh:永恒的
virtually|adverb|C1|Everyday Life|de:praktisch;en:virtually;es:virtualmente;fr:virtuellement;it:virtualmente;ja:実質的に;ko:사실상;pt:virtualmente;ru:практически;uk:практично;zh:实际上
merely|adverb|C1|Everyday Life|de:lediglich;en:merely;es:simplemente;fr:simplement;it:semplicemente;ja:単に;ko:단지;pt:meramente;ru:всего лишь;uk:лише;zh:仅仅
exquisitely|adverb|C1|Descriptions & Quality|de:vorzüglich;en:exquisitely;es:exquisitamente;fr:exquise;it:squisitamente;ja:見事に;ko:정교하게;pt:exquisitamente;ru:изысканно;uk:вишукано;zh:精美地
causality|noun|C2|Thoughts & Emotions|de:die Kausalität;en:causality;es:la causalidad;fr:la causalité;it:la causalità;ja:因果関係;ko:인과관계;pt:a causalidade;ru:причинность;uk:причинність;zh:因果关系
aesthetics|noun|C2|Society & Culture|de:die Ästhetik;en:aesthetics;es:la estética;fr:l'esthétique;it:l'estetica;ja:美学;ko:미학;pt:a estética;ru:эстетика;uk:естетика;zh:美学
quintessence|noun|C2|Thoughts & Emotions|de:die Quintessenz;en:quintessence;es:la quintaesencia;fr:la quintessence;it:la quintessenza;ja:真髄;ko:정수;pt:a quintessência;ru:квинтэссенция;uk:квінтесенція;zh:精髓
discourse|noun|C2|Society & Culture|de:der Diskurs;en:discourse;es:el discurso;fr:le discours;it:il discorso;ja:言説;ko:담론;pt:o discurso;ru:дискурс;uk:дискурс;zh:话语/论述
juxtaposition|noun|C2|Descriptions & Quality|de:die Gegenüberstellung;en:juxtaposition;es:la yuxtaposición;fr:la juxtaposition;it:la contrapposizione;ja:並置;ko:병치;pt:a justaposição;ru:сопоставление;uk:зіставлення;zh:并列
veracity|noun|C2|Thoughts & Emotions|de:die Wahrhaftigkeit;en:veracity;es:la veracidad;fr:la véracité;it:la veridicità;ja:真実性;ko:진실성;pt:a veracidade;ru:достоверность;uk:правдивість;zh:真实性
oblivion|noun|C2|Thoughts & Emotions|de:das Vergessen;en:oblivion;es:el olvido;fr:l'oubli;it:l'oblio;ja:忘却;ko:망각;pt:o esquecimento;ru:забвение;uk:забуття;zh:遗忘
lucid|adjective|C2|Thoughts & Emotions|de:klar;en:lucid;es:lúcido;fr:lucide;it:lucido;ja:明快な;ko:명료한;pt:lúcido;ru:ясный;uk:ясний;zh:清晰的
paramount|adjective|C2|Descriptions & Quality|de:überragend;en:paramount;es:primordial;fr:primordial;it:primordiale;ja:最高の;ko:가장 중요한;pt:primordial;ru:первостепенный;uk:першорядний;zh:至高无上的
indomitable|adjective|C2|Thoughts & Emotions|de:unbezwingbar;en:indomitable;es:indomable;fr:indomptable;it:indomabile;ja:不屈の;ko:불굴의;pt:indomável;ru:неукротимый;uk:непохитний;zh:不屈的
immaculate|adjective|C2|Descriptions & Quality|de:tadellos;en:immaculate;es:inmaculado;fr:immaculé;it:immacolato;ja:汚れのない;ko:결점 없는;pt:imaculado;ru:безупречный;uk:бездоганний;zh:纯洁无瑕的
ubiquitous|adjective|C2|Descriptions & Quality|de:allgegenwärtig;en:ubiquitous;es:ubicuo;fr:omniprésent;it:onnipresenziale;ja:偏在する;ko:어디에나 있는;pt:onipresente;ru:вездесущий;uk:всюдисущий;zh:无处不在的
infallible|adjective|C2|Descriptions & Quality|de:unfehlbar;en:infallible;es:infalible;fr:infaillible;it:infallibile;ja:誤りのない;ko:틀림없는;pt:infalível;ru:непогрешимый;uk:безпомилковий;zh:绝不犯错的
pristine|adjective|C2|Descriptions & Quality|de:makellos;en:pristine;es:prístino;fr:immaculé;it:incontaminato;ja:素朴な;ko:원시 그대로의;pt:impecável;ru:первозданный;uk:незайманий;zh:原始纯洁的
arguably|adverb|C2|Thoughts & Emotions|de:wohl;en:arguably;es:posiblemente;fr:sans doute;it:probabilmente;ja:おそらく;ko:거의 틀림없이;pt:provavelmente;ru:возможно;uk:можливо;zh:可以认为是
incontrovertibly|adverb|C2|Thoughts & Emotions|de:unwiderlegbar;en:incontrovertibly;es:incontrovertiblemente;fr:irréfutablement;it:incontrovertibilmente;ja:議論の余地なく;ko:명백히;pt:incontrovertivelmente;ru:неопровержимо;uk:незаперечно;zh:无可争议地
music player|noun|A2|Everyday Life|de:der Musikspieler;en:music player;es:el reproductor;fr:le lecteur de musique;it:il lettore musicale;ja:プレーヤー;ko:음악 플레이어;pt:o reprodutor;ru:музыкальный плеер;uk:музичний плеєр;zh:播放器
camera|noun|A2|Everyday Life|de:die Kamera;en:camera;es:la cámara;fr:l'appareil photo;it:la fotocamera;ja:カメラ;ko:카메라;pt:a câmera;ru:фотоаппарат;uk:камера;zh:照相机
screen|noun|A2|Professional & Tech|de:der Bildschirm;en:screen;es:la pantalla;fr:l'écran;it:lo schermo;ja:画面;ko:화면;pt:a tela;ru:экран;uk:екран;zh:屏幕
keyboard|noun|A2|Professional & Tech|de:die Tastatur;en:keyboard;es:el teclado;fr:le clavier;it:la tastiera;ja:キーボード;ko:키보드;pt:o teclado;ru:клавиатура;uk:клавіатура;zh:键盘
mouse|noun|A2|Professional & Tech|de:die Maus;en:mouse;es:el ratón;fr:la souris;it:il mouse;ja:マウス;ko:마우스;pt:o mouse;ru:мышь;uk:миша;zh:鼠标
library|noun|A2|Society & Culture|de:die Bibliothek;en:library;es:la biblioteca;fr:la bibliothèque;it:la biblioteca;ja:図書館;ko:도서관;pt:a biblioteca;ru:библиотека;uk:бібліотека;zh:图书馆
museum|noun|A2|Society & Culture|de:das Museum;en:museum;es:el museo;fr:le musée;it:il museo;ja:博物館;ko:박물관;pt:o museu;ru:музей;uk:музей;zh:博物馆
theater|noun|A2|Society & Culture|de:das Theater;en:theater;es:el teatro;fr:le théâtre;it:il teatro;ja:劇場;ko:극장;pt:o teatro;ru:театр;uk:театр;zh:剧院
cinema|noun|A2|Society & Culture|de:das Kino;en:cinema;es:el cine;fr:le cinéma;it:il cinema;ja:映画館;ko:영화관;pt:o cinema;ru:кинотеатр;uk:кінотеатр;zh:电影院
pharmacy|noun|A2|Everyday Life|de:die Apotheke;en:pharmacy;es:la farmacia;fr:la pharmacie;it:la farmacia;ja:薬局;ko:약국;pt:a farmácia;ru:аптека;uk:аптека;zh:药店
bank|noun|A2|Everyday Life|de:die Bank;en:bank;es:el banco;fr:la banque;it:la banca;ja:銀行;ko:은행;pt:o banco;ru:банк;uk:банк;zh:银行
post office|noun|A2|Everyday Life|de:die Post;en:post office;es:la oficina de correos;fr:la poste;it:l'ufficio postale;ja:郵便局;ko:우체국;pt:os correios;ru:почта;uk:пошта;zh:邮局
police|noun|A2|Society & Culture|de:die Polizei;en:police;es:la policía;fr:la police;it:la polizia;ja:警察;ko:경찰;pt:a polícia;ru:полиция;uk:поліція;zh:警察
fire station|noun|A2|Society & Culture|de:die Feuerwehr;en:fire station;es:los bomberos;fr:les pompiers;it:i vigili del fuoco;ja:消防署;ko:소방서;pt:os bombeiros;ru:пожарная служба;uk:пожежна служба;zh:消防局
bridge|noun|A2|Travel & Exploration|de:die Brücke;en:bridge;es:el puente;fr:le pont;it:il ponte;ja:橋;ko:다리;pt:a ponte;ru:мост;uk:міст;zh:桥梁
park|noun|A2|Nature & Environment|de:der Park;en:park;es:el parque;fr:le parc;it:il parco;ja:公園;ko:공원;pt:o parque;ru:парк;uk:парк;zh:公园
lake|noun|A2|Nature & Environment|de:der See;en:lake;es:el lago;fr:le lac;it:il lago;ja:湖;ko:호수;pt:o lago;ru:озеро;uk:озеро;zh:湖泊
river|noun|A2|Nature & Environment|de:der Fluss;en:river;es:el río;fr:le fleuve;it:il fiume;ja:川;ko:강;pt:o rio;ru:река;uk:річка;zh:河流
forest|noun|A2|Nature & Environment|de:der Wald;en:forest;es:el bosque;fr:la forêt;it:la foresta;ja:森;ko:숲;pt:a floresta;ru:лес;uk:ліс;zh:森林
village|noun|A2|Travel & Exploration|de:das Dorf;en:village;es:el pueblo;fr:le village;it:il villaggio;ja:村;ko:마을;pt:a vila;ru:деревня;uk:село;zh:村庄
building|noun|A2|Travel & Exploration|de:das Gebäude;en:building;es:el edificio;fr:le bâtiment;it:l'edificio;ja:建物;ko:건물;pt:o edifício;ru:здание;uk:будівля;zh:建筑物
square|noun|A2|Travel & Exploration|de:der Platz;en:square;es:la plaza;fr:la place;it:la piazza;ja:広場;ko:광장;pt:a praça;ru:площадь;uk:площа;zh:广场
corner|noun|A2|Travel & Exploration|de:die Ecke;en:corner;es:la esquina;fr:le coin;it:l'angolo;ja:角;ko:모퉁이;pt:a esquina;ru:угол;uk:кут;zh:角落
map|noun|A2|Travel & Exploration|de:die Karte;en:map;es:el mapa;fr:la carte;it:la mappa;ja:地図;ko:지도;pt:o mapa;ru:карта;uk:карта;zh:地图
passport|noun|A2|Travel & Exploration|de:der Reisepass;en:passport;es:el pasaporte;fr:le passeport;it:il passaporto;ja:パスポート;ko:여권;pt:o passaporte;ru:паспорт;uk:паспорт;zh:护照
to reserve|verb|A2|Travel & Exploration|de:reservieren;en:to reserve;es:reservar;fr:réserver;it:prenotare;ja:予約する;ko:예약하다;pt:reservar;ru:бронировать;uk:бронювати;zh:预订
to confirm|verb|A2|Professional & Tech|de:bestätigen;en:to confirm;es:confirmar;fr:confirmer;it:confermare;ja:確認する;ko:확인하다;pt:confirmar;ru:подтверждать;uk:підтверджувати;zh:确认
to cancel|verb|A2|Everyday Life|de:stornieren;en:to cancel;es:cancelar;fr:annuler;it:cancellare;ja:キャンセルする;ko:취소하다;pt:cancelar;ru:отменять;uk:скасовувати;zh:取消
to celebrate|verb|A2|Society & Culture|de:feiern;en:to celebrate;es:celebrar;fr:célébrer;it:festeggiare;ja:祝う;ko:축하하다;pt:celebrar;ru:праздновать;uk:святкувати;zh:庆祝
to recommend|verb|B1|Society & Culture|de:empfehlen;en:to recommend;es:recomendar;fr:recommander;it:raccomandare;ja:勧める;ko:추천하다;pt:recomendar;ru:рекомендовать;uk:рекомендувати;zh:推荐
"""

    private const val CHUNK_5 = """
to complain|verb|B1|Everyday Life|de:beschweren;en:to complain;es:quejarse;fr:se plaindre;it:lamentarsi;ja:苦情を言う;ko:불평하다;pt:reclamar;ru:жаловаться;uk:скаржитися;zh:抱怨
to replace|verb|B1|Action Verbs|de:ersetzen;en:to replace;es:reemplazar;fr:remplacer;it:sostituire;ja:置き換える;ko:대체하다;pt:substituir;ru:заменять;uk:замінювати;zh:替换
to belong|verb|B1|Thoughts & Emotions|de:gehören;en:to belong;es:pertenecer;fr:appartenir;it:appartenere;ja:属する;ko:속하다;pt:pertencer;ru:принадлежать;uk:належати;zh:属于
"""

    private val RAW_CHUNKS = listOf(CHUNK_0, CHUNK_1, CHUNK_2, CHUNK_3, CHUNK_4, CHUNK_5)

}
