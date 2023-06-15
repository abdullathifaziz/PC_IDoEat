package com.example.capstone_idoeat.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.MainActivity
import com.example.capstone_idoeat.databinding.ActivitySearchFoodBinding

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodBinding
    private lateinit var searchViewModel: SearchFoodViewModel
    private lateinit var searchFoodAdapter: SearchFoodAdapter

    companion object{
        const val EXTRA_SEARCH = "extra_search"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (intent.getStringExtra(EXTRA_SEARCH) != null) {
            val query = intent.getStringExtra(EXTRA_SEARCH)
            binding.svFood.setIconifiedByDefault(false)
            binding.svFood.requestFocus()
            binding.svFood.post {
                binding.svFood.setQuery(query, true)
            }
        }

        searchViewModel = ViewModelProvider(this).get(SearchFoodViewModel::class.java)
        searchFoodAdapter = SearchFoodAdapter(emptyList())

        binding.rvFood.layoutManager = LinearLayoutManager(this)
        binding.rvFood.adapter = searchFoodAdapter

        searchViewModel.getFoodList().observe(this, Observer { foodList ->
            searchFoodAdapter = SearchFoodAdapter(foodList)
            binding.rvFood.adapter = searchFoodAdapter
        })

        binding.svFood.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchViewModel.searchFood(newText)
                return true
            }
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }
}