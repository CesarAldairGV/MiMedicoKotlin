package com.example.mimedicokotlin.ui.petitions.add

data class AddPetitionFormState(
    val subjectError: Int? = null,
    val bodyError: Int? = null,
    val isDataValid: Boolean = false
)