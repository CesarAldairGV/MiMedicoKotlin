package com.example.mimedicokotlin.ui.chat

data class ConsultData(
    val subject: String,
    val body: String,
    val medicName: String,
    val medicId: String,
    val isFinished: Boolean,
    val hasComment: Boolean,
    val imgUrl: String?
)