package com.turing.conferdent_conferentsmanagement.models

data class SpeakerUIModel(
    val id: String = "",
    val name: String,
    val avatar: String, // URL Image
    val workingAt: String,
    val presentationSessionID: List<String> = emptyList<String>()
)