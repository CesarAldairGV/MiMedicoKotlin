package com.example.mimedicokotlin.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mimedicokotlin.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: PorfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.homeCardLayout.visibility = View.INVISIBLE
        binding.homeLoading.visibility = View.VISIBLE
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.homeNameField.text = it.name
            binding.homeEmailField.text = it.email
            binding.homeCurpField.text = it.curp
            binding.homeCardLayout.visibility = View.VISIBLE
            binding.homeLoading.visibility = View.INVISIBLE
        }

        viewModel.loadProfileData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}