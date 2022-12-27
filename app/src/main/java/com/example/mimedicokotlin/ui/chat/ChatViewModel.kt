package com.example.mimedicokotlin.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mimedicokotlin.services.ConsultService

class ChatViewModel() : ViewModel(){

    private val consultService = ConsultService()
    private val _messageState: MutableLiveData<Boolean> = MutableLiveData()
    val messageState: LiveData<Boolean> get() = _messageState

    fun sendMessage(consultId: String, message: String){
        consultService.sendMessage(consultId,message)
    }

    fun checkMessage(message: String){
        _messageState.value = message.isNotEmpty()
    }
}