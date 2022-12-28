package com.example.mimedicokotlin.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels()
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
                binding.signupFirstname.error = getString(R.string.signup_firstname_err)
            }
            if(it.lastnameError != null){
                binding.signupLastname.error = getString(R.string.signup_lastname_err)
            }
            if(it.emailError != null){
                binding.signupEmail.error = getString(R.string.signup_email_err)
            }
            if(it.curpError != null){
                binding.signupCurp.error = getString(R.string.signup_curp_err)
            }
            if(it.passwordError != null){
                binding.signupPassword.error = getString(R.string.signup_password_err)
            }
            binding.signupAction.isEnabled = it.isDataValid
        }

        viewModel.signupResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_SignupFragment_to_SignupSuccessFragment)
            }else {
                Toast.makeText(activity, getString(R.string.signup_err1), Toast.LENGTH_LONG)
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