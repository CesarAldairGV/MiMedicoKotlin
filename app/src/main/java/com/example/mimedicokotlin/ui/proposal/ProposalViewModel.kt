package com.example.mimedicokotlin.ui.proposal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimedicokotlinfirebase.dto.Proposal
import com.example.mimedicokotlinfirebase.services.ProposalService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
            val proposal = proposalService.getProposalById(proposalId)!!
            val proposalInfo = proposal.toProposalInfo()
            _proposalInfo.value = proposalInfo
        }
    }

    fun refuse(proposalId: String){

    }

    fun accept(proposalId: String){
        viewModelScope.launch {
            _acceptState.value = proposalService.acceptProposal(proposalId)
        }
    }

    private fun Proposal.toProposalInfo() = ProposalInfo(
        name = this.medicName,
        yearsExp = this.yearsExp,
        business = this.business,
        school = this.school,
        photoUrl = this.photoUrl,
        likes = 0,
        body = this.body
    )
}