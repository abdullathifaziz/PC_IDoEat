package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityRiwayatKaloriBinding

class RiwayatKaloriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatKaloriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_kalori)

        binding = ActivityRiwayatKaloriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivKembali: ImageView = binding.ivBack

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_riwayat)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Riwayat Harian"
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}