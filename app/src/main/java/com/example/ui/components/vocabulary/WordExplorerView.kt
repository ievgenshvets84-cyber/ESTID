package com.example.ui.components.vocabulary

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.VocabularyWordEntity
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.util.AppLocalization
import com.example.ui.viewmodel.FilterStatus

@Composable
fun WordExplorerView(
    words: List<VocabularyWordEntity>,
    searchQuery: String,
    filterStatus: FilterStatus,
    currentStage: Int,
    totalStages: Int,
    onSearchChanged: (String) -> Unit,
    onFilterChanged: (FilterStatus) -> Unit,
    onSelectStage: (Int) -> Unit,
    onJumpToRank: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    onToggleBookmark: (VocabularyWordEntity) -> Unit,
    onToggleMastery: (VocabularyWordEntity) -> Unit,
    onAiDeepDive: (VocabularyWordEntity) -> Unit,
    modifier: Modifier = Modifier,
    onOpenVerbTable: ((VocabularyWordEntity) -> Unit)? = null,
    nativeLangCode: String = "de"
) {
    val strings = remember(nativeLangCode) { AppLocalization.getStrings(nativeLangCode) }
    val focusManager = LocalFocusManager.current
    var showRankDialog by remember { mutableStateOf(false) }
    var rankInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search Bar & Rank Jump Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChanged,
                placeholder = { Text(strings.searchPlaceholder, style = MaterialTheme.typography.bodyMedium) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextSecondary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChanged("") }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("vocabulary_search_input"),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryIndigo,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                color = PrimaryIndigoLight,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .clickable { showRankDialog = !showRankDialog }
                    .testTag("jump_rank_chip")
            ) {
                Text(
                    text = "#",
                    style = MaterialTheme.typography.labelMedium,
                    color = PrimaryIndigo,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
                )
            }
        }

        // Quick Rank Jump Input expansion
        if (showRankDialog) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigoLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = rankInput,
                        onValueChange = { if (it.all { char -> char.isDigit() } && it.length <= 5) rankInput = it },
                        placeholder = { Text(strings.enterRankPlaceholder, fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            val r = rankInput.toIntOrNull()
                            if (r != null) {
                                onJumpToRank(r)
                                showRankDialog = false
                            }
                            focusManager.clearFocus()
                        })
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        color = PrimaryIndigo,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.clickable {
                            val r = rankInput.toIntOrNull()
                            if (r != null) {
                                onJumpToRank(r)
                                showRankDialog = false
                            }
                            focusManager.clearFocus()
                        }
                    ) {
                        Text(
                            text = strings.jumpButton,
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Stages Carousel (Stage 1 to 100)
        Text(
            text = "${strings.stageLabel} (1 – $totalStages):",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items((1..totalStages).toList()) { stage ->
                val isSelected = (stage == currentStage)
                val startRank = (stage - 1) * 100 + 1
                val endRank = stage * 100

                Surface(
                    color = if (isSelected) PrimaryIndigo else Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable { onSelectStage(stage) }
                        .testTag("stage_chip_$stage")
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${strings.stageLabel} $stage",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) Color.White else TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "#$startRank-$endRank",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Filter status chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterStatus.entries.forEach { status ->
                val isSelected = (filterStatus == status)
                val label = when (status) {
                    FilterStatus.ALL -> "${strings.filterAll} (${words.size})"
                    FilterStatus.VERBS -> strings.filterVerbs
                    FilterStatus.TO_LEARN -> strings.filterToLearn
                    FilterStatus.MASTERED -> strings.filterMastered
                    FilterStatus.BOOKMARKED -> strings.filterBookmarked
                }

                FilterChip(
                    selected = isSelected,
                    onClick = { onFilterChanged(status) },
                    label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryIndigoLight,
                        selectedLabelColor = PrimaryIndigo
                    ),
                    modifier = Modifier.testTag("filter_chip_${status.name}")
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Word Items List
        if (words.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = strings.noWordsFound,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(words, key = { "${it.languageCode}_${it.frequencyRank}_${it.id}" }) { wordItem ->
                    WordItemCard(
                        word = wordItem,
                        onSpeak = { onSpeak(wordItem.word) },
                        onToggleBookmark = { onToggleBookmark(wordItem) },
                        onToggleMastery = { onToggleMastery(wordItem) },
                        onAiDeepDive = { onAiDeepDive(wordItem) },
                        onOpenVerbTable = onOpenVerbTable
                    )
                }
            }
        }
    }
}

@Composable
private fun WordItemCard(
    word: VocabularyWordEntity,
    onSpeak: () -> Unit,
    onToggleBookmark: () -> Unit,
    onToggleMastery: () -> Unit,
    onAiDeepDive: () -> Unit,
    onOpenVerbTable: ((VocabularyWordEntity) -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Frequency Rank Badge
            Surface(
                color = PrimaryIndigoLight,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "#${word.frequencyRank}",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = PrimaryIndigo,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Word, Phonetic, Translation
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    if (word.phonetic.isNotBlank()) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "[${word.phonetic}]",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryIndigo
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = word.translation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${word.cefrLevel} • ${word.category}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextTertiary
                    )

                    if (word.partOfSpeech == "verb" && onOpenVerbTable != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = PrimaryIndigoLight,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.clickable { onOpenVerbTable(word) }
                        ) {
                            Text(
                                text = "📊 Verb Table",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = PrimaryIndigo,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Right side action icons
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Audio Speak
                IconButton(onClick = onSpeak, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Speak",
                        tint = PrimaryIndigo,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // AI Deep Dive
                IconButton(onClick = onAiDeepDive, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Breakdown",
                        tint = AccentAmber,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Bookmark
                IconButton(onClick = onToggleBookmark, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (word.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (word.isBookmarked) AccentCoral else TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Mastery Toggle (Check circle)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (word.masteryLevel >= 3) SuccessGreenLight else Color(0xFFF1F5F9))
                        .clickable { onToggleMastery() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Mastery",
                        tint = if (word.masteryLevel >= 3) SuccessGreen else TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
