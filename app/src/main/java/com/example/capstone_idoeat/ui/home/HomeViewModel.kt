package com.example.capstone_idoeat.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class HomeViewModel : ViewModel() {
    val rvAdapterList = MutableLiveData<List<RecyclerView.Adapter<*>>>().apply {
        value = listOf(HomeHistoryListAdapter(), HomeRecommendationListAdapter())
    }
}