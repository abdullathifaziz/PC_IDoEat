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
import com.example.capstone_idoeat.ui.detail.food.DetailFoodActivity
import com.example.capstone_idoeat.ui.profile.riwayat.RiwayatListAdapter
import com.example.capstone_idoeat.ui.profile.riwayat.RiwayatViewModel
import com.google.firebase.auth.FirebaseAuth

class RiwayatKaloriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatKaloriBinding
    private lateinit var preference: UserPreference
    private lateinit var viewModel : RiwayatViewModel
    private lateinit var adapter: RiwayatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_kalori)
        binding = ActivityRiwayatKaloriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference(this)

        val recyclerViewRiwayat = binding.rvListRiwayat
        val ivKembali: ImageView = binding.ivBack

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        adapter = RiwayatListAdapter(emptyList()) { productId ->
            val intent = Intent(this, DetailFoodActivity::class.java)
            intent.putExtra("productId", productId)
            startActivity(intent)
        }

        binding.rvListRiwayat.layoutManager = LinearLayoutManager(this)
        binding.rvListRiwayat.adapter = adapter

        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModel = ViewModelProvider(this).get(RiwayatViewModel::class.java)
        viewModel.getHistoryUserFood(uid)?.observe(this, {
            adapter = RiwayatListAdapter(it) { productId ->
                val intent = Intent(this, DetailFoodActivity::class.java)
                intent.putExtra("productId", productId)
                startActivity(intent)
            }
            recyclerViewRiwayat.adapter = adapter
        })
        binding.rvListRiwayat.layoutManager = LinearLayoutManager(this)

    }
}