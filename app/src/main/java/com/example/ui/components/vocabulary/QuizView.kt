package com.example.ui.components.vocabulary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.vocabulary.AdaptiveQuizSummary
import com.example.data.vocabulary.QuizQuestion
import com.example.data.vocabulary.QuizQuestionType
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.util.AppLocalization

@Composable
fun QuizView(
    questions: List<QuizQuestion>,
    currentIndex: Int,
    selectedAnswer: Int?,
    isAnswerSubmitted: Boolean,
    score: Int,
    streak: Int,
    isQuizFinished: Boolean,
    onSelectAnswer: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier,
    nativeLangCode: String = "de",
    currentDifficulty: Int = 2,
    peakDifficulty: Int = 2,
    adaptiveFeedback: String? = null,
    totalQuestionsCount: Int = 12,
    quizSummary: AdaptiveQuizSummary? = null
) {
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }
    val scrollState = rememberScrollState()

    if (questions.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(strings.loadingQuizText, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        }
        return
    }

    if (isQuizFinished) {
        QuizCompletionCard(
            score = score,
            totalQuestions = totalQuestionsCount,
            quizSummary = quizSummary,
            onRestart = onRestartQuiz,
            modifier = modifier,
            strings = strings
        )
        return
    }

    val currentQuestion = questions.getOrNull(currentIndex) ?: questions.first()
    val totalDisplayCount = maxOf(totalQuestionsCount, questions.size)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Top Header: Progress, Adaptive Level & Streak
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Question progress counter
                Text(
                    text = "${strings.quizTitle} ${currentIndex + 1} ${strings.stageOf} $totalDisplayCount",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )

                // Right side: Adaptive Level, Streak & Score badges
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Adaptive Difficulty Badge
                    val (diffBg, diffTextColor) = getDifficultyColors(currentQuestion.difficultyLevel)
                    Surface(
                        color = diffBg,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = currentQuestion.difficultyLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = diffTextColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    if (streak > 1) {
                        Surface(
                            color = Color(0xFFFEF3C7),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = AccentAmber,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "$streak",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF92400E),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Surface(
                        color = PrimaryIndigoLight,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "$score Pkt",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryIndigo,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { ((currentIndex + 1).toFloat() / totalDisplayCount).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryIndigo,
                trackColor = Color(0xFFE2E8F0)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Dynamic Difficulty Mode Alert Banner
        if (currentQuestion.isReinforcement) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF0FDF4),
                border = BorderStroke(1.dp, Color(0xFF86EFAC))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🛡️", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = strings.reinforcementBadge,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )
                        Text(
                            text = strings.reinforcementSubtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF15803D)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        } else if (currentQuestion.isChallenge) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFF5F3FF),
                border = BorderStroke(1.dp, Color(0xFFDDD6FE))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "⚡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = strings.challengeBadge,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5B21B6)
                        )
                        Text(
                            text = strings.challengeSubtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6D28D9)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Question Prompt Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category & CEFR Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = currentQuestion.word.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        color = Color(0xFFEEF2FF),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "CEFR ${currentQuestion.word.cefrLevel}",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryIndigo,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Prompt Question Text
                Text(
                    text = currentQuestion.promptText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Main Display: Target Word or Sentence
                when (currentQuestion.questionType) {
                    QuizQuestionType.CONTEXT_CLOZE -> {
                        Surface(
                            color = Color(0xFFF8FAFC),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = currentQuestion.clozeSentence ?: currentQuestion.word.exampleSentence,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                    QuizQuestionType.TRANSLATION_TO_WORD -> {
                        Text(
                            text = currentQuestion.word.translation,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                    else -> {
                        // Standard WORD_TO_TRANSLATION or REINFORCEMENT_CHOICE
                        Text(
                            text = currentQuestion.word.word,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )

                        if (currentQuestion.word.phonetic.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "[${currentQuestion.word.phonetic}]",
                                style = MaterialTheme.typography.bodyMedium,
                                color = PrimaryIndigo
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        IconButton(
                            onClick = { onSpeak(currentQuestion.word.word) },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PrimaryIndigoLight)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen",
                                tint = PrimaryIndigo,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                // Reinforcement Memory Hint
                if (!currentQuestion.hintText.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = Color(0xFFFFFBEB),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Hint",
                                tint = AccentAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentQuestion.hintText,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF92400E),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Options List (4 interactive choices)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            currentQuestion.options.forEachIndexed { index, optionText ->
                val isSelected = (selectedAnswer == index)
                val isCorrectAnswer = (index == currentQuestion.correctIndex)

                val backgroundColor = when {
                    !isAnswerSubmitted && isSelected -> PrimaryIndigoLight
                    isAnswerSubmitted && isCorrectAnswer -> SuccessGreenLight
                    isAnswerSubmitted && isSelected && !isCorrectAnswer -> Color(0xFFFFECEB)
                    else -> Color.White
                }

                val borderColor = when {
                    !isAnswerSubmitted && isSelected -> PrimaryIndigo
                    isAnswerSubmitted && isCorrectAnswer -> SuccessGreen
                    isAnswerSubmitted && isSelected && !isCorrectAnswer -> AccentCoral
                    else -> Color(0xFFE2E8F0)
                }

                val textColor = when {
                    isAnswerSubmitted && isCorrectAnswer -> SuccessGreen
                    isAnswerSubmitted && isSelected && !isCorrectAnswer -> AccentCoral
                    else -> TextPrimary
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(enabled = !isAnswerSubmitted) { onSelectAnswer(index) }
                        .testTag("quiz_option_$index"),
                    shape = RoundedCornerShape(14.dp),
                    color = backgroundColor,
                    border = BorderStroke(1.5.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (isSelected || (isAnswerSubmitted && isCorrectAnswer)) FontWeight.Bold else FontWeight.Medium,
                            color = textColor,
                            modifier = Modifier.weight(1f)
                        )

                        if (isAnswerSubmitted) {
                            if (isCorrectAnswer) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Correct",
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(22.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Incorrect",
                                    tint = AccentCoral,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Adaptive Real-Time Feedback Card
        if (isAnswerSubmitted) {
            val isUserCorrect = (selectedAnswer == currentQuestion.correctIndex)

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = if (isUserCorrect) Color(0xFFF0FDF4) else Color(0xFFFEF2F2),
                border = BorderStroke(1.dp, if (isUserCorrect) Color(0xFF86EFAC) else Color(0xFFFECACA))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isUserCorrect) Icons.Default.CheckCircle else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isUserCorrect) SuccessGreen else AccentCoral,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isUserCorrect) "Richtig! +${10 * currentQuestion.difficultyLevel} Punkte" else "Richtige Antwort: ${currentQuestion.options.getOrNull(currentQuestion.correctIndex)}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isUserCorrect) Color(0xFF166534) else Color(0xFF991B1B)
                        )
                    }

                    if (!adaptiveFeedback.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = adaptiveFeedback,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isUserCorrect) Color(0xFF15803D) else Color(0xFFB91C1C),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (!currentQuestion.explanationText.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = Color.White.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = currentQuestion.explanationText,
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                color = TextSecondary,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Next Question / Finish Button
        Button(
            onClick = onNextQuestion,
            enabled = isAnswerSubmitted,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("quiz_continue_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (currentIndex + 1 < totalDisplayCount) strings.nextQuestion else strings.quizCompleted,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

private fun getDifficultyColors(level: Int): Pair<Color, Color> {
    return when (level) {
        1 -> Pair(Color(0xFFDCFCE7), Color(0xFF15803D)) // Green (A1)
        2 -> Pair(Color(0xFFCCFBF1), Color(0xFF0F766E)) // Teal (A2)
        3 -> Pair(Color(0xFFE0E7FF), Color(0xFF4338CA)) // Indigo (B1)
        4 -> Pair(Color(0xFFF3E8FF), Color(0xFF7E22CE)) // Purple (B2)
        else -> Pair(Color(0xFFFEF3C7), Color(0xFFB45309)) // Amber/Gold (C1/C2)
    }
}

@Composable
private fun QuizCompletionCard(
    score: Int,
    totalQuestions: Int,
    quizSummary: AdaptiveQuizSummary?,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
    strings: com.example.ui.util.AppUiStrings
) {
    val scrollState = rememberScrollState()
    val totalCount = maxOf(totalQuestions, quizSummary?.totalQuestions ?: 12)
    val percentage = ((score.toFloat() / (totalCount * 10).coerceAtLeast(1)) * 100).toInt().coerceIn(0, 100)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trophy Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFEF3C7)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = AccentAmber,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = strings.quizCompleted,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${strings.quizScore}: $score ($percentage%)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Adaptive Performance Report
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.adaptiveReportTitle,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            // Peak difficulty badge
                            val peak = quizSummary?.peakDifficulty ?: 2
                            val (peakBg, peakColor) = getDifficultyColors(peak)
                            Surface(
                                color = peakBg,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Max: Stufe $peak",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = peakColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Difficulty Level Trajectory Row
                        if (quizSummary != null && quizSummary.difficultyProgression.isNotEmpty()) {
                            Text(
                                text = strings.adaptiveProgressionTitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                quizSummary.difficultyProgression.forEach { level ->
                                    val (chipBg, chipTextColor) = getDifficultyColors(level)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(26.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(chipBg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "L$level",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = chipTextColor
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        // Metrics Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Reinforcements Metric
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = strings.reinforcementSuccessLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${quizSummary?.reinforcementSuccessCount ?: 0} / ${quizSummary?.reinforcementCount ?: 0}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }
                            }

                            // Challenges Metric
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = strings.challengesSuccessLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${quizSummary?.challengesSuccessCount ?: 0} / ${quizSummary?.challengesCount ?: 0}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryIndigo
                                    )
                                }
                            }
                        }

                        // Diagnostic text
                        if (quizSummary != null && quizSummary.diagnosisText.isNotBlank()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                color = Color(0xFFEEF2FF),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "💡 ${quizSummary.diagnosisText}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PrimaryIndigo,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onRestart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = strings.restartAdaptiveQuiz, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
