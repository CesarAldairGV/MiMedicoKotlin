package com.example.mimedicokotlin.ui.petitions

data class Petition(
    val subject: String,
    val date: String,
    val body: String,
    val img: String?
)