package com.ptit_booth_chekin.project.ui.screen.home.screen_form.data

enum class AnswerType {
    EMAIL, PHONE, FILE, RADIO, CHECKBOX, TEXT, TEXTAREA, NUMBER, DATE
}

fun String.castToAnswerType(): AnswerType {
    val result =  when(this) {
        "EMAIL" -> AnswerType.EMAIL
        "PHONE" -> AnswerType.PHONE
        "FILE" -> AnswerType.FILE
        "RADIO" -> AnswerType.RADIO
        "CHECKBOX" -> AnswerType.CHECKBOX
        "TEXT" -> AnswerType.TEXT
        "TEXTAREA" -> AnswerType.TEXTAREA
        "NUMBER" -> AnswerType.NUMBER
        "DATE" -> AnswerType.DATE
        else -> throw IllegalArgumentException("Unknown AnswerType: $this")
    }
    return result
}
