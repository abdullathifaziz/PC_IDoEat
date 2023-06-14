package com.example.capstone_idoeat.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.model.Rekomendasi

class HomeViewModel : ViewModel() {
    val rvAdapterList = MutableLiveData<List<RecyclerView.Adapter<*>>>().apply {
        val recommendationList = arrayListOf<Rekomendasi>()
        value = listOf(HomeHistoryListAdapter(), HomeRecommendationListAdapter(recommendationList))
//        value = listOf(HomeHistoryListAdapter())
    }
}