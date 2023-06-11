package com.example.capstone_idoeat.object_detection

import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log
import java.util.*


interface Classifier {
    class Recognition(val id: String?, val title: String?, val confidence: Float?, val location: RectF) {
        override fun toString(): String {
            var resultString = ""
            if (id != null) {
                resultString += "[$id] "
            }
            if (title != null) {
                resultString += "$title "
            }
            if (confidence != null) {
                resultString += String.format(
                    Locale.getDefault(), "(%.1f%%) ", confidence * 100.0f
                )
            }
            resultString += "Location: $location"
            return resultString.trim()
        }
    }

    class ImageResults(val bitmap: Bitmap?){
        override fun toString(): String {
            var resultString = ""
            if (bitmap != null) {
                resultString += "Bitmap: $bitmap "
            }
            return resultString.trim()
        }
    }

    fun recognizeImage(bitmap: Bitmap?): List<Recognition?>?

    fun close()
}