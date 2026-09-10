package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.models.SupportedLanguages
import com.example.data.vocabulary.VocabularyTiers
import com.example.ui.components.vocabulary.FlashcardView
import com.example.ui.components.vocabulary.PronunciationDrillView
import com.example.ui.components.vocabulary.QuizView
import com.example.ui.components.vocabulary.VerbExplorerView
import com.example.ui.components.vocabulary.VerbTableSheet
import com.example.ui.components.vocabulary.WordDeepDiveSheet
import com.example.ui.components.vocabulary.WordExplorerView
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.VocabularyMode
import com.example.ui.viewmodel.VocabularyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordLearningScreen(
    viewModel: VocabularyViewModel,
    modifier: Modifier = Modifier,
    onLanguageSelected: ((com.example.data.models.Language) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showLanguageDropdown by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "10,000 Words",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Language selector chip
                        Box {
                            Surface(
                                color = PrimaryIndigoLight,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .clickable { showLanguageDropdown = true }
                                    .testTag("vocabulary_language_picker")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${uiState.selectedLanguage.flag} ${uiState.selectedLanguage.name}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryIndigo
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.ExpandMore,
                                        contentDescription = "Select Language",
                                        tint = PrimaryIndigo,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            DropdownMenu(
                                expanded = showLanguageDropdown,
                                onDismissRequest = { showLanguageDropdown = false }
                            ) {
                                SupportedLanguages.forEach { language ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(language.flag, fontSize = 20.sp)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(language.name, fontWeight = FontWeight.Medium)
                                            }
                                        },
                                        onClick = {
                                            onLanguageSelected?.invoke(language) ?: viewModel.setLanguage(language)
                                            showLanguageDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8FAFC))
        ) {
            // Overall 10,000 Word Progress Header Card
            Surface(
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "10,000 Words Curriculum",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "${uiState.masteredWordsCount}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryIndigo
                                )
                                Text(
                                    text = " / 10,000 words mastered",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(bottom = 2.dp, start = 4.dp)
                                )
                            }
                        }

                        Surface(
                            color = Color(0xFFFEF3C7),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = AccentAmber,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Stage ${uiState.currentStage} / 100",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { (uiState.masteredWordsCount.toFloat() / 10000f).coerceIn(0.01f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = SuccessGreen,
                        trackColor = Color(0xFFE2E8F0)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tier Badges Carousel (A1 to C2)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(VocabularyTiers) { tier ->
                            val isSelected = (uiState.currentTier.tierNumber == tier.tierNumber)
                            Surface(
                                color = if (isSelected) PrimaryIndigo else Color(0xFFF1F5F9),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .clickable { viewModel.selectTier(tier) }
                                    .testTag("tier_chip_${tier.tierNumber}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = tier.emoji, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${tier.cefrLevel} (#${tier.startRank}-${tier.endRank})",
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

            // Learning Mode Selector Tabs
            PrimaryTabRow(
                selectedTabIndex = uiState.currentMode.ordinal,
                containerColor = Color.White,
                contentColor = PrimaryIndigo
            ) {
                Tab(
                    selected = uiState.currentMode == VocabularyMode.FLASHCARDS,
                    onClick = { viewModel.setMode(VocabularyMode.FLASHCARDS) },
                    text = { Text("Cards", fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.ViewCarousel, contentDescription = "Flashcards", modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_flashcards")
                )
                Tab(
                    selected = uiState.currentMode == VocabularyMode.EXPLORER,
                    onClick = { viewModel.setMode(VocabularyMode.EXPLORER) },
                    text = { Text("Explorer", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.School, contentDescription = "Explorer", modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_explorer")
                )
                Tab(
                    selected = uiState.currentMode == VocabularyMode.VERBS,
                    onClick = { viewModel.setMode(VocabularyMode.VERBS) },
                    text = { Text("Verbs", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.TableChart, contentDescription = "Verbs", modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_verbs")
                )
                Tab(
                    selected = uiState.currentMode == VocabularyMode.QUIZ,
                    onClick = { viewModel.setMode(VocabularyMode.QUIZ) },
                    text = { Text("Quiz", fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.Quiz, contentDescription = "Quiz", modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_quiz")
                )
                Tab(
                    selected = uiState.currentMode == VocabularyMode.PRONUNCIATION,
                    onClick = { viewModel.setMode(VocabularyMode.PRONUNCIATION) },
                    text = { Text("Speech", fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                    icon = { Icon(Icons.Default.GraphicEq, contentDescription = "Pronunciation", modifier = Modifier.size(18.dp)) },
                    modifier = Modifier.testTag("tab_pronunciation")
                )
            }

            // Content Area based on mode
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.isLoading && uiState.words.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryIndigo)
                    }
                } else {
                    when (uiState.currentMode) {
                        VocabularyMode.FLASHCARDS -> {
                            val currentWord = viewModel.getCurrentFlashcardWord()
                            FlashcardView(
                                word = currentWord,
                                currentIndex = uiState.currentFlashcardIndex,
                                totalCount = uiState.words.size,
                                isFlipped = uiState.isCardFlipped,
                                onFlip = { viewModel.flipCard() },
                                onPrevious = { viewModel.previousCard() },
                                onNext = { viewModel.nextCard() },
                                onMarkMastery = { masteryLevel -> viewModel.markCurrentCardMastery(masteryLevel) },
                                onToggleBookmark = { word -> viewModel.toggleBookmark(word) },
                                onSpeak = { text -> viewModel.speakWord(text) },
                                onAiDeepDive = { word -> viewModel.openWordDeepDive(word) }
                            )
                        }

                        VocabularyMode.EXPLORER -> {
                            WordExplorerView(
                                words = uiState.filteredWords,
                                searchQuery = uiState.searchQuery,
                                filterStatus = uiState.filterStatus,
                                currentStage = uiState.currentStage,
                                totalStages = uiState.totalStages,
                                onSearchChanged = { viewModel.onSearchQueryChanged(it) },
                                onFilterChanged = { viewModel.setFilterStatus(it) },
                                onSelectStage = { viewModel.selectStage(it) },
                                onJumpToRank = { viewModel.jumpToRank(it) },
                                onSpeak = { text -> viewModel.speakWord(text) },
                                onToggleBookmark = { word -> viewModel.toggleBookmark(word) },
                                onToggleMastery = { word ->
                                    val newM = if (word.masteryLevel >= 3) 0 else 3
                                    viewModel.markCurrentCardMastery(newM)
                                },
                                onAiDeepDive = { word -> viewModel.openWordDeepDive(word) }
                            )
                        }

                        VocabularyMode.VERBS -> {
                            VerbExplorerView(
                                verbs = uiState.filteredVerbsList,
                                searchQuery = uiState.verbSearchQuery,
                                onSearchChanged = { viewModel.onVerbSearchChanged(it) },
                                onOpenVerbTable = { verb -> viewModel.openVerbTable(verb) },
                                onSpeak = { text -> viewModel.speakWord(text) },
                                onToggleBookmark = { word -> viewModel.toggleBookmark(word) },
                                onAiDeepDive = { word -> viewModel.openWordDeepDive(word) }
                            )
                        }

                        VocabularyMode.QUIZ -> {
                            val currentQ = uiState.quizQuestions.getOrNull(uiState.currentQuizIndex)
                            QuizView(
                                questions = uiState.quizQuestions,
                                currentIndex = uiState.currentQuizIndex,
                                selectedAnswer = uiState.selectedQuizAnswer,
                                isAnswerSubmitted = uiState.isQuizAnswerSubmitted,
                                score = uiState.quizScore,
                                streak = uiState.quizStreak,
                                isQuizFinished = uiState.isQuizFinished,
                                onSelectAnswer = { index -> viewModel.selectQuizAnswer(index) },
                                onNextQuestion = { viewModel.nextQuizQuestion() },
                                onRestartQuiz = { viewModel.startNewQuiz() },
                                onSpeak = { text -> viewModel.speakWord(text) }
                            )
                        }

                        VocabularyMode.PRONUNCIATION -> {
                            val currentWord = viewModel.getCurrentFlashcardWord() ?: uiState.words.firstOrNull()
                            PronunciationDrillView(
                                word = currentWord,
                                isListening = uiState.isDrillListening,
                                recognizedText = uiState.drillRecognizedText,
                                feedback = uiState.drillFeedback,
                                score = uiState.drillScore,
                                onStartListening = {
                                    if (currentWord != null) viewModel.startPronunciationListening(currentWord)
                                },
                                onStopListening = { viewModel.stopPronunciationListening() },
                                onSpeak = { text -> viewModel.speakWord(text) },
                                onNextWord = { viewModel.nextCard() }
                            )
                        }
                    }
                }
            }
        }

        // AI Word Deep-Dive Sheet
        if (uiState.deepDiveWord != null) {
            WordDeepDiveSheet(
                word = uiState.deepDiveWord,
                deepDiveContent = uiState.deepDiveContent,
                isLoading = uiState.isDeepDiveLoading,
                onDismiss = { viewModel.closeWordDeepDive() },
                onSpeak = { text -> viewModel.speakWord(text) },
                onToggleBookmark = { word -> viewModel.toggleBookmark(word) }
            )
        }

        // Verb Conjugation Table Sheet
        if (uiState.selectedVerbForTable != null) {
            VerbTableSheet(
                word = uiState.selectedVerbForTable,
                onDismiss = { viewModel.closeVerbTable() },
                onSpeak = { text -> viewModel.speakWord(text) }
            )
        }
    }
}
