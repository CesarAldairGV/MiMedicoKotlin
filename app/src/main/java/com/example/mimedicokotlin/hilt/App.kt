package com.example.mimedicokotlin.hilt

import android.app.Application
import com.example.mimedicokotlinfirebase.services.UserAuthService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(){

    @Inject lateinit var authService: UserAuthService

    fun getCurrentUserId(): String?{
        return authService.getCurrentUserId()
    }

    fun logout(){
        return authService.logout()
    }
}