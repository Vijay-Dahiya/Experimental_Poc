package com.example.experiment.random


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")


@RequiresApi(Build.VERSION_CODES.O)
private val dayFormatter = DateTimeFormatter.ofPattern("E")   // e.g. "Mon", "Tue"
@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatterWeek = DateTimeFormatter.ofPattern("d")  // e.g. "14", "15"

//@RequiresApi(Build.VERSION_CODES.O)
//fun getCurrentWeekDates(): List<LocalDate> {
//    // Start with “today”
//    val today = LocalDate.now()
//    // If it’s Monday, shift=0 => we get that day; if not, find last Monday
//    val shift = (today.dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
//    val monday = today.minusDays(shift.toLong())
//
//    // Return a list from Monday..Sunday (7 days)
//    return (0..6).map { monday.plusDays(it.toLong()) }
//}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeekDates(): List<LocalDate> {
    // Return exactly 7 days: Monday..Sunday
    val today = LocalDate.now()
    val monday = today.with(java.time.DayOfWeek.MONDAY)
    return (0..6).map { monday.plusDays(it.toLong()) }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): String {
    return LocalDate.now().format(dateFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getLastMondayDate(): String {
    val today = LocalDate.now()
    val shift = (today.dayOfWeek.value - DayOfWeek.MONDAY.value + 7) % 7
    val lastMonday = today.minusDays(shift.toLong())
    return lastMonday.format(dateFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNextSundayDate(): String {
    val today = LocalDate.now()
    val shift = (DayOfWeek.SUNDAY.value - today.dayOfWeek.value + 7) % 7
    val nextSunday = today.plusDays(shift.toLong())
    return nextSunday.format(dateFormatter)
}


data class CalendarDay(
    val date: LocalDate,           // e.g. 2025-01-15
    val streakState: Int,          // 0..3 (Pending, Paused, Done, NotOnboarded)
    val isPartOfStreakTrail: Boolean,
    val isStartOfStreak: Boolean,
    val isEndOfStreak: Boolean,
    val isBeforeJoiningDate: Boolean,
    val isPartOfCurrentWeek: Boolean,
    val isTodayStreakDone: Boolean,  // iOS called “isTodaysStreakDone()”
    val mightBePerfectWeek: Boolean,
    val mightBePerfectMonth: Boolean
    // if you track “streakFlameType,” add here: e.g. spark, fiery, inferno, supernova
)

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.isBeforeOrEquals(other: LocalDate): Boolean {
    return this.isBefore(other) || this.isEqual(other)
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.isLastDayOfWeek(): Boolean {
    // iOS sets Monday=2, meaning Sunday=1.
    // Let's do Monday=1..Sunday=7 in Java time => the code can vary
    return this.dayOfWeek == DayOfWeek.SUNDAY
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.isFirstDayOfWeek(): Boolean {
    return this.dayOfWeek == DayOfWeek.MONDAY
}
