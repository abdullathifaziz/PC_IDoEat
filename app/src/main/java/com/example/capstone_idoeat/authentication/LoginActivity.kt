package com.example.capstone_idoeat.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBuatAkunKecil.setOnClickListener {
            alihLoginKeRegister()
        }

        binding.btnLogin.setOnClickListener {
            masukKeHome()
        }

        supportActionBar?.hide()
    }

    private fun masukKeHome(){
        Intent(this@LoginActivity, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun alihLoginKeRegister(){
        Intent(this@LoginActivity, RegisterActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}