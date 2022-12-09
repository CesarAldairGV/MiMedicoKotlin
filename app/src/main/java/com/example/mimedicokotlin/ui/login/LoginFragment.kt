package com.example.mimedicokotlin.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.mimedicokotlin.databinding.FragmentLoginBinding
import kotlin.math.log

class LoginFragment : Fragment() {

    private val loginViewModel = LoginViewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginViewModel.loginForm.observe(viewLifecycleOwner){
            if(it.emailError != null){
                binding.loginEmail.error = "Email debe ser valido"
            }
            if(it.passwordError != null){
                binding.loginPassword.error = "La contraseña debe ser valida"
            }

            binding.loginAction.isEnabled = it.isDataValid
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner){
            if(it.loginError == 1){
                Toast.makeText(activity, "Email no verificado", Toast.LENGTH_LONG).show()
            }else if(it.loginError == 2){
                Toast.makeText(activity, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
            }

            if(it.loginSuccess){
                Toast.makeText(activity, "Login Correcto", Toast.LENGTH_LONG).show()
            }
        }

        binding.loginEmail.addTextChangedListener {
            checkData()
        }
        binding.loginPassword.addTextChangedListener {
            checkData()
        }
        binding.loginAction.setOnClickListener {
            login()
        }

        binding.loginAction.isEnabled = false
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkData(){
        loginViewModel.checkData(
            binding.loginEmail.text.toString(),
            binding.loginPassword.text.toString()
        )
    }

    fun login(){
        loginViewModel.login(
            binding.loginEmail.text.toString(),
            binding.loginPassword.text.toString()
        )
    }
}