package com.example.mimedicokotlin.services

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConsultService {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun getChatByConsultIdAndOrderByTimestampQuery(consultId: String): Query =
        FirebaseFirestore.getInstance().collection("consults")
            .document(consultId)
            .collection("chat")
            .orderBy("timestamp")

    fun getConsultByUserIdQuery(userId: String): Query =
        FirebaseFirestore.getInstance().collection("consults")
            .whereEqualTo("userId",userId)

    fun getChatByConsultIdQuery(consultId: String): Query =
        FirebaseFirestore.getInstance().collection("consults")
            .document(consultId)
            .collection("chat")

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

    fun sendMessage(consultId: String, message: String){
        (getChatByConsultIdQuery(consultId) as CollectionReference)
            .add(hashMapOf(
                "message" to message,
                "date" to LocalDateTime.now().format(formatter),
                "timestamp" to Timestamp.now().seconds
            ))
    }
}