package com.example.mimedicokotlin.ui.petitions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PetitionsAdapter(options: FirestoreRecyclerOptions<PetitionItem>):
    FirestoreRecyclerAdapter<PetitionItem, PetitionItemViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetitionItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_petition, parent, false)
        return PetitionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetitionItemViewHolder, position: Int, model: PetitionItem) {
        holder.bindData(model)
    }

}