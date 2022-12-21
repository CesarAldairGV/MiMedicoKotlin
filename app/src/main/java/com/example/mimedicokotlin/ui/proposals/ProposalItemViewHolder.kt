package com.example.mimedicokotlin.ui.proposals

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R
import com.squareup.picasso.Picasso

class ProposalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name = itemView.findViewById<TextView>(R.id.item_prop_name)
    private val date = itemView.findViewById<TextView>(R.id.item_prop_date)
    private val img = itemView.findViewById<ImageView>(R.id.item_prop_img)
    private val showProposalButton = itemView.findViewById<Button>(R.id.item_prop_act)

    fun bindData(proposal: ProposalItem){
        name.text = proposal.medicName
        date.text = proposal.date
        Picasso.get().load(proposal.photoUrl).into(img)

        showProposalButton.setOnClickListener {
            val bundle = bundleOf("proposalId" to proposal.proposalId,"petitionId" to proposal.petitionId)
            itemView.findNavController().navigate(R.id.action_ProposalsFragment_to_ProposalFragment, bundle)
        }
    }
}