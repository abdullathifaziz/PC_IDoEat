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
import com.example.capstone_idoeat.databinding.ActivityOnBoarding3Binding
import com.example.capstone_idoeat.helper.UserPreference

class OnBoardingActivity3 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnBoarding3Binding
    private lateinit var btnKembali: Button
    private lateinit var btnLanjut: Button
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_on_boarding3)

        preference = UserPreference(this)

        binding = ActivityOnBoarding3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        btnKembali = findViewById(R.id.btn_onb_3_kembali)
        btnLanjut = findViewById(R.id.btn_onb_3_lanjut)
        btnKembali.setOnClickListener(this)
        btnLanjut.setOnClickListener(this)

        propertyAnimation()

//        supportActionBar?.title = "OnBoardig3"
        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_onb_3_kembali -> {
                val btnKembali = Intent(this@OnBoardingActivity3, OnBoardingActivity2::class.java)
                startActivity(btnKembali)
            }

            R.id.btn_onb_3_lanjut -> {
                val btnLanjut = Intent(this@OnBoardingActivity3, LoginActivity::class.java)
                startActivity(btnLanjut)
            }
        }
    }

    private fun propertyAnimation(){
        ObjectAnimator.ofFloat(binding.ivOnb3, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val imageView = ObjectAnimator.ofFloat(binding.ivOnb3, View.ALPHA, 1f).setDuration(500)
        val textTitle = ObjectAnimator.ofFloat(binding.tvTitleOnb3, View.ALPHA, 1f).setDuration(500)
        val textdesc = ObjectAnimator.ofFloat(binding.tvDesOnb3, View.ALPHA, 1f).setDuration(500)
        val btnKembali = ObjectAnimator.ofFloat(binding.btnOnb3Kembali, View.ALPHA, 1f).setDuration(500)
        val btnLanjut = ObjectAnimator.ofFloat(binding.btnOnb3Lanjut, View.ALPHA, 1f).setDuration(500)
        val slidenav = ObjectAnimator.ofFloat(binding.ivSlideNav3, View.ALPHA, 1f).setDuration(500)

//        val btn = AnimatorSet().apply {
//            playTogether(btnKembali, slidenav, btnLanjut)
//        }

        AnimatorSet().apply {
            playSequentially(imageView, textTitle, textdesc)
            start()
        }
    }
}