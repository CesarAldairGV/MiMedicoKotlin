package com.example.mimedicokotlin.services

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    suspend fun sendImage(consultId: String, bitmap: Bitmap){
        val bytes = getImageBytes(bitmap)
        val res = FirebaseStorage.getInstance()
            .getReference("chatimg")
            .child(UUID.randomUUID().toString())
            .putBytes(bytes)
            .await()
        val url = res.storage.downloadUrl.await()
        (getChatByConsultIdQuery(consultId) as CollectionReference)
            .add(hashMapOf(
                "imgUrl" to url,
                "date" to LocalDateTime.now().format(formatter),
                "timestamp" to Timestamp.now().seconds
            ))
    }

    private fun getImageBytes(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }
}