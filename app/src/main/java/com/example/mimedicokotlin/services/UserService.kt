package com.example.mimedicokotlin.services

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService {
    private val authService = AuthService()
    private lateinit var firestore: FirebaseFirestore

    suspend fun getActualUser(): DocumentSnapshot {
        firestore = FirebaseFirestore.getInstance()

        val userId = authService.getCurrentUser()!!.uid

        return firestore.collection("users")
            .document(userId)
            .get()
            .await()
    }
}