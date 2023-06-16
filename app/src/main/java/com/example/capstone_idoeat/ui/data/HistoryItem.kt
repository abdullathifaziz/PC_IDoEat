package com.example.capstone_idoeat.ui.data

data class HistoryItem(
    val date: String,
    val productId: String,
    val totalCalories: String,
    val product: FoodItem? // Tambahkan properti product yang berisi FoodItem
) {
    constructor() : this("", "" , "", null)
}
