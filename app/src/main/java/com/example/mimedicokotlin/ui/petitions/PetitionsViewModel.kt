package com.example.mimedicokotlin.ui.petitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PetitionsViewModel {

    private val _petitions: MutableLiveData<ArrayList<Petition>> = MutableLiveData()
    val petitions: LiveData<ArrayList<Petition>> get() = _petitions

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    private val list: ArrayList<Petition>  = ArrayList()

    fun getAllItems(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        val userId = firebaseAuth.currentUser!!.uid

        firebaseFirestore.collection("petitions")
            .whereEqualTo("userId",userId)
            .addSnapshotListener { value, _ ->
                val docs = value!!.documents.iterator()
                while(docs.hasNext()){
                    val obj = docs.next()
                    val petition = Petition(obj["subject",String::class.java]!!,
                        obj["date",String::class.java]!!,
                        obj["body",String::class.java]!!,
                        obj["urlPhoto",String::class.java])
                    list.add(petition)
                }
                _petitions.value = list
            }
    }
}