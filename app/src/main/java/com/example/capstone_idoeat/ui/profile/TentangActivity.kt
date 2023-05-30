package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.ActivityOnBoarding2Binding
import com.example.capstone_idoeat.databinding.ActivityTentangBinding
import com.example.capstone_idoeat.onboarding.OnBoardingActivity1
import com.example.capstone_idoeat.onboarding.OnBoardingActivity3

class TentangActivity : AppCompatActivity(), View.OnClickListener {

//    private lateinit var binding: ActivityTentangBinding
//    private lateinit var kembali: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)

//        binding = ActivityTentangBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        kembali = findViewById(R.id.kembali)
//        kembali.setOnClickListener(this)

        val kembali: TextView = findViewById(R.id.kembali)
        kembali.setOnClickListener(this)

        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.kembali -> {
                val Kembali = Intent(this, MainActivity::class.java)
                startActivity(Kembali)
            }
        }
    }



}