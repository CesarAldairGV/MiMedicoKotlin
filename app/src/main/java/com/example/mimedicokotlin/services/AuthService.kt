package com.example.mimedicokotlin.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthService {

    private val TAG = "AuthService"

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    suspend fun login(email: String, password: String): Int {
        auth = FirebaseAuth.getInstance()
        // Login, if it is not successfully return 2
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            //If it is not verified, return 1
            if (!result.user!!.isEmailVerified){
                auth.signOut()
                return 1
            }
            // Everything is correct, return 0
            return 0
        } catch (ex: Exception) {
            Log.d(TAG,ex.message!!)
            return 2
        }
    }

    suspend fun signup(firstname: String, lastname: String, email: String, curp: String, password: String): Boolean {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        try {
            val signupResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = signupResult.user!!.uid
            signupResult.user!!.sendEmailVerification().await()
            firestore.collection("users")
                .document(userId)
                .set(hashMapOf(
                        "firstname" to firstname,
                        "lastname" to lastname,
                        "email" to email,
                        "curp" to curp
                    ))
                .await()
            return true
        }catch (ex : Exception){
            Log.d(TAG,ex.message!!)
            return false
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }

}