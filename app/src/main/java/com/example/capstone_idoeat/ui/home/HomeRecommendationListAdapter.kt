package com.example.capstone_idoeat.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.model.Rekomendasi
import com.squareup.picasso.Picasso

class HomeRecommendationListAdapter(private val context: Context, private val recommendationList: ArrayList<Rekomendasi>) : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {

    //untuk halaman detail
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_recommendation, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRekom = recommendationList[position]
        Picasso.get().load(currentRekom.Image).into(holder.ivImageFood)
        holder.tvRecommendationName.text = currentRekom.FoodItem
        holder.tvRecommendationCalories.text = currentRekom.Cals_per100grams
        holder.tvRecommendationPrice.text = currentRekom.Price
    }

    override fun getItemCount(): Int {
        return recommendationList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val ivImageFood: ImageView = itemView.findViewById(R.id.iv_recommendation)
        val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
        val tvRecommendationCalories: TextView = itemView.findViewById(R.id.tv_recommendation_calories)
        val tvRecommendationPrice: TextView = itemView.findViewById(R.id.tv_recommendation_price)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}