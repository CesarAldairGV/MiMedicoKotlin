package com.example.mimedicokotlin.ui.petitions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentPetitionsBinding
import com.example.mimedicokotlin.services.AuthService
import com.example.mimedicokotlin.services.PetitionService
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges

class PetitionsFragment : Fragment() {

    private var _binding: FragmentPetitionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FirestoreRecyclerAdapter<PetitionItem, PetitionItemViewHolder>

    private val authService = AuthService()

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPetitionsBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        adapter = PetitionsAdapter.getAdapter(authService.getCurrentUser()!!.uid)
        binding.petitionsList.layoutManager = linearLayoutManager
        binding.petitionsList.adapter = adapter

        binding.petitionsAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_PetitionsFragment_to_AddPetitionFragment)
        }

        return binding.root
    }

}