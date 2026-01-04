package com.ptit_booth_chekin.project.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.*
import java.time.format.DateTimeFormatter


fun parseTimeFromServer(
    time: String // 2025-11-10T09:00:00.000Z
): LocalDateTime {
    val instant = Instant.parse(time)
    return instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()
}


object DateTimeFormatPattern {
    val PATTERN_DATE_ONLY: String = "dd/MM/yyyy"
    val PATTERN_TIME_ONLY: String = "HH:mm:ss"
    val PATTERN_DATE_TIME: String = "dd/MM/yyyy, HH:mm:ss"
    val PATTERN_TIME_DATE: String = "HH:mm dd/MM/yyyy"
    val PATTERN_SERVER: String = "yyyy-MM-dd"

    val PATERRN_DATE_TIME_SESSION: String = "HH:mm"
}

fun parseLocalDateToFormat(
    date: LocalDateTime,
    pattern: String = DateTimeFormatPattern.PATTERN_DATE_TIME
): String {
    return date.format(DateTimeFormatter.ofPattern(pattern))
}


fun getTodayDate(): LocalDate = LocalDate.now()
fun getStartDateOfWeek(): LocalDate {
    val today = getTodayDate()
    val startWeekDate = today.dayOfWeek.value
    return today.minusDays(startWeekDate - 1L)
}


fun getEndDateOfWeek(): LocalDate {
    val today = getTodayDate()
    val endWeek = 7 - today.dayOfWeek.value
    return today.plusDays(endWeek - 1L)

}


fun LocalDate.getStartDateInWeek(): LocalDate {
    val startWeekDate = this.dayOfWeek.value
    return this.minusDays(startWeekDate - 1L)
}

fun LocalDate.getEndDateInWeek(): LocalDate {
    val endWeek = 7 - this.dayOfWeek.value
    return this.plusDays(endWeek - 1L)
}

fun generateWeekFromStart(startDate: LocalDate): List<LocalDate> {
    val listDate = mutableListOf<LocalDate>()
    val startDateWeek = startDate.getStartDateInWeek()
    for (i in 0..6) {
        listDate.add(startDateWeek.plusDays(i.toLong()))
    }
    return listDate
}

fun Int.turnNumberIntoWeekName(): String{
    if(this == 7) return "CN"
    else  return "T${this+1}"
}


fun formatNotificationTime(time: String): String {
    val dateTime = parseTimeFromServer(time)
    return parseLocalDateToFormat(dateTime, DateTimeFormatPattern.PATERRN_DATE_TIME_SESSION)
}

fun main() {
    println(getTodayDate())
    println(getStartDateOfWeek())
    println(getEndDateOfWeek())
}