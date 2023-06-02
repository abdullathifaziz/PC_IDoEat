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
import androidx.navigation.findNavController
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.FragmentProfileBinding
import com.example.capstone_idoeat.ui.profile.bantuan.BantuanActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnKeluar: Button = binding.btnKeluar
        val ivPhoto: ImageView = binding.ivProfileFoto
        val tvNama: TextView = binding.tvNama
        val tvEmail: TextView = binding.tvEmail

//        val icProfile: ImageView = binding.icProfile
//        val tvProfile: TextView = binding.tvProfileSetting
//        val icRiwayat: ImageView = binding.icRiwayat
//        val tvRiwayat: TextView = binding.tvRiwayat
//        val icKalori: ImageView = binding.icKalori
//        val tvKalori: TextView = binding.tvKalori
//        val icPengaturan: ImageView = binding.icPengaturan
//        val tvPengaturan: TextView = binding.tvPengaturan
//        val icBantuan: ImageView = binding.icBantuan
//        val tvBantuan: TextView = binding.tvBantuan
//        val icTentang: ImageView = binding.icTentang
//        val tvTentang: TextView = binding.tvTentang

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


//        icProfile.setOnClickListener {
//            startActivity(Intent(activity, DetailProfileActivity::class.java))
//        }
//        tvProfile.setOnClickListener {
//            startActivity(Intent(activity, DetailProfileActivity::class.java))
//        }
//
//        icRiwayat.setOnClickListener {
//            startActivity(Intent(activity, RiwayatKaloriActivity::class.java))
//        }
//        tvRiwayat.setOnClickListener {
//            startActivity(Intent(activity, RiwayatKaloriActivity::class.java))
//        }
//
//        icKalori.setOnClickListener {
//            startActivity(Intent(activity, AturPolaActivity::class.java))
//        }
//        tvKalori.setOnClickListener {
//            startActivity(Intent(activity, AturPolaActivity::class.java))
//        }
//
//        icPengaturan.setOnClickListener {
//            startActivity(Intent(activity, PengaturanAppActivity::class.java))
//        }
//        tvPengaturan.setOnClickListener {
//            startActivity(Intent(activity, PengaturanAppActivity::class.java))
//        }
//
//        icBantuan.setOnClickListener {
//            startActivity(Intent(activity, BantuanActivity::class.java))
//        }
//        tvBantuan.setOnClickListener {
//            startActivity(Intent(activity, BantuanActivity::class.java))
//        }
//
//        icTentang.setOnClickListener {
//            startActivity(Intent(activity, TentangActivity::class.java))
//        }
//        tvTentang.setOnClickListener {
//            startActivity(Intent(activity, TentangActivity::class.java))
//        }




//        btnBantuan.setOnClickListener {
//            it.findNavController().navigate(R.id.action_navigation_profile_to_bantuanFragment)
//        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}