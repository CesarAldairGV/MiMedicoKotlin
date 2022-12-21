package com.example.mimedicokotlin.ui.proposal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.ProposalService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProposalViewModel : ViewModel(){

    private val _proposalInfo: MutableLiveData<ProposalInfo> = MutableLiveData();
    val proposalInfo: LiveData<ProposalInfo> get() = _proposalInfo

    private val _refuseState: MutableLiveData<Boolean> = MutableLiveData();
    val refuseState: LiveData<Boolean> get() = _refuseState

    private val _acceptState: MutableLiveData<Boolean> = MutableLiveData();
    val acceptState: LiveData<Boolean> get() = _acceptState

    private lateinit var firebaseFirestore: FirebaseFirestore
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private val proposalService = ProposalService()

    fun getProposalInfo(proposalId: String){
        firebaseFirestore = FirebaseFirestore.getInstance()

        viewModelScope.launch {
            val doc = firebaseFirestore.collection("proposals")
                .document(proposalId)
                .get()
                .await()

            val proposal = ProposalInfo(
                doc["medicName",String::class.java]!!,
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

    fun refuse(proposalId: String){
        firebaseFirestore = FirebaseFirestore.getInstance()
        viewModelScope.launch {
            firebaseFirestore.collection("proposals")
                .document(proposalId)
                .delete()
                .await()
            _refuseState.value = true
        }
    }

    fun accept(petitionId: String, proposalId: String){
        viewModelScope.launch {
            proposalService.acceptProposal(proposalId)
            _acceptState.value = true
        }
    }
}