package com.example.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.PrimaryIndigo
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Translate
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.PrimaryIndigoLight
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.LanguagePartnerViewModel
import com.example.ui.viewmodel.TranslatorViewModel
import com.example.ui.viewmodel.VocabularyViewModel

@Composable
fun MainContainerScreen(
    conversationViewModel: LanguagePartnerViewModel,
    vocabularyViewModel: VocabularyViewModel,
    translatorViewModel: TranslatorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    val conversationState by conversationViewModel.uiState.collectAsStateWithLifecycle()
    val vocabularyState by vocabularyViewModel.uiState.collectAsStateWithLifecycle()

    // Sync native language with translator source language and vocabulary view model
    androidx.compose.runtime.LaunchedEffect(conversationState.nativeLanguage) {
        translatorViewModel.setSourceLanguage(conversationState.nativeLanguage)
        vocabularyViewModel.setNativeLanguage(conversationState.nativeLanguage)
    }

    // Unidirectional language selection handler: updates both view models without reactive circular loops
    val onLanguageSelected: (com.example.data.models.Language) -> Unit = { newLanguage ->
        if (conversationState.selectedLanguage.code != newLanguage.code) {
            conversationViewModel.selectLanguage(newLanguage)
        }
        if (vocabularyState.selectedLanguage.code != newLanguage.code) {
            vocabularyViewModel.setLanguage(newLanguage)
        }
        translatorViewModel.setTargetLanguage(newLanguage)
    }

    val onNativeLanguageSelected: (com.example.data.models.Language) -> Unit = { newNativeLanguage ->
        conversationViewModel.selectNativeLanguage(newNativeLanguage)
        vocabularyViewModel.setNativeLanguage(newNativeLanguage)
        translatorViewModel.setSourceLanguage(newNativeLanguage)
    }

    val strings = com.example.ui.util.AppLocalization.getStrings(conversationState.nativeLanguage.code)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("main_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Default.ChatBubble else Icons.Outlined.ChatBubbleOutline,
                            contentDescription = strings.navPartner
                        )
                    },
                    label = {
                        Text(
                            text = strings.navPartner,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        indicatorColor = PrimaryIndigoLight,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_partner")
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Default.School else Icons.Outlined.School,
                            contentDescription = strings.navVocabulary
                        )
                    },
                    label = {
                        Text(
                            text = strings.navVocabulary,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        indicatorColor = PrimaryIndigoLight,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_words")
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Default.Translate else Icons.Outlined.Translate,
                            contentDescription = strings.navTranslator
                        )
                    },
                    label = {
                        Text(
                            text = strings.navTranslator,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        indicatorColor = PrimaryIndigoLight,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    ),
                    modifier = Modifier.testTag("nav_item_translator")
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (selectedTab) {
                0 -> {
                    MainConversationScreen(
                        viewModel = conversationViewModel,
                        onNavigateToWordLearning = { selectedTab = 1 },
                        onNavigateToTranslator = { selectedTab = 2 },
                        onLanguageSelected = onLanguageSelected,
                        onNativeLanguageSelected = onNativeLanguageSelected
                    )
                }

                1 -> {
                    WordLearningScreen(
                        viewModel = vocabularyViewModel,
                        onLanguageSelected = onLanguageSelected,
                        onNativeLanguageSelected = onNativeLanguageSelected
                    )
                }

                2 -> {
                    InteractiveTranslatorScreen(
                        viewModel = translatorViewModel,
                        onNavigateBack = { selectedTab = 0 },
                        onPracticeInChat = { text, lang ->
                            onLanguageSelected(lang)
                            conversationViewModel.sendMessage(text)
                            selectedTab = 0
                        }
                    )
                }
            }
        }
    }
}
