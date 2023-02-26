package com.example.mimedicokotlin.services

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommentService(private val authService: AuthService) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    suspend fun sendComment(consultId: String, medicId: String, comment: String){
        val firestore = FirebaseFirestore.getInstance()
        val user = authService.getCurrentUserInfo()

        firestore.collection("medics")
            .document(medicId)
            .collection("comments")
            .add(hashMapOf(
                "userName" to "${user!!["firstname",String::class.java]} ${user!!["lastname",String::class.java]}",
                "comment" to comment,
                "date" to LocalDateTime.now().format(formatter)
            ))
            .await()

        firestore.collection("consults")
            .document(consultId)
            .update("hasComment",true)
            .await()
    }
}