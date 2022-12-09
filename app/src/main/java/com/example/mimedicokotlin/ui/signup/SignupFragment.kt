package com.example.mimedicokotlin.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var viewModel: SignupViewModel = SignupViewModel()

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)

        viewModel.signupForm.observe(viewLifecycleOwner){
            if(it.firstnameError != null){
                binding.signupFirstname.error = "El nombre no debe estar vacio"
            }
            if(it.lastnameError != null){
                binding.signupLastname.error = "Los apellidos no debe estar vacio"
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

        viewModel.signupResult.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, "Registro Correcto", Toast.LENGTH_LONG)
                    .show()
                val nav = findNavController()
                nav.popBackStack()
                nav.navigate(R.id.SignupSuccessFragment)
            }else {
                Toast.makeText(activity, "Error, no se pudo registrar", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.signupFirstname.addTextChangedListener {
            checkData()
        }
        binding.signupLastname.addTextChangedListener{
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun signup(){
        viewModel.singup(
            binding.signupFirstname.text.toString(),
            binding.signupLastname.text.toString(),
            binding.signupEmail.text.toString(),
            binding.signupCurp.text.toString(),
            binding.signupPassword.text.toString())
    }

    fun checkData(){
        viewModel.checkData(
            binding.signupFirstname.text.toString(),
            binding.signupLastname.text.toString(),
            binding.signupEmail.text.toString(),
            binding.signupCurp.text.toString(),
            binding.signupPassword.text.toString())
    }
}