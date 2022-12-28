package com.example.mimedicokotlin.services

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService {

    private val tag = "UserService"

    suspend fun getUser(userId: String): DocumentSnapshot? {
        Log.d(tag,"Fetching a user...")
        return try {
            val doc = FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .get()
                .await()
            Log.d(tag,"User Fetched correctly")
            doc
        }catch (ex: Exception){
            Log.e(tag,ex.message!!)
            null
        }
    }

    suspend fun createUser(userId: String,
                           firstname: String,
                           lastname: String,
                           email: String,
                           curp: String): Boolean{
        return try{
            Log.d(tag,"Creating a new user...")
            FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .set(hashMapOf(
                    "firstname" to firstname,
                    "lastname" to lastname,
                    "email" to email,
                    "curp" to curp
                ))
                .await()
            Log.d(tag,"New user created successfully")
            true
        }catch (ex : Exception){
            Log.e(tag,ex.message!!)
            false
        }

    }
}