package com.example.capstone_idoeat.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.ActivityOnBoarding1Binding

class OnBoardingActivity1 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnBoarding1Binding
    private lateinit var btnLewati: Button
    private lateinit var btnLanjut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_on_boarding1)

        binding = ActivityOnBoarding1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLewati = findViewById(R.id.btn_onb_1_lewati)
        btnLanjut = findViewById(R.id.btn_onb_1_lanjut)
        btnLewati.setOnClickListener(this)
        btnLanjut.setOnClickListener(this)

        propertyAnimation()

//        supportActionBar?.title = "OnBoardig1"
        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_onb_1_lewati -> {
                val btnLewati = Intent(this@OnBoardingActivity1, LoginActivity::class.java)
                startActivity(btnLewati)
            }

            R.id.btn_onb_1_lanjut -> {
                val btnLanjut = Intent(this@OnBoardingActivity1, OnBoardingActivity2::class.java)
                startActivity(btnLanjut)
            }
        }
    }

    private fun propertyAnimation(){
        ObjectAnimator.ofFloat(binding.ivOnb1, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val imageView = ObjectAnimator.ofFloat(binding.ivOnb1, View.ALPHA, 1f).setDuration(500)
        val textTitle = ObjectAnimator.ofFloat(binding.tvTitleOnb1, View.ALPHA, 1f).setDuration(500)
        val textdesc = ObjectAnimator.ofFloat(binding.tvDesOnb1, View.ALPHA, 1f).setDuration(500)
        val btnLewati = ObjectAnimator.ofFloat(binding.btnOnb1Lewati, View.ALPHA, 1f).setDuration(500)
        val btnLanjut = ObjectAnimator.ofFloat(binding.btnOnb1Lanjut, View.ALPHA, 1f).setDuration(500)
        val slidenav = ObjectAnimator.ofFloat(binding.ivSlideNav, View.ALPHA, 1f).setDuration(500)

//        val btn = AnimatorSet().apply {
//            playTogether(btnLewati, slidenav, btnLanjut)
//        }

        AnimatorSet().apply {
            playSequentially(imageView, textTitle, textdesc)
            start()
        }
    }
}