package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Language
import com.example.data.models.PracticeScenario
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoDark
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SecondaryTeal
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.util.AppLocalization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoicePartnerSheet(
    language: Language,
    scenario: PracticeScenario,
    isListening: Boolean,
    isSpeaking: Boolean,
    isGenerating: Boolean,
    rmsDb: Float,
    partialSpeech: String,
    lastAiReply: String,
    speechSpeed: Float,
    onSpeechSpeedChange: (Float) -> Unit,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onStopSpeaking: () -> Unit,
    onDismiss: () -> Unit,
    nativeLangCode: String = "de"
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF0F172A),
        contentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = language.flag, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = strings.voicePartnerTitle,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${scenario.title} • ${language.nativeName}",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_voice_sheet_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Voice Mode",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Pulsing Audio Voice Orb
            VoiceOrb(
                isListening = isListening,
                isSpeaking = isSpeaking,
                isGenerating = isGenerating,
                rmsDb = rmsDb,
                onClick = {
                    if (isSpeaking) {
                        onStopSpeaking()
                    } else if (isListening) {
                        onStopListening()
                    } else {
                        onStartListening()
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // State Status Label
            val statusText = when {
                isGenerating -> strings.voiceStatusThinking
                isSpeaking -> strings.voiceStatusSpeaking
                isListening -> strings.voiceStatusListening
                else -> strings.voiceStatusTapMic
            }
            Text(
                text = statusText,
                color = when {
                    isSpeaking -> SecondaryTeal
                    isListening -> AccentCoral
                    isGenerating -> Color(0xFFA855F7)
                    else -> Color(0xFFCBD5E1)
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Live Transcript or Latest Reply Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1E293B),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isListening && partialSpeech.isNotBlank()) {
                        Text(
                            text = "\"$partialSpeech\"",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    } else if (lastAiReply.isNotBlank()) {
                        Text(
                            text = lastAiReply,
                            color = Color(0xFFE2E8F0),
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 3
                        )
                    } else {
                        Text(
                            text = "${strings.voiceStatusListening} (${language.name})",
                            color = Color(0xFF64748B),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Speech Speed Selector (0.8x Slow, 1.0x Normal, 1.2x Fast)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${strings.voiceSpeedLabel}:",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(10.dp))

                listOf(
                    0.8f to "0.8x",
                    1.0f to "1.0x",
                    1.2f to "1.2x"
                ).forEach { (speed, label) ->
                    val isSelected = kotlin.math.abs(speechSpeed - speed) < 0.05f
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) PrimaryIndigo else Color(0xFF1E293B),
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSpeechSpeedChange(speed) }
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.White else Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Large Microphone Action Button
            Surface(
                shape = CircleShape,
                color = if (isListening) AccentCoral else PrimaryIndigo,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .clickable {
                        if (isListening) onStopListening() else onStartListening()
                    }
                    .testTag("voice_sheet_mic_button")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                        contentDescription = if (isListening) "Stop Listening" else "Start Speaking",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isListening) "Tap to send" else "Tap to speak",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun VoiceOrb(
    isListening: Boolean,
    isSpeaking: Boolean,
    isGenerating: Boolean,
    rmsDb: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orb_anim")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val dynamicScale = when {
        isSpeaking -> pulseScale * 1.05f
        isListening -> 1f + (rmsDb.coerceIn(0f, 15f) / 50f)
        isGenerating -> pulseScale
        else -> 1f
    }

    val gradientColors = when {
        isSpeaking -> listOf(SecondaryTeal, PrimaryIndigo, Color(0xFF7C3AED))
        isListening -> listOf(AccentCoral, Color(0xFFEF4444), Color(0xFFF59E0B))
        isGenerating -> listOf(Color(0xFFA855F7), Color(0xFF6366F1), SecondaryTeal)
        else -> listOf(PrimaryIndigo, Color(0xFF6366F1), Color(0xFF4338CA))
    }

    Box(
        modifier = modifier
            .size(160.dp)
            .scale(dynamicScale)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow circle
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            gradientColors.first().copy(alpha = 0.45f),
                            Color.Transparent
                        )
                    )
                )
        )

        // Main sphere
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColors))
                .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when {
                    isSpeaking -> Icons.AutoMirrored.Filled.VolumeUp
                    isListening -> Icons.Default.Mic
                    else -> Icons.Default.MicNone
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(44.dp)
            )
        }
    }
}
