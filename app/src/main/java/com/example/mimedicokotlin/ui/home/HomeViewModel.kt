package com.example.mimedicokotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.AuthService
import com.example.mimedicokotlin.services.UserService
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userService : UserService,
    private val authService: AuthService): ViewModel() {

    private val _profileData: MutableLiveData<ProfileData> = MutableLiveData()
    val profileData: LiveData<ProfileData> get() = _profileData


    fun loadProfileData(){
        viewModelScope.launch {
            _profileData.value = userService.getUser(authService.getCurrentUser()!!.uid)?.toProfileData()
        }
    }

    private fun DocumentSnapshot.toProfileData(): ProfileData =
        ProfileData(
            name = "${this["firstname"]} ${this["lastname"]}",
            email = this["email", String::class.java],
            curp = this["curp", String::class.java]
        )

}