package com.example.mimedicokotlin.ui.proposals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


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


}