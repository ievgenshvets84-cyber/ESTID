package com.example.data.streak

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.max

data class DayActivity(
    val date: String,          // "yyyy-MM-dd"
    val dayOfWeekLabel: String, // "Mo", "Di", "Mi", etc.
    val isPracticed: Boolean,
    val isToday: Boolean
)

data class DailyStreakInfo(
    val currentStreak: Int = 1,
    val bestStreak: Int = 1,
    val isPracticedToday: Boolean = false,
    val lastPracticedDate: String? = null,
    val todayAiTurns: Int = 0,
    val todayVocabTasks: Int = 0,
    val totalActiveDays: Int = 1,
    val weeklyHistory: List<DayActivity> = emptyList(),
    val streakJustExtended: Boolean = false
)

enum class PracticeActivityType {
    AI_CONVERSATION,
    VOCABULARY_TASK
}

class DailyStreakManager private constructor(context: Context) {

    private val appContext = context.applicationContext
    private val streakPrefs: SharedPreferences = appContext.getSharedPreferences(
        "lingua_streak_prefs",
        Context.MODE_PRIVATE
    )
    private val partnerPrefs: SharedPreferences = appContext.getSharedPreferences(
        "lingua_partner_prefs",
        Context.MODE_PRIVATE
    )

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val _streakInfo = MutableStateFlow(loadInitialStreakInfo())
    val streakInfo: StateFlow<DailyStreakInfo> = _streakInfo.asStateFlow()

    init {
        // Evaluate streak on launch to see if days have passed since last practice
        recalculateState()
    }

    private fun getTodayString(): String = dateFormat.format(Date())

    private fun getYesterdayString(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        return dateFormat.format(cal.time)
    }

    /**
     * Calculates days between today and targetDateStr (0 = today, 1 = yesterday, >1 = missed)
     */
    private fun getDaysDifference(targetDateStr: String?): Int {
        if (targetDateStr.isNullOrBlank()) return -1
        return try {
            val targetDate = dateFormat.parse(targetDateStr) ?: return -1
            val targetCal = Calendar.getInstance().apply {
                time = targetDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val todayCal = Calendar.getInstance().apply {
                time = Date()
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val diffMs = todayCal.timeInMillis - targetCal.timeInMillis
            (diffMs / (24 * 60 * 60 * 1000)).toInt()
        } catch (e: Exception) {
            -1
        }
    }

    private fun loadInitialStreakInfo(): DailyStreakInfo {
        // Read existing streak or fallback to partnerPrefs
        var currentStreak = streakPrefs.getInt("streak_current", -1)
        var bestStreak = streakPrefs.getInt("streak_best", -1)
        var lastPracticedDate = streakPrefs.getString("streak_last_date", null)
        val todayStr = getTodayString()

        if (currentStreak < 0) {
            // First time initialization: adopt from partnerPrefs if exists
            val existingPartnerStreak = partnerPrefs.getInt("streak_days", 3)
            currentStreak = existingPartnerStreak.coerceAtLeast(1)
            bestStreak = max(currentStreak, 3)
            // Seed lastPracticedDate as yesterday so the streak is alive and ready to extend today
            lastPracticedDate = getYesterdayString()

            streakPrefs.edit()
                .putInt("streak_current", currentStreak)
                .putInt("streak_best", bestStreak)
                .putString("streak_last_date", lastPracticedDate)
                .apply()
        }

        val lastStoredToday = streakPrefs.getString("streak_today_date", "")
        val todayAiTurns = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_ai_turns", 0) else 0
        val todayVocabTasks = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_vocab", 0) else 0

        val daysDiff = getDaysDifference(lastPracticedDate)
        val isPracticedToday = (daysDiff == 0)

        // If more than 1 day missed, streak has reset to 0 until practice occurs today
        val effectiveStreak = when {
            daysDiff == 0 -> currentStreak
            daysDiff == 1 -> currentStreak // Still alive, waiting for today
            daysDiff > 1 -> 0 // Broken streak
            else -> currentStreak
        }

        val historySet = loadPracticedDates()
        val totalActiveDays = max(historySet.size, effectiveStreak)
        val weeklyHistory = buildWeeklyHistory(historySet, todayStr)

        return DailyStreakInfo(
            currentStreak = effectiveStreak,
            bestStreak = max(bestStreak, effectiveStreak),
            isPracticedToday = isPracticedToday,
            lastPracticedDate = lastPracticedDate,
            todayAiTurns = todayAiTurns,
            todayVocabTasks = todayVocabTasks,
            totalActiveDays = totalActiveDays,
            weeklyHistory = weeklyHistory,
            streakJustExtended = false
        )
    }

    private fun recalculateState() {
        val todayStr = getTodayString()
        val lastPracticedDate = streakPrefs.getString("streak_last_date", null)
        val daysDiff = getDaysDifference(lastPracticedDate)
        val isPracticedToday = (daysDiff == 0)

        val storedCurrent = streakPrefs.getInt("streak_current", 1)
        val storedBest = streakPrefs.getInt("streak_best", 1)

        val effectiveStreak = when {
            daysDiff == 0 -> storedCurrent
            daysDiff == 1 -> storedCurrent // Kept alive from yesterday
            daysDiff > 1 -> 0 // Streak broken
            else -> storedCurrent
        }

        val lastStoredToday = streakPrefs.getString("streak_today_date", "")
        val todayAiTurns = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_ai_turns", 0) else 0
        val todayVocabTasks = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_vocab", 0) else 0

        val history = loadPracticedDates()
        val weekly = buildWeeklyHistory(history, todayStr)

        _streakInfo.update {
            it.copy(
                currentStreak = effectiveStreak,
                bestStreak = max(storedBest, effectiveStreak),
                isPracticedToday = isPracticedToday,
                lastPracticedDate = lastPracticedDate,
                todayAiTurns = todayAiTurns,
                todayVocabTasks = todayVocabTasks,
                totalActiveDays = max(history.size, effectiveStreak),
                weeklyHistory = weekly
            )
        }
    }

    /**
     * Records practice activity (AI Conversation turn or Vocabulary Task)
     */
    @Synchronized
    fun recordActivity(type: PracticeActivityType) {
        val todayStr = getTodayString()
        val lastPracticedDate = streakPrefs.getString("streak_last_date", null)
        val daysDiff = getDaysDifference(lastPracticedDate)

        val storedCurrent = streakPrefs.getInt("streak_current", 1)
        val storedBest = streakPrefs.getInt("streak_best", 1)

        var newStreak = storedCurrent
        var streakJustExtended = false

        if (daysDiff == 0) {
            // Already practiced today, keep streak same
            newStreak = storedCurrent
        } else if (daysDiff == 1) {
            // Practiced yesterday, first practice today -> extend streak!
            newStreak = storedCurrent + 1
            streakJustExtended = true
        } else {
            // Broken streak or first time practice
            newStreak = 1
            streakJustExtended = true
        }

        val newBest = max(storedBest, newStreak)

        // Update daily counts
        val lastStoredToday = streakPrefs.getString("streak_today_date", "")
        var todayAi = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_ai_turns", 0) else 0
        var todayVocab = if (lastStoredToday == todayStr) streakPrefs.getInt("streak_today_vocab", 0) else 0

        when (type) {
            PracticeActivityType.AI_CONVERSATION -> todayAi += 1
            PracticeActivityType.VOCABULARY_TASK -> todayVocab += 1
        }

        // Add to history set
        val history = loadPracticedDates().toMutableSet()
        history.add(todayStr)
        savePracticedDates(history)

        // Persist to streakPrefs
        streakPrefs.edit()
            .putInt("streak_current", newStreak)
            .putInt("streak_best", newBest)
            .putString("streak_last_date", todayStr)
            .putString("streak_today_date", todayStr)
            .putInt("streak_today_ai_turns", todayAi)
            .putInt("streak_today_vocab", todayVocab)
            .apply()

        // Also sync with partnerPrefs for backward compatibility
        partnerPrefs.edit().putInt("streak_days", newStreak).apply()

        val weekly = buildWeeklyHistory(history, todayStr)

        _streakInfo.update {
            it.copy(
                currentStreak = newStreak,
                bestStreak = newBest,
                isPracticedToday = true,
                lastPracticedDate = todayStr,
                todayAiTurns = todayAi,
                todayVocabTasks = todayVocab,
                totalActiveDays = history.size,
                weeklyHistory = weekly,
                streakJustExtended = streakJustExtended
            )
        }
    }

    fun recordAiConversationTurn() {
        recordActivity(PracticeActivityType.AI_CONVERSATION)
    }

    fun recordVocabTask() {
        recordActivity(PracticeActivityType.VOCABULARY_TASK)
    }

    fun dismissCelebration() {
        _streakInfo.update { it.copy(streakJustExtended = false) }
    }

    private fun loadPracticedDates(): Set<String> {
        val json = streakPrefs.getString("streak_dates_json", null) ?: return defaultStarterDates()
        val set = mutableSetOf<String>()
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                set.add(arr.getString(i))
            }
        } catch (e: Exception) {
            return defaultStarterDates()
        }
        return set
    }

    private fun defaultStarterDates(): Set<String> {
        // Provide starter history matching the initial 3-day streak
        val set = mutableSetOf<String>()
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        set.add(dateFormat.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, -1)
        set.add(dateFormat.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, -1)
        set.add(dateFormat.format(cal.time))
        return set
    }

    private fun savePracticedDates(dates: Set<String>) {
        val arr = JSONArray()
        // Keep last 60 days
        dates.toList().takeLast(60).forEach { arr.put(it) }
        streakPrefs.edit().putString("streak_dates_json", arr.toString()).apply()
    }

    private fun buildWeeklyHistory(practicedDates: Set<String>, todayStr: String): List<DayActivity> {
        val result = mutableListOf<DayActivity>()
        val dayLabels = listOf("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa") // Calendar.SUNDAY = 1

        for (i in 6 downTo 0) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -i)
            val dateStr = dateFormat.format(cal.time)
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) // 1 = Sunday, 2 = Monday, ...
            val label = dayLabels[(dayOfWeek - 1) % 7]
            val isPracticed = practicedDates.contains(dateStr)
            val isToday = (dateStr == todayStr)
            result.add(
                DayActivity(
                    date = dateStr,
                    dayOfWeekLabel = label,
                    isPracticed = isPracticed,
                    isToday = isToday
                )
            )
        }
        return result
    }

    companion object {
        @Volatile
        private var INSTANCE: DailyStreakManager? = null

        fun getInstance(context: Context): DailyStreakManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyStreakManager(context).also { INSTANCE = it }
            }
        }
    }
}
