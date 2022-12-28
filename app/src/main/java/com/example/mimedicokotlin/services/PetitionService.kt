package com.example.mimedicokotlin.services

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PetitionService(
    private val authService: AuthService
) {

    private val TAG = "PetitionService"

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    suspend fun addPetition(subject: String, body:String, bitmap: Bitmap): Boolean {
        try{
            val user = authService.getCurrentUserInfo()
            val uid = user.id
            val name = "${user!!["firstname"]}  ${user!!["lastname"]}"
            val bytes = getImageBytes(bitmap)
            val date = LocalDateTime.now().format(formatter)

            val uploadTask = FirebaseStorage.getInstance().getReference("petitions")
                .child(UUID.randomUUID().toString())
                .putBytes(bytes)
                .await()

            val url = uploadTask.storage.downloadUrl.await()

            FirebaseFirestore.getInstance().collection("petitions")
                .add(hashMapOf(
                    "userId" to uid,
                    "name" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                    "urlPhoto" to url,
                    "finished" to false
                ))
                .await()
            return true
        }catch (ex: Exception){
            Log.d(TAG,ex.message!!)
            return false
        }
    }

    suspend fun addPetition(subject: String, body:String): Boolean {
        try{
            val user = authService.getCurrentUserInfo()
            val uid = user.id
            val name = "${user!!["firstname"]}  ${user!!["lastname"]}"
            val date = LocalDateTime.now().format(formatter)

            FirebaseFirestore.getInstance().collection("petitions")
                .add(hashMapOf(
                    "userId" to uid,
                    "name" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                    "finished" to false
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

    suspend fun getPetitionById(petitionId: String): DocumentSnapshot {
        return FirebaseFirestore.getInstance().collection("petitions")
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