package com.example.capstone_idoeat.ui.profile.bantuan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.FragmentBantuanBinding
import com.google.firebase.auth.FirebaseAuth

class BantuanFragment : Fragment() {

    private var _binding: FragmentBantuanBinding? = null
    private val binding get() = _binding!!
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bantuan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvNama: TextView = binding.tvNama
        val tvBantuan1: TextView = binding.tvBantuan1
        val tvBantuan2: TextView = binding.tvBantuan2
        val tvBantuan3: TextView = binding.tvBantuan3
        val tvBantuan4: TextView = binding.tvBantuan4
        val tvBantuan5: TextView = binding.tvBantuan5
        val ivBantuan1: ImageView = binding.ivBantuan1
        val ivBantuan2: ImageView = binding.ivBantuan2
        val ivBantuan3: ImageView = binding.ivBantuan3
        val ivBantuan4: ImageView = binding.ivBantuan4
        val ivBantuan5: ImageView = binding.ivBantuan5

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            tvNama.text = firebaseUser.displayName
        }

//        tvBantuan1.setOnClickListener {
//            it.findNavController().navigate(R.id.action_bantuanFragment_to_detailBantuanFragment)
//        }
    }

}