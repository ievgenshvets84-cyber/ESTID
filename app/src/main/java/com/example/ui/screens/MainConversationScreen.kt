package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.models.Language
import com.example.data.models.MessageSender
import com.example.ui.components.LanguageSelectorSheet
import com.example.ui.components.MessageItem
import com.example.ui.components.SavedVocabSheet
import com.example.ui.components.ScenarioGoalCard
import com.example.ui.components.ScenarioSelectorSheet
import com.example.ui.components.SuggestionChipsRow
import com.example.ui.components.TypingIndicatorBubble
import com.example.ui.components.VoicePartnerSheet
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoDark
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SecondaryTealLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.LanguagePartnerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainConversationScreen(
    viewModel: LanguagePartnerViewModel,
    modifier: Modifier = Modifier,
    onNavigateToWordLearning: (() -> Unit)? = null,
    onNavigateToTranslator: (() -> Unit)? = null,
    onLanguageSelected: ((Language) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    var inputText by remember { mutableStateOf("") }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var showScenarioSheet by remember { mutableStateOf(false) }
    var showSavedVocabSheet by remember { mutableStateOf(false) }

    // Audio recording permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startVoiceRecording()
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Microphone permission is needed for voice practice")
            }
        }
    }

    // Auto-scroll when new messages arrive
    LaunchedEffect(uiState.messages.size, uiState.isGenerating) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    // Show error snackbar if any
    LaunchedEffect(uiState.userErrorMessage) {
        uiState.userErrorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissError()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Language Chip
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PrimaryIndigoLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { showLanguageSheet = true }
                                .testTag("top_language_picker_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = uiState.selectedLanguage.flag, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = uiState.selectedLanguage.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryIndigo
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PrimaryIndigo
                                ) {
                                    Text(
                                        text = uiState.selectedLevel.levelCode,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }

                        // Streak Badge
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFF7ED),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFED7AA)),
                            modifier = Modifier.testTag("streak_badge")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🔥", fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${uiState.stats.streakDays}d",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentCoral
                                )
                            }
                        }
                    }
                },
                actions = {
                    // Auto-play sound toggle
                    IconButton(
                        onClick = { viewModel.toggleAutoPlayAudio() },
                        modifier = Modifier.testTag("toggle_sound_button")
                    ) {
                        Icon(
                            imageVector = if (uiState.autoPlayAudio) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                            contentDescription = "Toggle Audio Auto-play",
                            tint = if (uiState.autoPlayAudio) PrimaryIndigo else TextSecondary
                        )
                    }

                    // Saved Vocab Sheet trigger
                    IconButton(
                        onClick = { showSavedVocabSheet = true },
                        modifier = Modifier.testTag("saved_vocab_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (uiState.savedWords.isNotEmpty()) {
                                    Badge(containerColor = AccentCoral) {
                                        Text("${uiState.savedWords.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Saved Vocabulary",
                                tint = PrimaryIndigo
                            )
                        }
                    }

                    // 10,000 Words Curriculum Button
                    IconButton(
                        onClick = { onNavigateToWordLearning?.invoke() },
                        modifier = Modifier.testTag("open_10000_words_top_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "10,000 Word Learning",
                            tint = PrimaryIndigo
                        )
                    }

                    // Interactive Translator Button
                    IconButton(
                        onClick = { onNavigateToTranslator?.invoke() },
                        modifier = Modifier.testTag("open_translator_top_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Interactive Translator",
                            tint = PrimaryIndigo
                        )
                    }

                    // Reset conversation
                    IconButton(
                        onClick = { viewModel.clearChat() },
                        modifier = Modifier.testTag("reset_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Restart Scenario",
                            tint = TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)))
            ) {
                // Suggested Quick Responses from latest AI turn
                val latestAiMessage = uiState.messages.lastOrNull { it.sender == MessageSender.AI }
                if (latestAiMessage != null && latestAiMessage.suggestedReplies.isNotEmpty()) {
                    SuggestionChipsRow(
                        suggestions = latestAiMessage.suggestedReplies,
                        onSelectSuggestion = { suggestion ->
                            viewModel.sendMessage(suggestion)
                        }
                    )
                }

                // Chat Input Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Voice Mode Launcher FAB
                    Surface(
                        shape = CircleShape,
                        color = PrimaryIndigoLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.toggleVoiceMode() }
                            .testTag("launch_voice_partner_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Live Voice Partner Mode",
                                tint = PrimaryIndigo,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Text Input Field
                    OutlinedTextField(
                        value = if (uiState.isListening && uiState.partialSpeech.isNotBlank()) uiState.partialSpeech else inputText,
                        onValueChange = { inputText = it },
                        placeholder = {
                            Text(
                                text = if (uiState.isListening) "Listening in ${uiState.selectedLanguage.name}..." else "Type in ${uiState.selectedLanguage.name}...",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("message_input_field"),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryIndigo,
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC)
                        ),
                        singleLine = true,
                        trailingIcon = {
                            // Mic Button inside TextField
                            IconButton(
                                onClick = {
                                    if (uiState.isListening) {
                                        viewModel.stopVoiceRecording()
                                    } else {
                                        val hasPermission = ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.RECORD_AUDIO
                                        ) == PackageManager.PERMISSION_GRANTED
                                        if (hasPermission) {
                                            viewModel.startVoiceRecording()
                                        } else {
                                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                        }
                                    }
                                },
                                modifier = Modifier.testTag("mic_input_button")
                            ) {
                                Icon(
                                    imageVector = if (uiState.isListening) Icons.Default.Stop else Icons.Default.Mic,
                                    contentDescription = "Speak Input",
                                    tint = if (uiState.isListening) AccentCoral else PrimaryIndigo
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Send Button
                    Surface(
                        shape = CircleShape,
                        color = if (inputText.isNotBlank() && !uiState.isGenerating) PrimaryIndigo else Color(0xFFE2E8F0),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .clickable(enabled = inputText.isNotBlank() && !uiState.isGenerating) {
                                viewModel.sendMessage(inputText)
                                inputText = ""
                            }
                            .testTag("send_message_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Message",
                                tint = if (inputText.isNotBlank() && !uiState.isGenerating) Color.White else Color(0xFF94A3B8),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
        ) {
            // Scenario Goals Card at top of conversation
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                ScenarioGoalCard(
                    scenario = uiState.selectedScenario,
                    turnsCount = uiState.messages.count { it.sender == MessageSender.USER },
                    onClick = { showScenarioSheet = true }
                )
            }

            // Message List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(uiState.messages, key = { it.id }) { msg ->
                    val isSpeaking = uiState.isTtsSpeaking && uiState.currentSpeakingId == msg.id
                    val isSaved = uiState.savedWords.any { it.contextSentence.contains(msg.text) || it.word == msg.text }

                    MessageItem(
                        message = msg,
                        isSpeakingThis = isSpeaking,
                        showTranslationGlobal = uiState.showTranslations,
                        showPronunciationGlobal = uiState.showPronunciations,
                        isSaved = isSaved,
                        onSpeakClick = {
                            if (isSpeaking) {
                                viewModel.stopSpeaking()
                            } else {
                                viewModel.speakMessage(msg)
                            }
                        },
                        onSaveWordClick = { word, trans, ctx ->
                            viewModel.saveVocabulary(word, trans, ctx)
                        }
                    )
                }

                if (uiState.isGenerating) {
                    item {
                        TypingIndicatorBubble()
                    }
                }
            }
        }
    }

    // Modal Sheets
    if (showLanguageSheet) {
        LanguageSelectorSheet(
            currentLanguage = uiState.selectedLanguage,
            currentLevel = uiState.selectedLevel,
            onSelectLanguage = { lang ->
                onLanguageSelected?.invoke(lang) ?: viewModel.selectLanguage(lang)
            },
            onSelectLevel = { viewModel.selectLevel(it) },
            onDismiss = { showLanguageSheet = false }
        )
    }

    if (showScenarioSheet) {
        ScenarioSelectorSheet(
            currentScenario = uiState.selectedScenario,
            selectedLanguage = uiState.selectedLanguage,
            onSelectScenario = { viewModel.selectScenario(it) },
            onSpeakPhrase = { text ->
                viewModel.ttsManager.speak(text, uiState.selectedLanguage.ttsLocale, uiState.speechSpeed)
            },
            onPracticePhraseInChat = { phrase ->
                viewModel.sendMessage(phrase)
            },
            onDismiss = { showScenarioSheet = false }
        )
    }

    if (showSavedVocabSheet) {
        SavedVocabSheet(
            savedWords = uiState.savedWords,
            onSpeakWord = { word ->
                viewModel.ttsManager.speak(word, uiState.selectedLanguage.ttsLocale, uiState.speechSpeed)
            },
            onDeleteWord = { viewModel.deleteVocabulary(it) },
            onOpenWordLearning = {
                showSavedVocabSheet = false
                onNavigateToWordLearning?.invoke()
            },
            onDismiss = { showSavedVocabSheet = false }
        )
    }

    // Live Voice Partner Fullscreen Mode
    if (uiState.isVoiceModeActive) {
        val lastAiMessage = uiState.messages.lastOrNull { it.sender == MessageSender.AI }?.text ?: ""
        VoicePartnerSheet(
            language = uiState.selectedLanguage,
            scenario = uiState.selectedScenario,
            isListening = uiState.isListening,
            isSpeaking = uiState.isTtsSpeaking,
            isGenerating = uiState.isGenerating,
            rmsDb = uiState.rmsDb,
            partialSpeech = uiState.partialSpeech,
            lastAiReply = lastAiMessage,
            speechSpeed = uiState.speechSpeed,
            onSpeechSpeedChange = { viewModel.setSpeechSpeed(it) },
            onStartListening = {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                if (hasPermission) {
                    viewModel.startVoiceRecording()
                } else {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            onStopListening = { viewModel.stopVoiceRecording() },
            onStopSpeaking = { viewModel.stopSpeaking() },
            onDismiss = { viewModel.setVoiceMode(false) }
        )
    }
}
