package com.example.mimedicokotlin.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.ConsultService
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
            consultService.sendMessage(consultId,message)
        }
    }

    fun checkMessage(message: String){
        _messageState.value = message.isNotEmpty()
    }

    fun getConsultData(consultId: String){
        viewModelScope.launch {
            _consultData.value = consultService.getConsultData(consultId).toConsultData()
        }
    }

    fun DocumentSnapshot.toConsultData() = ConsultData(
        subject = this["subject",String::class.java]!!,
        body = "TODO",
        medicName = this["medicName",String::class.java]!!,
        medicId = this["medicId",String::class.java]!!,
        isFinished = this["finished",Boolean::class.java]!!,
        hasComment = this["hasComment",Boolean::class.java]!!
    )
}