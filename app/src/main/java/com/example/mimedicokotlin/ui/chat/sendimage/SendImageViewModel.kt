package com.example.mimedicokotlin.ui.chat.sendimage

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlinfirebase.services.ConsultService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendImageViewModel @Inject constructor(
    private val consultService : ConsultService
): ViewModel() {

    private val _imageState: MutableLiveData<Boolean> = MutableLiveData()
    val imageState: LiveData<Boolean> get() = _imageState

    fun sendImage(consultId: String, bitmap: Bitmap){
        viewModelScope.launch {
            consultService.sendImage(consultId,bitmap,null)
            _imageState.value = true
        }
    }
}