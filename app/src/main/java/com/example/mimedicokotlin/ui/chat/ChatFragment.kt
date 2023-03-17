package com.example.mimedicokotlin.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentChatBinding
import com.example.mimedicokotlin.ui.chat.sendcomment.SendCommentFragment
import com.example.mimedicokotlin.ui.chat.sendimage.SendImageDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ChatViewModel by viewModels()

    private lateinit var adapter: ChatAdapter

    private lateinit var consultId: String
    private lateinit var medicId: String

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
        adapter = ChatAdapter.getAdapter(consultId, binding.chatMsgList)
        binding.chatMsgList.layoutManager = linearLayoutManager
        binding.chatMsgList.adapter = adapter

        viewModel.consultData.observe(viewLifecycleOwner){
            binding.chatSubj.text = it.subject
            binding.chatBody.text = it.body
            binding.chatMedic.text = it.medicName
            medicId = it.medicId

            if(it.imgUrl != null){
                binding.chatImg.visibility = View.VISIBLE
                Picasso.get().load(it.imgUrl).into(binding.chatImg)
                binding.chatImg.visibility = View.VISIBLE
            }

            if(it.isFinished){
                binding.chatMsgImg.isEnabled = false
                binding.chatMsgField.isEnabled = false
                binding.chatMsgSend.isEnabled = false
                binding.chatCardMsj.visibility = View.GONE
                if(!it.hasComment){
                    binding.chatComment.visibility = View.VISIBLE
                }
            }
        }

        viewModel.messageState.observe(viewLifecycleOwner){
            binding.chatMsgSend.isEnabled = it
        }

        binding.chatMsgSend.setOnClickListener {
            viewModel.sendMessage(consultId, binding.chatMsgField.text.toString())
            binding.chatMsgField.text.clear()
        }

        binding.chatMsgImg.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.chatMsgField.addTextChangedListener {
            viewModel.checkMessage(it.toString())
        }

        binding.chatComment.setOnClickListener{
            val fragmentManager = this@ChatFragment.parentFragmentManager
            val newFragment = SendCommentFragment(this@ChatFragment.consultId,medicId)
            newFragment.show(fragmentManager, "dialog")
        }

        binding.chatMsgSend.isEnabled = false
        viewModel.getConsultData(consultId)
        return binding.root
    }
}