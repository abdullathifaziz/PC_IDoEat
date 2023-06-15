package com.example.capstone_idoeat.ui.scan_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.object_detection.Classifier
import com.example.capstone_idoeat.ui.data.FoodItem
import com.google.firebase.database.*

class ScanResultViewModel : ViewModel() {
    fun getFilterdFoodList(searchTitleFood: String, results: List<Classifier.Recognition>): LiveData<List<FoodItem>> {
        val filteredFoodList: MutableLiveData<List<FoodItem>> = MutableLiveData()
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
                val filteredItems = items.filter { foodItem ->
                    // Lakukan filter berdasarkan kriteria yang sesuai dengan query
                    foodItem.FoodItem.toLowerCase().contains(searchTitleFood.toLowerCase().trim()) ||
                            foodItem.FoodCategory.toLowerCase().contains(searchTitleFood.toLowerCase().trim())
                    // Anda dapat menambahkan kriteria filter lainnya sesuai dengan kebutuhan
                }
                filteredFoodList.value = filteredItems
                Log.d("SearchFoodViewModel", "loadFoodList: $filteredItems")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error here
            }
        })

        return filteredFoodList
    }
}
