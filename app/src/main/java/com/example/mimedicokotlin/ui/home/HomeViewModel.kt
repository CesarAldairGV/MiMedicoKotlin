package com.example.mimedicokotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel {

    private val _profileData: MutableLiveData<ProfileData> = MutableLiveData()
    val profileData: LiveData<ProfileData> get() = _profileData

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    fun loadProfileData(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        val userId = firebaseAuth.currentUser!!.uid

        firebaseFirestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val data = it.data
                val name = "${data?.get("firstname").toString()} ${data?.get("lastname").toString()}"
                val email = data?.get("email").toString()
                val curp = data?.get("curp").toString()
                _profileData.value = ProfileData(name,email,curp)
            }
    }
}