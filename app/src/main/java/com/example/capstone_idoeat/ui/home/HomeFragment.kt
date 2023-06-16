package com.example.capstone_idoeat.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.databinding.FragmentHomeBinding
import com.example.capstone_idoeat.model.Rekomendasi
import com.example.capstone_idoeat.ui.detail.food.DetailFoodActivity
import com.example.capstone_idoeat.helper.UserPreference
import com.example.capstone_idoeat.ui.data.HistoryItem
import com.example.capstone_idoeat.ui.profile.AturPolaActivity
import com.example.capstone_idoeat.ui.profile.riwayat.RiwayatViewModel
import com.example.capstone_idoeat.ui.recommendation.FilterRecommendationFragment
import com.example.capstone_idoeat.ui.search.SearchFoodActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
//    val firebase : FirebaseDatabase = FirebaseDatabase.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var preferences: UserPreference
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var riwayatViewModel: RiwayatViewModel
    private lateinit var recommendationList: ArrayList<Rekomendasi>
    private lateinit var dbRef: DatabaseReference
    private lateinit var context: Context

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

//        val layoutManagerHistory = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewHistory.layoutManager = layoutManagerHistory

//        val layoutManagerRecommendation = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewRecommendation.layoutManager = layoutManagerRecommendation

//        val layoutManagerRecommendation = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewRecommendation.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

//        homeViewModel.rvAdapterList.observe(viewLifecycleOwner) { adapterList ->
//            recyclerViewHistory.adapter = adapterList[0]
//            recyclerViewRecommendation.adapter = adapterList[1]
//        }

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

        dbRef = FirebaseDatabase.getInstance("https://datauser2.firebaseio.com/").getReference("datakalori")
//        dbRef = FirebaseDatabase.getInstance().getReference("datakalori")
        recommendationList = arrayListOf<Rekomendasi>()
//        recommendationList = arrayListOf()

        recyclerViewHistory.adapter = HomeHistoryListAdapter()
        binding.rvHomeHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvHomeRecommendation.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        getRekomendasiData()


        // hati-hati kehapus ini
        riwayatViewModel = RiwayatViewModel()
        val dataRiwayat = riwayatViewModel.getHistoryUserFood(firebaseUser!!.uid)
        preferences = UserPreference(requireContext())
        val calories = preferences.getChosenCalories()
        if (calories != null) {
            CaloriesReport(calories.toInt(), dataRiwayat)
        } else {
            binding.cdDailyReport.visibility = View.GONE
            binding.cdToSetting.visibility = View.VISIBLE
            binding.cdToSetting.setOnClickListener {
                startActivity(Intent(activity, AturPolaActivity::class.java))
            }
        }

        val searchView = binding.svFood
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val intent = Intent(activity, SearchFoodActivity::class.java)
                intent.putExtra(SearchFoodActivity.EXTRA_SEARCH, query)
                startActivity(intent)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }


    private fun CaloriesReport(CaloriesTarget: Int, dataRiwayat: LiveData<List<HistoryItem>>?) {

        var totalCalories = 0

        dataRiwayat?.value?.forEach { historyItem ->
            Log.d("______________________totalCalories", historyItem.totalCalories.toString())
            totalCalories += historyItem.totalCalories.toInt()
        }

        val caloriesPercentage = totalCalories.toDouble() * 100 / CaloriesTarget
        binding.tvPersentCalories.text = "$caloriesPercentage"
        binding.tvCountCalories.text = "$totalCalories / $CaloriesTarget"
    }

    //firebase
    private fun getRekomendasiData() {
        val recyclerViewRecommendation = binding.rvHomeRecommendation

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recommendationList.clear()
                if (snapshot.exists()) {
                    for (rekomSnap in snapshot.children) {
                        val rekomData = rekomSnap.getValue(Rekomendasi::class.java)
                        recommendationList.add(rekomData!!)
                    }
                    val mRekomAdapter =
                        HomeRecommendationListAdapter(requireContext(), recommendationList)
                    recyclerViewRecommendation.adapter = mRekomAdapter

                    //untuk ke detail makanan
                    mRekomAdapter.setOnItemClickListener(object :
                        HomeRecommendationListAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(activity, DetailFoodActivity::class.java)

                            intent.putExtra("Image", recommendationList[position].Image)
                            intent.putExtra("FoodItem", recommendationList[position].FoodItem)
                            intent.putExtra("Cals_per100grams", recommendationList[position].Cals_per100grams)
                            intent.putExtra("Price", recommendationList[position].Price)
                            intent.putExtra("FoodCategory", recommendationList[position].FoodCategory)
                            intent.putExtra("Link_Toko", recommendationList[position].Link_Toko)
                            intent.putExtra("productId", recommendationList[position].ProductID)
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