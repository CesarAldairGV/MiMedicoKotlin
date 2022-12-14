package com.example.mimedicokotlin.ui.petitions.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.databinding.FragmentAddPetitionBinding


class AddPetitionFragment : Fragment() {

    private var _binding: FragmentAddPetitionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

}