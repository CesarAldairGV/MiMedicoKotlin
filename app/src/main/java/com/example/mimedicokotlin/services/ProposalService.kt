package com.example.mimedicokotlin.services

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProposalService(
    private val petitionService : PetitionService,
    private val consultService : ConsultService
) {

    private val tag = "ProposalService"
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    suspend fun getProposalById(proposalId: String): DocumentSnapshot?{
        return try{
            Log.d(tag, "Fetching a proposal...")
            val doc = FirebaseFirestore.getInstance().collection("proposals")
                .document(proposalId)
                .get()
                .await()
            Log.d(tag, "Proposal fetched")
            doc
        }catch (ex : Exception){
            Log.d(tag,ex.message!!)
            null
        }
    }

    suspend fun acceptProposal(proposalId: String): Boolean{
        return try{
            Log.d(tag,"Accepting a proposal...")
            val proposal = getProposalById(proposalId)!!
            val petitionId = proposal["petitionId",String::class.java]!!
            val petition = petitionService.getPetitionById(petitionId)!!

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
            Log.d(tag,"Proposal Accepted")
            true
        }catch (ex : Exception){
            Log.d(tag,ex.message!!)
            false
        }
    }
}