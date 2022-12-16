package com.example.mimedicokotlin.ui.proposals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.ui.petitions.PetitionsAdapter
import com.squareup.picasso.Picasso

class ProposalsAdapter(
    private val list: ArrayList<ProposalItem>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProposalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.item_prop_name)
        private val date = itemView.findViewById<TextView>(R.id.item_prop_date)
        private val img = itemView.findViewById<ImageView>(R.id.item_prop_img)
        private val showProposalButton = itemView.findViewById<TextView>(R.id.item_prop_act)

        fun bindData(proposal: ProposalItem){
            name.text = proposal.name
            date.text = proposal.date
            Picasso.get().load(proposal.photoUrl).into(img)
        }
    }

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_proposal,parent,false)
        return ProposalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ProposalViewHolder){
            holder.bindData(list[position])
        }
    }

    override fun getItemCount(): Int = list.size
}