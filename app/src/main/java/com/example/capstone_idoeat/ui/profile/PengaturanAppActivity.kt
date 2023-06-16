package com.example.capstone_idoeat.ui.profile

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityPengaturanAppBinding
import com.example.capstone_idoeat.helper.MyContextWrapper
import com.example.capstone_idoeat.helper.UserPreference
import java.util.*


class PengaturanAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaturanAppBinding
    private lateinit var preference: UserPreference
    private lateinit var context: Context

    val languageList = arrayOf("en", "in")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pengaturan_app)

        preference = UserPreference(this)
        context = this

        binding = ActivityPengaturanAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ivKembali: ImageView = binding.ivBack
        val spSpinner: Spinner = binding.spinner
        val btnSimpan: Button = binding.btnSimpanApp

        spSpinner.adapter = ArrayAdapter(this, R.layout.simple_list_item_1,languageList)

        val lang = preference.getLoginCount()
        val index = languageList.indexOf(lang)
        if(index >= 0){
            spSpinner.setSelection(index)
        }

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnSimpan.setOnClickListener {
            preference.setLoginCount(languageList[spSpinner.selectedItemPosition])
            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_aplikasi)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Pengaturan Aplikasi"
        supportActionBar?.hide()
    }
}