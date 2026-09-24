package com.example.ui.components.vocabulary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.vocabulary.PhraseCategory
import com.example.data.vocabulary.PhraseItem
import com.example.data.vocabulary.PhraseQuizQuestion
import com.example.data.vocabulary.PhraseSubMode
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.util.AppLocalization

@Composable
fun PhraseLearningView(
    phrases: List<PhraseItem>,
    allPhrases: List<PhraseItem>,
    selectedCategory: PhraseCategory,
    searchQuery: String,
    subMode: PhraseSubMode,
    currentTrainerIndex: Int,
    isTrainerFlipped: Boolean,
    quizQuestions: List<PhraseQuizQuestion>,
    currentQuizIndex: Int,
    selectedQuizAnswer: Int?,
    isQuizSubmitted: Boolean,
    quizScore: Int,
    isQuizFinished: Boolean,
    onSelectCategory: (PhraseCategory) -> Unit,
    onSearchChanged: (String) -> Unit,
    onSelectSubMode: (PhraseSubMode) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onSpeak: (String) -> Unit,
    onAiDeepDive: (PhraseItem) -> Unit,
    onNextTrainerCard: () -> Unit,
    onPrevTrainerCard: () -> Unit,
    onFlipTrainerCard: () -> Unit,
    onAnswerQuiz: (Int) -> Unit,
    onNextQuizQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    nativeLangCode: String = "de",
    modifier: Modifier = Modifier
) {
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Sub-Mode Switcher: Katalog | Trainer | Quiz
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Katalog Tab
                val isBrowse = subMode == PhraseSubMode.BROWSE
                Surface(
                    color = if (isBrowse) PrimaryIndigo else Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectSubMode(PhraseSubMode.BROWSE) }
                        .testTag("phrase_submode_browse")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = if (isBrowse) Color.White else TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = strings.phrasesSubModeBrowse,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isBrowse) FontWeight.Bold else FontWeight.Medium,
                            color = if (isBrowse) Color.White else TextPrimary
                        )
                    }
                }

                // Trainer Tab
                val isTrainer = subMode == PhraseSubMode.TRAINER
                Surface(
                    color = if (isTrainer) PrimaryIndigo else Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectSubMode(PhraseSubMode.TRAINER) }
                        .testTag("phrase_submode_trainer")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewCarousel,
                            contentDescription = null,
                            tint = if (isTrainer) Color.White else TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = strings.phrasesSubModeTrainer,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isTrainer) FontWeight.Bold else FontWeight.Medium,
                            color = if (isTrainer) Color.White else TextPrimary
                        )
                    }
                }

                // Quiz Tab
                val isQuiz = subMode == PhraseSubMode.QUIZ
                Surface(
                    color = if (isQuiz) PrimaryIndigo else Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectSubMode(PhraseSubMode.QUIZ) }
                        .testTag("phrase_submode_quiz")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = if (isQuiz) Color.White else TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = strings.phrasesSubModeQuiz,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isQuiz) FontWeight.Bold else FontWeight.Medium,
                            color = if (isQuiz) Color.White else TextPrimary
                        )
                    }
                }
            }
        }

        // Sub-Mode Content
        when (subMode) {
            PhraseSubMode.BROWSE -> {
                PhraseBrowseScreen(
                    phrases = phrases,
                    allPhrases = allPhrases,
                    selectedCategory = selectedCategory,
                    searchQuery = searchQuery,
                    onSelectCategory = onSelectCategory,
                    onSearchChanged = onSearchChanged,
                    onToggleBookmark = onToggleBookmark,
                    onSpeak = onSpeak,
                    onAiDeepDive = onAiDeepDive,
                    strings = strings
                )
            }
            PhraseSubMode.TRAINER -> {
                PhraseTrainerScreen(
                    phrases = phrases.ifEmpty { allPhrases },
                    currentIndex = currentTrainerIndex,
                    isFlipped = isTrainerFlipped,
                    onNext = onNextTrainerCard,
                    onPrev = onPrevTrainerCard,
                    onFlip = onFlipTrainerCard,
                    onSpeak = onSpeak,
                    onAiDeepDive = onAiDeepDive,
                    onToggleBookmark = onToggleBookmark,
                    strings = strings
                )
            }
            PhraseSubMode.QUIZ -> {
                PhraseQuizScreen(
                    questions = quizQuestions,
                    currentIndex = currentQuizIndex,
                    selectedAnswer = selectedQuizAnswer,
                    isSubmitted = isQuizSubmitted,
                    score = quizScore,
                    isFinished = isQuizFinished,
                    onAnswer = onAnswerQuiz,
                    onNext = onNextQuizQuestion,
                    onRestart = onRestartQuiz,
                    onSpeak = onSpeak,
                    strings = strings
                )
            }
        }
    }
}

@Composable
private fun PhraseBrowseScreen(
    phrases: List<PhraseItem>,
    allPhrases: List<PhraseItem>,
    selectedCategory: PhraseCategory,
    searchQuery: String,
    onSelectCategory: (PhraseCategory) -> Unit,
    onSearchChanged: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onSpeak: (String) -> Unit,
    onAiDeepDive: (PhraseItem) -> Unit,
    strings: com.example.ui.util.AppUiStrings
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search & Category Chips
        Surface(color = Color.White) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChanged,
                    placeholder = { Text(strings.searchPhrasesPlaceholder, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChanged("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryIndigo,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        focusedContainerColor = Color(0xFFF8FAFC),
                        unfocusedContainerColor = Color(0xFFF8FAFC)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("phrase_search_field")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Categories Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val categories = listOf(
                        PhraseCategory.ALL to "Alle (${allPhrases.size})",
                        PhraseCategory.IDIOMS to "Redewendungen (${allPhrases.count { it.isIdiom }})",
                        PhraseCategory.DAILY to "Alltag",
                        PhraseCategory.TRAVEL to "Reisen",
                        PhraseCategory.DINING to "Restaurant",
                        PhraseCategory.BUSINESS to "Beruf",
                        PhraseCategory.FEELINGS to "Gefühle",
                        PhraseCategory.EMERGENCY to "Hilfe",
                        PhraseCategory.BOOKMARKED to "Gemerkt (${allPhrases.count { it.isBookmarked }})"
                    )

                    items(categories) { (cat, label) ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            color = if (isSelected) PrimaryIndigo else Color(0xFFF1F5F9),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .clickable { onSelectCategory(cat) }
                                .testTag("phrase_category_${cat.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(cat.emoji, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // List of Phrases
        if (phrases.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Keine passenden Phrasen gefunden",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Versuche einen anderen Suchbegriff oder eine andere Kategorie.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(phrases, key = { it.id }) { item ->
                    PhraseCardItem(
                        item = item,
                        onSpeak = onSpeak,
                        onToggleBookmark = onToggleBookmark,
                        onAiDeepDive = onAiDeepDive,
                        strings = strings
                    )
                }
            }
        }
    }
}

@Composable
fun PhraseCardItem(
    item: PhraseItem,
    onSpeak: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onAiDeepDive: (PhraseItem) -> Unit,
    strings: com.example.ui.util.AppUiStrings
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("phrase_card_${item.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Category Badge + Bookmark + Audio
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (item.isIdiom) Color(0xFFFEF3C7) else Color(0xFFEEF2FF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.category.emoji, fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (item.isIdiom) strings.phraseBadgeIdiom else strings.phraseBadgeDaily,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (item.isIdiom) Color(0xFF92400E) else PrimaryIndigo
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Audio Button
                IconButton(
                    onClick = { onSpeak(item.phrase) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(PrimaryIndigoLight.copy(alpha = 0.5f), CircleShape)
                        .testTag("speak_phrase_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Speak",
                        tint = PrimaryIndigo,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Bookmark Button
                IconButton(
                    onClick = { onToggleBookmark(item.id) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (item.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (item.isBookmarked) AccentAmber else Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Phrase text
            Text(
                text = item.phrase,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            // Phonetic
            if (item.phonetic.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "[ ${item.phonetic} ]",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Literal meaning (Wörtliche Übersetzung)
            if (item.literalMeaning.isNotBlank()) {
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.literalMeaningLabel,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.literalMeaning,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF334155)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Figurative / Idiomatic Meaning
            Surface(
                color = Color(0xFFEEF2FF),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = PrimaryIndigo,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.meaning,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryIndigo
                    )
                }
            }

            // Example Sentence
            if (item.exampleSentence.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = strings.phraseContextLabel,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "„${item.exampleSentence}“",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                Text(
                    text = "→ ${item.exampleTranslation}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            // Cultural Tip
            if (item.culturalTip.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFFBEB), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("💡", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.culturalTip,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // KI-Kulturanalyse button
            OutlinedButton(
                onClick = { onAiDeepDive(item) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryIndigo
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("phrase_ai_analysis_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = AccentAmber
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = strings.explainWithAiButton,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PhraseTrainerScreen(
    phrases: List<PhraseItem>,
    currentIndex: Int,
    isFlipped: Boolean,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onFlip: () -> Unit,
    onSpeak: (String) -> Unit,
    onAiDeepDive: (PhraseItem) -> Unit,
    onToggleBookmark: (String) -> Unit,
    strings: com.example.ui.util.AppUiStrings
) {
    if (phrases.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Keine Phrasen zum Trainieren vorhanden.")
        }
        return
    }

    val currentPhrase = phrases[currentIndex % phrases.size]
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "trainer_flip"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top status & progress
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentIndex + 1} / ${phrases.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryIndigo
                )
                Surface(
                    color = if (currentPhrase.isIdiom) Color(0xFFFEF3C7) else Color(0xFFEEF2FF),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (currentPhrase.isIdiom) strings.phraseBadgeIdiom else strings.phraseBadgeDaily,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (currentPhrase.isIdiom) Color(0xFF92400E) else PrimaryIndigo
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { ((currentIndex + 1).toFloat() / phrases.size.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryIndigo,
                trackColor = Color(0xFFE2E8F0)
            )
        }

        // 3D Flippable Flashcard
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clickable { onFlip() }
                .testTag("phrase_trainer_card")
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                if (rotation <= 90f) {
                    // FRONT OF CARD: Target Phrase
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = currentPhrase.category.emoji,
                            fontSize = 36.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = currentPhrase.phrase,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = TextPrimary
                        )
                        if (currentPhrase.phonetic.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "[ ${currentPhrase.phonetic} ]",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF64748B)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        IconButton(
                            onClick = { onSpeak(currentPhrase.phrase) },
                            modifier = Modifier
                                .size(52.dp)
                                .background(PrimaryIndigoLight.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Audio",
                                tint = PrimaryIndigo,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Tippen zum Aufdecken 👆",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary
                        )
                    }
                } else {
                    // BACK OF CARD: Meaning & Breakdown
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationY = 180f },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = strings.idiomaticMeaningLabel,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryIndigo
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentPhrase.meaning,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = TextPrimary
                        )

                        if (currentPhrase.literalMeaning.isNotBlank()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Surface(
                                color = Color(0xFFF1F5F9),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = strings.literalMeaningLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF64748B)
                                    )
                                    Text(
                                        text = "„${currentPhrase.literalMeaning}“",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontStyle = FontStyle.Italic,
                                        color = Color(0xFF334155)
                                    )
                                }
                            }
                        }

                        if (currentPhrase.exampleSentence.isNotBlank()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "„${currentPhrase.exampleSentence}“",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = currentPhrase.exampleTranslation,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { onAiDeepDive(currentPhrase) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(strings.explainWithAiButton)
                        }
                    }
                }
            }
        }

        // Bottom Controls: Zurück | Umdrehen | Nächste
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onPrev,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(strings.phraseTrainerPrev)
            }

            Button(
                onClick = onFlip,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64748B)),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(strings.phraseTrainerFlip)
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(strings.phraseTrainerNext)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun PhraseQuizScreen(
    questions: List<PhraseQuizQuestion>,
    currentIndex: Int,
    selectedAnswer: Int?,
    isSubmitted: Boolean,
    score: Int,
    isFinished: Boolean,
    onAnswer: (Int) -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onSpeak: (String) -> Unit,
    strings: com.example.ui.util.AppUiStrings
) {
    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nicht genügend Redewendungen für ein Quiz vorhanden.")
        }
        return
    }

    if (isFinished) {
        // Quiz Completed Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🎉", fontSize = 56.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quiz abgeschlossen!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Du hast $score von ${questions.size} Fragen richtig beantwortet.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRestart,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Erneut spielen", fontWeight = FontWeight.Bold)
            }
        }
        return
    }

    val currentQ = questions[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Progress header
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Frage ${currentIndex + 1} von ${questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryIndigo
                )
                Text(
                    text = "Punkte: $score",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = SuccessGreen
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { ((currentIndex + 1).toFloat() / questions.size.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = PrimaryIndigo,
                trackColor = Color(0xFFE2E8F0)
            )
        }

        // Question card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Was bedeutet diese Redewendung?",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "„${currentQ.phrase.phrase}“",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { onSpeak(currentQ.phrase.phrase) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak",
                            tint = PrimaryIndigo
                        )
                    }
                }
                if (currentQ.phrase.phonetic.isNotBlank()) {
                    Text(
                        text = "[ ${currentQ.phrase.phonetic} ]",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        // Multiple choice options
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            currentQ.options.forEachIndexed { index, option ->
                val isSelected = selectedAnswer == index
                val isCorrect = index == currentQ.correctIndex

                val cardColor = when {
                    !isSubmitted && isSelected -> Color(0xFFEEF2FF)
                    isSubmitted && isCorrect -> Color(0xFFDCFCE7) // Green
                    isSubmitted && isSelected && !isCorrect -> Color(0xFFFEE2E2) // Red
                    else -> Color.White
                }

                val borderColor = when {
                    !isSubmitted && isSelected -> PrimaryIndigo
                    isSubmitted && isCorrect -> Color(0xFF16A34A)
                    isSubmitted && isSelected && !isCorrect -> Color(0xFFDC2626)
                    else -> Color(0xFFE2E8F0)
                }

                Surface(
                    color = cardColor,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isSubmitted) { onAnswer(index) }
                        .testTag("phrase_quiz_option_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${('A' + index)}.",
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected || (isSubmitted && isCorrect)) PrimaryIndigo else TextSecondary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = TextPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        if (isSubmitted && isCorrect) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A))
                        }
                    }
                }
            }
        }

        // Bottom Action (Next button / Explanation)
        Column(modifier = Modifier.fillMaxWidth()) {
            if (isSubmitted) {
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentQ.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF334155),
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("phrase_quiz_next_button")
                ) {
                    Text(if (currentIndex + 1 < questions.size) "Nächste Frage →" else "Ergebnis anzeigen 🎉")
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}
