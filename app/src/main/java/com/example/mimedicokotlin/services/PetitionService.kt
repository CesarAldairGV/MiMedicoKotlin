package com.example.mimedicokotlin.services

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.time.Instant.now
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PetitionService(
    private val authService: AuthService
) {

    private val tag = "PetitionService"

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    suspend fun addPetition(subject: String, body:String, bitmap: Bitmap): Boolean {
        return try{
            Log.d(tag, "Creating a new petition with image...")
            val user = authService.getCurrentUserInfo()
            val uid = user?.id
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
                    "userName" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                    "urlPhoto" to url,
                    "timestamp" to Timestamp.now().seconds,
                    "finished" to false
                ))
                .await()
            Log.d(tag, "Petition with image created successfully")
            true
        }catch (ex: Exception){
            Log.d(tag,ex.message!!)
            false
        }
    }

    suspend fun addPetition(subject: String, body:String): Boolean {
        return try{
            Log.d(tag, "Creating a new petition...")
            val user = authService.getCurrentUserInfo()
            val uid = user?.id
            val name = "${user!!["firstname"]}  ${user!!["lastname"]}"
            val date = LocalDateTime.now().format(formatter)

            FirebaseFirestore.getInstance().collection("petitions")
                .add(hashMapOf(
                    "userId" to uid,
                    "userName" to name,
                    "date" to date,
                    "subject" to subject,
                    "body" to body,
                    "timestamp" to Timestamp.now().seconds,
                    "finished" to false
                ))
                .await()
            Log.d(tag, "Petition created successfully")
            true
        }catch (ex: Exception){
            Log.d(tag,ex.message!!)
            false
        }
    }

    private fun getImageBytes(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    suspend fun getPetitionById(petitionId: String): DocumentSnapshot? {
        return try{
            Log.d(tag, "Fetching a petition...")
            val doc = FirebaseFirestore.getInstance().collection("petitions")
                .document(petitionId)
                .get()
                .await()
            Log.d(tag, "Petition fetched")
            doc
        }catch (ex: Exception){
            Log.d(tag,ex.message!!)
            null
        }
    }

    suspend fun finalizePetition(petitionId: String): Boolean{
        return try{
            Log.d(tag, "Finishing Petition...")
            FirebaseFirestore.getInstance().collection("petitions")
                .document(petitionId)
                .update("finished",true)
                .await()
            Log.d(tag, "Petition Finished")
            true
        }catch (ex: Exception){
            Log.d(tag, ex.message!!)
            false
        }
    }
}