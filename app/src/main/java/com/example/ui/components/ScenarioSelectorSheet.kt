package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.models.Language
import com.example.data.models.PracticeScenario
import com.example.data.models.ScenarioPhrase
import com.example.data.scenarios.PhraseBankRepository
import com.example.data.scenarios.ScenarioRepository
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.SecondaryAmber
import com.example.ui.theme.SecondaryAmberLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScenarioSelectorSheet(
    currentScenario: PracticeScenario,
    selectedLanguage: Language? = null,
    onSelectScenario: (PracticeScenario) -> Unit,
    onSpeakPhrase: ((String) -> Unit)? = null,
    onPracticePhraseInChat: ((String) -> Unit)? = null,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedTabIndex by remember { mutableStateOf(0) } // 0: Scenarios (100), 1: Phrases (100)
    var selectedLevelFilter by remember { mutableStateOf("ALL") } // "ALL", "A1", "A2", "B1", "B2", "C1"
    var searchQuery by remember { mutableStateOf("") }
    var expandedScenarioId by remember { mutableStateOf<String?>(null) }

    val langCode = selectedLanguage?.code ?: "es"

    val allScenarios = remember { ScenarioRepository.allScenarios }
    val filteredScenarios = remember(selectedLevelFilter, searchQuery) {
        allScenarios.filter { scenario ->
            val matchesLevel = selectedLevelFilter == "ALL" || scenario.cefrLevel.equals(selectedLevelFilter, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    scenario.title.contains(searchQuery, ignoreCase = true) ||
                    scenario.description.contains(searchQuery, ignoreCase = true) ||
                    scenario.category.contains(searchQuery, ignoreCase = true)
            matchesLevel && matchesSearch
        }
    }

    val currentLevelForPhrases = if (selectedLevelFilter == "ALL") "A1" else selectedLevelFilter
    val levelPhrases = remember(currentLevelForPhrases, langCode, searchQuery) {
        val phrases = PhraseBankRepository.getPhrasesForLevel(currentLevelForPhrases, langCode)
        if (searchQuery.isBlank()) {
            phrases
        } else {
            phrases.filter { p ->
                p.targetText.contains(searchQuery, ignoreCase = true) ||
                        p.translation.contains(searchQuery, ignoreCase = true) ||
                        (p.germanTranslation?.contains(searchQuery, ignoreCase = true) == true) ||
                        p.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Praxis-Szenarien & Phrasen",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "100 Rollenspiele & 100 Phrasen je Niveau (${selectedLanguage?.name ?: "Spanisch"})",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Schließen",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation Tabs: Scenarios (100) vs Phrases (100)
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color(0xFFF1F5F9),
                contentColor = PrimaryIndigo,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .padding(2.dp)
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            text = "🎭 100 Szenarien (${filteredScenarios.size})",
                            fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            text = "💬 100 Phrasen ($currentLevelForPhrases)",
                            fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search input field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = if (selectedTabIndex == 0) "Szenario suchen (z.B. Café, Job, Reise)..." else "Phrase oder Bedeutung suchen...",
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Suche",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Löschen",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryIndigo,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Level Filter Chips (All, A1, A2, B1, B2, C1)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val levels = listOf(
                    "ALL" to "Alle (100)",
                    "A1" to "A1 Anfänger",
                    "A2" to "A2 Grundlagen",
                    "B1" to "B1 Mittelstufe",
                    "B2" to "B2 Fortgeschritten",
                    "C1" to "C1 Fließend"
                )
                items(levels) { (code, label) ->
                    val isSelected = selectedLevelFilter == code
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedLevelFilter = code },
                        label = { Text(label, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryIndigo,
                            selectedLabelColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) PrimaryIndigo else Color(0xFFE2E8F0)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Content: Scenarios Tab
            if (selectedTabIndex == 0) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredScenarios) { scenario ->
                        val isSelected = scenario.id == currentScenario.id
                        val isExpanded = expandedScenarioId == scenario.id
                        val scenarioPhrases = remember(scenario.id, langCode) {
                            PhraseBankRepository.getPhrasesForScenario(scenario.id, langCode, scenario.cefrLevel)
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) PrimaryIndigoLight else Color(0xFFF8FAFC),
                            border = BorderStroke(
                                1.5.dp,
                                if (isSelected) PrimaryIndigo else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    onSelectScenario(scenario)
                                    onDismiss()
                                }
                                .testTag("scenario_item_${scenario.id}")
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = scenario.iconEmoji,
                                        fontSize = 28.sp,
                                        modifier = Modifier.padding(end = 12.dp, top = 2.dp)
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = scenario.title,
                                                    fontSize = 15.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) PrimaryIndigo else TextPrimary
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                // CEFR badge
                                                Surface(
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = getCefrColor(scenario.cefrLevel)
                                                ) {
                                                    Text(
                                                        text = scenario.cefrLevel,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }

                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = if (isSelected) PrimaryIndigo else Color(0xFFE2E8F0)
                                            ) {
                                                Text(
                                                    text = scenario.category,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = if (isSelected) Color.White else TextSecondary,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = scenario.description,
                                            fontSize = 12.sp,
                                            color = TextSecondary,
                                            lineHeight = 17.sp
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "🎯 Lernziele: ${scenario.goals.joinToString(" • ")}",
                                            fontSize = 11.sp,
                                            color = if (isSelected) PrimaryIndigo else Color(0xFF475569),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    if (isSelected) {
                                        Box(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(22.dp)
                                                .clip(CircleShape)
                                                .background(PrimaryIndigo),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Ausgewählt",
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }

                                // Expandable Phrases Bar for this scenario
                                if (scenarioPhrases.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                expandedScenarioId = if (isExpanded) null else scenario.id
                                            }
                                            .background(Color.White.copy(alpha = 0.8f))
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "💬 Passende Redewendungen (${scenarioPhrases.size})",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = PrimaryIndigo
                                        )
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Aufklappen",
                                            tint = PrimaryIndigo,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    AnimatedVisibility(
                                        visible = isExpanded,
                                        enter = expandVertically(),
                                        exit = shrinkVertically()
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            scenarioPhrases.forEach { phrase ->
                                                ScenarioPhraseCard(
                                                    phrase = phrase,
                                                    onSpeak = { onSpeakPhrase?.invoke(phrase.targetText) },
                                                    onPracticeInChat = {
                                                        onSelectScenario(scenario)
                                                        onPracticePhraseInChat?.invoke(phrase.targetText)
                                                        onDismiss()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Phrases Tab (Up to 100 phrases categorized by level & language)
                Text(
                    text = "100 wichtige Redewendungen für Niveau $currentLevelForPhrases (${levelPhrases.size} geladen)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(levelPhrases) { phrase ->
                        ScenarioPhraseCard(
                            phrase = phrase,
                            onSpeak = { onSpeakPhrase?.invoke(phrase.targetText) },
                            onPracticeInChat = {
                                onPracticePhraseInChat?.invoke(phrase.targetText)
                                onDismiss()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ScenarioPhraseCard(
    phrase: ScenarioPhrase,
    onSpeak: () -> Unit,
    onPracticeInChat: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Text(
                            text = phrase.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = SecondaryAmberLight
                    ) {
                        Text(
                            text = phrase.formality,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SecondaryAmber,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Aussprache anhören",
                            tint = PrimaryIndigo,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick = onPracticeInChat,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Im Chat üben",
                            tint = SuccessGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Target Text
            Text(
                text = phrase.targetText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            // German / English Translation
            val translationDisplay = phrase.germanTranslation ?: phrase.translation
            Text(
                text = "🇩🇪 $translationDisplay",
                fontSize = 12.sp,
                color = TextSecondary
            )

            // Pronunciation guide if available
            if (!phrase.pronunciation.isNullOrBlank()) {
                Text(
                    text = "🗣️ ${phrase.pronunciation}",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

private fun getCefrColor(level: String): Color {
    return when (level.uppercase()) {
        "A1" -> Color(0xFF10B981) // Green
        "A2" -> Color(0xFF06B6D4) // Cyan
        "B1" -> Color(0xFF3B82F6) // Blue
        "B2" -> Color(0xFF8B5CF6) // Violet
        "C1" -> Color(0xFFD97706) // Amber
        else -> PrimaryIndigo
    }
}
