package com.example.mimedicokotlin.ui.proposal

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R

class CommentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val username = itemView.findViewById<TextView>(R.id.item_com_user)
    private val date = itemView.findViewById<TextView>(R.id.item_com_date)
    private val comment = itemView.findViewById<TextView>(R.id.item_com_com)

    fun bindData(commentItem: CommentItem){
        username.text = commentItem.username
        date.text = commentItem.date
        comment.text = commentItem.comment
    }
}