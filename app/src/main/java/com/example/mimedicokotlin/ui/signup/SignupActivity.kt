package com.example.mimedicokotlin.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.mimedicokotlin.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private var viewModel: SignupViewModel = SignupViewModel()
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.signupForm.observe(this){
            if(it.usernameError != null){
                binding.signupUsername.error = "Username debe ser mayor de 3 y menor de 15 caracteres"
            }
            if(it.emailError != null){
                binding.signupEmail.error = "El email debe ser valido"
            }
            if(it.curpError != null){
                binding.signupCurp.error = "El curp debe tener 18 caracteres"
            }
            if(it.passwordError != null){
                binding.signupPassword.error = "La contraseña debe ser mayor de 5 caracteres"
            }
            binding.signupAction.isEnabled = it.isDataValid
        }

        viewModel.signupResult.observe(this) {
            if (it) Toast.makeText(this@SignupActivity, "Registro Correcto", Toast.LENGTH_LONG)
                .show()
        }

        binding.signupUsername.addTextChangedListener {
            checkData()
        }

        binding.signupEmail.addTextChangedListener {
            checkData()
        }

        binding.signupCurp.addTextChangedListener {
            checkData()
        }

        binding.signupPassword.addTextChangedListener {
            checkData()
        }

        binding.signupAction.setOnClickListener {
            signup()
        }

        binding.signupAction.isEnabled = false
    }

    fun signup(){
        viewModel.singup(
            binding.signupUsername.text.toString(),
            binding.signupEmail.text.toString(),
            binding.signupCurp.text.toString(),
            binding.signupPassword.text.toString())
    }

    fun checkData(){
        viewModel.checkData(
            binding.signupUsername.text.toString(),
            binding.signupEmail.text.toString(),
            binding.signupCurp.text.toString(),
            binding.signupPassword.text.toString())
    }
}