package com.example.capstone_idoeat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemHomeRecommendationBinding

class HomeRecommendationListAdapter: RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHomeRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0) {
            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            param.marginStart = 50
            holder.itemView.layoutParams = param
        }else if (position == itemCount - 1) {
            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            param.marginEnd = 50
            holder.itemView.layoutParams = param
        }else {
            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            holder.itemView.layoutParams = param
        }
        holder.bindItem()
    }

    override fun getItemCount(): Int {
        return 4
    }

    class ViewHolder(private val binding: ItemHomeRecommendationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem() { //tambahkan parameter isi list
            binding.tvRecommendationName.text = "Gado-gado bu parmin"
            binding.tvRecommendationCalories.text = "500 kkal"
        }
    }
}