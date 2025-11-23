package com.turing.conferdent_conferentsmanagement.utils

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

    val PATTERN_SERVER: String = "yyyy-MM-dd"
}
fun parseLocalDateToFormat(
    date: LocalDateTime,
    pattern: String = DateTimeFormatPattern.PATTERN_DATE_TIME
): String {
    return date.format(DateTimeFormatter.ofPattern(pattern))
}