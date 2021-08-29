package com.example.firebase_login_reg_matrial.Models

data class Quiz(
    var id: String = "",
    var title: String = "",
    var questions: MutableMap<String, QuestionDataClass> = mutableMapOf(),
)
