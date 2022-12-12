package com.example.mimedicokotlin.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel(){

    private val TAG = "LoginViewModel"

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginForm : LiveData<LoginFormState> get() = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult : LiveData<LoginResult> get() = _loginResult

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

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
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            if(!it.user!!.isEmailVerified){
                _loginResult.value = LoginResult(loginError = 1)
            }else{
                _loginResult.value =LoginResult(loginSuccess = true)
            }
        }.addOnFailureListener {
            _loginResult.value =LoginResult(loginError = 2)
        }
    }
}