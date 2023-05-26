package com.example.capstone_idoeat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemHomeHistoryBinding

class HomeHistoryListAdapter: RecyclerView.Adapter<HomeHistoryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHomeHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class ViewHolder(private val binding: ItemHomeHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem() { //tambahkan parameter isi list
            binding.tvFoodName.text = "Nasi Goreng"
            binding.tvFoodCalories.text = "500 kkal"
        }
    }
}