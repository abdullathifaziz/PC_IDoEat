package com.example.capstone_idoeat.ui.detail.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityDetailFoodBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.model.Rekomendasi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFoodBinding
//    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var preference: UserPreference
//    private lateinit var rekomendasiList: ArrayList<Rekomendasi>
//    private lateinit var dbRef: DatabaseReference


    val ivImageFood: ImageView = binding.ivImageFood
    val tvName: TextView = binding.tvName
    val tvCalori: TextView = binding.tvCalories
    val tvPrice: TextView = binding.tvPrice
    val tvResto: TextView = binding.tvRestaurant
    val tvComposition: TextView = binding.tvItemComposition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_food)

        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(this)

        val ivKembali: ImageView = binding.ivBack
//        val ivImageFood: ImageView = binding.ivImageFood
//        val tvName: TextView = binding.tvName
//        val tvCalori: TextView = binding.tvCalories
//        val tvPrice: TextView = binding.tvPrice
//        val tvResto: TextView = binding.tvRestaurant
//        val tvComposition: TextView = binding.tvItemComposition

//        rekomendasiList = arrayListOf<Rekomendasi>()

        ivKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

//        getRekomendasiData()

    }

//    private fun getRekomendasiData(){
//        dbRef = FirebaseDatabase.getInstance().getReference("dataKalori")
//    }

    private fun setValuesToViews(){
        tvName.text = intent.getStringExtra("FoodItem")
        tvCalori.text = intent.getStringExtra("Cals_per100grams")

    }

}