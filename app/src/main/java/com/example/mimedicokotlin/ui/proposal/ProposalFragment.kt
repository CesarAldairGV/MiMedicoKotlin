package com.example.mimedicokotlin.ui.proposal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentProposalBinding
import com.squareup.picasso.Picasso

class ProposalFragment : Fragment() {

    private val viewModel = ProposalViewModel()
    private var _binding: FragmentProposalBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProposalInfo(arguments?.getString("proposalId")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentProposalBinding.inflate(inflater, container, false)

        binding.root.visibility = View.INVISIBLE

        viewModel.proposalInfo.observe(viewLifecycleOwner){
            binding.propName.text = it.name
            binding.propYears.text = it.yearsExp
            binding.propBussiness.text = it.business
            binding.propSchool.text = it.school
            binding.propMessage.text = it.body
            binding.propLikes.text = it.likes
            Picasso.get().load(it.photoUrl).into(binding.propPhoto)
            binding.root.visibility = View.VISIBLE
        }

        return binding.root
    }

}