package com.example.capstone_idoeat.ui.detail.food

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityDetailFoodBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.ui.data.FoodItem
import com.example.capstone_idoeat.ui.history.UserHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.util.*

class DetailFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFoodBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
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

        binding.btnEat.setOnClickListener {
            val measure = binding.etMeasure.text.toString().toInt()
            val calories100gram = intent.getStringExtra("Cals_per100grams").toString()
            val caloriesOnlyDigits = calories100gram.replace(Regex("[^\\d]"), "").toInt()
            showMeasureDialog(measure, caloriesOnlyDigits)
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
    private fun pushHistoryDatabase(totalCalories: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/")
        val reference: DatabaseReference = database.reference.child("datahistory")
        val uid = firebaseAuth.currentUser?.uid.toString()
        val productId = intent.getStringExtra("productId").toString()
        val date: Date = Calendar.getInstance().time
        val userHistory = UserHistory(productId, date.toString(), totalCalories)
        reference.child(uid).push().setValue(userHistory)
    }

    private fun showMeasureDialog(measure: Int, foodCalories: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val totalCalories = measure*foodCalories
        alertDialogBuilder.setTitle("Banyaknya Kalori pada Makananmu Adalah $totalCalories Kalori")
        alertDialogBuilder.setMessage("Apakah Anda ingin mencatat masukan kalori ini?")
        alertDialogBuilder.setPositiveButton("Simpan", DialogInterface.OnClickListener { dialog, which ->
            pushHistoryDatabase(totalCalories.toString())
            Toast.makeText(this, "Kalori Ideal tersimpan", Toast.LENGTH_SHORT).show()
        })
        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        alertDialogBuilder.show()
    }
}