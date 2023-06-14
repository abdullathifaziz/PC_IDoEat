package com.example.capstone_idoeat.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.databinding.FragmentHomeBinding
import com.example.capstone_idoeat.model.Rekomendasi
import com.example.capstone_idoeat.ui.detail.food.DetailFoodActivity
import com.example.capstone_idoeat.ui.recommendation.FilterRecommendationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
//    val firebase : FirebaseDatabase = FirebaseDatabase.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var recommendationList: ArrayList<Rekomendasi>
    private lateinit var dbRef: DatabaseReference

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

//        val layoutManagerRecommendation = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewRecommendation.layoutManager = layoutManagerRecommendation

//        val layoutManagerRecommendation = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewRecommendation.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.rvAdapterList.observe(viewLifecycleOwner) { adapterList ->
            recyclerViewHistory.adapter = adapterList[0]
            recyclerViewRecommendation.adapter = adapterList[1]
        }

        binding.btnFilterRecommendation.setOnClickListener {
            val filterRecommendationFragment = FilterRecommendationFragment()
            filterRecommendationFragment.arguments = Bundle().apply {
                putString("filter", "recommendation")
            }

            val fragmentManager = (binding.root.context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.root.id, filterRecommendationFragment)
                .addToBackStack(null)
                .commit()
        }


//        recyclerViewRecommendation.setOnClickListener {
//            startActivity(Intent(activity, DetailFoodActivity::class.java))
//        }

        dbRef = FirebaseDatabase.getInstance().getReference("datakalori")
        recommendationList = arrayListOf<Rekomendasi>()
//        recommendationList = arrayListOf()

        getRekomendasiData()

        binding.rvHomeRecommendation.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
        }

    }


//    private fun menujuLogin() {
//        Intent(this@HomeFragment, LoginActivity::class.java).also {
//            startActivity(it)
//        }
//    }

    private fun getRekomendasiData(){
        val recyclerViewRecommendation = binding.rvHomeRecommendation

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                recommendationList.clear()
                if (snapshot.exists()){
                    for (rekomSnap in snapshot.children){
                        val rekomData = rekomSnap.getValue(Rekomendasi::class.java)
                        recommendationList.add(rekomData!!)
                    }
                    val mRekomAdapter = HomeRecommendationListAdapter(recommendationList)
                    recyclerViewRecommendation.adapter = mRekomAdapter

                    //untuk ke detail makanan
                    mRekomAdapter.setOnItemClickListener(object : HomeRecommendationListAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(activity, DetailFoodActivity::class.java)

                            intent.putExtra("FoodItem", recommendationList[position].FoodItem)
                            intent.putExtra("Cals_per100grams", recommendationList[position].Cals_per100grams)
                            startActivity(intent)

                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error : $error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}