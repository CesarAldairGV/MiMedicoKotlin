package com.example.mimedicokotlin.services

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService {
    suspend fun getUser(userId: String): DocumentSnapshot {
        return FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .get()
            .await()
    }

    suspend fun createUser(userId: String,
                           firstname: String,
                           lastname: String,
                           email: String,
                           curp: String){
        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .set(hashMapOf(
                "firstname" to firstname,
                "lastname" to lastname,
                "email" to email,
                "curp" to curp
            ))
            .await()
    }
}