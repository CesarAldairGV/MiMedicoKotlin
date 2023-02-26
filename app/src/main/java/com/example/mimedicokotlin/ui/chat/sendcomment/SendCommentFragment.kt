package com.example.mimedicokotlin.ui.chat.sendcomment

import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentSendCommentBinding
import com.example.mimedicokotlin.databinding.FragmentSendImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendCommentFragment(
    private val consultId: String,
    private val medicId: String) : DialogFragment() {

    private val viewModel: SendCommentViewModel by viewModels()

    private var _binding: FragmentSendCommentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendCommentBinding.inflate(inflater, container, false)

        viewModel.result.observe(viewLifecycleOwner){
            if(it) dismiss()
        }

        binding.chatSendComCancel.setOnClickListener{
            dismiss()
        }

        binding.chatSendComSend.setOnClickListener{
            viewModel.sendComment(consultId, medicId, binding.chatSendComCom.text.toString())
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}