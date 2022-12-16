package com.example.mimedicokotlin.ui.proposals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentProposalsBinding

class ProposalsFragment : Fragment() {

    private val viewModel = ProposalsViewModel()
    private var _binding: FragmentProposalsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProposalsBinding.inflate(inflater, container, false)

        viewModel.proposals.observe(viewLifecycleOwner){
            val proposalsAdapter = ProposalsAdapter(it,requireContext())
            val recyclerView = binding.proposalsList
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = proposalsAdapter
        }

        viewModel.getAllItems(arguments?.getString("petitionId")!!)

        return binding.root
    }

}