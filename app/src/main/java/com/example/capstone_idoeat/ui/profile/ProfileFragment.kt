package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.FragmentProfileBinding
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.ui.profile.bantuan.BantuanActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var preference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnKeluar: Button = binding.btnKeluar
        val ivPhoto: ImageView = binding.ivProfileFoto
        val tvNama: TextView = binding.tvNama
        val tvEmail: TextView = binding.tvEmail
        val btnProfile: Button = binding.btnProfile
        val btnRiwayat: Button = binding.btnRiwayat
        val btnKalori: Button = binding.btnAturKalori
        val btnPengaturan: Button = binding.btnPengaturan
        val btnBantuan: Button = binding.btnBantuan
        val btnTentang: Button = binding.btnTentang

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            tvNama.text = firebaseUser.displayName
            tvEmail.text = firebaseUser.email

            if (firebaseUser.photoUrl!=null){
                Picasso.get().load(firebaseUser.photoUrl).into(ivPhoto)
            }
        }

        btnKeluar.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(activity, DetailProfileActivity::class.java))
        }

        btnRiwayat.setOnClickListener {
            startActivity(Intent(activity, RiwayatKaloriActivity::class.java))
        }

        btnKalori.setOnClickListener {
            startActivity(Intent(activity, AturPolaActivity::class.java))
        }


        btnPengaturan.setOnClickListener {
            startActivity(Intent(activity, PengaturanAppActivity::class.java))
        }

        btnBantuan.setOnClickListener {
            startActivity(Intent(activity, BantuanActivity::class.java))
        }

        btnTentang.setOnClickListener {
            startActivity(Intent(activity, TentangActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}