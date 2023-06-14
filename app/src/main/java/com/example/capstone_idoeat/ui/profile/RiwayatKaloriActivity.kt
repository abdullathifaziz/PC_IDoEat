package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityRiwayatKaloriBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.ui.profile.riwayat.RiwayatListAdapter
import com.example.capstone_idoeat.ui.profile.riwayat.RiwayatViewModel

class RiwayatKaloriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatKaloriBinding
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_kalori)

        binding = ActivityRiwayatKaloriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(this)

        val recyclerViewRiwayat = binding.rvListRiwayat
        val ivKembali: ImageView = binding.ivBack

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val riwayatViewModel =
            ViewModelProvider(this).get(RiwayatViewModel::class.java)
        binding.rvListRiwayat.layoutManager = LinearLayoutManager(this)
//        riwayatViewModel.rvListRiwayat.observe(viewLifecycleOwner) {
//            binding.rvListRiwayat.adapter = it
//        }
//            binding.rvListRiwayat.adapter = it

        val mRiwayatAdapter = RiwayatListAdapter()
        recyclerViewRiwayat.adapter = mRiwayatAdapter


//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_riwayat)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Riwayat Harian"
        supportActionBar?.hide()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}