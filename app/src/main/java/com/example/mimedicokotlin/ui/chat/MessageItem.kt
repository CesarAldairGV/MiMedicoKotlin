package com.example.mimedicokotlin.ui.chat

data class MessageItem(
    var message: String? = null,
    var imgUrl: String? = null,
    var photoUrl: String? = null,
    var date: String
)