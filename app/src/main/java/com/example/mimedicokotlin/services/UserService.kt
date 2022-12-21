package com.example.mimedicokotlin.services

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService {
    private lateinit var firestore: FirebaseFirestore

    suspend fun getUser(userId: String): DocumentSnapshot {
        firestore = FirebaseFirestore.getInstance()
        return firestore.collection("users")
            .document(userId)
            .get()
            .await()
    }
}