package com.example.mimedicokotlin.services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ConsultService {

    fun getConsultByUserIdQuery(userId: String): Query =
        FirebaseFirestore.getInstance().collection("consults")
            .whereEqualTo("userId",userId)

    suspend fun createConsult(
        proposalId: String,
        petitionId: String,
        medicId: String,
        userId: String,
        medicName: String,
        userName: String,
        subject: String,
        date: String
    ) {
        FirebaseFirestore.getInstance().collection("consults")
            .document()
            .set(
                hashMapOf(
                    "petitionId" to petitionId,
                    "proposalId" to proposalId,
                    "medicId" to medicId,
                    "userId" to userId,
                    "subject" to subject,
                    "medicName" to medicName,
                    "userName" to userName,
                    "date" to date
                )
            )
            .await()
    }
}