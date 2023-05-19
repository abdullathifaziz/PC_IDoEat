package com.example.capstone_idoeat.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMasukAkunKecil.setOnClickListener {
            alihRegisterKeLogin()
        }

        binding.btnRegister.setOnClickListener {
            alihRegisterKeLogin()
        }

        supportActionBar?.hide()
    }

    private fun alihRegisterKeLogin(){
        Intent(this@RegisterActivity, LoginActivity::class.java).also {
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