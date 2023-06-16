package com.example.capstone_idoeat.ui.profile.riwayat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.ui.data.FoodItem
import com.example.capstone_idoeat.ui.data.HistoryItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RiwayatViewModel : ViewModel() {
    fun getHistoryUserFood(uid: String): LiveData<List<HistoryItem>>? {
        val historyUserFood: MutableLiveData<List<HistoryItem>> = MutableLiveData()
        val listHistory = getHistoryUser(uid)
        listHistory.observeForever { historyItems ->
            val items: MutableList<HistoryItem> = mutableListOf()
            val ref = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/").reference.child("datakalori")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    items.clear() // Clear the list before populating it again
                    for (history in historyItems) {
                        val foodItem = snapshot.children
                            .mapNotNull { it.getValue(FoodItem::class.java) }
                            .find { it.ProductID == history.productId }

                        val historyItemWithProduct = HistoryItem(
                            history.date,
                            history.productId,
                            history.totalCalories,
                            foodItem
                        )
                        items.add(historyItemWithProduct)
                    }
                    historyUserFood.value = items
                    Log.d("________+++MAKANAN", "loadFoodList: $items")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error here
                }
            })
        }
        return historyUserFood
    }
    fun getHistoryUser(uid: String): LiveData<List<HistoryItem>> {
        val historyUser: MutableLiveData<List<HistoryItem>> = MutableLiveData()
        val ref = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/").reference.child("datahistory").child(uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: MutableList<HistoryItem> = mutableListOf()
                for (dataSnapshot in snapshot.children) {
                    val HistoryItem = dataSnapshot.getValue(HistoryItem::class.java)
                    HistoryItem?.let {
                        items.add(it)
                    }
                    historyUser.value = items
                    Log.d("________+++History", "loadFoodList: $items")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error here
            }
        })
        return historyUser
    }

//    fun getHistoryFood(productId: String): LiveData<List<FoodItem>> {
//        val historyFood: MutableLiveData<List<FoodItem>> = MutableLiveData()
//        val ref = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/").reference.child("datafood")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val items: MutableList<FoodItem> = mutableListOf()
//                for (dataSnapshot in snapshot.children) {
//                    val FoodItem = dataSnapshot.getValue(FoodItem::class.java)
//                    FoodItem?.let {
//                        items.add(it)
//                    }
//                    items.forEach { food ->
//                        history.forEach { history ->
//                            if (food.ProductID == history.productId) {
//                                items.add(food)
//                            }
//                        }
//                    }
//                    historyFood.value = items
//                    Log.d("________+++History", "loadFoodList: $items")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle database error here
//            }
//        })
//
//        return historyFood
//    }
}