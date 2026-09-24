package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

data class WordGrammarInfo(
    val partOfSpeech: String,
    val partOfSpeechLabel: String,
    val cefrLevel: String,
    val cefrLabel: String,
    val shortSummary: String,
    val morphologyRule: String,
    val syntaxUsageRule: String,
    val levelGuidance: String,
    val grammarTip: String
)

object VocabularyGrammarHelper {

    fun getGrammarInfo(word: VocabularyWordEntity, nativeLangCode: String): WordGrammarInfo {
        val lang = nativeLangCode.lowercase()
        val pos = word.partOfSpeech.lowercase()
        val cefr = word.cefrLevel.uppercase()
        val targetLang = word.languageCode.lowercase()

        val posLabel = getPartOfSpeechLabel(pos, lang)
        val cefrTitle = getCefrTitle(cefr, lang)
        val cefrGuide = getCefrGuidance(cefr, lang)
        val morph = getMorphologyGuidance(word, targetLang, pos, lang)
        val syntax = getSyntaxGuidance(word, targetLang, pos, lang)
        val tip = getPracticalTip(word, targetLang, pos, cefr, lang)
        val summary = getShortSummary(word, targetLang, pos, cefr, lang)

        return WordGrammarInfo(
            partOfSpeech = pos,
            partOfSpeechLabel = posLabel,
            cefrLevel = cefr,
            cefrLabel = cefrTitle,
            shortSummary = summary,
            morphologyRule = morph,
            syntaxUsageRule = syntax,
            levelGuidance = cefrGuide,
            grammarTip = tip
        )
    }

    private fun getPartOfSpeechLabel(pos: String, lang: String): String {
        return when (pos) {
            "verb" -> when (lang) {
                "de" -> "Verb (Tätigkeitswort / Prädikat)"
                "en" -> "Verb (Action word / Predicate)"
                "es" -> "Verbo (Acción / Predicado)"
                "fr" -> "Verbe (Action / Prédicat)"
                "it" -> "Verbo (Azione / Predicato)"
                "pt" -> "Verbo (Ação / Predicado)"
                "ru" -> "Глагол (Действие / Сказуемое)"
                "uk" -> "Дієслово (Дія / Присудок)"
                "zh" -> "动词 (动作 / 谓语)"
                "ja" -> "動詞 (動作 / 述語)"
                "ko" -> "동사 (동작 / 서술어)"
                else -> "Verb"
            }
            "noun" -> when (lang) {
                "de" -> "Substantiv / Nomen (Hauptwort)"
                "en" -> "Noun (Substantive / Subject-Object)"
                "es" -> "Sustantivo / Nombre (Sujeto / Objeto)"
                "fr" -> "Nom / Substantif (Sujet / Complément)"
                "it" -> "Sostantivo / Nome (Soggetto / Complemento)"
                "pt" -> "Substantivo / Nome (Sujeito / Objeto)"
                "ru" -> "Имя существительное (Предмет / Понятие)"
                "uk" -> "Іменник (Предмет / Поняття)"
                "zh" -> "名词 (实体 / 概念)"
                "ja" -> "名詞 (実体 / 概念)"
                "ko" -> "명사 (개념 / 체언)"
                else -> "Noun"
            }
            "adjective" -> when (lang) {
                "de" -> "Adjektiv (Eigenschaftswort)"
                "en" -> "Adjective (Modifier / Descriptive)"
                "es" -> "Adjetivo (Calificativo / Descriptivo)"
                "fr" -> "Adjectif (Qualificatif)"
                "it" -> "Aggettivo (Qualificativo)"
                "pt" -> "Adjetivo (Qualificativo)"
                "ru" -> "Имя прилагательное (Признак / Свойство)"
                "uk" -> "Прикметник (Ознака предмета)"
                "zh" -> "形容词 (修饰语 / 性质)"
                "ja" -> "形容詞 (修飾語 / 状態)"
                "ko" -> "형용사 (상태 / 수식어)"
                else -> "Adjective"
            }
            "adverb" -> when (lang) {
                "de" -> "Adverb (Umstandswort)"
                "en" -> "Adverb (Circumstance / Degree modifier)"
                "es" -> "Adverbio (Circunstancial / Modo)"
                "fr" -> "Adverbe (Manière / Temps / Lieu)"
                "it" -> "Avverbio (Modo / Tempo / Luogo)"
                "pt" -> "Advérbio (Modo / Tempo / Intensidade)"
                "ru" -> "Наречие (Обстоятельство / Степень)"
                "uk" -> "Прислівник (Обставина дії)"
                "zh" -> "副词 (状语 / 程度修饰)"
                "ja" -> "副詞 (連用修飾語)"
                "ko" -> "부사 (용언 수식어)"
                else -> "Adverb"
            }
            else -> when (lang) {
                "de" -> "Redewendung / Fester Ausdruck"
                "en" -> "Phrase / Idiomatic Expression"
                "es" -> "Frase hecha / Expresión"
                "fr" -> "Expression idiomatique / Locution"
                "it" -> "Espressione idiomatica / Frase fatta"
                "pt" -> "Expressão idiomática / Locução"
                "ru" -> "Идиоматическое выражение / Фразеологизм"
                "uk" -> "Ідіоматичний вираз / Фразеологізм"
                "zh" -> "固定表达 / 习语短语"
                "ja" -> "慣用句 / 定型表現"
                "ko" -> "관용구 / 숙어 표현"
                else -> "Phrase / Idiom"
            }
        }
    }

    private fun getCefrTitle(cefr: String, lang: String): String {
        return when (cefr) {
            "A1" -> when (lang) {
                "de" -> "A1 • Elementare Basis (Anfänger)"
                "en" -> "A1 • Foundational Essentials (Beginner)"
                "es" -> "A1 • Nivel Elemental Inicial (Principiante)"
                "fr" -> "A1 • Niveau Élémentaire (Débutant)"
                "it" -> "A1 • Livello Elementare (Principiante)"
                "pt" -> "A1 • Nível Elementar Inicial (Iniciante)"
                "ru" -> "A1 • Начальный базовый уровень"
                "uk" -> "A1 • Початковий базовий рівень"
                "zh" -> "A1 • 基础入门级别"
                "ja" -> "A1 • 入門・基礎レベル"
                "ko" -> "A1 • 입문 및 기초 레벨"
                else -> "A1 • Beginner"
            }
            "A2" -> when (lang) {
                "de" -> "A2 • Erweiterte Grundstufe (Alltag)"
                "en" -> "A2 • Everyday Fluency (Elementary)"
                "es" -> "A2 • Fluidez Cotidiana (Elemental)"
                "fr" -> "A2 • Aisance Quotidienne (Élémentaire)"
                "it" -> "A2 • Fluenza Quotidiana (Elementare)"
                "pt" -> "A2 • Fluência Cotidiana (Elementar)"
                "ru" -> "A2 • Разговорный элементарный уровень"
                "uk" -> "A2 • Розмовний елементарний рівень"
                "zh" -> "A2 • 日常会话初级水平"
                "ja" -> "A2 • 日常会話・初級レベル"
                "ko" -> "A2 • 일상 회화 초급 레벨"
                else -> "A2 • Elementary"
            }
            "B1" -> when (lang) {
                "de" -> "B1 • Selbstständige Sprachverwendung (Mittelstufe)"
                "en" -> "B1 • Conversational Competence (Intermediate)"
                "es" -> "B1 • Competencia Conversacional (Intermedio)"
                "fr" -> "B1 • Autonomie Conversationnelle (Intermédiaire)"
                "it" -> "B1 • Competenza Conversazionale (Intermedio)"
                "pt" -> "B1 • Competência Conversacional (Intermediário)"
                "ru" -> "B1 • Уверенная средняя ступень"
                "uk" -> "B1 • Середній рівень спілкування"
                "zh" -> "B1 • 独立应用中级水平"
                "ja" -> "B1 • 中級・自立した会話レベル"
                "ko" -> "B1 • 중급 실용 회화 레벨"
                else -> "B1 • Intermediate"
            }
            "B2" -> when (lang) {
                "de" -> "B2 • Fortgeschrittene Sprachbeherrschung (Fachsprache)"
                "en" -> "B2 • Professional & Academic (Upper-Intermediate)"
                "es" -> "B2 • Profesional y Académico (Intermedio Alto)"
                "fr" -> "B2 • Professionnel et Académique (Avancé)"
                "it" -> "B2 • Professionale e Accademico (Intermedio Avanzato)"
                "pt" -> "B2 • Profissional e Acadêmico (Intermediário Superior)"
                "ru" -> "B2 • Продвинутый профессиональный уровень"
                "uk" -> "B2 • Просунутий професійний рівень"
                "zh" -> "B2 • 中高级专业交流水平"
                "ja" -> "B2 • 中上級・専門的運用レベル"
                "ko" -> "B2 • 중상급 비즈니스 및 학술 레벨"
                else -> "B2 • Upper-Intermediate"
            }
            "C1" -> when (lang) {
                "de" -> "C1 • Fachkundige Sprachkenntnisse (Fortgeschritten)"
                "en" -> "C1 • Advanced Mastery & Stylistic Command"
                "es" -> "C1 • Dominio Avanzado y Estilístico"
                "fr" -> "C1 • Maîtrise Avancée et Nuances de Style"
                "it" -> "C1 • Padronanza Avanzata e Stile Raffinato"
                "pt" -> "C1 • Domínio Avançado e Estilístico"
                "ru" -> "C1 • Свободный продвинутый уровень"
                "uk" -> "C1 • Вільний просунутий рівень"
                "zh" -> "C1 • 高级精通与文体驾驭"
                "ja" -> "C1 • 上級・洗練された文体レベル"
                "ko" -> "C1 • 고급 숙달 및 문체 구사 레벨"
                else -> "C1 • Advanced"
            }
            else -> when (lang) {
                "de" -> "C2 • Annähernd muttersprachliche Perfektion"
                "en" -> "C2 • Near-Native Literary & Idiomatic Mastery"
                "es" -> "C2 • Maestría Casi Nativa e Idiomática"
                "fr" -> "C2 • Maîtrise Quasi-Native et Littéraire"
                "it" -> "C2 • Padronanza Madrelingua e Letteraria"
                "pt" -> "C2 • Maestria Quase Nativa e Literária"
                "ru" -> "C2 • Уровень свободного владения носителя"
                "uk" -> "C2 • Рівень вільного володіння носія мови"
                "zh" -> "C2 • 母语级地道精通与文学修辞"
                "ja" -> "C2 • 母語話者同等の文学・慣用熟達レベル"
                "ko" -> "C2 • 원어민 수준의 문학 및 관용 숙달 레벨"
                else -> "C2 • Native Mastery"
            }
        }
    }

    private fun getCefrGuidance(cefr: String, lang: String): String {
        return when (cefr) {
            "A1" -> when (lang) {
                "de" -> "Grundlegende Satzmuster im Präsens: Subjekt + finites Verb + Objekt. Bilden Sie einfache Aussagesätze und Ja/Nein-Fragen."
                "en" -> "Basic present-tense patterns: Subject + finite verb + object. Focus on simple affirmative sentences and yes/no questions."
                "es" -> "Estructuras básicas en presente: Sujeto + verbo conjugado + complemento. Uso en afirmaciones y preguntas cotidianas."
                "fr" -> "Schémas de base au présent : Sujet + verbe conjugué + complément. Privilégiez les affirmations simples et questions oui/non."
                "it" -> "Schemi di base al presente: Soggetto + verbo coniugato + oggetto. Frasi affermative e domande dirette."
                "pt" -> "Padrões básicos no presente: Sujeito + verbo conjugado + objeto. Frases afirmativas simples e perguntas diretas."
                "ru" -> "Базовые схемы в настоящем времени: Подлежащее + спрягаемый глагол + дополнение. Простые утверждения и вопросы."
                "uk" -> "Базові схеми теперішнього часу: Підмет + дієслово + додаток. Прості ствердження та запитання."
                "zh" -> "现在时基础句型：主语 + 谓语动词 + 宾语。适用于日常简单陈述与基础问答。"
                "ja" -> "現在形の基本構文：主語＋述語＋目的語。日常の単純な肯定文と平易な質問に使用します。"
                "ko" -> "현재 시제 기본 문형: 주어 + 목적어 + 서술어. 일상적인 단순 평서문과 기본 의문문에 적용됩니다."
                else -> "Basic present sentence structure: Subject + Verb + Object."
            }
            "A2" -> when (lang) {
                "de" -> "Erweiterte Satzverbindungen: Anwendung im Perfekt/Präteritum, Verknüpfungen mit 'weil', 'wenn' und 'dass', sowie Modalverb-Gefüge."
                "en" -> "Expanded sentence building: Past tense usage, connectives like 'because', 'when', 'if', and modal verb combinations."
                "es" -> "Construcción ampliada: Uso en pasado (pretérito / imperfecto), conectores como 'porque', 'cuando', 'si' y verbos modales."
                "fr" -> "Construction enrichie : Emploi au passé composé / imparfait, connecteurs 'parce que', 'quand', 'si' et verbes modaux."
                "it" -> "Costruzione ampliata: Uso nei tempi passati (passato prossimo / imperfetto), connettivi come 'perché', 'quando' e verbi modali."
                "pt" -> "Construção ampliada: Tempos do pretérito, conectivos como 'porque', 'quando', 'se' e verbos modais combinados."
                "ru" -> "Расширенный синтаксис: Прошедшее время, союзы 'потому что', 'когда', 'если' и связки с модальными глаголами."
                "uk" -> "Розширений синтаксис: Минулий час, сполучники 'тому що', 'коли', 'якщо' та комбінації з модальними дієсловами."
                "zh" -> "复合句扩展：过去时态表达、常用连接词（因为、当……时、如果）及情态动词搭配。"
                "ja" -> "複文の展開：過去形の運用、理由・条件の接続（〜から、〜とき、〜たら）および助動詞との組み合わせ。"
                "ko" -> "복문 확장: 과거 시제 활용, 접속사 연계(~아서/어서, ~(으)ㄹ 때, ~(으)면) 및 조동사 구문."
                else -> "Past tenses and basic subordinate clauses (because, when, if)."
            }
            "B1" -> when (lang) {
                "de" -> "Selbstständige Satzkomplexität: Relativsätze, Infinitivsätze mit 'zu/um zu', Bedingungssätze (Konjunktiv II) und indirekte Fragesätze."
                "en" -> "Substantive complexity: Relative clauses, infinitive clauses, hypothetical conditional structures, and indirect questions."
                "es" -> "Complejidad intermedia: Oraciones de relativo, construcciones de infinitivo, condicionales hipotéticos y preguntas indirectas."
                "fr" -> "Complexité intermédiaire : Propositions relatives, constructions infinitives, propositions conditionnelles et interrogations indirectes."
                "it" -> "Complessità intermedia: Proposizioni relative, costruzioni con infinito, condizionale ipotetico e domande indirette."
                "pt" -> "Complexidade intermediária: Orações relativas, construções infinitivas, estruturas condicionais e interrogações indiretas."
                "ru" -> "Самостоятельное построение: Придаточные определительные, инфинитивные обороты, условное наклонение и косвенные вопросы."
                "uk" -> "Самостійна складність: Підрядні означальні, інфінітивні звороти, умовний спосіб та непрямі запитання."
                "zh" -> "中级复合句式：定语从句、不定式结构、条件虚拟假设句及间接引语句式。"
                "ja" -> "中級複文構造：関係節による修飾、不定詞構文、仮定・条件法および間接疑問文の活用。"
                "ko" -> "중급 복문 구조: 관계절 수식, 부정사 구문, 가상 조건문 및 간접 의문문 표현."
                else -> "Relative clauses, infinitive phrases, and hypothetical conditionals."
            }
            "B2" -> when (lang) {
                "de" -> "Gehobene Syntax: Passivkonstruktionen (Zustands- und Vorgangspassiv), Partizipialgruppen, Konzessivsätze ('obwohl') und Nomen-Verb-Verbindungen."
                "en" -> "Advanced syntax: Passive voice structures, participial phrases, concessive clauses ('although'), and formal collocations."
                "es" -> "Sintaxis avanzada: Voz pasiva, construcciones de participio, oraciones concesivas ('aunque') y colocaciones léxicas formales."
                "fr" -> "Syntaxe avancée : Voix passive, participes présents et passés, propositions concessives ('bien que') et collocations soutenues."
                "it" -> "Sintassi avanzata: Forma passiva, costruzioni participiali, proposizioni concessive ('sebbene') e collocazioni formali."
                "pt" -> "Sintaxe avançada: Voz passiva, construções com particípio, orações concessivas ('embora') e colocações formais."
                "ru" -> "Продвинутый синтаксис: Страдательный залог, причастные и деепричастные обороты, уступительные придаточные и устойчивые сочетания."
                "uk" -> "Просунутий синтаксис: Пасивний стан, дієприкметникові та дієприслівникові звороти, допустові речення та стійкі словосполучення."
                "zh" -> "高阶句法结构：被动语态、分词短语、让步从句（尽管、虽然）及正式学术/职业词组搭配。"
                "ja" -> "上級構文：受動態構文、分詞構文、譲歩節（〜にもかかわらず）、改まった固定連語表現。"
                "ko" -> "상급 구문: 피동 구문, 분사 구문, 양보절(~에도 불구하고) 및 격식 있는 연어(Collocation) 표현."
                else -> "Passive voice, participial clauses, and formal connective discourse."
            }
            "C1" -> when (lang) {
                "de" -> "Stilistische Differenzierung: Inversionen zur Schwerpunktsetzung, Nominalstil, Passiversatzformen (sich lassen + Infinitiv) und feine Bedeutungsnuancen."
                "en" -> "Stylistic precision: Inversions for emphasis, nominalization, passive alternatives, and nuanced semantic shades in argumentation."
                "es" -> "Precisión estilística: Inversiones enfáticas, estilo nominal, perífrasis de pasiva y gradaciones semánticas sutiles."
                "fr" -> "Précision stylistique : Inversions d'emphase, style nominalisé, équivalents du passif et nuances sémantiques raffinées."
                "it" -> "Precisione stilistica: Inversioni per enfasi, stile nominale, alternative passive e sfumature semantiche d'alto registro."
                "pt" -> "Precisão estilística: Inversões de ênfase, estilo nominal, alternativas à voz passiva e nuances semânticas refinadas."
                "ru" -> "Стилистическая выразительность: Инверсии для логического ударения, отглагольный стиль, описательный пассив и тончайшие смысловые оттенки."
                "uk" -> "Стилістична виразність: Інверсії для емфази, віддієслівний стиль, пасивні еквіваленти та тонкі семантичні градації."
                "zh" -> "文体精妙掌控：修辞倒装强调、名词化严密风格、被动代换句式及微妙语义层次辨析。"
                "ja" -> "洗練された文体技法：強調のための倒置法、名詞化表現、受動代替構文、緻密な意味の使い分け。"
                "ko" -> "정교한 문체 기법: 강조를 위한 도치법, 명사화된 간결한 문체, 피동 대체 구문 및 섬세한 의미 구별."
                else -> "Stylistic inversion, nominal style, and nuanced discourse pragmatics."
            }
            else -> when (lang) {
                "de" -> "Muttersprachliche Virtuosität: Idiomatische Wendungen, rhetorische Stilmittel, historische Konnotationen und mühelose Registerwechsel."
                "en" -> "Near-native virtuosic control: Idiomatic mastery, rhetorical figures, literary undertones, and effortless register transitions."
                "es" -> "Virtuosismo casi nativo: Dominio idiomático pleno, recursos retóricos, resonancias literarias y cambios de registro espontáneos."
                "fr" -> "Virtuosité quasi-native : Maîtrise idiomatique complète, figures de rhétorique, résonances littéraires et fluidité absolue."
                "it" -> "Virtuosismo madrelingua: Pieno dominio idiomatico, figure retoriche, richiami letterari e naturalezza nei registri stilistici."
                "pt" -> "Virtuosimo quase nativo: Domínio idiomático pleno, recursos retóricos, ressonâncias literárias e transições de registro naturais."
                "ru" -> "Виртуозность носителя языка: Идиоматическая свобода, риторические фигуры, литературный подтекст и абсолютная гибкость регистра."
                "uk" -> "Віртуозність носія мови: Повна ідіоматична свобода, риторичні засоби, літературний підтекст та органічна зміна стилів."
                "zh" -> "母语名家级驾驭：融会贯通成语典故、高超修辞手法、文学意蕴及全语境自如切换。"
                "ja" -> "母国語最高峰の駆使：慣用成句の自在な運用、修辞技法、文学的余韻、場面に応じた完璧な語彙選択。"
                "ko" -> "원어민 최고 수준 구사: 관용구 및 사자성어의 자유자재 활용, 문학적 함의, 수사법 및 완벽한 어조 조절."
                else -> "Near-native idiomatic mastery, rhetorical devices, and literary nuance."
            }
        }
    }

    private fun getMorphologyGuidance(word: VocabularyWordEntity, targetLang: String, pos: String, lang: String): String {
        return when (targetLang) {
            "es" -> when (pos) {
                "verb" -> when (lang) {
                    "de" -> "Spanische Verben enden auf -ar, -er oder -ir. Im Präsens Indikativ folgen sie den regelmäßigen Personalendungen. Auf unregelmäßige Stammwechsel (e->ie, o->ue) achten."
                    "ru" -> "Испанские глаголы делятся на 3 спряжения: -ar, -er, -ir. В настоящем времени спрягаются по лицам; следите за дифтонгизацией в корне (e->ie, o->ue)."
                    "uk" -> "Іспанські дієслова мають закінчення -ar, -er або -ir. Відмінюються за особами; звертайте увагу на чергування голосних у корені."
                    "fr" -> "Verbe espagnol terminé en -ar, -er ou -ir. Suivre les terminaisons régulières ou vérifier les diphtongaisons (e->ie, o->ue)."
                    "it" -> "Verbo spagnolo in -ar, -er o -ir. Segue le desinenze regolari; fare attenzione ai cambi vocalici nella radice (e->ie, o->ue)."
                    "pt" -> "Verbo espanhol em -ar, -er ou -ir. Conjugação muito similar ao português; atente-se às ditongações (e->ie, o->ue)."
                    "zh" -> "西班牙语动词以-ar, -er, -ir结尾。现在时按人称变位，注意词干变音（e->ie, o->ue）。"
                    "ja" -> "スペイン語動詞（-ar, -er, -ir語尾）。人称に応じた規則活用をし、語幹母音変化（e->ie, o->ue）に注意します。"
                    "ko" -> "스페인어 동사(-ar, -er, -ir 어미). 인칭별 규칙 활용을 따르며 어간 모음 변화(e->ie, o->ue)에 유의하세요."
                    else -> "Spanish verb ending in -ar, -er, or -ir. Follows standard person endings; check for stem vowel shifts (e->ie, o->ue)."
                }
                "noun" -> when (lang) {
                    "de" -> "Substantive auf -o sind meist maskulin (el), auf -a feminin (la). Pluralbildung erfolgt regelmäßig mit -s (Vokal) oder -es (Konsonant)."
                    "ru" -> "Существительные на -o обычно мужского рода (el), на -a — женского (la). Множественное число образуется добавлением -s или -es."
                    "uk" -> "Іменники на -o зазвичай чоловічого роду (el), на -a — жіночого (la). Множина утворюється додаванням -s або -es."
                    "fr" -> "Noms en -o généralement masculins (el), en -a féminins (la). Pluriel en -s après voyelle, en -es après consonne."
                    "it" -> "Sostantivi in -o di norma maschili (el), in -a femminili (la). Il plurale si forma con -s o -es."
                    "pt" -> "Substantivos em -o masculinos (el), em -a femininos (la). Plural formado com -s ou -es."
                    "zh" -> "以-o结尾多为阳性（el），以-a结尾多为阴性（la）。复数加-s或-es。"
                    "ja" -> "-o語尾は男性名詞（el）、-a語尾は女性名詞（la）。複数形は語末に-sまたは-esを付加。"
                    "ko" -> "-o로 끝나면 남성 명사(el), -a로 끝나면 여성 명사(la). 복수형은 -s 또는 -es를 부가합니다."
                    else -> "Nouns in -o are typically masculine (el), in -a feminine (la). Plurals formed by adding -s or -es."
                }
                "adjective" -> when (lang) {
                    "de" -> "Adjektive passen sich in Genus (m/f) und Numerus (sg/pl) dem Bezugswort an (z.B. alto/alta/altos/altas). Steht meist nach dem Nomen."
                    "ru" -> "Прилагательное согласуется в роде и числе с существительным (alto/alta/altos/altas). Обычно ставится после существительного."
                    "uk" -> "Прикметник узгоджується в роді та числі з іменником (alto/alta/altos/altas). Зазвичай стоїть після іменника."
                    "fr" -> "L'adjectif s'accorde en genre et en nombre avec le nom qualifié. Il se place généralement après le nom."
                    "it" -> "L'aggettivo concorda in genere e numero col sostantivo. Si posiziona di regola dopo il nome."
                    "pt" -> "O adjetivo concorda em gênero e número com o substantivo. Fica geralmente após o substantivo."
                    "zh" -> "形容词与被修饰名词的性数保持一致（如alto/alta/altos/altas），通常置于名词之后。"
                    "ja" -> "形容詞は修飾する名詞の性と数に一致します（alto/alta/altos/altas）。通常は名詞の後ろに置かれます。"
                    "ko" -> "형용사는 수식하는 명사의 성과 수에 일치합니다(alto/alta/altos/altas). 대개 명사 뒤에 위치합니다."
                    else -> "Adjectives agree in gender and number with the noun they modify, typically placed after the noun."
                }
                else -> when (lang) {
                    "de" -> "Unveränderliche Wortart. Modifiziert Verben, Adjektive oder ganze Sätze (Endung oft auf -mente bei abgeleiteten Adverbien)."
                    "ru" -> "Неизменяемая часть речи. Часто образуется суффиксом -mente от женской формы прилагательного."
                    "uk" -> "Незмінювана частина мови. Часто утворюється суфіксом -mente від прикметника жіночого роду."
                    "fr" -> "Forme invariable. Se forme souvent avec le suffixe -mente à partir de la forme féminine de l'adjectif."
                    "it" -> "Forma invariabile. Si forma spesso con il suffisso -mente dalla forma femminile dell'aggettivo."
                    "pt" -> "Forma invariável. Frequentemente derivada com o sufixo -mente a partir do adjetivo feminino."
                    "zh" -> "无词形变化。副词常以形容词阴性加-mente构成，修饰动词或全句。"
                    "ja" -> "不変化詞。派生副詞は形容詞女性形に-menteを付けて作られ、動詞や文全体を修饰します。"
                    "ko" -> "불변화사. 파생 부사는 여성형 형용사에 -mente를 붙여 만들며 동사나 문장을 수식합니다."
                    else -> "Invariable word form. Modifies verbs or adjectives (adverbs often formed with -mente)."
                }
            }
            "de" -> when (pos) {
                "verb" -> when (lang) {
                    "de" -> "Finites deutsches Verb. Personalendungen im Präsens (-e, -st, -t, -en, -t, -en). Verbstellung: Position 2 im Hauptsatz, Satzende im Nebensatz."
                    "en" -> "German verb. Personal endings in present (-e, -st, -t, -en, -t, -en). V2 rule in main clauses; verb-final in subordinate clauses."
                    "es" -> "Verbo alemán. Desinencias de presente (-e, -st, -t, -en, -t, -en). Posición 2 en oración principal; al final en subordinadas."
                    "fr" -> "Verbe allemand. Terminaisons au présent (-e, -st, -t, -en, -t, -en). En 2e position en principale ; à la fin en subordonnée."
                    "it" -> "Verbo tedesco. Desinenze al presente (-e, -st, -t, -en, -t, -en). In seconda posizione nella principale; alla fine nella secondaria."
                    "pt" -> "Verbo alemão. Terminações no presente (-e, -st, -t, -en, -t, -en). Posição 2 na oração principal; no final na subordinada."
                    "ru" -> "Немецкий глагол. Окончания настоящего времени (-e, -st, -t, -en, -t, -en). Глагол на 2-м месте в главном предложении, в конце — в придаточном."
                    "uk" -> "Німецьке дієслово. Означення особи в теперішньому часі. 2-га позиція в головному реченні, кінець речення в підрядному."
                    "zh" -> "德语动词。现在时人称词尾（-e, -st, -t, -en, -t, -en）。主句第二位，从句置于句末。"
                    "ja" -> "ドイツ語動詞。現在形の人称変化語尾。主文では文頭から2番目（V2規則）、副文では文末に配置。"
                    "ko" -> "독일어 동사. 현재 인칭 변화(-e, -st, -t, -en, -t, -en). 주문에서는 2위치, 부문에서는 문장 끝에 위치합니다."
                    else -> "German verb. Conjugates by person (-e, -st, -t, -en, -t, -en). Verb second in main clause, verb final in subordinate."
                }
                "noun" -> when (lang) {
                    "de" -> "Deutsche Substantive werden stets großgeschrieben! Das grammatische Geschlecht (der, die, das) bestimmt Artikel- und Adjektivdeklination in 4 Kasus."
                    "en" -> "German nouns are always capitalized! Grammatical gender (der/die/das) controls declension across 4 cases (Nom, Acc, Dat, Gen)."
                    "es" -> "¡Los sustantivos alemanes siempre se escriben con mayúscula! El género (der/die/das) determina los 4 casos (Nom, Acus, Dat, Gen)."
                    "fr" -> "Les noms allemands prennent toujours une majuscule ! Le genre (der/die/das) régit les 4 cas (Nom, Acc, Dat, Gén)."
                    "it" -> "I sostantivi tedeschi hanno sempre l'iniziale maiuscola! Il genere (der/die/das) governa la declinazione nei 4 casi."
                    "pt" -> "Substantivos alemães sempre iniciam com maiúscula! O gênero (der/die/das) rege os 4 casos gramaticais."
                    "ru" -> "Немецкие существительные пишутся с заглавной буквы! Род (der/die/das) определяет склонение по 4 падежам (Nom, Akk, Dat, Gen)."
                    "uk" -> "Німецькі іменники пишуться з великої літери! Рід (der/die/das) визначає відмінювання за 4 відмінками."
                    "zh" -> "德语名词首字母必须大写！三个语法性（der/die/das）决定四种格（主格、宾格、与格、属格）的变化。"
                    "ja" -> "ドイツ語名詞は常に語頭を大文字で表記！3つの文法性（der/die/das）が4格の格変化を支配します。"
                    "ko" -> "독일어 명사는 항상 첫 글자를 대문자로 씁니다! 3가지 성(der/die/das)이 4개 격변화를 결정합니다."
                    else -> "German nouns are capitalized! Gender (der/die/das) determines declension in Nominative, Accusative, Dative, Genitive."
                }
                "adjective" -> when (lang) {
                    "de" -> "Adjektivdeklination folgt drei Mustern: schwach (nach bestimmtem Artikel), stark (ohne Artikel) und gemischt (nach ein/kein/mein)."
                    "en" -> "German adjective endings follow three patterns: weak (after 'der'), strong (no article), and mixed (after 'ein/mein')."
                    "es" -> "Las terminaciones de los adjetivos siguen tres declinaciones: débil (tras 'der'), fuerte (sin artículo) y mixta (tras 'ein')."
                    "fr" -> "Déclinaison de l'adjectif selon 3 modèles : faible (après 'der'), forte (sans article) et mixte (après 'ein')."
                    "it" -> "La declinazione dell'aggettivo segue 3 modelli: debole (dopo 'der'), forte (senza articolo) e mista (dopo 'ein')."
                    "pt" -> "A declinação do adjetivo segue 3 modelos: fraca (após 'der'), forte (sem artigo) e mista (após 'ein')."
                    "ru" -> "Склонение прилагательных: слабое (после определенного артикля), сильное (без артикля) и смешанное (после 'ein')."
                    "uk" -> "Відмінювання прикметників: слабке (після визначеного артикля), сильне (без артикля) та мішане (після 'ein')."
                    "zh" -> "德语形容词词尾遵循三类变化：弱变化（定冠词后）、强变化（无冠词）和混合变化（不定冠词后）。"
                    "ja" -> "形容詞の格語尾は3変化：弱変化（定冠詞後）、強変化（無冠詞）、混合変化（不定冠词後）。"
                    "ko" -> "형용사 어미 변화: 약변화(정관사 뒤), 강변화(무관사), 혼합변화(부정관사 뒤)의 3가지 패턴."
                    else -> "German adjective endings depend on preceding determiners: weak (after 'der'), strong (no article), mixed (after 'ein')."
                }
                else -> when (lang) {
                    "de" -> "Adverbien sind im Deutschen unveränderlich. Häufig temporal, lokal, modal oder kausal als Satzglied verwendet."
                    "en" -> "Adverbs in German are undeclined. They answer when, where, how, or why without adding suffixes."
                    "es" -> "Los adverbios en alemán no se declinan. Responden a preguntas de modo, tiempo, lugar o causa."
                    "fr" -> "Les adverbes allemands ne se déclinent pas. Ils indiquent le temps, le lieu, la manière ou la cause."
                    "it" -> "Gli avverbi tedeschi sono invariabili. Rispondono alle circostanze di tempo, luogo, modo e causa."
                    "pt" -> "Advérbios em alemão são invariáveis. Indicam circunstâncias de tempo, lugar, modo ou causa."
                    "ru" -> "Немецкие наречия не склоняются и не изменяются. Обозначают образ действия, время, причину или место."
                    "uk" -> "Німецькі прислівники невідмінювані. Вказують на спосіб дії, час, причину або місце."
                    "zh" -> "德语副词无词形曲折变化，用于指示时间、地点、方式或原因。"
                    "ja" -> "ドイツ語の副詞は格変化せず不変。時間、場所、様態、理由を表します。"
                    "ko" -> "독일어 부사는 어미 변화 없이 불변하며 시간, 장소, 양태, 이유를 나타냅니다."
                    else -> "German adverbs are undeclined and do not change form."
                }
            }
            "fr" -> when (pos) {
                "verb" -> when (lang) {
                    "de" -> "Französisches Verb. 1. Gruppe (-er), 2. Gruppe (-ir) oder unregelmäßige 3. Gruppe. Beachten Sie die Bindung (Liaison) und Zeiten wie Passé Composé."
                    "ru" -> "Французский глагол. 1-я группа (-er), 2-я группа (-ir) или неправильная 3-я группа. Обратите внимание на согласование в Passé Composé."
                    "uk" -> "Французьке дієслово. 1-ша група (-er), 2-га (-ir) або неправильна 3-тя група. Звертайте увагу на узгодження в Passé Composé."
                    "en" -> "French verb: 1st group (-er), 2nd group (-ir), or 3rd group irregular. Notice auxiliary selection (être/avoir) in compound tenses."
                    "es" -> "Verbo francés: 1er grupo (-er), 2º grupo (-ir) o 3er grupo irregular. Selección de auxiliar (être/avoir) en tiempos compuestos."
                    "it" -> "Verbo francese: 1º gruppo (-er), 2º gruppo (-ir) o 3º gruppo irregolare. Scelta dell'ausiliare (être/avoir) nei tempi composti."
                    "pt" -> "Verbo francês: 1º grupo (-er), 2º grupo (-ir) ou 3º grupo irregular. Seleção do auxiliar (être/avoir) nos tempos compostos."
                    "zh" -> "法语动词：第一组（-er）、第二组（-ir）或不规则第三组。复合时态注意助动词（être/avoir）的选择与配合。"
                    "ja" -> "フランス語動詞：第1群（-er）、第2群（-ir）、不規則な第3群。複合過去での助動詞（être/avoir）の選択と過去分詞の一致に注意。"
                    "ko" -> "프랑스어 동사: 1군(-er), 2군(-ir), 불규칙 3군. 복합 시제에서 조동사(être/avoir) 선택 및 과거분사 일치에 유의하세요."
                    else -> "French verb: 1st, 2nd, or irregular 3rd group. Conjugation depends on tense and subject pronoun."
                }
                "noun" -> when (lang) {
                    "de" -> "Nomen im Französischen haben zwei Genera: maskulin (le/un) oder feminin (la/une). Das Plural-S wird meist nicht gesprochen."
                    "ru" -> "Два рода: мужской (le/un) и женский (la/une). Окончание множественного числа -s на конце слов обычно не произносится."
                    "uk" -> "Два роди: чоловічий (le/un) та жіночий (la/une). Закінчення множини -s зазвичай німе у вимові."
                    "en" -> "Two genders: masculine (le/un) and feminine (la/une). The plural -s ending is generally silent."
                    "es" -> "Dos géneros: masculino (le/un) y femenino (la/une). La -s final de plural generalmente no se pronuncia."
                    "it" -> "Due generi: maschile (le/un) e femminile (la/une). La -s finale del plurale è di norma muta."
                    "pt" -> "Dois gêneros: masculino (le/un) e feminino (la/une). O -s plural é mudo na pronúncia."
                    "zh" -> "两个语法性：阳性（le/un）和阴性（la/une）。复数-s词尾在读音中通常不发音。"
                    "ja" -> "文法性は男性（le/un）と女性（la/une）の2つ。複数形の語尾-sは通常発音されません。"
                    "ko" -> "남성(le/un)과 여성(la/une)의 두 가지 성. 복수 어미 -s는 대개 묵음입니다."
                    else -> "French nouns have two genders: masculine and feminine. The plural suffix -s is usually silent."
                }
                else -> when (lang) {
                    "de" -> "Adjektive passen sich in Genus und Numerus an. Enden im Femininum oft auf stummes -e, im Plural auf -s."
                    "ru" -> "Прилагательные согласуются в роде и числе. Женский род образуется добавлением немого -e, множественное число — -s."
                    "uk" -> "Прикметники узгоджуються в роді й числі. Жіночий рід утворюється додаванням німого -e, множина — -s."
                    "en" -> "Adjectives agree in gender and number. The feminine adds -e (often silent), and plural adds -s."
                    "es" -> "Los adjetivos concuerdan en género y número. El femenino añade -e muda y el plural añade -s."
                    "it" -> "Gli aggettivi concordano in genere e numero. Il femminile aggiunge -e muta, il plurale -s."
                    "pt" -> "Adjetivos concordam em gênero e número. O feminino recebe -e mudo e o plural recebe -s."
                    "zh" -> "形容词与名词性数配合。阴性通常加不发音的-e，复数加-s。"
                    "ja" -> "形容詞は名詞の性と数に一致。女性形には通常黙音の-eを、複数形には-sを付加します。"
                    "ko" -> "형용사는 명사의 성·수와 일치합니다. 여성형은 묵음 -e, 복수형은 -s를 추가합니다."
                    else -> "Adjectives agree with the noun in gender and number."
                }
            }
            "ru", "uk" -> when (pos) {
                "verb" -> when (lang) {
                    "de" -> "Slawisches Verb. Kernkategorie ist der Aspekt (unvollendet vs. vollendet). Konjugation nach der I. oder II. Konjugation."
                    "en" -> "Slavic verb. Central feature is grammatical aspect (imperfective vs. perfective) and 1st/2nd conjugation verb endings."
                    "es" -> "Verbo eslavo. El rasgo esencial es el aspecto (imperfectivo vs. perfectivo) y la 1ª o 2ª conjugación."
                    "fr" -> "Verbe slave. La caractéristique fondamentale est l'aspect (imperfectif vs. perfectif) et la conjugaison."
                    "it" -> "Verbo slavo. La caratteristica chiave è l'aspetto verbale (imperfettivo vs. perfettivo) e la coniugazione."
                    "pt" -> "Verbo eslavo. O aspecto verbal (imperfectivo vs. perfectivo) é a categoria central de conjugação."
                    "ru" -> "Глагол. Главная грамматическая категория — вид (совершенный / несовершенный). Спряжение I или II типа."
                    "uk" -> "Дієслово. Головна категорія — вид (доконаний / недоконаний). Дієвідміна I або II типу."
                    "zh" -> "斯拉夫语动词。核心语法特征是体（未完成体 vs 完成体）及人称变位。"
                    "ja" -> "スラヴ語動詞。文法範疇の中心は体（不完了体 vs 完了体）および人称変化。"
                    "ko" -> "슬라브어 동사. 핵심 문법 범주는 상(불완료상 vs 완료상) 및 인칭 변화입니다."
                    else -> "Verbal aspect (imperfective vs perfective) is the core grammatical category."
                }
                "noun" -> when (lang) {
                    "de" -> "Substantive flektieren nach Genus (m/f/n) und 6 bzw. 7 Kasus. Die Endung drückt syntaktische Funktion und Kasus präzise aus."
                    "en" -> "Nouns inflect across 3 genders (m/f/n) and 6 to 7 grammatical cases, showing precise syntactic relations."
                    "es" -> "Los sustantivos declinan en 3 géneros (m/f/n) y 6 o 7 casos gramaticales según su función sintáctica."
                    "fr" -> "Les noms se déclinent selon 3 genres (m/f/n) et 6 ou 7 cas grammaticaux."
                    "it" -> "I sostantivi si declinano in 3 generi (m/f/n) e 6 o 7 casi grammaticali."
                    "pt" -> "Substantivos flexionam em 3 gêneros (m/f/n) e 6 a 7 casos gramaticais."
                    "ru" -> "Существительное имеет 3 рода (мужской, женский, средний) и склоняется по 6 падежам (Именительный, Родительный, Дательный, Винительный, Творительный, Предложный)."
                    "uk" -> "Іменник має 3 роди (чоловічий, жіночий, середній) і відмінюється за 7 відмінками (зокрема Кличний)."
                    "zh" -> "名词分为三性（阳/阴/中），按6个（俄语）或7个（乌克兰语）格变格，以词尾表达语法关系。"
                    "ja" -> "名詞は3つの性（男・女・中）を持ち、6または7つの格変化語尾によって文中の役割を表します。"
                    "ko" -> "명사는 3가지 성(남/여/중)을 지니며, 6개(러시아어) 또는 7개(우크라이나어) 격변화 어미로 역할을 나타냅니다."
                    else -> "Nouns inflect for 3 genders and 6 to 7 grammatical cases."
                }
                else -> when (lang) {
                    "de" -> "Adjektive stimmen in Genus, Numerus und Kasus mit dem Substantiv überein."
                    "en" -> "Adjectives fully agree with nouns in gender, number, and case."
                    "es" -> "Los adjetivos concuerdan con el sustantivo en género, número y caso."
                    "fr" -> "L'adjectif s'accorde en genre, nombre et cas avec le nom."
                    "it" -> "L'aggettivo concorda in genere, numero e caso col sostantivo."
                    "pt" -> "Adjetivos concordam plenamente em gênero, número e caso com o substantivo."
                    "ru" -> "Прилагательное полностью согласуется с существительным в роде, числе и падеже."
                    "uk" -> "Прикметник повністю узгоджується з іменником у роді, числі та відмінку."
                    "zh" -> "形容词与名词在性、数、格三方面保持完全一致。"
                    "ja" -> "形容詞は名詞の性・数・格に完全に一致して変化します。"
                    "ko" -> "형용사는 명사의 성, 수, 격에 완전히 일치하여 변화합니다."
                    else -> "Adjectives agree in gender, number, and grammatical case."
                }
            }
            "zh" -> when (lang) {
                "de" -> "Chinesisch ist isolierend. Keine Konjugation oder Deklination! Die Grammatik stützt sich auf feste Wortstellung (SVO), Aspektpartikel (了, 着, 过) und Zählwörter."
                "en" -> "Chinese is an isolating language. No conjugation or declension! Meaning relies on word order (SVO), aspect particles (了, 着, 过), and measure words."
                "es" -> "El chino es una lengua aislante. ¡Sin conjugación ni declinación! Se basa en el orden de palabras (SVO), partículas de aspecto y clasificadores."
                "fr" -> "Le chinois est une langue isolante. Pas de déclinaisons ni de conjugaisons ! La syntaxe dépend de l'ordre (SVO), des particules et des classificateurs."
                "it" -> "Il cinese è una lingua isolante: nessuna flessione! Il significato dipende dall'ordine dei costituenti (SVO), dalle particelle aspettuali e dai classificatori."
                "pt" -> "O chinês é uma língua isolante. Sem flexões verbais ou nominais! A gramática apoia-se na ordem SVO, partículas e classificadores."
                "ru" -> "Китайский язык — изолирующий. Нет спряжения и склонения! Смысл выражается фиксированным порядком слов (SVO), счетными словами и частицами (了, 着, 过)."
                "uk" -> "Китайська мова — ізолююча. Немає відмінювання! Граматика базується на порядку слів (SVO), лічильних словах та частках."
                "zh" -> "汉语为孤立语，无词形曲折变化。依靠严格语序（主-谓-宾）、虚词、体标记（了、着、过）和量词搭配。"
                "ja" -> "中国語は孤立語。語形変化（活用・格変化）はなく、語順（SVO）、量詞、アスペクト助詞（了・着・過）が文法を担います。"
                "ko" -> "중국어는 고립어로 어미 활용이나 격변화가 없습니다. 고정된 어순(SVO), 양사, 상 조사(了, 着, 过)로 문법적 관계를 나타냅니다."
                else -> "Chinese is isolating: grammar relies on strict SVO word order, particles, and measure words."
            }
            "ja" -> when (lang) {
                "de" -> "Japanisch ist agglutinierend. Feste Satzstellung SOV (Prädikat stets am Ende). Partikeln (は, が, を, に, で) markieren die grammatische Funktion."
                "en" -> "Japanese is agglutinative with SOV word order (verb strictly at the end). Particles (wa, ga, o, ni, de) mark grammatical roles."
                "es" -> "El japonés es aglutinante con orden SOV (el verbo siempre al final). Las partículas (wa, ga, o, ni, de) indican la función sintáctica."
                "fr" -> "Le japonais est agglutinant avec un ordre SOV (verbe toujours à la fin). Les particules (wa, ga, o, ni, de) marquent les rôles."
                "it" -> "Il giapponese è agglutinante con ordine SOV (il verbo sempre alla fine). Le particelle (wa, ga, o, ni, de) indicano i ruoli sintattici."
                "pt" -> "O japonês é aglutinante com ordem SOV (verbo estritamente no final). As partículas (wa, ga, o, ni, de) marcam as funções sintáticas."
                "ru" -> "Японский язык — агглютинативный. Порядок слов SOV (сказуемое строго в конце). Падежные частицы (は, が, を, に, で) определяют синтаксические роли."
                "uk" -> "Японська мова — аглютинативна. Порядок слів SOV (дієслово наприкінці). Частки (は, が, を, に, で) визначають граматичні ролі."
                "zh" -> "日语为黏着语，语序为SOV（谓语严格置于句末）。格助词（は、が、を、に、で）标示词语的句法成分。"
                "ja" -> "膠着語でSOV語順（述語が文末）。格助詞（は、が、を、に、で）により文法的関係が示され、動詞・形容詞の語尾が規則的に活用します。"
                "ko" -> "한국어와 동일한 교착어 및 SOV 어순(서술어가 문장 끝). 조사(は, が, を, に, で)가 문법적 관계를 나타내며 활용형이 발달했습니다."
                else -> "Japanese is agglutinative with SOV word order. Grammatical roles are marked with postpositional particles."
            }
            "ko" -> when (lang) {
                "de" -> "Koreanisch ist agglutinierend mit SOV-Wortstellung. Partikeln (이/가, 은/는, 을/를) und Verbalendungen für Höflichkeitsstufen (해요체, 하십시오체)."
                "en" -> "Korean is agglutinative with SOV word order. Postpositional particles (i/ga, eun/neun, eul/reul) mark cases, with honorific verb endings."
                "es" -> "El coreano es aglutinante con orden SOV. Las partículas (i/ga, eun/neun, eul/reul) marcan las funciones y los sufijos expresan cortesía."
                "fr" -> "Le coréen est agglutinant avec un ordre SOV. Les particules et les désinences indiquent les fonctions et les niveaux de politesse."
                "it" -> "Il coreano è agglutinante con ordine SOV. Le particelle e i suffissi verbali indicano i casi e i livelli di cortesia."
                "pt" -> "O coreano é aglutinante com ordem SOV. Partículas pós-postas e desinências verbais expressam casos e graus de polidez."
                "ru" -> "Корейский язык — агглютинативный с порядком слов SOV. Частицы (이/가, 은/는, 을/를) и развитая система уровней вежливости глаголов."
                "uk" -> "Корейська мова — аглютинативна з порядком слів SOV. Частки (이/가, 은/는, 을/를) та суфікси ввічливості визначають структуру речення."
                "zh" -> "韩语为黏着语，SOV语序。格助词（이/가, 은/는, 을/를）标记成分，动词词尾表达丰富的敬语体系（如해요体、하십시오体）。"
                "ja" -> "朝鮮語は膠着語で日本語と文法構造が極めて酷似（SOV語順）。助詞（이/가, 은/는, 을/를）と敬語体系の語尾変化が発達しています。"
                "ko" -> "체언 뒤에 조사(이/가, 은/는, 을/를)가 결합하며, 어미 활용을 통해 시제와 높임법(해요체, 하십시오체)을 구현하는 교착어입니다."
                else -> "Korean is agglutinative with SOV word order, postpositional particles, and honorific verbal inflections."
            }
            else -> when (lang) {
                "de" -> "Englische Grammatik: Feste Wortstellung SVO. Substantive unterscheiden Singular und Plural (-s). Verben erhalten in der 3. Person Singular Präsens ein -s."
                "en" -> "English grammar: Fixed SVO word order. Nouns distinguish singular and plural (-s). Third-person singular present verbs add -s."
                "es" -> "Gramática inglesa: Orden fijo SVO. Sustantivos con singular y plural (-s). Los verbos añaden -s en 3ª persona singular del presente."
                "fr" -> "Grammaire anglaise : Ordre strict SVO. Noms au pluriel en -s. Verbes avec -s à la 3e personne du singulier au présent."
                "it" -> "Grammatica inglese: Ordine rigido SVO. I sostantivi aggiungono -s al plurale; i verbi ricevono la -s alla 3ª persona singolare."
                "pt" -> "Gramática inglesa: Ordem fixa SVO. Substantivos flexionam no plural (-s). Verbos recebem -s na 3ª pessoa do singular do presente."
                "ru" -> "Английская грамматика: Фиксированный порядок слов SVO. Множественное число существительных на -s. Окончание -s у глаголов 3-го лица ед. ч."
                "uk" -> "Англійська граматика: Фіксований порядок слів SVO. Множина іменників на -s. Закінчення -s у дієсловах 3-ї особи однини."
                "zh" -> "英语语法：严格SVO语序。名词复数加-s/-es，一般现在时第三人称单数动词加-s。"
                "ja" -> "英語文法：SVOの固定語順。名詞の複数形は-sを付加、現在形3人称単数の動詞には-sを付加します。"
                "ko" -> "영어 문법: 고정된 SVO 어순. 명사 복수형 -s, 현재 시제 3인칭 단수 주어 동사 뒤 -s 부가."
                else -> "English features fixed SVO word order and third-person singular present verb suffix -s."
            }
        }
    }

    private fun getSyntaxGuidance(word: VocabularyWordEntity, targetLang: String, pos: String, lang: String): String {
        return when (lang) {
            "de" -> when (pos) {
                "verb" -> "Verbindung im Satz: Steht typischerweise mit direktem oder präpositionalem Objekt. Überprüfen Sie den Rektionskasus des Verbs."
                "noun" -> "Satzfunktion: Kann als grammatisches Subjekt, direktes/indirektes Objekt oder als Attribut eines anderen Nomens auftreten."
                "adjective" -> "Attributive (vor dem Nomen mit Deklinationsendung) oder prädikative Verwendung (hinter dem Hilfsverb 'sein/bleiben' ohne Endung)."
                "adverb" -> "Kann frei im Mittelfeld platziert werden, um Handlungen zeitlich, räumlich oder modal zu präzisieren."
                else -> "Feste idiomatische Fügung; sollte ganzheitlich als syntaktische Einheit memoriert werden."
            }
            "ru" -> when (pos) {
                "verb" -> "Синтаксическая роль: выступает в роли сказуемого. Обратите внимание на переходность и управление (какой падеж требует глагол)."
                "noun" -> "Синтаксическая роль: подлежащее или дополнение. Падеж определяется управляющим глаголом или предлогом."
                "adjective" -> "Атрибутивное употребление (перед или после существительного) или предикативное (в составе сказуемого)."
                "adverb" -> "Выступает обстоятельством. Обычно примыкает к глаголу или прилагательному, конкретизируя действие."
                else -> "Устойчивый фразеологический оборот; запоминается как неделимая смысловая единица."
            }
            "uk" -> when (pos) {
                "verb" -> "Синтаксична роль: виступає присудком. Зверніть увагу на перехідність та відмінкове керування."
                "noun" -> "Синтаксична роль: підмет або додаток. Відмінок визначається дієсловом або прийменником."
                "adjective" -> "Атрибутивне або предикативне вживання. Узгоджується з іменником."
                "adverb" -> "Виступає обставиною. Прилягає до дієслова чи прикметника."
                else -> "Стійка фразеологічна одиниця; вивчається як цілісна конструкція."
            }
            "en" -> when (pos) {
                "verb" -> "Sentence role: Acts as main predicate. Check whether it takes a direct object (transitive) or prepositional complement."
                "noun" -> "Sentence role: Functions as sentence subject, direct/indirect object, or object of a preposition."
                "adjective" -> "Used attributively (modifying a noun) or predicatively (after linking verbs like 'be', 'seem', 'feel')."
                "adverb" -> "Modifies verbs, adjectives, or entire clauses, detailing manner, time, degree, or frequency."
                else -> "Fixed idiomatic phrase; best learned and used as a complete grammatical chunk."
            }
            "es" -> when (pos) {
                "verb" -> "Función oracional: Actúa como núcleo del predicado. Verifique si rige objeto directo o complemento preposicional."
                "noun" -> "Función oracional: Funciona como sujeto, objeto directo o indirecto, o término de preposición."
                "adjective" -> "Uso atributivo (pospuesto o antepuesto al sustantivo) o predicativo (con verbos copulativos ser/estar)."
                "adverb" -> "Modifica verbos, adjetivos u otros adverbios aportando circunstancias de modo, tiempo o grado."
                else -> "Locución fija; se memoriza y emplea como un bloque léxico compacto."
            }
            "fr" -> when (pos) {
                "verb" -> "Rôle dans la phrase : Noyau du prédicat. Vérifiez la transitivité (complément d'objet direct ou indirect)."
                "noun" -> "Rôle dans la phrase : Sujet, complément d'objet direct/indirect ou complément du nom."
                "adjective" -> "Emploi épithète (accolé au nom) ou attribut (avec verbe d'état comme être, sembler, devenir)."
                "adverb" -> "Modifie un verbe, un adjectif ou une proposition entière en précisant la manière ou le degré."
                else -> "Expression figée ; à mémoriser comme un ensemble syntaxique indivisible."
            }
            "it" -> when (pos) {
                "verb" -> "Ruolo nella frase: Nucleo del predicato verbale. Verificare la reggenza (oggetto diretto o indiretto)."
                "noun" -> "Ruolo nella frase: Soggetto, oggetto diretto o complemento indiretto retto da preposizione."
                "adjective" -> "Uso attributivo o predicativo (con verbi copulativi come essere, sembrare)."
                "adverb" -> "Modifica il verbo o l'aggettivo specificando modo, tempo o intensità."
                else -> "Espressione fissa; da memorizzare come blocco sintattico unitario."
            }
            "pt" -> when (pos) {
                "verb" -> "Função sintática: Núcleo do predicado. Atente para a transitividade direta ou indireta."
                "noun" -> "Função sintática: Sujeito, objeto direto/indireto ou complemento nominal."
                "adjective" -> "Uso atributivo (junto ao nome) ou predicativo (com verbos de ligação ser/estar)."
                "adverb" -> "Modifica verbos, adjetivos ou orações, denotando circunstâncias de modo ou intensidade."
                else -> "Locução fixa; deve ser aprendida como uma estrutura coesa."
            }
            "zh" -> when (pos) {
                "verb" -> "句法功能：充当谓语核心。注意及物与不及物区别，以及是否需要介词引导宾语。"
                "noun" -> "句法功能：作主语、宾语或定语。与其他名词组合时注意'的'的使用。"
                "adjective" -> "作定语修饰名词（如'……的'），或直接作谓语（如'天气很好'，无需系动词）。"
                "adverb" -> "作状语修饰动词或形容词，置于所修饰成分之前。"
                else -> "固定成语/习语短语；建议作为完整语义模块记忆与运用。"
            }
            "ja" -> when (pos) {
                "verb" -> "構文的機能：文の述語となります。他動詞・自動詞の区別（〜を vs 〜が）に留意してください。"
                "noun" -> "構文的機能：主語・目的語として格助詞（は、が、を、に）を伴って用いられます。"
                "adjective" -> "連体修飾語（名詞を直接修飾）または述語（文末で述語となる）として機能します。"
                "adverb" -> "連用修飾語として動詞や形容詞の前に置かれ、状態や程度を限定します。"
                else -> "定型連語・慣用句。分割せず一つの文法ブロックとして習得するのが効果的です。"
            }
            else -> when (pos) {
                "verb" -> "문장 내 역할: 서술어의 핵심. 타동사/자동사 구분 및 목적어 조사(을/를) 결합을 확인하세요."
                "noun" -> "문장 내 역할: 주어, 목적어, 보어로 조사(이/가, 을/를, 에/에서)와 결합합니다."
                "adjective" -> "명사를 수식하는 관형사형(~은/는/을/한) 또는 서술어로 쓰입니다."
                "adverb" -> "용언(동사/형용사) 앞에 위치하여 양태나 정도를 수식합니다."
                else -> "고정 관용구/숙어. 쪼개지 않고 전체 문맥 단위로 기억하는 것이 좋습니다."
            }
        }
    }

    private fun getPracticalTip(word: VocabularyWordEntity, targetLang: String, pos: String, cefr: String, lang: String): String {
        return when (lang) {
            "de" -> "Lern-Tipp: Formulieren Sie sofort einen eigenen Beispielsatz auf Niveau $cefr mit '${word.word}', um das Wort im Langzeitgedächtnis zu verankern."
            "ru" -> "Совет по изучению: Составьте собственное предложение уровня $cefr со словом '${word.word}', чтобы закрепить его в памяти."
            "uk" -> "Порада для навчання: Складіть власне речення рівня $cefr зі словом '${word.word}', щоб надійно його запам'ятати."
            "en" -> "Study tip: Immediately create your own $cefr-level sentence using '${word.word}' to secure it in long-term memory."
            "es" -> "Consejo de estudio: Crea de inmediato una frase propia de nivel $cefr con '${word.word}' para fijarla en tu memoria."
            "fr" -> "Astuce d'apprentissage : Formulez immédiatement votre propre phrase de niveau $cefr avec '${word.word}' pour bien la mémoriser."
            "it" -> "Consiglio di studio: Crea subito una frase personale di livello $cefr con '${word.word}' per memorizzarla stabilmente."
            "pt" -> "Dica de estudo: Crie imediatamente uma frase no nível $cefr com '${word.word}' para consolidá-la na memória."
            "zh" -> "学习建议：立即尝试用'${word.word}'造一个${cefr}级别的完整句子，以巩固长期记忆。"
            "ja" -> "学習のヒント：'${word.word}'を使って、${cefr}レベルの自作の例文を1つ作成すると記憶に定着しやすくなります。"
            else -> "학습 팁: '${word.word}'을(를) 활용해 $cefr 레벨의 나만의 문장을 바로 만들어 보면 장기 기억에 효과적입니다."
        }
    }

    private fun getShortSummary(word: VocabularyWordEntity, targetLang: String, pos: String, cefr: String, lang: String): String {
        val posTitle = when (pos) {
            "verb" -> when (lang) {
                "de" -> "Verb"
                "ru" -> "Глагол"
                "uk" -> "Дієслово"
                "fr" -> "Verbe"
                "es" -> "Verbo"
                "it" -> "Verbo"
                "pt" -> "Verbo"
                "zh" -> "动词"
                "ja" -> "動詞"
                "ko" -> "동사"
                else -> "Verb"
            }
            "noun" -> when (lang) {
                "de" -> "Substantiv"
                "ru" -> "Существительное"
                "uk" -> "Іменник"
                "fr" -> "Nom"
                "es" -> "Sustantivo"
                "it" -> "Sostantivo"
                "pt" -> "Substantivo"
                "zh" -> "名词"
                "ja" -> "名詞"
                "ko" -> "명사"
                else -> "Noun"
            }
            "adjective" -> when (lang) {
                "de" -> "Adjektiv"
                "ru" -> "Прилагательное"
                "uk" -> "Прикметник"
                "fr" -> "Adjectif"
                "es" -> "Adjetivo"
                "it" -> "Aggettivo"
                "pt" -> "Adjetivo"
                "zh" -> "形容词"
                "ja" -> "形容詞"
                "ko" -> "형용사"
                else -> "Adjective"
            }
            "adverb" -> when (lang) {
                "de" -> "Adverb"
                "ru" -> "Наречие"
                "uk" -> "Прислівник"
                "fr" -> "Adverbe"
                "es" -> "Adverbio"
                "it" -> "Avverbio"
                "pt" -> "Advérbio"
                "zh" -> "副词"
                "ja" -> "副詞"
                "ko" -> "부사"
                else -> "Adverb"
            }
            else -> when (lang) {
                "de" -> "Redewendung"
                "ru" -> "Идиома"
                "uk" -> "Ідіома"
                "fr" -> "Locution"
                "es" -> "Expresión"
                "it" -> "Espressione"
                "pt" -> "Locução"
                "zh" -> "固定习语"
                "ja" -> "慣用表現"
                "ko" -> "관용 표현"
                else -> "Phrase"
            }
        }

        return when (lang) {
            "de" -> "$posTitle • Niveau $cefr • Grammatik & Verwendung"
            "ru" -> "$posTitle • Уровень $cefr • Грамматика и употребление"
            "uk" -> "$posTitle • Рівень $cefr • Граматика та вживання"
            "fr" -> "$posTitle • Niveau $cefr • Grammaire & Usage"
            "es" -> "$posTitle • Nivel $cefr • Gramática y uso"
            "it" -> "$posTitle • Livello $cefr • Grammatica e uso"
            "pt" -> "$posTitle • Nível $cefr • Gramática e uso"
            "zh" -> "$posTitle • 级别 $cefr • 语法解析与用法"
            "ja" -> "$posTitle • レベル $cefr • 文法解説と用法"
            "ko" -> "$posTitle • 레벨 $cefr • 문법 설명 및 용법"
            else -> "$posTitle • Level $cefr • Grammar & Usage"
        }
    }
}
