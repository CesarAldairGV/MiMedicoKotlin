package com.example.mimedicokotlin.ui.chat

import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mimedicokotlin.databinding.FragmentSendImageBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SendImageDialogFragment(private val consultId: String, private val image: Uri) : DialogFragment() {

    private val viewModel : SendImageViewModel by viewModels()

    private var _binding: FragmentSendImageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSendImageBinding.inflate(inflater, container, false)
        binding.chatSendImgImg.setImageURI(image)

        viewModel.imageState.observe(viewLifecycleOwner){
            if(it) dismiss()
        }

        binding.chatSendImgCancel.setOnClickListener {
            dismiss()
        }

        binding.chatSendImgSend.setOnClickListener {
            viewModel.sendImage(consultId, (binding.chatSendImgImg.drawable as BitmapDrawable).bitmap)
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}