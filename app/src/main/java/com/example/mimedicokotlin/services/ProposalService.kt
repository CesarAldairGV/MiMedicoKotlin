package com.example.mimedicokotlin.services

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProposalService {
    private val petitionService = PetitionService()
    private val consultService = ConsultService()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun getProposalsByPetitionIdQuery(petitionId: String): Query =
        FirebaseFirestore.getInstance()
        .collection("proposals")
        .whereEqualTo("petitionId",petitionId)

    suspend fun getProposalById(proposalId: String): DocumentSnapshot{
        return FirebaseFirestore.getInstance().collection("proposals")
            .document(proposalId)
            .get()
            .await()
    }

    suspend fun acceptProposal(proposalId: String){
        val proposal = getProposalById(proposalId)
        val petitionId = proposal["petitionId",String::class.java]!!
        val petition = petitionService.getPetitionById(petitionId)

        petitionService.finalizePetition(petitionId)

        consultService.createConsult(
            proposalId,
            petitionId,
            proposal["medicId",String::class.java]!!,
            petition["userId",String::class.java]!!,
            proposal["medicName",String::class.java]!!,
            petition["userName",String::class.java]!!,
            petition["subject", String::class.java]!!,
            LocalDateTime.now().format(formatter)
        )
    }
}