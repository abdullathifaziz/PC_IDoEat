package com.example.capstone_idoeat.ui.scan_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemFoodScanBinding
import com.example.capstone_idoeat.ui.data.FoodItem

class FoodScanAdapter(private val FoodList: List<FoodItem>) : RecyclerView.Adapter<FoodScanAdapter.FoodScanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodScanViewHolder {
        val itemView = ItemFoodScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodScanViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return FoodList.size
    }

    override fun onBindViewHolder(holder: FoodScanViewHolder, position: Int) {
        val currentItem = FoodList[position]
        holder.bind(currentItem)
    }

    inner class FoodScanViewHolder(private val binding: ItemFoodScanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(FoodItem: FoodItem) {
            binding.apply {
                tvFoodName.text = FoodItem.FoodItem
                tvFoodCategory.text = FoodItem.FoodCategory
                tvFoodCalories.text = FoodItem.Cals_per100grams
            }
        }
    }
}