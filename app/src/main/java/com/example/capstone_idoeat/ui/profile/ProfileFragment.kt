package com.example.capstone_idoeat.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    val firebaseAuth = FirebaseAuth.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val tvNama: TextView = binding.tvNama
        val tvEmail: TextView = binding.tvEmail
        val tvIdUser: TextView = binding.tvIdUser

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            tvNama.text = firebaseUser.displayName
            tvEmail.text = firebaseUser.email
            tvIdUser.text = firebaseUser.uid
        }

        btnKeluar.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}