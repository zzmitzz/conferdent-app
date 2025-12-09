package com.turing.conferdent_conferentsmanagement.ui.screen.home.event.models

data class SpeakerUIModel(
    val id: Int = 0,
    val name: String,
    val avatar: String,
    val workingAt: String,
    val presentationSessionID: List<String> = emptyList<String>()
)

data class OrganizerUIModel(
    val id: String = "",
    val name: String,
    val avatar: String, // URL Image
    val description: String,

)