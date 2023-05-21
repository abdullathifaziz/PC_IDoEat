package com.example.capstone_idoeat.authentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityLoginBinding
    //Ini budi

    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvRegister: TextView
    lateinit var progressDialog: ProgressDialog

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.tvBuatAkunKecil.setOnClickListener {
//            alihLoginKeRegister()
//        }
//
//        binding.btnLogin.setOnClickListener {
//            masukKeHome()
//        }

        editEmail = findViewById(R.id.ed_login_email)
        editPassword = findViewById(R.id.ed_login_password)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tv_buat_akun_kecil)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Masuk")
        progressDialog.setMessage("Silahkan menunggu sebentar...")

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            if (editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                prosesLogin()
            } else {
                Toast.makeText(this, "Silahkan isi email dan password terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        supportActionBar?.hide()
    }

    private fun prosesLogin(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                progressDialog.dismiss()
            }
    }













//
//    private fun masukKeHome(){
//        Intent(this@LoginActivity, MainActivity::class.java).also {
//            startActivity(it)
//        }
//    }
//
//    private fun alihLoginKeRegister(){
//        Intent(this@LoginActivity, RegisterActivity::class.java).also {
//            startActivity(it)
//        }
//    }
//
//    private fun showLoading(state: Boolean){
//        if(state){
//            binding.progressBar.visibility = View.VISIBLE
//        }else{
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}