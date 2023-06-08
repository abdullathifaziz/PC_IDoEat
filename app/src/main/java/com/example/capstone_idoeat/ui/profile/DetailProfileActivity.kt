package com.example.capstone_idoeat.ui.profile

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivityDetailProfileBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class DetailProfileActivity : AppCompatActivity() {

    //    private var binding: FragmentProfileBinding?
    private lateinit var binding: ActivityDetailProfileBinding
    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var preference: UserPreference

    companion object {
        const val REQUEST_CAMERA = 100
    }

    private lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_profile)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(this)


        val ivPhoto: ImageView = binding.ivProfileFoto
        val edNama: EditText = binding.edNama
        val tvEmail: TextView = binding.tvEmail
//        val ivSimpan: ImageView = binding.ivSimpanProfile
        val btnSimpan: Button = binding.btnSimpan
        val ivKembali: ImageView = binding.ivBack

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            if (firebaseUser.photoUrl != null) {
                Picasso.get().load(firebaseUser.photoUrl).into(ivPhoto)
            } else {
                Picasso.get().load("https://picsum.photos/id/58/200/200").into(ivPhoto)
            }

            edNama.setText(firebaseUser.displayName)
            tvEmail.text = firebaseUser.email


//            edNama.text = firebaseUser.displayName
//            edEmail.text = firebaseUser.email
        }

        ivKembali.setOnClickListener {
            val Kembali = Intent(this, MainActivity::class.java)
            startActivity(Kembali)
        }


        ivPhoto.setOnClickListener {
            intentCamera()
        }

        btnSimpan.setOnClickListener {
            // untuk foto
//        ivSimpan.setOnClickListener {
            val image = when {
                ::imageUri.isInitialized -> imageUri
                firebaseUser?.photoUrl == null -> Uri.parse("https://picsum.photos/id/58/200/200")
                else -> firebaseUser.photoUrl
            }

            // deklarasi
            val nama = edNama.text.toString().trim()
//            val email = edEmail.text.toString().trim()

            // untuk nama
            if (nama.isEmpty()) {
                edNama.error = "Nama harus dilsi"
                edNama.requestFocus()
                return@setOnClickListener
            }

//            if (email.isEmpty()) {
//                edNama.error = "Email harus dilsi"
//                edNama.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                edNama.error = "Email tidak valid"
//                edNama.requestFocus()
//                return@setOnClickListener
//            }

//            firebaseUser?.let {
//                firebaseUser.updateEmail(email).addOnCompleteListener {
//                    if (it.isSuccessful){
//                        val actionEmailUpdated = Upd
//                    }
//                }
//            }

//            val user = FirebaseAuth.getInstance().currentUser
//            // Get auth credentials from the user for re-authentication
//            // Get auth credentials from the user for re-authentication
//            val credential = EmailAuthProvider
//                .getCredential() // Current Login Credentials \\
//
//            // Prompt the user to re-provide their sign-in credentials
//            // Prompt the user to re-provide their sign-in credentials
//            user!!.reauthenticate(credential)
//                .addOnCompleteListener {
//                    Log.d(TAG, "User re-authenticated.")
//                    //Now change your email address \\
//                    //----------------Code for Changing Email Address----------\\
//                    val user = FirebaseAuth.getInstance().currentUser
//                    user!!.updateEmail("user@example.com")
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Log.d(TAG, "User email address updated.")
//                            }
//                        }
//                    //----------------------------------------------------------\\
//                }

            // untuk tombol simpan
            UserProfileChangeRequest.Builder()
                .setDisplayName(nama)
                .setPhotoUri(image)
                .build().also {
                    firebaseUser?.updateProfile(it)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
        }


//        supportActionBar!!.setTitle(Html.fromHtml("<font color=\"#6C4AB6\">"+getString(R.string.back_bantuan)+"</font>"))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Edit Profile"
        supportActionBar?.hide()
    }


//    private fun intentCamera() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
//            intent.resolveActivity(packageManager)?.also {
//                startActivityForResult(intent, REQUEST_CAMERA)
//            }
//        }
//    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            this.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {

        val ivPhoto: ImageView = binding.ivProfileFoto

        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("imgUser/${FirebaseAuth.getInstance().currentUser?.uid}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        ref.putBytes(image)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            imageUri = it
                            ivPhoto.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }

}