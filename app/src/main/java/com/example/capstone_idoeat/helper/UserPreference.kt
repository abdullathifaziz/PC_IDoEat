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

//    companion object{
//        const val PREFS_NAME = "login_pref"
//        const val USERNAME= "username"
//        const val EMAIL= "email"
//        const val TOKEN = "token"
//        const val IS_LOGIN= "login"
//    }

//    companion object {
//        const val SETTINGS = "Settings"
//    }

//    private val preferences = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
//    val editor = preferences.edit()

//    fun put(Lang: String){
//        editor.putString("My_Lang", Lang)
//        editor.apply()
//    }

//    fun getString() {
//        return preferences.getString(key, null)
//        val language = preferences.getString("My_Lang", "")
//        setLocate(language!!)
//    }


//    fun put(key: String, value: String){
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getString(key: String): String? {
//        return preferences.getString(key, null)
//    }
//
//    fun put(key: String, value: Boolean){
//        editor.putBoolean(key, value)
//        editor.apply()
//    }
//
//    fun getBoolean(key: String): Boolean? {
//        return preferences.getBoolean(key, false)
//    }
//
//    fun logOut(){
//        editor.clear()
//        editor.apply()
//    }


//    private fun setLocate(Lang: String) {
//        val locale = Locale(Lang)
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//
//        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        editor.putString("My_Lang", Lang)
//        editor.apply()
//    }
//
//    private fun loadLocate() {
//        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
//        val language = sharedPreferences.getString("My_Lang", "")
//        if (language != null) {
//            setLocate(language)
//        }
//
//    }


//    companion object {
//        const val SETTINGS = "Settings"
//        const val PREFERENCE_LANGUAGE = "My_Lang"
//    }
//
//    private val preference = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
//
//    fun getLoginCount(): String? {
//        return preference.getString(PREFERENCE_LANGUAGE, "")
//    }
//
//    fun setLoginCount(Language: String) {
//        val editor = preference.edit()
//        editor.putString(PREFERENCE_LANGUAGE, Language)
//        editor.apply()
//    }


//    fun setLocate(lang: String) {
//        val locale = Locale(lang)
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//    }
//
//    fun loadLocate() {
//        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
//        val language = sharedPreferences.getString("My_Lang", "")
//        setLocate(language!!)
//    }

}