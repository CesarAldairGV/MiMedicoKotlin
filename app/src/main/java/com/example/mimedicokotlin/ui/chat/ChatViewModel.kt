package com.example.mimedicokotlin.ui.chat

import androidx.lifecycle.ViewModel
import com.example.mimedicokotlin.services.ConsultService

class ChatViewModel(val consultService: ConsultService) : ViewModel(){

    fun sendMessage(consultId: String, message: String){
        consultService.sendMessage(consultId,message)
    }
}