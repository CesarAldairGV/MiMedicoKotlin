package com.example.mimedicokotlin.ui.chat

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.ConsultService
import kotlinx.coroutines.launch

class SendImageViewModel : ViewModel() {
    private val consultService = ConsultService()

    fun sendImage(consultId: String, bitmap: Bitmap){
        viewModelScope.launch {
            consultService.sendImage(consultId,bitmap)
        }
    }
}