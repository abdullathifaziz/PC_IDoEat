package com.example.capstone_idoeat.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.ui.data.FoodItem
import com.google.firebase.database.*

class SearchFoodViewModel : ViewModel() {
    private val originalFoodList: MutableLiveData<List<FoodItem>> = MutableLiveData()
    private val filteredFoodList: MutableLiveData<List<FoodItem>> = MutableLiveData()

    fun getFoodList(): MutableLiveData<List<FoodItem>> {
        loadFoodList()
        return filteredFoodList
    }

    fun searchFood(query: String) {
        val originalList = originalFoodList.value
        if (originalList != null) {
            val filteredList = originalList.filter { foodItem ->
                foodItem.FoodItem.contains(query, ignoreCase = true) || foodItem.FoodCategory.contains(
                    query,
                    ignoreCase = true
                )
            }
            filteredFoodList.value = filteredList
        }
    }

    private fun loadFoodList() {
        val ref = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/").reference.child("datakalori")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: MutableList<FoodItem> = mutableListOf()
                for (dataSnapshot in snapshot.children) {
                    val foodItem = dataSnapshot.getValue(FoodItem::class.java)
                    foodItem?.let {
                        items.add(it)
                    }
                }
                originalFoodList.value = items
                filteredFoodList.value = items
                Log.d("SearchFoodViewModel", "loadFoodList: $items")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error here
            }
        })
    }
}