package com.example.capstone_idoeat.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    val rvListNotifications = MutableLiveData<NotificationsListAdapter>().apply {
        value = NotificationsListAdapter()
    }
}