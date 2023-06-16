package com.example.capstone_idoeat.helper

import android.content.Context
import android.content.res.Configuration
import java.util.*

class UserPreference(context: Context) {

    companion object{
        val PREFERENCE_NAME = "SharedPreferenceExample"
        val PREFERENCE_LANGUAGE = "Language"
    }

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getLoginCount() : String? {
        return preference.getString(PREFERENCE_LANGUAGE,"en")
    }

    fun setLoginCount(Language:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_LANGUAGE,Language)
        editor.apply()
    }

    fun setChosenCalories(calories: String){
        val editor = preference.edit()
        editor.putString("ChosenCalories", calories)
        editor.apply()
    }

    fun getChosenCalories(): String? {
        if(preference.getString("ChosenCalories", null) != null){
            return preference.getString("ChosenCalories", null)
        }
        return null
    }
}