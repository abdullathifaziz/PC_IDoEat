package com.example.capstone_idoeat.ui.scan_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemFoodScanBinding
import com.example.capstone_idoeat.ui.data.FoodScanCard

class FoodScanAdapter : RecyclerView.Adapter<FoodScanAdapter.FoodScanViewHolder>() {
    private val scanResultsList = mutableListOf<FoodScanCard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodScanViewHolder {
        val itemView = ItemFoodScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodScanViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scanResultsList.size
    }

    override fun onBindViewHolder(holder: FoodScanViewHolder, position: Int) {
        holder.bind(scanResultsList[position])
    }

    fun setData(scanResults: MutableList<FoodScanCard>?) {
        if (scanResults != null) {
            scanResultsList.clear()
            scanResultsList.addAll(scanResults)
            notifyDataSetChanged()
        }
    }

    inner class FoodScanViewHolder(private val binding: ItemFoodScanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodScanCard: FoodScanCard) {
            binding.apply {
                tvFoodName.text = foodScanCard.foodItem
                tvFoodCategory.text = foodScanCard.foodCategory
                tvFoodCalories.text = foodScanCard.Cals_per100grams
            }
        }
    }
}