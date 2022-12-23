package com.example.mimedicokotlin.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentChatBinding
import com.example.mimedicokotlin.services.ConsultService
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.MetadataChanges


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val consultService = ConsultService()
    private lateinit var consultId: String

    private lateinit var adapter: ChatAdapter

    private val viewModel = ChatViewModel(consultService)

    private var getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it == null) return@registerForActivityResult
        val fragmentManager = this@ChatFragment.parentFragmentManager
        val newFragment = SendImageDialogFragment(consultId,it!!)
        newFragment.show(fragmentManager, "dialog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        consultId = arguments?.getString("consultId")!!
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.chatMsgList.layoutManager = linearLayoutManager


        val options = FirestoreRecyclerOptions.Builder<MessageItem>()
            .setQuery(consultService.getChatByConsultIdAndOrderByTimestampQuery(consultId),
                MetadataChanges.INCLUDE){
                it.toMessageItem()
            }
            .build()

        adapter = ChatAdapter(options)

        binding.chatMsgSend.setOnClickListener {
            viewModel.sendMessage(consultId, binding.chatMsgField.text.toString())
        }

        binding.chatMsgImg.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.chatMsgList.adapter = adapter
        return binding.root
    }

    fun DocumentSnapshot.toMessageItem(): MessageItem =
        MessageItem(
            message = this["message",String::class.java],
            photoUrl = this["photoUrl",String::class.java],
            imgUrl = this["imgUrl",String::class.java],
            date = this["date",String::class.java]!!
        )
}