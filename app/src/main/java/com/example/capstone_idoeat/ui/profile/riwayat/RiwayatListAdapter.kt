package com.example.capstone_idoeat.ui.profile.riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.databinding.ItemHistoryBinding


class RiwayatListAdapter: RecyclerView.Adapter<RiwayatListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem()
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem() { //tambahkan parameter isi list
            binding.tvFoodName.text = "Nasi Goreng"
            binding.tvFoodDate.text = "12 Mei 2023"
            binding.tvFoodPrice.text = "Rp. 20.000"
            binding.tvFoodCalories.text = "2000 kalori"

        }
    }
}