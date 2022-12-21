package com.example.mimedicokotlin.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthService{

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    suspend fun login(email: String, password: String): Int{
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Get User, if it is null return 2
        val result = auth.signInWithEmailAndPassword(email, password)
            .await() ?: return 2

        // If is not verified, return 1
        if(!result.user!!.isEmailVerified) return 1

        // Everything is correct, return 0
        return 0;
    }

    fun getCurrentUser(): FirebaseUser? {
        auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }

}