package com.example.mimedicokotlin.ui.consults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.R
import com.example.mimedicokotlin.databinding.FragmentConsultsBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges

class ConsultsFragment : Fragment() {

    private var _binding: FragmentConsultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultsBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val query = firestore.collection("consults")
            .whereEqualTo("userId", auth.currentUser?.uid )

        val options = FirestoreRecyclerOptions.Builder<Consult>()
            .setQuery(query,MetadataChanges.INCLUDE, SnapshotParser {
                val consult = Consult()
                consult.consultId = it.id
                consult.date = it["date",String::class.java]
                consult.subject = it["subject",String::class.java]
                consult.medicName = it["medicName",String::class.java]
                consult
            })
            .build()

        val adapter = object: FirestoreRecyclerAdapter<Consult, ConsultViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_consult, parent, false)
                return ConsultViewHolder(view)
            }
            override fun onBindViewHolder(holder: ConsultViewHolder, position: Int, model: Consult) {
                holder.bindData(model)
            }
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        binding.consultsList.layoutManager = linearLayoutManager
        binding.consultsList.adapter = adapter

        adapter.startListening()
        return binding.root
    }

}