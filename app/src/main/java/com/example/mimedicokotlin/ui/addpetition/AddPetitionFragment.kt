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
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentAddPetitionBinding

class AddPetitionFragment : Fragment() {

    private val TAG = "AddPetitionFragment"

    private val viewModel = AddPetitionViewModel()
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

        val subjectWatcher = binding.addpSubject.doAfterTextChanged {
            checkData()
        }

        val bodyWatcher = binding.addpBody.doAfterTextChanged {
            checkData()
        }

        viewModel.formState.observe(viewLifecycleOwner){
            if(it.subjectError == 1){
                binding.addpSubject.error = getString(R.string.addp_subject_err)
            }
            if(it.bodyError == 1){
                binding.addpBody.error = getString(R.string.addp_body_err)
            }
            binding.addpButtonAction.isEnabled = it.isDataValid
        }

        viewModel.resultState.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(activity, getString(R.string.addp_sended), Toast.LENGTH_LONG).show()
                binding.addpSubject.removeTextChangedListener(subjectWatcher)
                binding.addpBody.removeTextChangedListener(bodyWatcher)
                binding.addpSubject.text.clear()
                binding.addpBody.text.clear()
                binding.addpImage.setImageResource(R.drawable.ic_launcher_background)
                binding.addpButtonAction.isEnabled = false
                isImageSelected = false
                binding.addpSubject.addTextChangedListener(subjectWatcher)
                binding.addpBody.addTextChangedListener(bodyWatcher)
            }else{
                Toast.makeText(activity, getString(R.string.addp_err1), Toast.LENGTH_LONG).show()
            }
        }

        binding.addpImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.addpButtonAction.setOnClickListener {
            sendPetition()
        }

        binding.addpButtonAction.isEnabled = false

        return binding.root
    }

    fun checkData(){
        viewModel.checkData(
            binding.addpSubject.text.toString(),
            binding.addpBody.text.toString(),
        )
    }

    fun sendPetition(){
        val subject = binding.addpSubject.text.toString()
        val body = binding.addpBody.text.toString()
        if(isImageSelected) {
            val bitmap = (binding.addpImage.drawable as BitmapDrawable).bitmap
            viewModel.sendPetition(subject, body, bitmap)
        }else{
            viewModel.sendPetition(subject, body)
        }
    }
}