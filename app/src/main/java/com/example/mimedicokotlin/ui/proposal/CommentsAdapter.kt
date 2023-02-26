package com.example.mimedicokotlin.ui.proposal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.ui.consults.ConsultsAdapter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges

class CommentsAdapter(options: FirestoreRecyclerOptions<CommentItem>) :
    FirestoreRecyclerAdapter<CommentItem, CommentItemViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int, model: CommentItem) {
        holder.bindData(model)
    }

    companion object {
        fun getAdapter(medicId: String): CommentsAdapter {

            val query = FirebaseFirestore.getInstance().collection("medics")
                .document(medicId)
                .collection("comments")

            val options = FirestoreRecyclerOptions.Builder<CommentItem>()
                .setQuery(query,
                    MetadataChanges.EXCLUDE){
                    it.toCommentItem()
                }
                .build()

            return CommentsAdapter(options)
        }

        private fun DocumentSnapshot.toCommentItem(): CommentItem =
            CommentItem(
                username = this["userName", String::class.java]!!,
                comment = this["comment", String::class.java]!!,
                date = this["date", String::class.java]!!
            )
    }
}