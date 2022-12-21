package com.example.mimedicokotlin.ui.proposals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentProposalsBinding
import com.example.mimedicokotlin.services.ProposalService
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges

class ProposalsFragment : Fragment() {

    private var _binding: FragmentProposalsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProposalsAdapter

    private val proposalService = ProposalService()
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
        binding.proposalsList.layoutManager = linearLayoutManager

        val options = FirestoreRecyclerOptions.Builder<ProposalItem>()
            .setQuery(proposalService.getProposalsByPetitionIdQuery(petitionId), MetadataChanges.INCLUDE){
                it.toProposalItem()
            }
            .build()

        adapter = ProposalsAdapter(options)

        binding.proposalsList.adapter = adapter

        return binding.root
    }

    fun DocumentSnapshot.toProposalItem(): ProposalItem = ProposalItem(
        proposalId = this.id,
        petitionId = this["petitionId",String::class.java]!!,
        medicName = this["medicName",String::class.java]!!,
        date = this["date",String::class.java]!!,
        photoUrl = this["photoUrl",String::class.java]!!
    )

}