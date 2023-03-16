package com.example.mimedicokotlin.ui.signup

data class SignupFormState (
    val firstnameError: Int? = null,
    val lastnameError: Int? = null,
    val emailError: Int? = null,
    val phoneError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)