package com.example.capstone_idoeat

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capstone_idoeat.databinding.ActivityMainBinding
import com.example.capstone_idoeat.helper.MyContextWrapper
import com.example.capstone_idoeat.helper.UserPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preference: UserPreference
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_scan, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.hide()
    }

    override fun attachBaseContext(newBase: Context?) {
        preference = UserPreference(newBase!!)
        val lang = preference.getLoginCount()
        super.attachBaseContext(lang?.let { MyContextWrapper.wrap(newBase, it) })
    }

    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            finishAffinity() // Keluar dari aplikasi
            return
        }

        this.backPressedOnce = true
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            backPressedOnce = false
        }, 2000) // Reset backPressedOnce setelah 2 detik
    }
}