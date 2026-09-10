package com.example.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        val engine = tts ?: return
        if (!isInitialized) {
            pendingUtterance = PendingUtterance(text, locale, speechRate, utteranceId)
            return
        }

        try {
            val result = engine.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TtsManager", "Language $locale not supported or missing data, falling back to default")
                engine.language = Locale.getDefault()
            }
            engine.setSpeechRate(speechRate.coerceIn(0.5f, 1.5f))
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } catch (e: Exception) {
            Log.e("TtsManager", "Error speaking text", e)
            _isSpeaking.value = false
        }
    }

    fun stop() {
        pendingUtterance = null
        try {
            tts?.stop()
            _isSpeaking.value = false
            _currentUtteranceId.value = null
        } catch (e: Exception) {
            Log.e("TtsManager", "Error stopping TTS", e)
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (e: Exception) {
            Log.e("TtsManager", "Error shutting down TTS", e)
        }
    }
}
