package com.example.mimedicokotlin.ui.proposals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ProposalsViewModel : ViewModel() {

    private val _proposals: MutableLiveData<ArrayList<ProposalItem>> = MutableLiveData()
    val proposals : LiveData<ArrayList<ProposalItem>> get() = _proposals

    private lateinit var firebaseFirestore: FirebaseFirestore

    private val list : ArrayList<ProposalItem> = ArrayList()

    fun getAllItems(petitionId: String){
        firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("proposals")
            .whereEqualTo("petitionId",petitionId)
            .addSnapshotListener { value, _ ->
                val docs = value!!.documents.iterator()
                val iterator = docs.iterator()

                while(iterator.hasNext()){
                    val obj = iterator.next()
                    val proposalItem = ProposalItem(
                        obj.id,
                        obj["petitionId",String::class.java]!!,
                        obj["name",String::class.java]!!,
                        obj["date",String::class.java]!!,
                        obj["photoUrl",String::class.java]!!,
                        )
                    list.add(proposalItem)
                }

                _proposals.value = list
            }
    }
}