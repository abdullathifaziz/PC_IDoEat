package com.example.capstone_idoeat.ui.profile.riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemHistoryBinding
import com.example.capstone_idoeat.ui.data.FoodItem
import com.example.capstone_idoeat.ui.data.HistoryItem
import com.squareup.picasso.Picasso


class RiwayatListAdapter(private val listHistory: List<HistoryItem>, private val onItemClick: (String) -> Unit): RecyclerView.Adapter<RiwayatListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listHistory[position]
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(HistoryFoodItem: HistoryItem) {
            binding.apply {
                Picasso.get().load(HistoryFoodItem.product?.Image).into(imgFood)
                tvFoodName.text = HistoryFoodItem.product?.FoodItem
                tvFoodCategory.text = HistoryFoodItem.product?.FoodCategory
                tvFoodCalories.text = HistoryFoodItem.totalCalories + " kkal"
                tvFoodPrice.text = HistoryFoodItem.product?.Price

                // Mengirim ID item saat card item ditekan
                itemView.setOnClickListener {
                    onItemClick(HistoryFoodItem.product?.ProductID ?: "")
                }
            }
        }
    }
}