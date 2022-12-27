package com.example.mimedicokotlin.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ChatAdapter(val recyclerView: RecyclerView, options: FirestoreRecyclerOptions<MessageItem>):
    FirestoreRecyclerAdapter<MessageItem, MessageItemViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MessageItemViewHolder,
        position: Int,
        model: MessageItem
    ) {
        holder.bindData(model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        recyclerView.scrollToPosition(this.itemCount - 1)
    }
}