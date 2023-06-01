package com.example.capstone_idoeat.ui.scan

interface ObjectDetectorListener {
    fun onInitialized()
    fun onError(errorMessage: String)
}