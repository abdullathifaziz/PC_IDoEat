package com.example.capstone_idoeat.ui.data

data class FoodItem(

    val Cals_per100grams: String,
    val FoodCategory: String,
    val FoodItem: String,
    val Image: String,
    val KJ_per100grams: String,
    val Link_Toko: String,
    val ProductID: String,
    val UserId: String,
    val per100grams: String,
    val score: Int
){
    constructor() : this("", "", "", "", "", "", "", "", "", 0)
}