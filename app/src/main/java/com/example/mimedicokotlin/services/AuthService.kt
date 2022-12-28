package com.example.mimedicokotlin.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class AuthService(
    private val userService : UserService
){

    private val TAG = "AuthService"

    suspend fun login(email: String, password: String): Int {
        val auth = FirebaseAuth.getInstance()
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

    suspend fun signup(firstname: String,
                       lastname: String,
                       email: String,
                       curp: String,
                       password: String): Boolean {
        try {
            val signupResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).await()
            val userId = signupResult.user!!.uid
            signupResult.user!!.sendEmailVerification().await()
            userService.createUser(userId, firstname, lastname, email, curp)
            return true
        }catch (ex : Exception){
            Log.d(TAG,ex.message!!)
            return false
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    suspend fun getCurrentUserInfo(): DocumentSnapshot{
        return userService.getUser(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

}