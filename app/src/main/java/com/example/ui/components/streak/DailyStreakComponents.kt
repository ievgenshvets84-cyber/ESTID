package com.example.ui.components.streak

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import com.example.data.streak.DailyStreakInfo
import com.example.data.streak.DayActivity
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.util.AppLocalization

/**
 * Compact, tappable Streak Badge for TopAppBars.
 */
@Composable
fun DailyStreakBadge(
    streakInfo: DailyStreakInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "streak_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (!streakInfo.isPracticedToday) 1.15f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val bgColor = if (streakInfo.isPracticedToday) {
        Color(0xFFFFF7ED)
    } else {
        Color(0xFFFEF2F2)
    }

    val borderColor = if (streakInfo.isPracticedToday) {
        Color(0xFFFDBA74)
    } else {
        Color(0xFFFECACA)
    }

    val textColor = if (streakInfo.isPracticedToday) AccentCoral else Color(0xFFDC2626)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(1.2.dp, borderColor),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("daily_streak_badge")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.scale(if (!streakInfo.isPracticedToday) pulseScale else 1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (streakInfo.isPracticedToday) "🔥" else "⚡",
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${streakInfo.currentStreak}d",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            // Small status dot
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(
                        color = if (streakInfo.isPracticedToday) SuccessGreen else AccentAmber,
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * Detailed Daily Streak Bottom Sheet showing 7-day activity, milestone progress,
 * and breakdown of conversation & vocabulary actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyStreakSheet(
    streakInfo: DailyStreakInfo,
    onDismiss: () -> Unit,
    onStartPractice: () -> Unit,
    nativeLangCode: String = "de"
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        modifier = Modifier.testTag("daily_streak_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Close button top right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            // Big Animated Flame Hero Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFEDD5),
                                Color(0xFFFFF7ED),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (streakInfo.isPracticedToday) "🔥" else "⚡",
                    fontSize = 44.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Streak Count Title
            Text(
                text = "${streakInfo.currentStreak} ${strings.streakDaysText} ${strings.dailyStreakTitle}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = if (streakInfo.isPracticedToday) AccentCoral else TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (streakInfo.isPracticedToday) {
                    strings.streakActiveToday
                } else {
                    strings.streakPendingToday
                },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (streakInfo.isPracticedToday) SuccessGreen else AccentAmber
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Today's Status Banner Card
            Surface(
                color = if (streakInfo.isPracticedToday) Color(0xFFF0FDF4) else Color(0xFFFFFBEB),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    if (streakInfo.isPracticedToday) Color(0xFFBBF7D0) else Color(0xFFFDE68A)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (streakInfo.isPracticedToday) Icons.Default.CheckCircle else Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = if (streakInfo.isPracticedToday) SuccessGreen else AccentAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (streakInfo.isPracticedToday) {
                                "Deine Serie ist für heute gesichert!"
                            } else {
                                strings.streakKeepGoingPrompt
                            },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (streakInfo.isPracticedToday) Color(0xFF166534) else Color(0xFF92400E)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (streakInfo.isPracticedToday) {
                            "Hervorragend! Du hast heute bereits gelernt und deine Serie erfolgreich um einen Tag verlängert."
                        } else {
                            "Führe ein kurzes KI-Gespräch oder löse eine Vokabelübung, um deine Serie von ${streakInfo.currentStreak} Tagen nicht zu verlieren!"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (streakInfo.isPracticedToday) Color(0xFF15803D) else Color(0xFFB45309)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 7-Day Weekly Calendar Tracker Row
            Surface(
                color = Color(0xFFF8FAFC),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Wochenübersicht (Letzte 7 Tage)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        streakInfo.weeklyHistory.forEach { day ->
                            DayActivityCircle(day = day)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Today's Activity Breakdown
            Surface(
                color = Color(0xFFF8FAFC),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = strings.streakTodayPracticeTitle,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Row 1: AI Turns
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFEEF2FF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChatBubbleOutline,
                                contentDescription = null,
                                tint = PrimaryIndigo,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.streakAiTurnsLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Mit dem KI-Partner geübt",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "${streakInfo.todayAiTurns}x",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryIndigo
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Row 2: Vocab Tasks
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFFEF3C7), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = AccentAmber,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.streakVocabTasksLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Karten, Phrasen & Quizze",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                        Text(
                            text = "${streakInfo.todayVocabTasks}x",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AccentCoral
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Milestones Progress
            val milestones = listOf(3, 7, 14, 30, 60, 100)
            val nextMilestone = milestones.firstOrNull { it > streakInfo.currentStreak } ?: 100
            val prevMilestone = milestones.lastOrNull { it <= streakInfo.currentStreak } ?: 0
            val progress = ((streakInfo.currentStreak - prevMilestone).toFloat() / (nextMilestone - prevMilestone).toFloat())
                .coerceIn(0f, 1f)

            Surface(
                color = Color.White,
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
                            text = "${strings.streakMilestoneTitle}: $nextMilestone ${strings.streakDaysText} 🏆",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Noch ${maxOf(0, nextMilestone - streakInfo.currentStreak)} ${strings.streakDaysText}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryIndigo
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AccentCoral,
                        trackColor = Color(0xFFFFEDD5)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        MilestoneBadge(targetDays = 3, label = "Starter", currentStreak = streakInfo.currentStreak)
                        MilestoneBadge(targetDays = 7, label = "1 Woche", currentStreak = streakInfo.currentStreak)
                        MilestoneBadge(targetDays = 14, label = "Profi", currentStreak = streakInfo.currentStreak)
                        MilestoneBadge(targetDays = 30, label = "Legende", currentStreak = streakInfo.currentStreak)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Button
            Button(
                onClick = {
                    onDismiss()
                    onStartPractice()
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("streak_action_button")
            ) {
                Text(
                    text = if (streakInfo.isPracticedToday) "Weiterlernen 🚀" else "Jetzt Üben & Serie Sichern 🔥",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
private fun DayActivityCircle(day: DayActivity) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day.dayOfWeekLabel,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Medium,
            color = if (day.isToday) PrimaryIndigo else Color(0xFF64748B),
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = when {
                        day.isPracticed -> Color(0xFFFFEDD5) // orange bg
                        day.isToday -> Color(0xFFFEF2F2)
                        else -> Color(0xFFF1F5F9)
                    },
                    shape = CircleShape
                )
                .border(
                    width = if (day.isToday) 2.dp else 1.dp,
                    color = when {
                        day.isToday && day.isPracticed -> AccentCoral
                        day.isToday -> PrimaryIndigo
                        day.isPracticed -> Color(0xFFFDBA74)
                        else -> Color(0xFFE2E8F0)
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (day.isPracticed) {
                Text(text = "🔥", fontSize = 14.sp)
            } else if (day.isToday) {
                Text(text = "⏳", fontSize = 12.sp)
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color(0xFFCBD5E1), CircleShape)
                )
            }
        }
    }
}

@Composable
private fun MilestoneBadge(
    targetDays: Int,
    label: String,
    currentStreak: Int
) {
    val reached = currentStreak >= targetDays
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (reached) Color(0xFFFEF3C7) else Color(0xFFF1F5F9),
                    shape = CircleShape
                )
                .border(
                    1.dp,
                    if (reached) AccentAmber else Color(0xFFCBD5E1),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (reached) "🏅" else "🔒",
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$targetDays d",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (reached) TextPrimary else TextSecondary,
            fontSize = 11.sp
        )
    }
}

/**
 * Celebratory popup shown when user practices for the first time today and extends streak!
 */
@Composable
fun StreakCelebrationDialog(
    newStreak: Int,
    onDismiss: () -> Unit,
    nativeLangCode: String = "de"
) {
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .testTag("streak_celebration_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🎉", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = strings.streakCelebrateTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = AccentCoral,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = String.format(strings.streakCelebrateSubtitle, newStreak),
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = Color(0xFFFFF7ED),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFFED7AA)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "🔥", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "$newStreak ${strings.streakDaysText}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = AccentCoral
                            )
                            Text(
                                text = "Serie aktiv gehalten",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Fantastisch! Weiter so 🚀",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
