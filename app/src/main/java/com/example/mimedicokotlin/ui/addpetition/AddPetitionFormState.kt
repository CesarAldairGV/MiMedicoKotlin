package com.example.mimedicokotlin.ui.addpetition

data class AddPetitionFormState(
    val subjectError: Int? = null,
    val bodyError: Int? = null,
    val isDataValid: Boolean = false
)