package com.example.mimedicokotlin.ui.chat

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.ConsultService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendImageViewModel @Inject constructor(
    private val consultService : ConsultService
): ViewModel() {
    fun sendImage(consultId: String, bitmap: Bitmap){
        viewModelScope.launch {
            consultService.sendImage(consultId,bitmap)
        }
    }
}