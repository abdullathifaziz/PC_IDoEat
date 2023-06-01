package com.example.capstone_idoeat.object_detection

import android.graphics.Bitmap
import java.util.*


interface Classifier {
    class Recognition(val id: String?, val title: String?, val confidence: Float?) {
        override fun toString(): String {
            var resultString = ""
            if (id != null) {
                resultString += "[$id] "
            }
            if (title != null) {
                resultString += "$title "
            }
            if (confidence != null) {
                resultString += java.lang.String.format(
                    Locale.getDefault(), "(%.1f%%) ", confidence * 100.0f
                )
            }
            return resultString.trim { it <= ' ' }
        }
    }

    fun recognizeImage(bitmap: Bitmap?): List<Recognition?>?
    fun close()
}