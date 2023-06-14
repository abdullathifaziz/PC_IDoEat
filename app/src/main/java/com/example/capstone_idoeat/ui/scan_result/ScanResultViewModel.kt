package com.example.capstone_idoeat.ui.scan_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.ui.data.FoodScanCard
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ScanResultViewModel : ViewModel() {
    private val _listFoodResults = MutableLiveData<MutableList<FoodScanCard>>()
    val listFoodResults: LiveData<MutableList<FoodScanCard>> get() = _listFoodResults

    private val foodScanRef: DatabaseReference = Firebase.database.reference

    fun searchFoodScan(query: String) {
        val searchQuery = foodScanRef.startAt(query).endAt(query + "\uf8ff")

        searchQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val results = mutableListOf<FoodScanCard>()
                for (dataSnapshot in snapshot.children) {
                    val foodScanCard = dataSnapshot.getValue(FoodScanCard::class.java)
                    foodScanCard?.let { results.add(it) }
                }
                _listFoodResults.value = results
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during the search
            }
        })
    }
}