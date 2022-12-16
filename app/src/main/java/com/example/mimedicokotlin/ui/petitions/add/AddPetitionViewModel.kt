package com.example.mimedicokotlin.ui.petitions.add

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddPetitionViewModel : ViewModel() {

    private val TAG = "AddPetitionViewModel"

    private val _formState: MutableLiveData<AddPetitionFormState> = MutableLiveData()
    val formState: LiveData<AddPetitionFormState> get() = _formState

    private val _resultState: MutableLiveData<Boolean> = MutableLiveData()
    val resultState: LiveData<Boolean> get() = _resultState

    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    fun sendPetition(subject: String, body:String, bitmap: Bitmap) {
        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        val uid = firebaseAuth.currentUser!!.uid
        val bytes = getImageBytes(bitmap)
        val petitionId = UUID.randomUUID().toString()
        val date = LocalDateTime.now().format(formatter)
        var name = ""

        firebaseFirestore.collection("users")
            .document(uid)
            .get()
            .onSuccessTask {
                name = "${it.data!!["firstname"]}  ${it.data!!["lastname"]}"
                firebaseStorage.getReference("petitions")
                    .child(petitionId)
                    .putBytes(bytes)
            }.onSuccessTask {
                it.storage.downloadUrl
            }.onSuccessTask {
                firebaseFirestore.collection("petitions")
                    .document(petitionId)
                    .set(
                        hashMapOf(
                            "userId" to uid,
                            "name" to name,
                            "date" to date,
                            "subject" to subject,
                            "body" to body,
                            "urlPhoto" to it.toString()
                        )
                    )
            }.addOnSuccessListener {
                _resultState.value = true
            }.addOnSuccessListener {
                _resultState.value = false
            }
    }

    fun sendPetition(subject: String, body: String) {
        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        val uid = firebaseAuth.currentUser!!.uid
        val petitionId = UUID.randomUUID().toString()
        val date = LocalDateTime.now().format(formatter)
        var name = ""

        firebaseFirestore.collection("users")
            .document(uid)
            .get()
            .onSuccessTask {
                name = "${it.data!!["firstname"]}  ${it.data!!["lastname"]}"
                firebaseFirestore.collection("petitions")
                    .document(petitionId)
                    .set(
                        hashMapOf(
                            "userId" to uid,
                            "name" to name,
                            "date" to date,
                            "subject" to subject,
                            "body" to body,
                        )
                    )
            }.addOnSuccessListener {
                _resultState.value = true
            }.addOnSuccessListener {
                _resultState.value = false
            }
    }

    fun checkData(subject: String, body : String){
        var subjectError: Int? = null
        var bodyError: Int? = null
        var isDataValid = false
        if(subject.isEmpty() || subject.length !in 5..30){
            subjectError = 1
        }
        if(body.isEmpty() || body.length !in 30..280){
            bodyError = 1
        }
        if(subjectError == null && bodyError == null){
            isDataValid = true
        }
        _formState.value = AddPetitionFormState(subjectError,bodyError,isDataValid)
    }

    fun getImageBytes(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }
}