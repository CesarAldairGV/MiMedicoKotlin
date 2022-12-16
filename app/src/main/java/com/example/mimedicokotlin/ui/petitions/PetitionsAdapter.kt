package com.example.mimedicokotlin.ui.petitions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R
import com.squareup.picasso.Picasso

class PetitionsAdapter(
    private val list: ArrayList<Petition>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PetitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subject: TextView = itemView.findViewById(R.id.item_pet_subj)
        private val date: TextView = itemView.findViewById(R.id.item_pet_date)
        private val body: TextView = itemView.findViewById(R.id.item_pet_body)
        private val img: ImageView = itemView.findViewById(R.id.item_pet_img)
        private val proposalButton: TextView = itemView.findViewById(R.id.item_pet_act)

        fun bindData(petition: Petition){
            subject.text = petition.subject
            date.text = petition.date
            body.text = petition.body
            if(petition.img != null) Picasso.get().load(petition.img).into(img)
            else img.visibility = ImageView.GONE

            proposalButton.setOnClickListener{
                val bundle = bundleOf("petitionId" to petition.petitionId)
                itemView.findNavController().navigate(R.id.action_PetitionsFragment_to_ProposalsFragment, bundle)
            }
        }
    }

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_petition,parent,false)
        return PetitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PetitionViewHolder){
            holder.bindData(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

}