package com.example.mimedicokotlin.ui.consults

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges

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

    companion object {
        fun getAdapter(userId: String): ConsultsAdapter {

            val query = FirebaseFirestore.getInstance().collection("consults")
                .whereEqualTo("userId",userId)
                .orderBy("timestamp")

            val options = FirestoreRecyclerOptions.Builder<ConsultItem>()
                .setQuery(query,
                    MetadataChanges.INCLUDE){
                    it.toConsultItem()
                }
                .build()

            return ConsultsAdapter(options)
        }

        private fun DocumentSnapshot.toConsultItem(): ConsultItem =
            ConsultItem(
                consultId = this.id,
                medicName = this["medicName",String::class.java]!!,
                subject = this["subject",String::class.java]!!,
                date = this["date",String::class.java]!!
            )
    }
}