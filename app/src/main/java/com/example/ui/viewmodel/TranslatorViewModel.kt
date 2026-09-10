package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.tts.TtsManager
import com.example.data.models.Language
import com.example.data.models.SupportedLanguages
import com.example.data.translation.QuickPhraseItem
import com.example.data.translation.TranslationHistoryItem
import com.example.data.translation.TranslationResult
import com.example.data.translation.TranslationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TranslatorUiState(
    val sourceText: String = "",
    val sourceLanguage: Language = SupportedLanguages.find { it.code == "de" } ?: SupportedLanguages[0],
    val targetLanguage: Language = SupportedLanguages.find { it.code == "es" } ?: SupportedLanguages[1],
    val formalityPreference: String = "Natural", // "Natural", "Formal", "Informal"
    val isTranslating: Boolean = false,
    val translationResult: TranslationResult? = null,
    val history: List<TranslationHistoryItem> = emptyList(),
    val errorMessage: String? = null
)

class TranslatorViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val translationService: TranslationService = TranslationService()
    val ttsManager: TtsManager = TtsManager(application)

    private val _uiState = MutableStateFlow(TranslatorUiState())
    val uiState: StateFlow<TranslatorUiState> = _uiState.asStateFlow()

    init {
        // Pre-fill starter history for discovery
        val starterHistory = listOf(
            TranslationHistoryItem(
                sourceText = "Können Sie mir bitte helfen?",
                translatedText = "¿Me puede ayudar, por favor?",
                sourceLangCode = "de",
                targetLangCode = "es",
                pronunciation = "Meh PWEH-deh ah-yoo-DAHR, pohr fah-VOHR?"
            ),
            TranslationHistoryItem(
                sourceText = "Wo ist die nächste U-Bahn-Station?",
                translatedText = "¿Dónde está la estación de metro más cercana?",
                sourceLangCode = "de",
                targetLangCode = "es",
                pronunciation = "DOHN-deh ess-TAH lah ess-tah-SYOHN deh MEH-troh...?"
            )
        )
        _uiState.update { it.copy(history = starterHistory) }
    }

    fun setSourceText(text: String) {
        _uiState.update { it.copy(sourceText = text) }
    }

    fun setSourceLanguage(language: Language) {
        _uiState.update { it.copy(sourceLanguage = language) }
        if (_uiState.value.sourceText.isNotBlank()) {
            translateCurrentText()
        }
    }

    fun setTargetLanguage(language: Language) {
        _uiState.update { it.copy(targetLanguage = language) }
        if (_uiState.value.sourceText.isNotBlank()) {
            translateCurrentText()
        }
    }

    fun setFormalityPreference(formality: String) {
        _uiState.update { it.copy(formalityPreference = formality) }
        if (_uiState.value.sourceText.isNotBlank()) {
            translateCurrentText()
        }
    }

    fun swapLanguages() {
        _uiState.update { state ->
            val prevSource = state.sourceLanguage
            val prevTarget = state.targetLanguage
            val prevSourceText = state.sourceText
            val prevTranslatedText = state.translationResult?.translatedText ?: ""

            state.copy(
                sourceLanguage = prevTarget,
                targetLanguage = prevSource,
                sourceText = prevTranslatedText.ifEmpty { prevSourceText }
            )
        }
        if (_uiState.value.sourceText.isNotBlank()) {
            translateCurrentText()
        }
    }

    fun translateCurrentText() {
        val text = _uiState.value.sourceText.trim()
        if (text.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isTranslating = true, errorMessage = null) }
            try {
                val result = translationService.translate(
                    text = text,
                    sourceLang = _uiState.value.sourceLanguage,
                    targetLang = _uiState.value.targetLanguage,
                    formalityPreference = _uiState.value.formalityPreference
                )
                _uiState.update { state ->
                    val newHistoryItem = TranslationHistoryItem(
                        sourceText = text,
                        translatedText = result.translatedText,
                        sourceLangCode = state.sourceLanguage.code,
                        targetLangCode = state.targetLanguage.code,
                        pronunciation = result.pronunciation
                    )
                    val updatedHistory = listOf(newHistoryItem) + state.history.filterNot { it.sourceText.equals(text, ignoreCase = true) }
                    state.copy(
                        isTranslating = false,
                        translationResult = result,
                        history = updatedHistory.take(30)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isTranslating = false, errorMessage = "Translation error: ${e.message}") }
            }
        }
    }

    fun selectHistoryItem(item: TranslationHistoryItem) {
        val srcLang = SupportedLanguages.find { it.code == item.sourceLangCode } ?: _uiState.value.sourceLanguage
        val tgtLang = SupportedLanguages.find { it.code == item.targetLangCode } ?: _uiState.value.targetLanguage
        _uiState.update {
            it.copy(
                sourceText = item.sourceText,
                sourceLanguage = srcLang,
                targetLanguage = tgtLang
            )
        }
        translateCurrentText()
    }

    fun toggleBookmark(id: String) {
        _uiState.update { state ->
            val updated = state.history.map {
                if (it.id == id) it.copy(isBookmarked = !it.isBookmarked) else it
            }
            state.copy(history = updated)
        }
    }

    fun clearHistory() {
        _uiState.update { it.copy(history = emptyList()) }
    }

    fun speakText(text: String, language: Language) {
        ttsManager.speak(text, language.ttsLocale, 1.0f)
    }

    val quickPhrases = listOf(
        QuickPhraseItem("Greetings", "Guten Tag, wie geht es Ihnen?", "👋"),
        QuickPhraseItem("Restaurant", "Ich möchte gerne einen Tisch für zwei reservieren.", "🍽️"),
        QuickPhraseItem("Shopping", "Wie viel kostet das und kann ich mit Karte zahlen?", "🛍️"),
        QuickPhraseItem("Directions", "Entschuldigung, wie komme ich zum Hauptbahnhof?", "🗺️"),
        QuickPhraseItem("Hotel", "Ich habe eine Zimmerreservierung auf meinen Namen.", "🏨"),
        QuickPhraseItem("Emergency", "Könnten Sie bitte einen Notarzt rufen?", "🚨"),
        QuickPhraseItem("Work", "Lassen Sie uns die Details im morgigen Meeting besprechen.", "💼")
    )

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
        ttsManager.shutdown()
    }
}
