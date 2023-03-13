package com.example.mimedicokotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlinfirebase.dto.User
import com.example.mimedicokotlinfirebase.services.UserAuthService
import com.example.mimedicokotlinfirebase.services.UserService
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userService : UserService,
    private val authService: UserAuthService): ViewModel() {

    private val _profileData: MutableLiveData<ProfileData> = MutableLiveData()
    val profileData: LiveData<ProfileData> get() = _profileData


    fun loadProfileData(){
        viewModelScope.launch {
            _profileData.value = userService.getUser(authService.getCurrentUserId()!!)?.toProfileData()
        }
    }

    private fun User.toProfileData(): ProfileData =
        ProfileData(
            name = "${this.firstName} ${this.lastName}",
            email = this.email,
            curp = this.phone
        )

}