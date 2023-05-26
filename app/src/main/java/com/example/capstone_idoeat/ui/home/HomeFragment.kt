package com.example.capstone_idoeat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val firebaseAuth = FirebaseAuth.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
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

        // INFO: recycleView riwayat dan rekomendasi makanan
        val recyclerViewHistory = binding.rvHomeHistory
        val recyclerViewRecommendation = binding.rvHomeRecommendation

        val layoutManagerHistory = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewHistory.layoutManager = layoutManagerHistory

        val layoutManagerRecommendation = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewRecommendation.layoutManager = layoutManagerRecommendation

        homeViewModel.rvAdapterList.observe(viewLifecycleOwner) { adapterList ->
            recyclerViewHistory.adapter = adapterList[0]
            recyclerViewRecommendation.adapter = adapterList[1]
        }
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