package com.example.capstone_idoeat.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.onboarding.OnBoardingActivity1
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 2000
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            setContentView(R.layout.activity_splash)
            Handler().postDelayed({
                startActivity(Intent(this, OnBoardingActivity1::class.java))
                finish()
            }, SPLASH_TIME_OUT)
        }
    }
}