package com.example.capstone_idoeat.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityOnBoarding2Binding

class OnBoardingActivity2 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnBoarding2Binding
    private lateinit var btnKembali: Button
    private lateinit var btnLanjut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_on_boarding2)

        binding = ActivityOnBoarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        btnKembali = findViewById(R.id.btn_onb_2_kembali)
        btnLanjut = findViewById(R.id.btn_onb_2_lanjut)
        btnKembali.setOnClickListener(this)
        btnLanjut.setOnClickListener(this)

        propertyAnimation()

//        supportActionBar?.title = "OnBoardig2"
        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_onb_2_kembali -> {
                val btnKembali = Intent(this@OnBoardingActivity2, OnBoardingActivity1::class.java)
                startActivity(btnKembali)
            }

            R.id.btn_onb_2_lanjut -> {
                val btnLanjut = Intent(this@OnBoardingActivity2, OnBoardingActivity3::class.java)
                startActivity(btnLanjut)
            }
        }
    }

    private fun propertyAnimation(){
        ObjectAnimator.ofFloat(binding.ivOnb2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val imageView = ObjectAnimator.ofFloat(binding.ivOnb2, View.ALPHA, 1f).setDuration(500)
        val textTitle = ObjectAnimator.ofFloat(binding.tvTitleOnb2, View.ALPHA, 1f).setDuration(500)
        val textdesc = ObjectAnimator.ofFloat(binding.tvDesOnb2, View.ALPHA, 1f).setDuration(500)
        val btnKembali = ObjectAnimator.ofFloat(binding.btnOnb2Kembali, View.ALPHA, 1f).setDuration(500)
        val btnLanjut = ObjectAnimator.ofFloat(binding.btnOnb2Lanjut, View.ALPHA, 1f).setDuration(500)
        val slidenav = ObjectAnimator.ofFloat(binding.ivSlideNav2, View.ALPHA, 1f).setDuration(500)

//        val btn = AnimatorSet().apply {
//            playTogether(btnKembali, slidenav, btnLanjut)
//        }

        AnimatorSet().apply {
            playSequentially(imageView, textTitle, textdesc)
            start()
        }
    }

}