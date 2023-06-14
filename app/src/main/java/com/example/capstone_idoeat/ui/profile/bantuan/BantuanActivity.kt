package com.example.capstone_idoeat.ui.profile.bantuan

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityBantuanBinding
import com.example.capstone_idoeat.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class BantuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBantuanBinding
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bantuan)

        binding = ActivityBantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivKembali: ImageView = binding.ivBack
        val tvNama: TextView = binding.tvNama
        val tvBantuan1: TextView = binding.tvBantuan1
        val tvBantuan2: TextView = binding.tvBantuan2
        val tvBantuan3: TextView = binding.tvBantuan3
        val tvBantuan4: TextView = binding.tvBantuan4
        val tvBantuan5: TextView = binding.tvBantuan5
        val ivBantuan1: ImageView = binding.ivBantuan1
        val ivBantuan2: ImageView = binding.ivBantuan2
        val ivBantuan3: ImageView = binding.ivBantuan3
        val ivBantuan4: ImageView = binding.ivBantuan4
        val ivBantuan5: ImageView = binding.ivBantuan5

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            tvNama.text = firebaseUser.displayName
        }

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tvBantuan1.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity::class.java))
            finish()
        }

        tvBantuan2.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity2::class.java))
            finish()
        }

        tvBantuan3.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity3::class.java))
            finish()
        }

        tvBantuan4.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity4::class.java))
            finish()
        }

        tvBantuan5.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity5::class.java))
            finish()
        }

        ivBantuan1.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity::class.java))
            finish()
        }

        ivBantuan2.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity2::class.java))
            finish()
        }

        ivBantuan3.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity3::class.java))
            finish()
        }

        ivBantuan4.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity4::class.java))
            finish()
        }

        ivBantuan5.setOnClickListener {
            startActivity(Intent(this, DetailBantuanActivity5::class.java))
            finish()
        }

//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">" + getString(R.string.back_bantuan) + "</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Pusat Bantuan"
        supportActionBar?.hide()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}