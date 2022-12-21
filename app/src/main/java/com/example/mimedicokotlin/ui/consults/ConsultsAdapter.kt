package com.example.mimedicokotlin.ui.consults

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ConsultsAdapter(options: FirestoreRecyclerOptions<ConsultItem>):
    FirestoreRecyclerAdapter<ConsultItem, ConsultItemViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consult, parent, false)
        return ConsultItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultItemViewHolder, position: Int, model: ConsultItem) {
        holder.bindData(model)
    }
}