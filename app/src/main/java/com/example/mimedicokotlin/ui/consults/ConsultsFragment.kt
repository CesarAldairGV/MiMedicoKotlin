package com.example.mimedicokotlin.ui.consults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimedicokotlin.databinding.FragmentConsultsBinding
import com.example.mimedicokotlin.hilt.App

class ConsultsFragment : Fragment() {

    private var _binding: FragmentConsultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : ConsultsAdapter
    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as App
    }

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
        adapter = ConsultsAdapter.getAdapter(app.getCurrentUserId()!!)

        binding.consultsList.layoutManager = linearLayoutManager
        binding.consultsList.adapter = adapter

        return binding.root
    }

}