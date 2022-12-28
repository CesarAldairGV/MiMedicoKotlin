package com.example.mimedicokotlin.ui.proposal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlin.services.ProposalService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProposalViewModel @Inject constructor(
    private val proposalService : ProposalService
): ViewModel(){

    private val _proposalInfo: MutableLiveData<ProposalInfo> = MutableLiveData();
    val proposalInfo: LiveData<ProposalInfo> get() = _proposalInfo

    private val _refuseState: MutableLiveData<Boolean> = MutableLiveData();
    val refuseState: LiveData<Boolean> get() = _refuseState

    private val _acceptState: MutableLiveData<Boolean> = MutableLiveData();
    val acceptState: LiveData<Boolean> get() = _acceptState

    fun getProposalInfo(proposalId: String){
        viewModelScope.launch {
            val doc = proposalService.getProposalById(proposalId)

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

    }

    fun accept(proposalId: String){
        viewModelScope.launch {
            proposalService.acceptProposal(proposalId)
            _acceptState.value = true
        }
    }
}