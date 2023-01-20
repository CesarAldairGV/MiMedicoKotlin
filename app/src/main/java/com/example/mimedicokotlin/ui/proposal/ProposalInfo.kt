package com.example.mimedicokotlin.ui.proposal

data class ProposalInfo(
    val name: String,
    val yearsExp: Int,
    val business: String,
    val school: String,
    val photoUrl: String,
    val likes: Int,
    val body: String
)