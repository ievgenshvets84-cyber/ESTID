package com.example.data.vocabulary

import com.example.data.db.VocabularyWordEntity

enum class QuizQuestionType {
    WORD_TO_TRANSLATION,     // Target word -> Native translation
    TRANSLATION_TO_WORD,     // Native translation -> Target word (Reverse Challenge)
    CONTEXT_CLOZE,           // Example sentence fill-in (Advanced Context Challenge)
    REINFORCEMENT_CHOICE     // Lower tier foundational reinforcement with clue
}

data class QuizQuestion(
    val word: VocabularyWordEntity,
    val promptText: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficultyLevel: Int = 2,
    val difficultyLabel: String = "Stufe 2 (A2)",
    val isReinforcement: Boolean = false,
    val isChallenge: Boolean = false,
    val questionType: QuizQuestionType = QuizQuestionType.WORD_TO_TRANSLATION,
    val clozeSentence: String? = null,
    val hintText: String? = null,
    val explanationText: String? = null
)

data class AdaptiveStepRecord(
    val question: QuizQuestion,
    val selectedIndex: Int,
    val isCorrect: Boolean,
    val difficultyLevel: Int
)

data class AdaptiveQuizSummary(
    val totalQuestions: Int,
    val totalCorrect: Int,
    val startDifficulty: Int,
    val peakDifficulty: Int,
    val finalDifficulty: Int,
    val difficultyProgression: List<Int>,
    val reinforcementCount: Int,
    val reinforcementSuccessCount: Int,
    val challengesCount: Int,
    val challengesSuccessCount: Int,
    val score: Int,
    val diagnosisText: String
)

/**
 * Dynamic Difficulty Adjustment (DDA) Engine for the Quiz Module.
 *
 * Algorithm Rules:
 * 1. Tracks recent answers, streaks, and error patterns in real-time.
 * 2. On Correct answers:
 *    - Increases streak and awards points scaled by difficulty level (10 * level + streak bonus).
 *    - Consecutive correct answers (>=2) or high recent accuracy dynamically escalates difficulty (up to Level 5).
 *    - Triggers complex challenges (Reverse Translation, Cloze Context, subtle distractors).
 * 3. On Incorrect answers:
 *    - Immediately steps difficulty down to an easier level (down to Level 1).
 *    - Triggers an easier reinforcement question on the next turn with explicit memory clues,
 *      foundational vocabulary, and distinct distractors to consolidate fundamentals.
 */
class AdaptiveQuizEngine(
    val nativeLangCode: String = "de",
    val targetLangName: String = "Spanisch",
    val initialDifficulty: Int = 2
) {
    var currentDifficulty: Int = initialDifficulty.coerceIn(1, 5)
        private set

    var peakDifficulty: Int = currentDifficulty
        private set

    var streak: Int = 0
        private set

    var score: Int = 0
        private set

    var consecutiveCorrect: Int = 0
        private set

    var consecutiveIncorrect: Int = 0
        private set

    var pendingReinforcement: Boolean = false
        private set

    var lastMissedWord: VocabularyWordEntity? = null
        private set

    private val _history = mutableListOf<AdaptiveStepRecord>()
    val history: List<AdaptiveStepRecord> get() = _history

    val usedWordIds = mutableSetOf<Long>()

    fun getDifficultyLabel(level: Int): String {
        return when (level) {
            1 -> when (nativeLangCode) {
                "de" -> "Stufe 1 (A1 - Grundlagen) 🌱"
                "ru" -> "Уровень 1 (A1 - Базовый) 🌱"
                "uk" -> "Рівень 1 (A1 - Базовий) 🌱"
                "fr" -> "Niveau 1 (A1 - Débutant) 🌱"
                "es" -> "Nivel 1 (A1 - Principiante) 🌱"
                "it" -> "Livello 1 (A1 - Principiante) 🌱"
                "pt" -> "Nível 1 (A1 - Básico) 🌱"
                "zh" -> "第1级 (A1 基础) 🌱"
                "ja" -> "レベル1 (A1 初級) 🌱"
                "ko" -> "레벨 1 (A1 기초) 🌱"
                else -> "Level 1 (A1 - Beginner) 🌱"
            }
            2 -> when (nativeLangCode) {
                "de" -> "Stufe 2 (A2 - Grundkenntnisse) 🚶"
                "ru" -> "Уровень 2 (A2 - Элементарный) 🚶"
                "uk" -> "Рівень 2 (A2 - Елементарний) 🚶"
                "fr" -> "Niveau 2 (A2 - Élémentaire) 🚶"
                "es" -> "Nivel 2 (A2 - Elemental) 🚶"
                "it" -> "Livello 2 (A2 - Elementare) 🚶"
                "pt" -> "Nível 2 (A2 - Elementar) 🚶"
                "zh" -> "第2级 (A2 初级) 🚶"
                "ja" -> "レベル2 (A2 初中級) 🚶"
                "ko" -> "레벨 2 (A2 초급) 🚶"
                else -> "Level 2 (A2 - Elementary) 🚶"
            }
            3 -> when (nativeLangCode) {
                "de" -> "Stufe 3 (B1 - Mittelstufe) 🗣️"
                "ru" -> "Уровень 3 (B1 - Средний) 🗣️"
                "uk" -> "Рівень 3 (B1 - Середній) 🗣️"
                "fr" -> "Niveau 3 (B1 - Intermédiaire) 🗣️"
                "es" -> "Nivel 3 (B1 - Intermedio) 🗣️"
                "it" -> "Livello 3 (B1 - Intermedio) 🗣️"
                "pt" -> "Nível 3 (B1 - Intermediário) 🗣️"
                "zh" -> "第3级 (B1 中级) 🗣️"
                "ja" -> "レベル3 (B1 中級) 🗣️"
                "ko" -> "레벨 3 (B1 중급) 🗣️"
                else -> "Level 3 (B1 - Intermediate) 🗣️"
            }
            4 -> when (nativeLangCode) {
                "de" -> "Stufe 4 (B2 - Fortgeschritten) 💼"
                "ru" -> "Уровень 4 (B2 - Продвинутый) 💼"
                "uk" -> "Рівень 4 (B2 - Просунутий) 💼"
                "fr" -> "Niveau 4 (B2 - Avancé) 💼"
                "es" -> "Nivel 4 (B2 - Avanzado) 💼"
                "it" -> "Livello 4 (B2 - Avanzato) 💼"
                "pt" -> "Nível 4 (B2 - Avançado) 💼"
                "zh" -> "第4级 (B2 高级) 💼"
                "ja" -> "レベル4 (B2 上級) 💼"
                "ko" -> "레벨 4 (B2 고급) 💼"
                else -> "Level 4 (B2 - Upper Intermediate) 💼"
            }
            else -> when (nativeLangCode) {
                "de" -> "Stufe 5 (C1/C2 - Experten-Meisterschaft) 👑"
                "ru" -> "Уровень 5 (C1/C2 - Мастерство) 👑"
                "uk" -> "Рівень 5 (C1/C2 - Майстерність) 👑"
                "fr" -> "Niveau 5 (C1/C2 - Maîtrise) 👑"
                "es" -> "Nivel 5 (C1/C2 - Maestría) 👑"
                "it" -> "Livello 5 (C1/C2 - Maestria) 👑"
                "pt" -> "Nível 5 (C1/C2 - Domínio) 👑"
                "zh" -> "第5级 (C1/C2 精通) 👑"
                "ja" -> "レベル5 (C1/C2 最上級) 👑"
                "ko" -> "레벨 5 (C1/C2 마스터) 👑"
                else -> "Level 5 (C1/C2 - Native Mastery) 👑"
            }
        }
    }

    /**
     * Evaluates the submitted answer and dynamically recalculates difficulty.
     */
    fun onAnswerSubmitted(question: QuizQuestion, selectedIndex: Int): Pair<Boolean, String> {
        val isCorrect = (selectedIndex == question.correctIndex)
        val step = AdaptiveStepRecord(
            question = question,
            selectedIndex = selectedIndex,
            isCorrect = isCorrect,
            difficultyLevel = question.difficultyLevel
        )
        _history.add(step)

        if (isCorrect) {
            consecutiveCorrect++
            consecutiveIncorrect = 0
            streak++

            val basePoints = 10 * question.difficultyLevel
            val streakBonus = if (streak >= 2) streak * 5 else 0
            score += (basePoints + streakBonus)

            // Dynamic progression check:
            // 2 in a row correct OR strong recent window triggers difficulty increase
            val recentCorrectCount = _history.takeLast(3).count { it.isCorrect }
            if (consecutiveCorrect >= 2 || (recentCorrectCount >= 2 && currentDifficulty < 5)) {
                val oldDiff = currentDifficulty
                currentDifficulty = (currentDifficulty + 1).coerceAtMost(5)
                peakDifficulty = maxOf(peakDifficulty, currentDifficulty)
                pendingReinforcement = false

                val feedback = if (currentDifficulty > oldDiff) {
                    when (nativeLangCode) {
                        "de" -> "Exzellent! Stufe $currentDifficulty freigeschaltet ⚡ Komplexere Herausforderung folgt!"
                        "ru" -> "Отлично! Открыт уровень $currentDifficulty ⚡ Впереди более сложная задача!"
                        "uk" -> "Чудово! Відкрито рівень $currentDifficulty ⚡ Попереду складніше завдання!"
                        "fr" -> "Excellent ! Niveau $currentDifficulty débloqué ⚡ Défi plus complexe à venir !"
                        "es" -> "¡Excelente! Nivel $currentDifficulty desbloqueado ⚡ ¡Próximo desafío más complejo!"
                        "it" -> "Eccellente! Livello $currentDifficulty sbloccato ⚡ Sfida più complessa in arrivo!"
                        "pt" -> "Excelente! Nível $currentDifficulty desbloqueado ⚡ Desafio mais complexo a seguir!"
                        "zh" -> "太棒了！解锁第 $currentDifficulty 级 ⚡ 即将迎来更复杂的挑战！"
                        "ja" -> "素晴らしい！レベル $currentDifficulty 解除 ⚡ より高度なチャレンジに進みます！"
                        "ko" -> "훌륭합니다! 레벨 $currentDifficulty 잠금 해제 ⚡ 더 복잡한 도전 과제 진행!"
                        else -> "Outstanding! Level $currentDifficulty unlocked ⚡ Next challenge will be more complex!"
                    }
                } else {
                    when (nativeLangCode) {
                        "de" -> "Hervorragend! Höchste Meisterstufe (Stufe 5) behauptet! 👑"
                        "ru" -> "Великолепно! Вы на высшем уровне мастерства (Уровень 5)! 👑"
                        "uk" -> "Чудово! Ви на найвищому рівні майстерності (Рівень 5)! 👑"
                        else -> "Flawless! Retaining top mastery level (Level 5)! 👑"
                    }
                }
                return Pair(true, feedback)
            } else {
                pendingReinforcement = false
                val feedback = when (nativeLangCode) {
                    "de" -> "Richtig! +$basePoints Punkte 🎯 Bleib dran!"
                    "ru" -> "Верно! +$basePoints очков 🎯 Так держать!"
                    "uk" -> "Правильно! +$basePoints балів 🎯 Так тримати!"
                    "fr" -> "Correct ! +$basePoints points 🎯 Continuez !"
                    "es" -> "¡Correcto! +$basePoints puntos 🎯 ¡Sigue así!"
                    "it" -> "Esatto! +$basePoints punti 🎯 Continua così!"
                    "pt" -> "Correto! +$basePoints pontos 🎯 Continue assim!"
                    "zh" -> "正确！+$basePoints 分 🎯 继续保持！"
                    "ja" -> "正解！+$basePoints ポイント 🎯 その調子です！"
                    "ko" -> "정답입니다! +$basePoints 점 🎯 계속 힘내세요!"
                    else -> "Correct! +$basePoints pts 🎯 Keep the momentum!"
                }
                return Pair(true, feedback)
            }
        } else {
            // Incorrect answer:
            consecutiveIncorrect++
            consecutiveCorrect = 0
            streak = 0
            lastMissedWord = question.word

            // Dynamic difficulty drop & flag for reinforcement
            val oldDiff = currentDifficulty
            currentDifficulty = (currentDifficulty - 1).coerceAtLeast(1)
            pendingReinforcement = true

            val feedback = when (nativeLangCode) {
                "de" -> "Kein Problem! Nächste Aufgabe: Leichtere Verstärkung (Stufe $currentDifficulty) 🌱 zur sicheren Festigung."
                "ru" -> "Ничего страшного! Следующий вопрос: закрепление (Уровень $currentDifficulty) 🌱 для уверенности."
                "uk" -> "Нічого страшного! Наступне завдання: підкріплення (Рівень $currentDifficulty) 🌱 для впевненості."
                "fr" -> "Pas d'inquiétude ! Question suivante : renforcement plus facile (Niveau $currentDifficulty) 🌱 pour consolider."
                "es" -> "¡No te preocupes! Siguiente pregunta: refuerzo más fácil (Nivel $currentDifficulty) 🌱 para consolidar."
                "it" -> "Non preoccuparti! Prossima domanda: rinforzo più facile (Livello $currentDifficulty) 🌱 per consolidare."
                "pt" -> "Não se preocupe! Próxima pergunta: reforço mais fácil (Nível $currentDifficulty) 🌱 para consolidar."
                "zh" -> "没关系！下一题：基础强化题（第 $currentDifficulty 级）🌱 帮助巩固记忆。"
                "ja" -> "大丈夫です！次は基礎を定着させるやさしい復習問題（レベル $currentDifficulty）🌱 です。"
                "ko" -> "괜찮습니다! 다음 문제는 기초를 다지는 쉬운 강화 문제(레벨 $currentDifficulty)🌱 입니다."
                else -> "No worries! Next up: Easier reinforcement question (Level $currentDifficulty) 🌱 to solidify fundamentals."
            }
            return Pair(false, feedback)
        }
    }

    /**
     * Builds an adaptively tailored QuizQuestion.
     */
    fun createQuestion(
        targetWord: VocabularyWordEntity,
        distractorPool: List<VocabularyWordEntity>
    ): QuizQuestion {
        usedWordIds.add(targetWord.id)

        val isReinforce = pendingReinforcement || (currentDifficulty == 1 && consecutiveIncorrect > 0)
        val isChallenge = currentDifficulty >= 3 && !isReinforce

        // Determine question type based on difficulty and mode
        val qType: QuizQuestionType = when {
            isReinforce -> QuizQuestionType.REINFORCEMENT_CHOICE
            currentDifficulty == 1 -> QuizQuestionType.WORD_TO_TRANSLATION
            currentDifficulty == 2 -> QuizQuestionType.WORD_TO_TRANSLATION
            currentDifficulty == 3 -> {
                // Mix in reverse challenge
                if (_history.size % 2 == 1) QuizQuestionType.TRANSLATION_TO_WORD else QuizQuestionType.WORD_TO_TRANSLATION
            }
            currentDifficulty == 4 -> {
                // Challenge: Reverse translation or cloze
                if (targetWord.exampleSentence.isNotBlank() && targetWord.exampleSentence.contains(targetWord.word, ignoreCase = true)) {
                    QuizQuestionType.CONTEXT_CLOZE
                } else {
                    QuizQuestionType.TRANSLATION_TO_WORD
                }
            }
            else -> {
                // Level 5 Master Challenge
                if (targetWord.exampleSentence.isNotBlank() && targetWord.exampleSentence.contains(targetWord.word, ignoreCase = true)) {
                    QuizQuestionType.CONTEXT_CLOZE
                } else {
                    QuizQuestionType.TRANSLATION_TO_WORD
                }
            }
        }

        // Generate options & correct index
        val (options, correctIdx, clozeSentence) = when (qType) {
            QuizQuestionType.TRANSLATION_TO_WORD -> {
                val correctTargetWord = targetWord.word
                val wrongWords = distractorPool
                    .filter { it.id != targetWord.id && it.word.isNotBlank() && !it.word.equals(correctTargetWord, ignoreCase = true) }
                    .shuffled()
                    .take(3)
                    .map { it.word }

                val all = (wrongWords + correctTargetWord).shuffled()
                Triple(all, all.indexOf(correctTargetWord), null)
            }
            QuizQuestionType.CONTEXT_CLOZE -> {
                val correctTargetWord = targetWord.word
                val rawSentence = targetWord.exampleSentence
                val blanked = rawSentence.replace(Regex("(?i)\\b${Regex.escape(correctTargetWord)}\\b"), "______")
                    .ifBlank { rawSentence.replace(correctTargetWord, "______", ignoreCase = true) }

                val wrongWords = distractorPool
                    .filter { it.id != targetWord.id && it.word.isNotBlank() && !it.word.equals(correctTargetWord, ignoreCase = true) }
                    .shuffled()
                    .take(3)
                    .map { it.word }

                val all = (wrongWords + correctTargetWord).shuffled()
                Triple(all, all.indexOf(correctTargetWord), blanked)
            }
            else -> {
                // WORD_TO_TRANSLATION & REINFORCEMENT_CHOICE
                val correctTranslation = targetWord.translation
                val wrongTranslations = distractorPool
                    .filter { it.id != targetWord.id && it.translation.isNotBlank() && !it.translation.equals(correctTranslation, ignoreCase = true) }
                    .shuffled()
                    .take(3)
                    .map { it.translation }

                val all = (wrongTranslations + correctTranslation).shuffled()
                Triple(all, all.indexOf(correctTranslation), null)
            }
        }

        // Prompt text
        val prompt = when (qType) {
            QuizQuestionType.TRANSLATION_TO_WORD -> when (nativeLangCode) {
                "de" -> "Wie heißt „${targetWord.translation}“ auf $targetLangName?"
                "ru" -> "Как переводится «${targetWord.translation}» на $targetLangName?"
                "uk" -> "Як перекладається «${targetWord.translation}» мовою $targetLangName?"
                "fr" -> "Comment dit-on « ${targetWord.translation} » en $targetLangName ?"
                "es" -> "¿Cómo se dice \"${targetWord.translation}\" en $targetLangName?"
                "it" -> "Come si dice «${targetWord.translation}» in $targetLangName?"
                "pt" -> "Como se diz \"${targetWord.translation}\" em $targetLangName?"
                "zh" -> "“${targetWord.translation}”在${targetLangName}中怎么说？"
                "ja" -> "「${targetWord.translation}」は${targetLangName}で何と言いますか？"
                "ko" -> "“${targetWord.translation}”은(는) ${targetLangName}(으)로 어떻게 말하나요?"
                else -> "How do you translate \"${targetWord.translation}\" into $targetLangName?"
            }
            QuizQuestionType.CONTEXT_CLOZE -> when (nativeLangCode) {
                "de" -> "Welches Wort vervollständigt den Satz sinnvoll?"
                "ru" -> "Какое слово пропущено в предложении?"
                "uk" -> "Яке слово пропущено в реченні?"
                "fr" -> "Quel mot complète correctement la phrase ?"
                "es" -> "¿Qué palabra completa correctamente la frase?"
                "it" -> "Quale parola completa correttamente la frase?"
                "pt" -> "Qual palavra completa a frase corretamente?"
                "zh" -> "哪个词填入空格最合适？"
                "ja" -> "空欄に入る最も適切な単語はどれですか？"
                "ko" -> "빈칸에 들어갈 가장 알맞은 단어는 무엇인가요?"
                else -> "Which word correctly completes the sentence?"
            }
            QuizQuestionType.REINFORCEMENT_CHOICE -> when (nativeLangCode) {
                "de" -> "🌱 Verstärkung: Was bedeutet „${targetWord.word}“?"
                "ru" -> "🌱 Закрепление: Что означает «${targetWord.word}»?"
                "uk" -> "🌱 Підкріплення: Що означає «${targetWord.word}»?"
                "fr" -> "🌱 Renforcement : Que signifie « ${targetWord.word} » ?"
                "es" -> "🌱 Refuerzo: ¿Qué significa \"${targetWord.word}\"?"
                "it" -> "🌱 Rinforzo: Cosa significa «${targetWord.word}»?"
                "pt" -> "🌱 Reforço: O que significa \"${targetWord.word}\"?"
                "zh" -> "🌱 基础强化：“${targetWord.word}”是什么意思？"
                "ja" -> "🌱 復習強化：「${targetWord.word}」の意味は何ですか？"
                "ko" -> "🌱 기초 강화: “${targetWord.word}”의 뜻은 무엇인가요?"
                else -> "🌱 Reinforcement: What does \"${targetWord.word}\" mean?"
            }
            else -> when (nativeLangCode) {
                "de" -> "Was bedeutet „${targetWord.word}“?"
                "ru" -> "Что означает «${targetWord.word}»?"
                "uk" -> "Що означає «${targetWord.word}»?"
                "fr" -> "Que signifie « ${targetWord.word} » ?"
                "es" -> "¿Qué significa \"${targetWord.word}\"?"
                "it" -> "Cosa significa «${targetWord.word}»?"
                "pt" -> "O que significa \"${targetWord.word}\"?"
                "zh" -> "“${targetWord.word}”是什么意思？"
                "ja" -> "「${targetWord.word}」の意味は何ですか？"
                "ko" -> "“${targetWord.word}”의 뜻은 무엇인가요?"
                else -> "What is the meaning of \"${targetWord.word}\"?"
            }
        }

        val hint = if (isReinforce) {
            val cat = targetWord.category.ifBlank { "Alltag" }
            when (nativeLangCode) {
                "de" -> "Kategorie: $cat • Aussprache: [${targetWord.phonetic.ifBlank { targetWord.word }}]"
                "ru" -> "Категория: $cat • Произношение: [${targetWord.phonetic.ifBlank { targetWord.word }}]"
                "uk" -> "Категорія: $cat • Вимова: [${targetWord.phonetic.ifBlank { targetWord.word }}]"
                else -> "Category: $cat • Pronunciation: [${targetWord.phonetic.ifBlank { targetWord.word }}]"
            }
        } else null

        val explanation = "${targetWord.word} [${targetWord.phonetic}] = ${targetWord.translation}" +
                if (targetWord.exampleSentence.isNotBlank()) "\n${targetWord.exampleSentence} (${targetWord.exampleTranslation})" else ""

        return QuizQuestion(
            word = targetWord,
            promptText = prompt,
            options = options,
            correctIndex = correctIdx,
            difficultyLevel = currentDifficulty,
            difficultyLabel = getDifficultyLabel(currentDifficulty),
            isReinforcement = isReinforce,
            isChallenge = isChallenge,
            questionType = qType,
            clozeSentence = clozeSentence,
            hintText = hint,
            explanationText = explanation
        )
    }

    /**
     * Builds complete post-quiz diagnostic summary.
     */
    fun createSummary(): AdaptiveQuizSummary {
        val total = _history.size
        val totalCorrect = _history.count { it.isCorrect }
        val finalDiff = currentDifficulty
        val progression = _history.map { it.difficultyLevel }

        val reinfCount = _history.count { it.question.isReinforcement }
        val reinfSuccess = _history.count { it.question.isReinforcement && it.isCorrect }

        val challCount = _history.count { it.question.isChallenge }
        val challSuccess = _history.count { it.question.isChallenge && it.isCorrect }

        val diag = when {
            peakDifficulty >= 4 && totalCorrect.toFloat() / total.coerceAtLeast(1) >= 0.75f -> when (nativeLangCode) {
                "de" -> "Hervorragende Anpassungsfähigkeit! Du hast komplexe Herausforderungen auf Stufe $peakDifficulty erfolgreich gemeistert."
                "ru" -> "Великолепная адаптивность! Вы успешно справились со сложными задачами на уровне $peakDifficulty."
                "uk" -> "Чудова адаптивність! Ви успішно подолали складні виклики на рівні $peakDifficulty."
                else -> "Outstanding adaptability! You conquered complex challenges at Level $peakDifficulty."
            }
            reinfCount > 0 && reinfSuccess >= reinfCount -> when (nativeLangCode) {
                "de" -> "Starke Lerndynamik! Die Verstärkungsaufgaben haben deine Wissenslücken gezielt und nachhaltig geschlossen."
                "ru" -> "Отличная динамика! Закрепляющие вопросы помогли закрыть пробелы и закрепить знания."
                "uk" -> "Відмінна динаміка! Завдання на підкріплення допомогли закрити прогалини та закріпити знання."
                else -> "Strong learning dynamics! Reinforcement questions effectively solidified your fundamentals."
            }
            else -> when (nativeLangCode) {
                "de" -> "Solide Basis aufgebaut! Das System hat sich deinem Lerntempo angepasst. Weiter so!"
                "ru" -> "Прочная основа заложена! Система адаптировалась под ваш темп. Продолжайте тренировки!"
                "uk" -> "Міцна основа закладена! Система адаптувалася під ваш темп. Продовжуйте тренування!"
                else -> "Solid foundation built! The system adapted to your optimal learning pace."
            }
        }

        return AdaptiveQuizSummary(
            totalQuestions = total,
            totalCorrect = totalCorrect,
            startDifficulty = initialDifficulty,
            peakDifficulty = peakDifficulty,
            finalDifficulty = finalDiff,
            difficultyProgression = progression,
            reinforcementCount = reinfCount,
            reinforcementSuccessCount = reinfSuccess,
            challengesCount = challCount,
            challengesSuccessCount = challSuccess,
            score = score,
            diagnosisText = diag
        )
    }
}
