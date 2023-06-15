package com.example.capstone_idoeat.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemFoodScanBinding
import com.example.capstone_idoeat.ui.data.FoodItem
import com.squareup.picasso.Picasso
class SearchFoodAdapter(private val FoodList: List<FoodItem>) : RecyclerView.Adapter<SearchFoodAdapter.FoodSearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodSearchViewHolder {
        val itemView = ItemFoodScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodSearchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return FoodList.size
    }

    override fun onBindViewHolder(holder: FoodSearchViewHolder, position: Int) {
        val currentItem = FoodList[position]
        holder.bind(currentItem)
    }

    inner class FoodSearchViewHolder(private val binding: ItemFoodScanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(FoodItem: FoodItem) {
            binding.apply {
                Picasso.get().load(FoodItem.Image).into(imgHistory)
                tvFoodName.text = FoodItem.FoodItem
                tvFoodCategory.text = FoodItem.FoodCategory
                tvFoodCalories.text = FoodItem.Cals_per100grams
            }
        }
    }
}