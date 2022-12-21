package com.example.mimedicokotlin.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProposalService {

    private lateinit var firestore: FirebaseFirestore

    fun getProposalsByPetitionIdQuery(petitionId: String): Query =
        FirebaseFirestore.getInstance()
        .collection("proposals")
        .whereEqualTo("petitionId",petitionId)
}