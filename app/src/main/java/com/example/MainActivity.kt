package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.MainContainerScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LanguagePartnerViewModel
import com.example.ui.viewmodel.TranslatorViewModel
import com.example.ui.viewmodel.VocabularyViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val conversationViewModel: LanguagePartnerViewModel = viewModel()
        val vocabularyViewModel: VocabularyViewModel = viewModel()
        val translatorViewModel: TranslatorViewModel = viewModel()
        MainContainerScreen(
          conversationViewModel = conversationViewModel,
          vocabularyViewModel = vocabularyViewModel,
          translatorViewModel = translatorViewModel
        )
      }
    }
  }
}

