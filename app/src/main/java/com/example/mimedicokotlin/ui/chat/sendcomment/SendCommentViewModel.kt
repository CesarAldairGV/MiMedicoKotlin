package com.example.mimedicokotlin.ui.chat.sendcomment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.CommentService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendCommentViewModel @Inject constructor(
    private val commentService: CommentService
) : ViewModel() {

    private val _result: MutableLiveData<Boolean> = MutableLiveData()
    val result: LiveData<Boolean> get() = _result

    fun sendComment(consultId: String, medicId: String, comment: String){
        viewModelScope.launch {
            commentService.sendComment(consultId,medicId, comment)
            _result.value = true
        }
    }
}