package com.example.mimedicokotlin.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mimedicokotlin.ui.signup.SignupFormState
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel: ViewModel() {

    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupForm : LiveData<SignupFormState> = _signupForm

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult : LiveData<Boolean> = _signupResult

    private lateinit var firebaseAuth: FirebaseAuth

    fun singup(username: String, email: String, curp: String, password: String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            _signupResult.value = true
        }
    }

    fun checkUsername(username: String): Boolean{
        return username.length in 4..14
    }

    fun checkEmail(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkCurp(curp: String): Boolean{
        return curp.length == 18
    }

    fun checkPassword(password: String): Boolean{
        return password.length > 5
    }

    fun checkData(username: String, email: String, curp: String, password: String){
        var usernameError : Int? = null
        var emailError : Int? = null
        var curpError : Int? = null
        var passwordError : Int? = null
        var isDataValid = false
        if(!checkUsername(username)){
            usernameError = 1
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
        if(usernameError == null && emailError == null &&
            curpError == null && passwordError == null){
            isDataValid = true
        }
        _signupForm.value = SignupFormState(usernameError, emailError, curpError, passwordError, isDataValid)
    }
}