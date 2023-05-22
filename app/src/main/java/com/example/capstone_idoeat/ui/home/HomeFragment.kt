package com.example.capstone_idoeat.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capstone_idoeat.authentication.LoginActivity
import com.example.capstone_idoeat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

//    lateinit var textFullName: TextView

    val firebaseAuth = FirebaseAuth.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textFullName: TextView = binding.fullName
//        textFullName.text = "Tfdgdfgfdgfdgdf"

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            textFullName.text = firebaseUser.displayName
        }
//        } else {
//            menujuLogin()
//        }
    }

//    private fun menujuLogin() {
//        Intent(this@HomeFragment, LoginActivity::class.java).also {
//            startActivity(it)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}