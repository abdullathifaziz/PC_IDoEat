package com.example.capstone_idoeat.ui.history

class UserHistory {
    var productId: String? = null
    var date: String? = null
    var totalCalories: String? = null

    constructor() {
        // Diperlukan konstruktor kosong untuk Firebase Realtime Database.
    }

    constructor(productId: String?, date: String?, totalCalories: String?) {
        this.productId = productId
        this.date = date
        this.totalCalories = totalCalories
    }
}