package com.example.mimedicokotlin.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentChatBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val query = firebaseFirestore.collection("consults")
            .document(arguments?.getString("consultId")!!)
            .collection("chat")

        val options = FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        val adapter = object : FirestoreRecyclerAdapter<Message, MessageViewHolder>(options){
            override fun onBindViewHolder(holder: MessageViewHolder, position: Int, message : Message){
                holder.bindData(message)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                val view: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false)
                return MessageViewHolder(view)
            }
        }

        val linearLayoutManager = LinearLayoutManager(requireContext())

        binding.chatMsgList.layoutManager = linearLayoutManager
        binding.chatMsgList.adapter = adapter

        adapter.startListening()

        return binding.root
    }


}