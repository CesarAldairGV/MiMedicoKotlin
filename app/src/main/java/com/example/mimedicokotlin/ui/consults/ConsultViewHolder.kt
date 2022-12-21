package com.example.mimedicokotlin.ui.consults

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R

class ConsultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val subject = itemView.findViewById<TextView>(R.id.item_cons_subj)
    private val date = itemView.findViewById<TextView>(R.id.item_cons_date)
    private val medicName = itemView.findViewById<TextView>(R.id.item_cons_medic)
    private val chatButton = itemView.findViewById<Button>(R.id.item_cons_chat)


    fun bindData(consult: Consult){
        subject.text = consult.subject
        date.text = consult.date
        medicName.text = consult.medicName

        chatButton.setOnClickListener {
            val bundle = bundleOf("consultId" to consult.consultId)
            itemView.findNavController().navigate(R.id.action_ConsultsFragment_to_ChatFragment,bundle)
        }
    }

}