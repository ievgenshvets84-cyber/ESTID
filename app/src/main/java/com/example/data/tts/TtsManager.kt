package com.example.data.tts

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.Locale

class TtsManager(context: Context) {
    private val appContext = context.applicationContext
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private data class PendingUtterance(val text: String, val locale: Locale, val speechRate: Float, val utteranceId: String)
    private var pendingUtterance: PendingUtterance? = null

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentUtteranceId = MutableStateFlow<String?>(null)
    val currentUtteranceId: StateFlow<String?> = _currentUtteranceId.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var mediaPlayer: MediaPlayer? = null
    private var networkAudioJob: Job? = null

    init {
        tts = TextToSpeech(appContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                        _currentUtteranceId.value = utteranceId
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                        _currentUtteranceId.value = null
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                        _currentUtteranceId.value = null
                    }

                    override fun onError(utteranceId: String?, errorCode: Int) {
                        _isSpeaking.value = false
                        _currentUtteranceId.value = null
                    }
                })

                pendingUtterance?.let { pending ->
                    pendingUtterance = null
                    speak(pending.text, pending.locale, pending.speechRate, pending.utteranceId)
                }
            } else {
                Log.w("TtsManager", "TTS initialization failed: $status")
            }
        }
    }

    fun speak(text: String, locale: Locale, speechRate: Float = 1.0f, utteranceId: String = System.currentTimeMillis().toString()) {
        stop()

        val cleanText = text.trim()
        if (cleanText.isEmpty()) return

        val lang = locale.language.lowercase()

        // High fidelity online audio fallback for Japanese, Mandarin, and Ukrainian
        // which typically lack offline TTS voice packs in Android emulators and standard images
        if (lang == "ja" || lang == "zh" || lang == "uk") {
            playNetworkAudio(cleanText, lang, utteranceId)
            return
        }

        val engine = tts
        if (!isInitialized || engine == null) {
            pendingUtterance = PendingUtterance(cleanText, locale, speechRate, utteranceId)
            return
        }

        try {
            var languageSet = false
            val candidates = getCandidateLocales(locale)
            for (candidate in candidates) {
                val result = engine.setLanguage(candidate)
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    languageSet = true
                    Log.d("TtsManager", "Successfully configured TTS locale: $candidate for requested: $locale")
                    break
                }
            }

            if (!languageSet) {
                Log.w("TtsManager", "No voice candidate available for $locale, falling back to online audio stream")
                playNetworkAudio(cleanText, lang, utteranceId)
                return
            }

            engine.setSpeechRate(speechRate.coerceIn(0.5f, 1.5f))
            engine.setPitch(1.0f)
            _isSpeaking.value = true
            _currentUtteranceId.value = utteranceId
            engine.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } catch (e: Exception) {
            Log.e("TtsManager", "Error speaking text with system TTS, trying online audio stream", e)
            playNetworkAudio(cleanText, lang, utteranceId)
        }
    }

    private fun playNetworkAudio(text: String, langCode: String, utteranceId: String) {
        networkAudioJob?.cancel()
        networkAudioJob = scope.launch {
            _isSpeaking.value = true
            _currentUtteranceId.value = utteranceId

            try {
                val chunks = splitTextForTts(text, maxLength = 160)
                val tl = when (langCode) {
                    "zh" -> "zh-CN"
                    "uk" -> "uk"
                    "ja" -> "ja"
                    "ru" -> "ru"
                    "ko" -> "ko"
                    "pt" -> "pt"
                    "es" -> "es"
                    "fr" -> "fr"
                    "de" -> "de"
                    "it" -> "it"
                    else -> langCode
                }

                val audioFiles = withContext(Dispatchers.IO) {
                    chunks.mapNotNull { chunk ->
                        downloadTtsChunk(chunk, tl)
                    }
                }

                if (audioFiles.isEmpty()) {
                    _isSpeaking.value = false
                    _currentUtteranceId.value = null
                    return@launch
                }

                playAudioFilesSequentially(audioFiles, 0)
            } catch (e: Exception) {
                Log.e("TtsManager", "Error playing network TTS audio", e)
                _isSpeaking.value = false
                _currentUtteranceId.value = null
            }
        }
    }

    private fun playAudioFilesSequentially(files: List<File>, index: Int) {
        if (index >= files.size) {
            _isSpeaking.value = false
            _currentUtteranceId.value = null
            mediaPlayer?.release()
            mediaPlayer = null
            return
        }

        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                        .build()
                )
                setDataSource(files[index].absolutePath)
                setOnCompletionListener {
                    playAudioFilesSequentially(files, index + 1)
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("TtsManager", "MediaPlayer error: what=$what, extra=$extra")
                    _isSpeaking.value = false
                    _currentUtteranceId.value = null
                    true
                }
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.e("TtsManager", "Failed to start MediaPlayer for file ${files[index]}", e)
            _isSpeaking.value = false
            _currentUtteranceId.value = null
        }
    }

    private fun downloadTtsChunk(text: String, tl: String): File? {
        return try {
            val cacheKey = "tts_${tl}_${text.hashCode().toString().replace("-", "n")}.mp3"
            val cacheFile = File(appContext.cacheDir, cacheKey)
            if (cacheFile.exists() && cacheFile.length() > 500) {
                return cacheFile
            }

            val encoded = URLEncoder.encode(text, "UTF-8")
            val urlString = "https://translate.google.com/translate_tts?ie=UTF-8&tl=$tl&client=tw-ob&q=$encoded"
            val connection = URL(urlString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)")
            connection.connectTimeout = 5000
            connection.readTimeout = 7000

            if (connection.responseCode == 200) {
                val tempFile = File(appContext.cacheDir, "$cacheKey.tmp")
                connection.inputStream.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                }
                tempFile.renameTo(cacheFile)
                cacheFile
            } else {
                Log.w("TtsManager", "Network TTS returned HTTP ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.w("TtsManager", "Failed to fetch network TTS for '$text': ${e.message}")
            null
        }
    }

    private fun splitTextForTts(text: String, maxLength: Int = 160): List<String> {
        if (text.length <= maxLength) return listOf(text)
        val chunks = mutableListOf<String>()
        val sentences = text.split(Regex("(?<=[.!?。！？\n])\\s*")).filter { it.isNotBlank() }
        var currentChunk = StringBuilder()
        for (s in sentences) {
            if (currentChunk.length + s.length > maxLength) {
                if (currentChunk.isNotEmpty()) {
                    chunks.add(currentChunk.toString().trim())
                    currentChunk = StringBuilder()
                }
                if (s.length > maxLength) {
                    s.split(" ").forEach { word ->
                        if (currentChunk.length + word.length > maxLength) {
                            chunks.add(currentChunk.toString().trim())
                            currentChunk = StringBuilder()
                        }
                        currentChunk.append(word).append(" ")
                    }
                } else {
                    currentChunk.append(s).append(" ")
                }
            } else {
                currentChunk.append(s).append(" ")
            }
        }
        if (currentChunk.isNotBlank()) {
            chunks.add(currentChunk.toString().trim())
        }
        return chunks.ifEmpty { listOf(text.take(maxLength)) }
    }

    fun isLanguageAvailable(locale: Locale): Boolean {
        val lang = locale.language.lowercase()
        // Japanese, Chinese, Ukrainian have online fallback
        if (lang == "ja" || lang == "zh" || lang == "uk") return true
        val engine = tts ?: return true
        val candidates = getCandidateLocales(locale)
        for (candidate in candidates) {
            val res = engine.isLanguageAvailable(candidate)
            if (res >= TextToSpeech.LANG_AVAILABLE) return true
        }
        return true
    }

    private fun getCandidateLocales(locale: Locale): List<Locale> {
        val candidates = mutableListOf<Locale>()
        candidates.add(locale)
        val lang = locale.language.lowercase()
        when (lang) {
            "zh" -> {
                candidates.add(Locale.SIMPLIFIED_CHINESE)
                candidates.add(Locale.CHINA)
                candidates.add(Locale.forLanguageTag("zh-CN"))
                candidates.add(Locale.forLanguageTag("zh-Hans"))
                candidates.add(Locale.forLanguageTag("zh-Hans-CN"))
                candidates.add(Locale.TAIWAN)
                candidates.add(Locale.TRADITIONAL_CHINESE)
                candidates.add(Locale.CHINESE)
            }
            "ja" -> {
                candidates.add(Locale.JAPAN)
                candidates.add(Locale.forLanguageTag("ja-JP"))
                candidates.add(Locale.JAPANESE)
            }
            "ko" -> {
                candidates.add(Locale.KOREA)
                candidates.add(Locale.forLanguageTag("ko-KR"))
                candidates.add(Locale.KOREAN)
            }
            "it" -> {
                candidates.add(Locale.ITALY)
                candidates.add(Locale.forLanguageTag("it-IT"))
                candidates.add(Locale.ITALIAN)
            }
            "ru" -> {
                candidates.add(Locale.forLanguageTag("ru-RU"))
                candidates.add(Locale("ru", "RU"))
                candidates.add(Locale("ru"))
            }
            "pt" -> {
                candidates.add(Locale.forLanguageTag("pt-BR"))
                candidates.add(Locale.forLanguageTag("pt-PT"))
                candidates.add(Locale("pt", "BR"))
                candidates.add(Locale("pt"))
            }
            "de" -> {
                candidates.add(Locale.GERMANY)
                candidates.add(Locale.forLanguageTag("de-DE"))
                candidates.add(Locale.GERMAN)
            }
            "fr" -> {
                candidates.add(Locale.FRANCE)
                candidates.add(Locale.forLanguageTag("fr-FR"))
                candidates.add(Locale.FRENCH)
            }
            "es" -> {
                candidates.add(Locale.forLanguageTag("es-ES"))
                candidates.add(Locale.forLanguageTag("es-US"))
                candidates.add(Locale.forLanguageTag("es-MX"))
                candidates.add(Locale("es"))
            }
            "uk" -> {
                candidates.add(Locale.forLanguageTag("uk-UA"))
                candidates.add(Locale("uk", "UA"))
                candidates.add(Locale("uk"))
            }
            "en" -> {
                candidates.add(Locale.US)
                candidates.add(Locale.UK)
                candidates.add(Locale.ENGLISH)
            }
        }
        candidates.add(Locale.forLanguageTag(lang))
        return candidates.distinct()
    }

    fun stop() {
        pendingUtterance = null
        networkAudioJob?.cancel()
        networkAudioJob = null
        try {
            mediaPlayer?.let { mp ->
                if (mp.isPlaying) mp.stop()
                mp.release()
            }
            mediaPlayer = null
            tts?.stop()
        } catch (e: Exception) {
            Log.e("TtsManager", "Error stopping TTS", e)
        } finally {
            _isSpeaking.value = false
            _currentUtteranceId.value = null
        }
    }

    fun shutdown() {
        stop()
        try {
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (e: Exception) {
            Log.e("TtsManager", "Error shutting down TTS", e)
        }
    }
}
