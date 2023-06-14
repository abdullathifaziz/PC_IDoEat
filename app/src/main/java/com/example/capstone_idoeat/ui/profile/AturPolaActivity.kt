package com.example.capstone_idoeat.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityAturPolaBinding
import com.example.capstone_idoeat.helper.BaseApplication
import com.example.capstone_idoeat.helper.UserPreference

class AturPolaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAturPolaBinding
    private lateinit var preference: UserPreference
//    private lateinit var notificationManager: NotificationManagerCompat

    companion object {
        const val notificationid = 101
        const val CHANNEL_ID = "notifikasi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturPolaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.activity_array,
            android.R.layout.simple_spinner_item
        )

        val genderAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        )

        val polaDietAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.diet_array,
            android.R.layout.simple_spinner_item
        )

        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        polaDietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spActivity.adapter = activityAdapter
        binding.spGender.adapter = genderAdapter
        binding.spPolaDiet.adapter = polaDietAdapter

        binding.btnHitungKalori.setOnClickListener {
            hitungKaloriIdeal()
        }

        supportActionBar?.hide()

        //semua yang menggunakan preference harus di inisialisasi terlebih dahulu, seperti ini
        preference = UserPreference(this)
        val cardView = binding.cvDailyCalories
        val tvDailyCalories = binding.tvDailyCalories
        val chosenCalories = preference.getChosenCalories()
        if (chosenCalories != null) {
            cardView.visibility = View.VISIBLE // Tampilkan CardView
            tvDailyCalories.text = chosenCalories.toString()
        } else {
            cardView.visibility = View.GONE // Sembunyikan CardView
        }

        createNotification()
    }

    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pola Kalori"
            val descriptionText = "Data Kalori Ideal Yang Baru Telah Ditambahkan"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun hitungKaloriIdeal() {
        val gender = getGender()
        val weight = binding.etWeight.text.toString().toDoubleOrNull()
        val height = binding.etHeight.text.toString().toDoubleOrNull()
        val age = binding.etAge.text.toString().toIntOrNull()
        val polaDiet = getPolaDiet()

        if (gender.isNotEmpty() && weight != null && height != null && age != null) {
            val kaloriIdeal = calculateKaloriIdeal(gender, weight, height, age, polaDiet)
            showDialog(kaloriIdeal.toString())
        } else {
            Toast.makeText(this, "Mohon isi semua data dengan benar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateKaloriIdeal(gender: String, weight: Double, height: Double, age: Int, polaDiet: String): Int {
        // Lakukan perhitungan kalori ideal berdasarkan rumus yang sesuai dengan tipe pola kalori harian
        // Misalnya, Anda dapat menggunakan rumus Harris-Benedict untuk menghitung BMR
        // Kemudian, sesuaikan BMR dengan tingkat aktivitas fisik dan tujuan individu

        // Contoh perhitungan sederhana:
        val bmr = if (gender.equals("male", ignoreCase = true)) {
            66 + (13.75 * weight) + (5 * height) - (6.75 * age)
        } else {
            655 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
        }

        // Sesuaikan BMR dengan tingkat aktivitas fisik dan tujuan individu
        val tdee = bmr * getActivityPhysicsMultiplier()

        // Contoh pengaturan pola kalori harian
        val kaloriIdeal = when (polaDiet) { // Ganti dengan tipe pola kalori harian yang sesuai
            "normal" -> (tdee * 0.9).toInt() // Misalnya, mengurangi 10% dari TDEE untuk pola normal
            "diet" -> (tdee * 0.8).toInt() // Misalnya, mengurangi 20% dari TDEE untuk pola diet
            "menggemukan" -> (tdee * 1.1).toInt() // Misalnya, menambah 10% dari TDEE untuk pola menggemukkan
            else -> tdee.toInt() // Default: menggunakan TDEE tanpa perubahan
        }

        return kaloriIdeal
    }

    private fun getPolaDiet(): String {
        val polaIndex = binding.spPolaDiet.selectedItemPosition
        return when (polaIndex) {
            0 -> "normal" // Normal
            1 -> "diet" // Diet
            2 -> "menggemukan" // Gemuk
            else -> "normal" // Default: normal
        }
    }

    private fun getGender(): String{
        val genderIndex = binding.spGender.selectedItemPosition
        return when (genderIndex) {
            0 -> "male"// Laki-laki
            1 -> "female" // Perempuan
            else -> "male" // Default: laki-laki
        }
    }
    private fun getActivityPhysicsMultiplier(): Double {
        // Sesuaikan dengan tingkat aktivitas fisik pengguna yang diinputkan
        val polaKegiatanIndex = binding.spActivity.selectedItemPosition
        return when (polaKegiatanIndex ) {
            0 -> 1.2 // Kurang Aktif (Sedentary)
            1 -> 1.375 // Aktif Ringan (Lightly Active)
            2 -> 1.55 // Aktivitas Sedang (Moderate Activity)
            3 -> 1.725 // Sangat Aktif (Highly Active)
            4 -> 1.9 // Sangat Aktif Sekali (Very Active)
            else -> 1.0 // Default: aktivitas sedentary
        }
    }

    // Fungsi untuk menampilkan dialog konfirmasi
    private fun showDialog(kaloriIdeal: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Kalori yang disarankan: " + kaloriIdeal)
        alertDialogBuilder.setMessage("Apakah Anda ingin menyimpan kalori ideal ini?")
        alertDialogBuilder.setPositiveButton("Simpan", DialogInterface.OnClickListener { dialog, which ->
            preference.setChosenCalories(kaloriIdeal)
            Toast.makeText(this, "Kalori Ideal tersimpan", Toast.LENGTH_SHORT).show()

            sendNotification()
        })
        alertDialogBuilder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        alertDialogBuilder.show()
    }


    private fun sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_idoeat)
            .setContentTitle("I DO EAT")
            .setContentText("Data Kalori Ideal Yang Baru Telah Ditambahkan")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@AturPolaActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationid, builder.build())
        }
    }


}
