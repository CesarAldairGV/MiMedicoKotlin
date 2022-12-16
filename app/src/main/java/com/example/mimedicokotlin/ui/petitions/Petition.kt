package com.example.mimedicokotlin.ui.petitions

data class Petition(
    val petitionId: String,
    val subject: String,
    val date: String,
    val body: String,
    val img: String?
)