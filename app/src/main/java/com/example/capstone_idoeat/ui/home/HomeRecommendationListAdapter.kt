package com.example.capstone_idoeat.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.ItemHomeRecommendationBinding
import com.example.capstone_idoeat.model.Rekomendasi
import com.google.firebase.database.FirebaseDatabase

//class HomeRecommendationListAdapter :
//    RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = ItemHomeRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if(position == 0) {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            param.marginStart = 50
//            holder.itemView.layoutParams = param
//        }else if (position == itemCount - 1) {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            param.marginEnd = 50
//            holder.itemView.layoutParams = param
//        }else {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            holder.itemView.layoutParams = param
//        }
//        holder.bindItem()
//    }
//
//    override fun getItemCount(): Int {
//        return 4
//    }
//
//    class ViewHolder(private val binding: ItemHomeRecommendationBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bindItem() { //tambahkan parameter isi list
//            binding.tvRecommendationName.text = "Gado-gado bu parmin"
//            binding.tvRecommendationCalories.text = "500 kkal"
//        }
//    }


class HomeRecommendationListAdapter(private val recommendationList: ArrayList<Rekomendasi>) :
    RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
    //class HomeRecommendationListAdapter : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {

    //untuk halaman detail
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = ItemHomeRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(itemView)

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recommendation, parent, false)
        return ViewHolder(itemView, mListener)
//        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (position == 0) {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            param.marginStart = 50
//            holder.itemView.layoutParams = param
//        } else if (position == itemCount - 1) {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            param.marginEnd = 50
//            holder.itemView.layoutParams = param
//        } else {
//            val param = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
//            holder.itemView.layoutParams = param
//        }
//        holder.bindItem()
        val currentRekom = recommendationList[position]
//        holder.apply {
//            tvRecommendationName.text = currentRekom.FoodItem
//            tvRecommendationCalories.text = currentRekom.Cals_per100grams
//        }

        holder.tvRecommendationName.text = currentRekom.FoodItem
        holder.tvRecommendationCalories.text = currentRekom.Cals_per100grams


    }

    override fun getItemCount(): Int {
//        return 4
        return recommendationList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        fun bindItem() {
//            val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
//            val tvRecommendationCalories: TextView =
//                itemView.findViewById(R.id.tv_recommendation_calories)
//        }


        val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
        val tvRecommendationCalories: TextView = itemView.findViewById(R.id.tv_recommendation_calories)


        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

//    class ViewHolder(val binding: ItemHomeRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
//        val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
//        val tvRecommendationCalories: TextView = itemView.findViewById(R.id.tv_recommendation_calories)
//    }

//    class ViewHolder(private val binding: ItemHomeRecommendationBinding, clickListener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
//        fun bindItem() { //tambahkan parameter isi list
//            val tvRecommendationName : TextView = binding.tvRecommendationName
//            val tvRecommendationCalories : TextView = binding.tvRecommendationCalories
//
//            init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }
//
////            binding.tvRecommendationName.text
////            binding.tvRecommendationCalories.text
//        }
//    }
}


