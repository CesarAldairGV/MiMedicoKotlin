package com.example.mimedicokotlin.ui.petitions.add

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

        viewModel.formState.observe(viewLifecycleOwner){
            if(it.subjectError == 1){
                binding.addpSubject.error = "Debe contener entre 5 y 30 caracteres"
            }
            if(it.bodyError == 1){
                binding.addpBody.error = "Debe contener entre 30 y 280 caracteres"
            }
            binding.addpButtonAction.isEnabled = it.isDataValid
        }

        viewModel.resultState.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(activity, "Peticion enviada correctamente", Toast.LENGTH_LONG).show()
                binding.addpSubject.text.clear()
                binding.addpBody.text.clear()
                binding.addpImage.setImageResource(R.drawable.ic_launcher_background)
                binding.addpButtonAction.isEnabled = false
                isImageSelected = false
            }else{
                Toast.makeText(activity, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()
            }
        }

        binding.addpSubject.doAfterTextChanged {
            checkData()
        }

        binding.addpBody.doAfterTextChanged {
            checkData()
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