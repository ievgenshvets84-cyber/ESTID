package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.GeminiLanguageService
import com.example.data.db.VocabularyWordEntity
import com.example.data.models.Language
import com.example.data.models.SupportedLanguages
import com.example.data.speech.SpeechRecognizerManager
import com.example.data.tts.TtsManager
import com.example.data.vocabulary.FrequencyTier
import com.example.data.vocabulary.VocabularyRepository
import com.example.data.vocabulary.VocabularyTiers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class VocabularyMode {
    FLASHCARDS,
    EXPLORER,
    VERBS,
    QUIZ,
    PRONUNCIATION
}

enum class FilterStatus {
    ALL,
    VERBS,
    TO_LEARN,
    MASTERED,
    BOOKMARKED
}

data class QuizQuestion(
    val word: VocabularyWordEntity,
    val promptText: String,
    val options: List<String>,
    val correctIndex: Int
)

data class VocabularyUiState(
    val selectedLanguage: Language = SupportedLanguages[0],
    val currentTier: FrequencyTier = VocabularyTiers[0],
    val currentStage: Int = 1, // Stage 1 (1-100), Stage 2 (101-200), ... up to 100 (9901-10000)
    val totalStages: Int = 100,
    val words: List<VocabularyWordEntity> = emptyList(),
    val filteredWords: List<VocabularyWordEntity> = emptyList(),
    val isLoading: Boolean = false,
    val currentMode: VocabularyMode = VocabularyMode.EXPLORER,
    val filterStatus: FilterStatus = FilterStatus.ALL,
    val searchQuery: String = "",

    // Verb Table state
    val selectedVerbForTable: VocabularyWordEntity? = null,
    val verbSearchQuery: String = "",
    val verbsList: List<VocabularyWordEntity> = emptyList(),
    val filteredVerbsList: List<VocabularyWordEntity> = emptyList(),

    // Flashcards
    val currentFlashcardIndex: Int = 0,
    val isCardFlipped: Boolean = false,

    // Quiz
    val currentQuizIndex: Int = 0,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val selectedQuizAnswer: Int? = null,
    val isQuizAnswerSubmitted: Boolean = false,
    val quizScore: Int = 0,
    val quizStreak: Int = 0,
    val isQuizFinished: Boolean = false,

    // Pronunciation drill
    val isDrillListening: Boolean = false,
    val drillRecognizedText: String = "",
    val drillFeedback: String? = null,
    val drillScore: Int? = null,

    // Overall metrics
    val totalWordsCatalog: Int = 10000,
    val masteredWordsCount: Int = 0,
    val learningWordsCount: Int = 0,
    val dailyGoalTarget: Int = 20,
    val dailyLearnedToday: Int = 8,

    // AI Deep-dive
    val deepDiveWord: VocabularyWordEntity? = null,
    val deepDiveContent: String? = null,
    val isDeepDiveLoading: Boolean = false
)

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VocabularyRepository.getInstance(application)
    private val geminiService = GeminiLanguageService()
    val ttsManager = TtsManager(application)
    val speechManager = SpeechRecognizerManager(application)
    private val prefs = application.getSharedPreferences("lingua_partner_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(VocabularyUiState())
    val uiState: StateFlow<VocabularyUiState> = _uiState.asStateFlow()

    private var wordsJob: Job? = null
    private var statsJob: Job? = null
    private var setupJob: Job? = null
    private var verbsJob: Job? = null

    init {
        val savedLangCode = prefs.getString("selected_lang", "es") ?: "es"
        val initialLang = SupportedLanguages.find { it.code == savedLangCode } ?: SupportedLanguages[0]
        _uiState.update { it.copy(selectedLanguage = initialLang) }
        setupLanguage(initialLang)
    }

    fun setLanguage(language: Language) {
        if (_uiState.value.selectedLanguage.code == language.code) return
        ttsManager.stop()
        speechManager.stopListening()
        prefs.edit().putString("selected_lang", language.code).apply()

        _uiState.update {
            it.copy(
                selectedLanguage = language,
                currentStage = 1,
                currentTier = VocabularyTiers[0],
                currentFlashcardIndex = 0,
                isCardFlipped = false,
                isDrillListening = false,
                drillFeedback = null,
                drillScore = null,
                selectedVerbForTable = null,
                verbSearchQuery = ""
            )
        }
        setupLanguage(language)
    }

    private fun setupLanguage(language: Language) {
        setupJob?.cancel()
        setupJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.initializeLanguageIfNeeded(language.code)
            observeWordData(language.code, _uiState.value.currentStage)
            observeStats(language.code)
            observeVerbs(language.code)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun observeVerbs(languageCode: String) {
        verbsJob?.cancel()
        verbsJob = viewModelScope.launch {
            repository.getVerbsForLanguage(languageCode).collect { verbs ->
                _uiState.update { state ->
                    val filtered = if (state.verbSearchQuery.isBlank()) {
                        verbs
                    } else {
                        val q = state.verbSearchQuery.trim().lowercase()
                        verbs.filter {
                            it.word.lowercase().contains(q) || it.translation.lowercase().contains(q)
                        }
                    }
                    state.copy(verbsList = verbs, filteredVerbsList = filtered)
                }
            }
        }
    }

    fun openVerbTable(word: VocabularyWordEntity) {
        _uiState.update { it.copy(selectedVerbForTable = word) }
    }

    fun closeVerbTable() {
        _uiState.update { it.copy(selectedVerbForTable = null) }
    }

    fun onVerbSearchChanged(query: String) {
        _uiState.update { state ->
            val filtered = if (query.isBlank()) {
                state.verbsList
            } else {
                val q = query.trim().lowercase()
                state.verbsList.filter {
                    it.word.lowercase().contains(q) || it.translation.lowercase().contains(q)
                }
            }
            state.copy(verbSearchQuery = query, filteredVerbsList = filtered)
        }
    }

    fun setMode(mode: VocabularyMode) {
        _uiState.update { it.copy(currentMode = mode, isCardFlipped = false) }
        if (mode == VocabularyMode.QUIZ && _uiState.value.quizQuestions.isEmpty()) {
            startNewQuiz()
        }
    }

    fun selectTier(tier: FrequencyTier) {
        val stageForTier = ((tier.startRank - 1) / 100) + 1
        selectStage(stageForTier)
        _uiState.update { it.copy(currentTier = tier) }
    }

    fun selectStage(stageNumber: Int) {
        val clampedStage = stageNumber.coerceIn(1, 100)
        val startRank = (clampedStage - 1) * 100 + 1
        val tier = VocabularyTiers.firstOrNull { it.startRank <= startRank && it.endRank >= startRank }
            ?: VocabularyTiers[0]

        _uiState.update {
            it.copy(
                currentStage = clampedStage,
                currentTier = tier,
                currentFlashcardIndex = 0,
                isCardFlipped = false,
                isLoading = true
            )
        }

        viewModelScope.launch {
            repository.loadStageWords(_uiState.value.selectedLanguage.code, startRank, 100)
            observeWordData(_uiState.value.selectedLanguage.code, clampedStage)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun jumpToRank(rank: Int) {
        val clampedRank = rank.coerceIn(1, 10000)
        val targetStage = ((clampedRank - 1) / 100) + 1
        selectStage(targetStage)
    }

    private fun observeWordData(languageCode: String, stage: Int) {
        wordsJob?.cancel()
        val startRank = (stage - 1) * 100 + 1
        val endRank = stage * 100

        wordsJob = viewModelScope.launch {
            repository.getWordsByRankRange(languageCode, startRank, endRank).collect { wordList ->
                _uiState.update { state ->
                    val filtered = applyFilterAndSearch(wordList, state.filterStatus, state.searchQuery)
                    state.copy(
                        words = wordList,
                        filteredWords = filtered
                    )
                }
            }
        }
    }

    private fun observeStats(languageCode: String) {
        statsJob?.cancel()
        statsJob = viewModelScope.launch {
            launch {
                repository.getMasteredCountFlow(languageCode).collect { count ->
                    _uiState.update { it.copy(masteredWordsCount = count) }
                }
            }
            launch {
                repository.getLearningCountFlow(languageCode).collect { count ->
                    _uiState.update { it.copy(learningWordsCount = count) }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { state ->
            val filtered = applyFilterAndSearch(state.words, state.filterStatus, query)
            state.copy(searchQuery = query, filteredWords = filtered)
        }
    }

    fun setFilterStatus(status: FilterStatus) {
        _uiState.update { state ->
            val filtered = applyFilterAndSearch(state.words, status, state.searchQuery)
            state.copy(filterStatus = status, filteredWords = filtered)
        }
    }

    private fun applyFilterAndSearch(
        list: List<VocabularyWordEntity>,
        filter: FilterStatus,
        query: String
    ): List<VocabularyWordEntity> {
        val filteredByStatus = when (filter) {
            FilterStatus.ALL -> list
            FilterStatus.VERBS -> list.filter { it.partOfSpeech == "verb" }
            FilterStatus.TO_LEARN -> list.filter { it.masteryLevel == 0 }
            FilterStatus.MASTERED -> list.filter { it.masteryLevel >= 3 }
            FilterStatus.BOOKMARKED -> list.filter { it.isBookmarked }
        }

        return if (query.isBlank()) {
            filteredByStatus
        } else {
            val q = query.trim().lowercase()
            filteredByStatus.filter {
                it.word.lowercase().contains(q) ||
                        it.translation.lowercase().contains(q) ||
                        it.category.lowercase().contains(q)
            }
        }
    }

    // Flashcard Actions
    fun flipCard() {
        _uiState.update { it.copy(isCardFlipped = !it.isCardFlipped) }
    }

    fun nextCard() {
        _uiState.update { state ->
            val nextIndex = (state.currentFlashcardIndex + 1).coerceAtMost(state.words.size - 1)
            state.copy(currentFlashcardIndex = nextIndex, isCardFlipped = false)
        }
    }

    fun previousCard() {
        _uiState.update { state ->
            val prevIndex = (state.currentFlashcardIndex - 1).coerceAtLeast(0)
            state.copy(currentFlashcardIndex = prevIndex, isCardFlipped = false)
        }
    }

    fun markCurrentCardMastery(masteryLevel: Int) {
        val currentWord = getCurrentFlashcardWord() ?: return
        viewModelScope.launch {
            repository.updateMastery(currentWord.id, masteryLevel)
            nextCard()
        }
    }

    fun toggleBookmark(word: VocabularyWordEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(word.id, word.isBookmarked)
        }
    }

    fun getCurrentFlashcardWord(): VocabularyWordEntity? {
        val list = _uiState.value.words
        val index = _uiState.value.currentFlashcardIndex
        return if (index in list.indices) list[index] else null
    }

    fun speakWord(word: String) {
        ttsManager.speak(word, _uiState.value.selectedLanguage.ttsLocale, 0.9f)
    }

    // Quiz Mode Actions
    fun startNewQuiz() {
        viewModelScope.launch {
            val samplePool = repository.getRandomQuizWords(_uiState.value.selectedLanguage.code, 20)
            if (samplePool.size < 4) return@launch

            val questions = samplePool.take(10).map { targetWord ->
                val distractors = samplePool
                    .filter { it.id != targetWord.id }
                    .shuffled()
                    .take(3)
                    .map { it.translation }

                val allOptions = (distractors + targetWord.translation).shuffled()
                val correctIdx = allOptions.indexOf(targetWord.translation)

                QuizQuestion(
                    word = targetWord,
                    promptText = "What is the meaning of \"${targetWord.word}\"?",
                    options = allOptions,
                    correctIndex = correctIdx
                )
            }

            _uiState.update {
                it.copy(
                    quizQuestions = questions,
                    currentQuizIndex = 0,
                    selectedQuizAnswer = null,
                    isQuizAnswerSubmitted = false,
                    quizScore = 0,
                    quizStreak = 0,
                    isQuizFinished = false
                )
            }
        }
    }

    fun selectQuizAnswer(index: Int) {
        val state = _uiState.value
        if (state.isQuizAnswerSubmitted || state.isQuizFinished) return

        val question = state.quizQuestions.getOrNull(state.currentQuizIndex) ?: return
        val isCorrect = (index == question.correctIndex)
        val newScore = if (isCorrect) state.quizScore + 10 else state.quizScore
        val newStreak = if (isCorrect) state.quizStreak + 1 else 0

        _uiState.update {
            it.copy(
                selectedQuizAnswer = index,
                isQuizAnswerSubmitted = true,
                quizScore = newScore,
                quizStreak = newStreak
            )
        }

        // Update word mastery in background
        viewModelScope.launch {
            val newMastery = if (isCorrect) (question.word.masteryLevel + 1).coerceAtMost(3) else 1
            repository.updateMastery(question.word.id, newMastery)
        }
    }

    fun nextQuizQuestion() {
        val state = _uiState.value
        if (state.currentQuizIndex + 1 < state.quizQuestions.size) {
            _uiState.update {
                it.copy(
                    currentQuizIndex = it.currentQuizIndex + 1,
                    selectedQuizAnswer = null,
                    isQuizAnswerSubmitted = false
                )
            }
        } else {
            _uiState.update { it.copy(isQuizFinished = true) }
        }
    }

    // Pronunciation Drill
    fun startPronunciationListening(targetWord: VocabularyWordEntity) {
        val langCode = _uiState.value.selectedLanguage.code
        _uiState.update {
            it.copy(
                isDrillListening = true,
                drillRecognizedText = "",
                drillFeedback = null,
                drillScore = null
            )
        }

        speechManager.startListening(
            languageCode = langCode,
            onFinalResult = { text ->
                gradePronunciation(targetWord, text)
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        isDrillListening = false,
                        drillFeedback = "Could not detect audio. Try speaking closer to the mic."
                    )
                }
            }
        )
    }

    fun stopPronunciationListening() {
        speechManager.stopListening()
        _uiState.update { it.copy(isDrillListening = false) }
    }

    private fun gradePronunciation(target: VocabularyWordEntity, spoken: String) {
        val cleanTarget = target.word.lowercase().replace(Regex("[^\\p{L}]"), "")
        val cleanSpoken = spoken.lowercase().replace(Regex("[^\\p{L}]"), "")

        val score = when {
            cleanSpoken == cleanTarget -> 100
            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) -> 85
            else -> calculateSimilarity(cleanTarget, cleanSpoken)
        }

        val feedback = when {
            score >= 90 -> "🌟 Native-level pronunciation! Flawless pitch and clarity."
            score >= 70 -> "👏 Great effort! Very understandable and clear."
            else -> "💪 Good attempt. Listen to the audio and try again focusing on the rhythm."
        }

        _uiState.update {
            it.copy(
                isDrillListening = false,
                drillRecognizedText = spoken,
                drillFeedback = feedback,
                drillScore = score
            )
        }

        if (score >= 80) {
            viewModelScope.launch {
                repository.updateMastery(target.id, 3)
            }
        }
    }

    private fun calculateSimilarity(s1: String, s2: String): Int {
        if (s1.isEmpty() || s2.isEmpty()) return 0
        val maxLen = maxOf(s1.length, s2.length)
        val dist = levenshtein(s1, s2)
        return (((maxLen - dist).toFloat() / maxLen) * 100).toInt().coerceIn(10, 95)
    }

    private fun levenshtein(lhs: CharSequence, rhs: CharSequence): Int {
        val lhsLength = lhs.length
        val rhsLength = rhs.length
        var cost = Array(lhsLength + 1) { it }
        var newCost = Array(lhsLength + 1) { 0 }

        for (i in 1..rhsLength) {
            newCost[0] = i
            for (j in 1..lhsLength) {
                val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1
                val costReplace = cost[j - 1] + match
                val costInsert = cost[j] + 1
                val costDelete = newCost[j - 1] + 1
                newCost[j] = minOf(costInsert, costDelete, costReplace)
            }
            val swap = cost
            cost = newCost
            newCost = swap
        }
        return cost[lhsLength]
    }

    // AI Deep-Dive
    fun openWordDeepDive(word: VocabularyWordEntity) {
        _uiState.update {
            it.copy(
                deepDiveWord = word,
                deepDiveContent = null,
                isDeepDiveLoading = true
            )
        }

        viewModelScope.launch {
            val content = geminiService.getWordDeepDive(
                word = word.word,
                translation = word.translation,
                language = _uiState.value.selectedLanguage
            )
            _uiState.update {
                it.copy(
                    deepDiveContent = content,
                    isDeepDiveLoading = false
                )
            }
        }
    }

    fun closeWordDeepDive() {
        _uiState.update {
            it.copy(deepDiveWord = null, deepDiveContent = null, isDeepDiveLoading = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
        speechManager.destroy()
    }
}
