package com.example.mimedicokotlin.ui.petitions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentPetitionsBinding


class PetitionsFragment : Fragment() {

    private var _binding: FragmentPetitionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPetitionsBinding.inflate(inflater, container, false)

        binding.petitionsAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_PetitionsFragment_to_AddPetitionFragment)
        }

        return binding.root
    }
}