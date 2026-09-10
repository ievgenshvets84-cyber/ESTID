package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.ChatMessage
import com.example.data.models.MessageSender
import com.example.data.models.PracticeScenario
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.FeedbackCardBg
import com.example.ui.theme.FeedbackCardBorder
import com.example.ui.theme.PartnerBubbleBg
import com.example.ui.theme.PartnerBubbleBorder
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.UserBubbleBg

@Composable
fun MessageItem(
    message: ChatMessage,
    isSpeakingThis: Boolean,
    showTranslationGlobal: Boolean,
    showPronunciationGlobal: Boolean,
    isSaved: Boolean,
    onSpeakClick: () -> Unit,
    onSaveWordClick: (word: String, translation: String, context: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isTranslationExpanded by remember { mutableStateOf(showTranslationGlobal) }

    if (message.sender == MessageSender.USER) {
        UserMessageBubble(message = message, modifier = modifier)
    } else {
        AiMessageBubble(
            message = message,
            isSpeaking = isSpeakingThis,
            isTranslationVisible = isTranslationExpanded || showTranslationGlobal,
            showPronunciation = showPronunciationGlobal,
            isSaved = isSaved,
            onToggleTranslation = { isTranslationExpanded = !isTranslationExpanded },
            onSpeakClick = onSpeakClick,
            onSaveWordClick = onSaveWordClick,
            modifier = modifier
        )
    }
}

@Composable
fun UserMessageBubble(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 18.dp, topEnd = 4.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
            color = UserBubbleBg,
            shadowElevation = 1.dp,
            modifier = Modifier
                .widthIn(max = 300.dp)
                .testTag("user_message_bubble")
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AiMessageBubble(
    message: ChatMessage,
    isSpeaking: Boolean,
    isTranslationVisible: Boolean,
    showPronunciation: Boolean,
    isSaved: Boolean,
    onToggleTranslation: () -> Unit,
    onSpeakClick: () -> Unit,
    onSaveWordClick: (word: String, translation: String, context: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        // AI Partner Avatar badge
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PrimaryIndigoLight)
                .border(1.5.dp, PrimaryIndigo, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✨",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.widthIn(max = 320.dp)) {
            Surface(
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
                color = PartnerBubbleBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, PartnerBubbleBorder),
                shadowElevation = 0.5.dp,
                modifier = Modifier.testTag("ai_message_bubble")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Pronunciation phonetic guide (if available and enabled)
                    if (showPronunciation && !message.pronunciation.isNullOrBlank()) {
                        Text(
                            text = message.pronunciation,
                            color = SecondaryTeal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    // Main Target Language Speech
                    Text(
                        text = message.text,
                        color = TextPrimary,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    // English Translation
                    AnimatedVisibility(visible = isTranslationVisible && !message.translation.isNullOrBlank()) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = message.translation ?: "",
                                    color = TextSecondary,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Audio & Interactive Controls Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Speaker Play button
                        AudioPlayPill(
                            isSpeaking = isSpeaking,
                            onClick = onSpeakClick
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Translation toggle button
                            IconButton(
                                onClick = onToggleTranslation,
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("toggle_translation_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Translate,
                                    contentDescription = "Toggle Translation",
                                    tint = if (isTranslationVisible) PrimaryIndigo else TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            // Bookmark word action
                            IconButton(
                                onClick = {
                                    val cleanWord = message.text.take(40)
                                    val trans = message.translation ?: "Expression"
                                    onSaveWordClick(cleanWord, trans, message.text)
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("save_word_button")
                            ) {
                                Icon(
                                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Save to Vocabulary",
                                    tint = if (isSaved) AccentCoral else TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Real-time Grammar Feedback & Coaching Card
            if (!message.grammarFeedback.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                GrammarCoachCard(
                    feedback = message.grammarFeedback,
                    betterAlternative = message.betterAlternative
                )
            }
        }
    }
}

@Composable
fun AudioPlayPill(
    isSpeaking: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "audio_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isSpeaking) PrimaryIndigo else Color(0xFFF1F5F9),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("audio_play_pill")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isSpeaking) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Outlined.VolumeUp,
                contentDescription = "Play Audio",
                tint = if (isSpeaking) Color.White else PrimaryIndigo,
                modifier = Modifier
                    .size(16.dp)
                    .scale(if (isSpeaking) pulseScale else 1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isSpeaking) "Speaking..." else "Listen",
                color = if (isSpeaking) Color.White else PrimaryIndigo,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun GrammarCoachCard(
    feedback: String,
    betterAlternative: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = FeedbackCardBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, FeedbackCardBorder),
        modifier = modifier
            .fillMaxWidth()
            .testTag("grammar_feedback_card")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = SuccessGreenDark,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Coach Feedback",
                    color = SuccessGreenDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedback,
                color = Color(0xFF1E293B),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            if (!betterAlternative.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Column {
                        Text(
                            text = "✨ Native way to say it:",
                            color = AccentCoral,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = betterAlternative,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestionChipsRow(
    suggestions: List<String>,
    onSelectSuggestion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (suggestions.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💬 Suggested responses:",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestions.forEachIndexed { index, suggestion ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.35f)),
                    shadowElevation = 0.5.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onSelectSuggestion(suggestion) }
                        .testTag("suggestion_chip_$index")
                ) {
                    Text(
                        text = suggestion,
                        color = PrimaryIndigo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TypingIndicatorBubble(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    val dot1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 0),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )
    val dot2 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )
    val dot3 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF1F5F9),
            modifier = Modifier.testTag("typing_indicator")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Maya is thinking",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .scale(dot1)
                        .clip(CircleShape)
                        .background(PrimaryIndigo)
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .scale(dot2)
                        .clip(CircleShape)
                        .background(PrimaryIndigo)
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .scale(dot3)
                        .clip(CircleShape)
                        .background(PrimaryIndigo)
                )
            }
        }
    }
}

@Composable
fun ScenarioGoalCard(
    scenario: PracticeScenario,
    turnsCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        shadowElevation = 0.5.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("scenario_goal_banner")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Text(
                    text = scenario.iconEmoji,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = scenario.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = PrimaryIndigoLight
                        ) {
                            Text(
                                text = scenario.category,
                                color = PrimaryIndigo,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Goal: ${scenario.goals.firstOrNull() ?: scenario.description}",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }

            // Practice turn counter
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (turnsCount >= 3) SuccessGreen.copy(alpha = 0.15f) else Color(0xFFF1F5F9)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (turnsCount >= 3) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = "$turnsCount turns",
                        color = if (turnsCount >= 3) SuccessGreenDark else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
