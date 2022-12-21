package com.example.mimedicokotlin.ui.proposals

data class ProposalItem(
    val proposalId: String,
    val petitionId: String,
    val medicName: String,
    val date: String,
    val photoUrl: String
)