package com.example.mimedicokotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _profileData: MutableLiveData<ProfileData> = MutableLiveData()
    val profileData: LiveData<ProfileData> get() = _profileData

    private val userService = UserService()

    fun loadProfileData(){
        viewModelScope.launch {
            _profileData.value = userService.getActualUser().toProfileData()
        }
    }

    fun DocumentSnapshot.toProfileData(): ProfileData{
        val name = "${this["firstname"]} ${this["lastname"]}"
        val email = this["email", String::class.java]
        val curp = this["curp", String::class.java]

        return ProfileData(name,email,curp)
    }
}