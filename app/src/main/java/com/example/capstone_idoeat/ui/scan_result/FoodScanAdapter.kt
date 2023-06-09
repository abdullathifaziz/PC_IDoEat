package com.example.capstone_idoeat.ui.scan_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemFoodScanBinding
import com.example.capstone_idoeat.object_detection.Classifier
import com.example.capstone_idoeat.ui.data.FoodItem
import com.squareup.picasso.Picasso

class FoodScanAdapter(
    private val FoodList: List<FoodItem>,
    private val recognitions: List<Classifier.Recognition>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FoodScanAdapter.FoodScanViewHolder>() {
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
        fun bind(foodItem: FoodItem) {
            binding.apply {
                Picasso.get().load(foodItem.Image).into(imgHistory)
                tvFoodName.text = foodItem.FoodItem
                tvFoodCategory.text = foodItem.FoodCategory
                tvFoodCalories.text = foodItem.Cals_per100grams + "/100g"
                if (recognitions.isNotEmpty()) {
                    tvFoodConfidence.text = String.format("%.2f%%", recognitions[0].confidence?.times(100))
                } else {
                    tvFoodConfidence.text = ""
                }
                tvFoodPrice.text = foodItem.Price

                // Mengirim ID item saat card item ditekan
                itemView.setOnClickListener {
                    onItemClick(foodItem.ProductID)
                }
            }
        }
    }
}