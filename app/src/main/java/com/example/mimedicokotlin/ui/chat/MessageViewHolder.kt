package com.example.mimedicokotlin.ui.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimedicokotlin.R
import com.squareup.picasso.Picasso

class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val body = itemView.findViewById<TextView>(R.id.item_msg_body)
    private val photo = itemView.findViewById<ImageView>(R.id.item_msg_photo)
    private val date = itemView.findViewById<TextView>(R.id.item_msg_date)
    private val image = itemView.findViewById<ImageView>(R.id.item_msg_img)


    fun bindData(message: Message){
        date.text = message.date
        if(message.message == null){
            Picasso.get().load(message.imgUrl).into(image)
            body.visibility = View.GONE
        }else{
            body.text = message.message
            image.visibility = View.GONE
        }
        if(message.photoUrl != null){
            Picasso.get().load(message.photoUrl).into(photo)
        }
    }

}