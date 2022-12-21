package com.example.mimedicokotlin.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.AuthService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){

    private val TAG = "LoginViewModel"

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginForm : LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult : LiveData<LoginResult> get() = _loginResult

    private val authService = AuthService()

    private fun checkEmail(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPassword(password: String): Boolean{
        return password.length > 5
    }

    fun checkData(email: String, password: String){
        var emailError: Int? = null
        var passwordError: Int? = null
        var isDataValid = false
        if(!checkEmail(email)) emailError = 1
        if(!checkPassword(password)) passwordError = 1
        if(emailError == null && passwordError == null) isDataValid = true
        _loginForm.value = LoginFormState(emailError, passwordError, isDataValid)
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            val res = authService.login(email,password)
            if(res == 0) _loginResult.value = LoginResult(loginSuccess = true)
            else _loginResult.value = LoginResult(loginError = res)
        }
    }
}