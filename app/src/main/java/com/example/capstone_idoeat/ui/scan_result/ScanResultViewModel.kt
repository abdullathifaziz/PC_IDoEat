package com.example.capstone_idoeat.ui.scan_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.ui.data.FoodItem
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ScanResultViewModel : ViewModel() {
        private val filteredFoodList: MutableLiveData<List<FoodItem>> = MutableLiveData()

        fun getFilterdFoodList(query: String): MutableLiveData<List<FoodItem>> {
            searchFood(query)
            return filteredFoodList
        }

        private fun searchFood(query: String) {
            val ref = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/datakalori").reference
            ref.orderByChild("name")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items: MutableList<FoodItem> = mutableListOf()
                        for (dataSnapshot in snapshot.children) {
                            val foodItem = dataSnapshot.getValue(FoodItem::class.java)
                            foodItem?.let {
                                items.add(it)
                            }
                        }
                        filteredFoodList.value = items
                        Log.d("SearchFoodViewModel", "loadFoodList: $items")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error here
                    }
                })
        }
    }
