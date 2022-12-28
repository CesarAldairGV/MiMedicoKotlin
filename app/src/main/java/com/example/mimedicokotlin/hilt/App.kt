package com.example.mimedicokotlin.hilt

import android.app.Application
import com.example.mimedicokotlin.services.AuthService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(){

    @Inject lateinit var authService: AuthService

    fun getCurrentUserId(): String?{
        return authService.getCurrentUser()?.uid
    }

    fun logout(){
        return authService.logout()
    }
}