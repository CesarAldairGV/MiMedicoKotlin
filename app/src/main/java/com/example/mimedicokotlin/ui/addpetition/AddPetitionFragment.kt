package com.example.mimedicokotlin.ui.addpetition

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentAddPetitionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPetitionFragment : Fragment() {

    private val TAG = "AddPetitionFragment"

    private val viewModel : AddPetitionViewModel by viewModels()
    private var _binding: FragmentAddPetitionBinding? = null
    private val binding get() = _binding!!

    private var isImageSelected = false
    private var getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.addpImage.setImageURI(it)
        isImageSelected = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPetitionBinding.inflate(inflater, container, false)

        val subjectWatcher = binding.addpTitle.doAfterTextChanged {
            checkData()
        }

        val bodyWatcher = binding.addpBody.doAfterTextChanged {
            checkData()
        }

        viewModel.formState.observe(viewLifecycleOwner){
            if(it.subjectError == 1){
                binding.addpTitle.error = getString(R.string.addp_title_err)
            }
            if(it.bodyError == 1){
                binding.addpBody.error = getString(R.string.addp_body_err)
            }
            binding.addpButtonAction.isEnabled = it.isDataValid
        }

        viewModel.resultState.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(activity, getString(R.string.addp_sended), Toast.LENGTH_LONG).show()
                binding.addpTitle.removeTextChangedListener(subjectWatcher)
                binding.addpBody.removeTextChangedListener(bodyWatcher)
                binding.addpTitle.text.clear()
                binding.addpBody.text.clear()
                binding.addpImage.setImageResource(android.R.color.transparent)
                binding.addpButtonAction.isEnabled = false
                isImageSelected = false
                binding.addpTitle.addTextChangedListener(subjectWatcher)
                binding.addpBody.addTextChangedListener(bodyWatcher)
                binding.addpProgressBar.visibility = View.GONE
            }else{
                Toast.makeText(activity, getString(R.string.addp_err1), Toast.LENGTH_LONG).show()
                binding.addpProgressBar.visibility = View.GONE
            }
        }

        binding.addpImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.addpButtonAction.setOnClickListener {
            sendPetition()
            binding.addpProgressBar.visibility = View.VISIBLE
        }

        binding.addpButtonAction.isEnabled = false

        return binding.root
    }

    private fun checkData(){
        viewModel.checkData(
            binding.addpTitle.text.toString(),
            binding.addpBody.text.toString(),
        )
    }

    private fun sendPetition(){
        val subject = binding.addpTitle.text.toString()
        val body = binding.addpBody.text.toString()
        if(isImageSelected) {
            val bitmap = (binding.addpImage.drawable as BitmapDrawable).bitmap
            viewModel.sendPetition(subject, body, bitmap)
        }else{
            viewModel.sendPetition(subject, body)
        }
    }
}