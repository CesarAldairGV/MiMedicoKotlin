package com.example.mimedicokotlin.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlinfirebase.services.UserAuthService
import com.example.mimedicokotlinfirebase.dto.UserSignupRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authService : UserAuthService
): ViewModel() {

    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupForm : LiveData<SignupFormState> = _signupForm

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult : LiveData<Boolean> = _signupResult

    fun singup(firstname: String, lastname: String, email: String, curp: String, password: String){
        viewModelScope.launch {
            val signupRequest = UserSignupRequest(
                firstname,
                lastname,
                email,
                curp,
                password
            )
            _signupResult.value = authService.signup(signupRequest)
        }
    }

    private fun checkFirstname(firstname: String): Boolean{
        return firstname.isNotEmpty()
    }

    private fun checkLastname(lastname: String): Boolean{
        return lastname.isNotEmpty()
    }

    private fun checkEmail(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkCurp(curp: String): Boolean{
        return curp.length == 18
    }

    private fun checkPassword(password: String): Boolean{
        return password.length > 5
    }

    fun checkData(firstname: String, lastname: String, email: String, curp: String, password: String){
        var firstnameError : Int? = null
        var lastnameError : Int? = null
        var emailError : Int? = null
        var curpError : Int? = null
        var passwordError : Int? = null
        var isDataValid = false
        if(!checkFirstname(firstname)){
            firstnameError = 1
        }
        if(!checkLastname(lastname)){
            lastnameError = 1
        }
        if (!checkEmail(email)){
            emailError = 1
        }
        if (!checkCurp(curp)){
            curpError = 1
        }
        if (!checkPassword(password)){
            passwordError = 1
        }
        if(firstnameError == null && lastnameError == null &&
            emailError == null && curpError == null &&
            passwordError == null){
            isDataValid = true
        }
        _signupForm.value = SignupFormState(firstnameError, lastnameError, emailError, curpError, passwordError, isDataValid)
    }
}