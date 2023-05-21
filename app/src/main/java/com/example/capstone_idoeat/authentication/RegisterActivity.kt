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
import android.widget.Toast.LENGTH_SHORT
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityRegisterBinding

    lateinit var editFullName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editPasswordConf: EditText
    lateinit var btnRegister: Button
    lateinit var tvLogin: TextView
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
        setContentView(R.layout.activity_register)

        editFullName = findViewById(R.id.ed_register_name)
        editEmail = findViewById(R.id.ed_register_email)
        editPassword = findViewById(R.id.ed_register_password)
        editPasswordConf = findViewById(R.id.ed_register_password_konfirmasi)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tv_masuk_akun_kecil)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Buat Akun")
        progressDialog.setMessage("Silahkan menunggu sebentar...")

//        binding = ActivityRegisterBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        showLoading(false)

//        binding.btnRegister.setOnClickListener {
//            val name: String = binding.edRegisterName.text.toString()
//            val email: String = binding.edRegisterEmail.text.toString()
//            val password: String = binding.edRegisterPassword.text.toString()
//            showLoading(true)
//
//            buatAkun(name, email, password)
//        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnRegister.setOnClickListener{
            if (editFullName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                if (editPassword.text.toString() == editPasswordConf.text.toString()){
                        // Launch Register
                        prosesRegister()
                    } else {
                    Toast.makeText(this, "Password harus sama", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Silahkan isi data terlebih dahulu", LENGTH_SHORT).show()
            }
        }

        supportActionBar?.hide()
    }

    private fun prosesRegister(){
        val fullName = editFullName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = fullName
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener { error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()
                        }
                } else {
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }

//    private fun showLoading(state: Boolean){
//        if(state){
//            binding.progressBar.visibility = View.VISIBLE
//        }else{
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}