package com.example.mimedicokotlin.ui.addpetition

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.PetitionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPetitionViewModel @Inject constructor(
    private val petitionService : PetitionService
): ViewModel() {

    private val TAG = "AddPetitionViewModel"

    private val _formState: MutableLiveData<AddPetitionFormState> = MutableLiveData()
    val formState: LiveData<AddPetitionFormState> get() = _formState

    private val _resultState: MutableLiveData<Boolean> = MutableLiveData()
    val resultState: LiveData<Boolean> get() = _resultState

    fun sendPetition(subject: String, body:String, bitmap: Bitmap) {
        viewModelScope.launch {
            _resultState.value = petitionService.addPetition(subject, body, bitmap)
        }
    }

    fun sendPetition(subject: String, body: String) {
        viewModelScope.launch {
            _resultState.value = petitionService.addPetition(subject, body)
        }
    }

    fun checkData(subject: String, body : String){
        var subjectError: Int? = null
        var bodyError: Int? = null
        var isDataValid = false
        if(subject.isEmpty() || subject.length !in 5..30){
            subjectError = 1
        }
        if(body.isEmpty() || body.length !in 30..280){
            bodyError = 1
        }
        if(subjectError == null && bodyError == null){
            isDataValid = true
        }
        _formState.value = AddPetitionFormState(subjectError,bodyError,isDataValid)
    }
}