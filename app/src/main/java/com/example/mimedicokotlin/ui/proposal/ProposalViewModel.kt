package com.example.mimedicokotlin.ui.proposal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProposalViewModel : ViewModel(){

    private val _proposalInfo: MutableLiveData<ProposalInfo> = MutableLiveData();
    val proposalInfo: LiveData<ProposalInfo> get() = _proposalInfo

    private lateinit var firebaseFirestore: FirebaseFirestore

    fun getProposalInfo(proposalId: String){
        firebaseFirestore = FirebaseFirestore.getInstance()

        viewModelScope.launch {
            val doc = firebaseFirestore.collection("proposals")
                .document(proposalId)
                .get()
                .await()

            val proposal = ProposalInfo(
                doc["name",String::class.java]!!,
                doc["yearsExp",String::class.java]!!,
                doc["business",String::class.java]!!,
                doc["school",String::class.java]!!,
                doc["photoUrl",String::class.java]!!,
                doc["likes",String::class.java]!!,
                doc["body",String::class.java]!!
            )

            _proposalInfo.value = proposal
        }
    }
}