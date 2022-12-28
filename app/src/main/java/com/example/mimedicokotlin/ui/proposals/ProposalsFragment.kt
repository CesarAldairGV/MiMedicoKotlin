package com.example.mimedicokotlin.ui.proposals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentProposalsBinding

class ProposalsFragment : Fragment() {

    private var _binding: FragmentProposalsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProposalsAdapter

    private lateinit var petitionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petitionId = arguments?.getString("petitionId")!!
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProposalsBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        adapter = ProposalsAdapter.getAdapter(petitionId)
        binding.proposalsList.layoutManager = linearLayoutManager
        binding.proposalsList.adapter = adapter

        return binding.root
    }

}