package com.example.capstone_idoeat.ui.detail.food

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityDetailFoodBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.model.Rekomendasi
import com.example.capstone_idoeat.ui.data.FoodItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DetailFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFoodBinding
//    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var preference: UserPreference
//    private lateinit var rekomendasiList: ArrayList<Rekomendasi>
//    private lateinit var dbRef: DatabaseReference


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

        if(intent.getStringExtra("FoodItem") != null){
            setValuesToViews()
        } else {
            setViewById(intent.getStringExtra("productId").toString())
        }




        //        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_bantuan)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Detail Food"
        supportActionBar?.hide()

    }

//    private fun getRekomendasiData(){
//        dbRef = FirebaseDatabase.getInstance().getReference("dataKalori")
//    }

    private fun setValuesToViews(){
        val ivImageFood: ImageView = binding.ivImageFood
        val tvName: TextView = binding.tvName
        val tvCalori: TextView = binding.tvCalories
        val tvPrice: TextView = binding.tvPrice
        val tvResto: TextView = binding.tvRestaurant
        val btnOrder: Button = binding.btnOrder

        Picasso.get().load(intent.getStringExtra("Image")).into(ivImageFood)
        tvName.text = intent.getStringExtra("FoodItem")
        tvCalori.text = intent.getStringExtra("Cals_per100grams")
        tvPrice.text = intent.getStringExtra("Price")
        tvResto.text = intent.getStringExtra("FoodCategory")
        // iki rung reti carane ben linke melu recyclerview, makane tak kei link banana wae
        btnOrder.setOnClickListener {
            val tokopedia = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tokopedia.com/search?navsource=&sc=2702&srp_component_id=04.06.00.00&srp_page_id=&srp_page_title=&st=product&q=Banana"))
            startActivity(tokopedia)
        }
    }

    private fun setViewById(productId: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/")
        val reference: DatabaseReference = database.reference.child("datakalori")
        Log.d("+++++++++ProductId", productId)

        val query: Query = reference.orderByChild("ProductID").equalTo(productId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val foodItem: FoodItem? = snapshot.getValue(FoodItem::class.java)
                    if (foodItem != null) {
                        binding.tvName.text = foodItem.FoodItem
                        binding.tvCalories.text = foodItem.Cals_per100grams
                        binding.tvPrice.text = foodItem.Price
                        binding.tvRestaurant.text = foodItem.FoodCategory
                        Glide.with(this@DetailFoodActivity)
                            .load(foodItem.Image)
                            .into(binding.ivImageFood)
                        binding.btnOrder.setOnClickListener {
                            val tokopedia = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tokopedia.com/search?navsource=&sc=2702&srp_component_id=04.06.00.00&srp_page_id=&srp_page_title=&st=product&q=Banana"))
                            startActivity(tokopedia)
                        }
                        Log.d("FirebaseData", foodItem.toString())
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Error handler jika terjadi kesalahan saat mengambil data
                Log.e("FirebaseError", databaseError.message)
            }
        })
    }
}