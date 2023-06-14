package com.example.capstone_idoeat.ui.profile.riwayat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_idoeat.ui.notifications.NotificationsListAdapter


class RiwayatViewModel : ViewModel() {

    val rvListRiwayat = MutableLiveData<RiwayatListAdapter>().apply {
        value = RiwayatListAdapter()
    }
}