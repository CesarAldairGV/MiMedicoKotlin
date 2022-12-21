package com.example.mimedicokotlin.ui.consults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentConsultsBinding
import com.example.mimedicokotlin.services.AuthService
import com.example.mimedicokotlin.services.ConsultService
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges

class ConsultsFragment : Fragment() {

    private var _binding: FragmentConsultsBinding? = null
    private val binding get() = _binding!!

    private val authService = AuthService()
    private val consultService = ConsultService()

    private lateinit var adapter : ConsultsAdapter

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultsBinding.inflate(inflater, container, false)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        binding.consultsList.layoutManager = linearLayoutManager

        val query = consultService.getConsultByUserIdQuery(authService.getCurrentUser()?.uid!!)

        val options = FirestoreRecyclerOptions.Builder<ConsultItem>()
            .setQuery(query,MetadataChanges.INCLUDE){
                it.toConsultItem()
            }
            .build()

        adapter = ConsultsAdapter(options)

        binding.consultsList.adapter = adapter

        return binding.root
    }

    fun DocumentSnapshot.toConsultItem(): ConsultItem = ConsultItem(
        consultId = this.id,
        medicName = this["medicName",String::class.java]!!,
        subject = this["subject",String::class.java]!!,
        date = this["date",String::class.java]!!
    )

}