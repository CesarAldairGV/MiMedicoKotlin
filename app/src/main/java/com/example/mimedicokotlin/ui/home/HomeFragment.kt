package com.example.mimedicokotlin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel = HomeViewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.homeNameField.text = it.name
            binding.homeEmailField.text = it.email
            binding.homeCurpField.text = it.curp
        }

        viewModel.loadProfileData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}