package com.example.capstone_idoeat.object_detection

data class DetectionResult(
    val score: Float,
    val classIndex: Int,
    val location: FloatArray
)