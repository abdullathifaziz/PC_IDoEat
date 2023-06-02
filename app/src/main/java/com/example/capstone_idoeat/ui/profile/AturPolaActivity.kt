package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityAturPolaBinding

class AturPolaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAturPolaBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_atur_pola)

        binding = ActivityAturPolaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivKembali: ImageView = binding.ivBack

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_atur_kalori)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Atur Kalori"
        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}