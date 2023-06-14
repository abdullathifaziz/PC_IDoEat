package com.example.capstone_idoeat.ui.profile.bantuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.capstone_idoeat.databinding.ActivityDetailBantuan3Binding
import com.example.capstone_idoeat.databinding.ActivityDetailBantuanBinding

class DetailBantuanActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBantuan3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_bantuan3)

        binding = ActivityDetailBantuan3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivKembali: ImageView = binding.ivBack

        ivKembali.setOnClickListener {
            startActivity(Intent(this, BantuanActivity::class.java))
            finish()
        }

//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_bantuan)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Artikel"
        supportActionBar?.hide()
    }
}