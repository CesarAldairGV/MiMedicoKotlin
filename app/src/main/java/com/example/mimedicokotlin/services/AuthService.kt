package com.example.mimedicokotlin.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class AuthService(
    private val userService : UserService
){

    private val tag = "AuthService"

    suspend fun login(email: String, password: String): Int {
        val auth = FirebaseAuth.getInstance()
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (!result.user!!.isEmailVerified){
                Log.d(tag, "User not verified")
                auth.signOut()
                return 1
            }
            Log.d(tag, "Login Successfully")
            return 0
        } catch (ex: Exception) {
            Log.e(tag,ex.message!!)
            return 2
        }
    }

    suspend fun signup(firstname: String,
                       lastname: String,
                       email: String,
                       curp: String,
                       password: String): Boolean {
        return try {
            val signupResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).await()
            val userId = signupResult.user!!.uid
            signupResult.user!!.sendEmailVerification().await()
            userService.createUser(userId, firstname, lastname, email, curp)
            Log.d(tag,"Signup Successfully")
            true
        }catch (ex : Exception){
            Log.d(tag,ex.message!!)
            false
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    suspend fun getCurrentUserInfo(): DocumentSnapshot?{
        val userId = getCurrentUser()?.uid
        if(userId != null) return userService.getUser(userId)
        return null
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

}