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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllItems(arguments?.getString("petitionId")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProposalsBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        binding.proposalsList.layoutManager = linearLayoutManager
        val proposalsAdapter = ProposalsAdapter(requireContext())
        binding.proposalsList.adapter = proposalsAdapter

        viewModel.proposals.observe(viewLifecycleOwner){
            proposalsAdapter.list = it
            proposalsAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

}