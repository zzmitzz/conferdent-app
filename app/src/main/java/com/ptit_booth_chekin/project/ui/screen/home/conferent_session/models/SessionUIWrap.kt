package com.ptit_booth_chekin.project.ui.screen.home.conferent_session.models

import java.time.LocalDate
import java.time.LocalDateTime

enum class SessionTypeState{
    UPCOMING,
    HAPPENING,
    CANCELED
}


data class SpeakerSession(
    val id: String,
    val avatarLink :String,
)

data class SessionUIWrap(
    val id: Int = 0,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val sessionName: String,
    val status: SessionTypeState,
    val description: String,
    val place: String,
    val speaker: List<SpeakerSession> = emptyList(),
    val isNotificationOn: Boolean = false
)