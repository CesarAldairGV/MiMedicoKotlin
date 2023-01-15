package com.example.mimedicokotlin.ui.proposals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges

class ProposalsAdapter(options: FirestoreRecyclerOptions<ProposalItem>)
    : FirestoreRecyclerAdapter<ProposalItem, ProposalItemViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProposalItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proposal, parent, false)
        return ProposalItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProposalItemViewHolder, position: Int, model: ProposalItem) {
        holder.bindData(model)
    }

    companion object{

        fun getAdapter(petitionId: String): ProposalsAdapter {
            val query = FirebaseFirestore.getInstance()
                .collection("proposals")
                .whereEqualTo("petitionId",petitionId)
                .orderBy("timestamp")

            val options = FirestoreRecyclerOptions.Builder<ProposalItem>()
                .setQuery(query, MetadataChanges.EXCLUDE){
                    it.toProposalItem()
                }
                .build()

            return ProposalsAdapter(options)
        }

        private fun DocumentSnapshot.toProposalItem(): ProposalItem = ProposalItem(
            proposalId = this.id,
            petitionId = this["petitionId", String::class.java]!!,
            medicName = this["medicName", String::class.java]!!,
            date = this["date", String::class.java]!!,
            photoUrl = this["photoUrl", String::class.java]!!
        )
    }
}