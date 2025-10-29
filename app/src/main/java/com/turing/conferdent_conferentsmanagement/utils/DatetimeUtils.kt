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

fun parseLocalDateToFormat(
    date: LocalDateTime
): String {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss"))
}