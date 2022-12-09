package com.example.mimedicokotlin.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentSignupBinding
import com.example.mimedicokotlin.databinding.FragmentSignupSuccessBinding


class SignupSuccessFragment : Fragment() {

    private var _binding: FragmentSignupSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

}