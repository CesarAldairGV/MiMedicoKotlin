package com.example.mimedicokotlin.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlinfirebase.dto.Consult
import com.example.mimedicokotlinfirebase.dto.SendChatMessageRequest
import com.example.mimedicokotlinfirebase.services.ConsultService
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val consultService : ConsultService
) : ViewModel(){

    private val _consultData: MutableLiveData<ConsultData> = MutableLiveData()
    val consultData: LiveData<ConsultData> get() = _consultData

    private val _messageState: MutableLiveData<Boolean> = MutableLiveData()
    val messageState: LiveData<Boolean> get() = _messageState

    fun sendMessage(consultId: String, message: String){
        viewModelScope.launch {
            val messageRequest = SendChatMessageRequest(
                consultId = consultId,
                message = message,
                medicPhotoUrl = null
            )
            consultService.sendMessage(messageRequest)
        }
    }

    fun checkMessage(message: String){
        _messageState.value = message.isNotEmpty()
    }

    fun getConsultData(consultId: String){
        viewModelScope.launch {
            _consultData.value = consultService.getConsultById(consultId)!!.toConsultData()
        }
    }

    fun Consult.toConsultData() = ConsultData(
        subject = this.title,
        body = this.body,
        medicName = this.medicName,
        medicId = this.medicId,
        isFinished = this.finished,
        hasComment = this.comment
    )
}