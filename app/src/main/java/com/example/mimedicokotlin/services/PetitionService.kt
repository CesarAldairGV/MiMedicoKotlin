package com.example.mimedicokotlin.services

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PetitionService {

    private val TAG = "PetitionService"

    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private val authService = AuthService()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    suspend fun addPetition(subject: String, body:String, bitmap: Bitmap): Boolean {
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()

        try{
            val user = authService.getCurrentUserInfo()
            val uid = user.id
            val name = "${user!!["firstname"]}  ${user!!["lastname"]}"
            val bytes = getImageBytes(bitmap)
            val date = LocalDateTime.now().format(formatter)

            val uploadTask = storage.getReference("petitions")
                .child(UUID.randomUUID().toString())
                .putBytes(bytes)
                .await()

            val url = uploadTask.storage.downloadUrl.await()

            firestore.collection("petitions")
                .add(hashMapOf(
                    "userId" to uid,
                    "name" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                    "urlPhoto" to url
                ))
                .await()
            return true
        }catch (ex: Exception){
            Log.d(TAG,ex.message!!)
            return false
        }
    }

    suspend fun addPetition(subject: String, body:String): Boolean {
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()

        try{
            val user = authService.getCurrentUserInfo()
            val uid = user.id
            val name = "${user!!["firstname"]}  ${user!!["lastname"]}"
            val date = LocalDateTime.now().format(formatter)

            firestore.collection("petitions")
                .add(hashMapOf(
                    "userId" to uid,
                    "name" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                ))
                .await()
            return true
        }catch (ex: Exception){
            Log.d(TAG,ex.message!!)
            return false
        }
    }

    private fun getImageBytes(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun getPetitionsByCurrentUserQuery(): Query =
        getPetitionsByUserIdQuery(authService.getCurrentUser()!!.uid)

    fun getPetitionsByUserIdQuery(userId: String): Query =
        FirebaseFirestore.getInstance().collection("petitions")
            .whereEqualTo("userId",userId)

    suspend fun getPetitionById(petitionId: String): DocumentSnapshot {
        firestore = FirebaseFirestore.getInstance()
        return firestore.collection("petitions")
            .document(petitionId)
            .get()
            .await()
    }

    suspend fun finalizePetition(petitionId: String){
        FirebaseFirestore.getInstance().collection("petitions")
            .document(petitionId)
            .update("finished",true)
            .await()
    }
}