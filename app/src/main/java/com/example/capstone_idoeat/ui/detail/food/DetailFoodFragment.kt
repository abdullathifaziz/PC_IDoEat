package com.example.capstone_idoeat.ui.detail.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.FragmentDetailFoodBinding
import com.google.firebase.auth.FirebaseAuth

class DetailFoodFragment : Fragment() {

    private var _binding: FragmentDetailFoodBinding? = null
    val firebaseAuth = FirebaseAuth.getInstance()
//    val firebase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_food, container, false)
    }



}