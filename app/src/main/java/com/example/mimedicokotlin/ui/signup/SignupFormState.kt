package com.example.mimedicokotlin.ui.signup

data class SignupFormState (
    val usernameError: Int? = null,
    val emailError: Int? = null,
    val curpError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)