package com.example.capstone_idoeat.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.model.Rekomendasi
import com.example.capstone_idoeat.ui.detail.food.DetailFoodActivity
import com.squareup.picasso.Picasso

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


//class HomeRecommendationListAdapter(private val recommendationList: ArrayList<Rekomendasi>) : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
//class HomeRecommendationListAdapter : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {

//untuk halaman detail
//    private lateinit var mListener: onItemClickListener
//
//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(clickListener: onItemClickListener){
//        mListener = clickListener
//    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = ItemHomeRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(itemView)

//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recommendation, parent, false)
//        return ViewHolder(itemView, mListener)
//        return ViewHolder(itemView)
//    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
//        val currentRekom = recommendationList[position]
//        holder.apply {
//            tvRecommendationName.text = currentRekom.FoodItem
//            tvRecommendationCalories.text = currentRekom.Cals_per100grams
//        }

//        holder.tvRecommendationName.text = currentRekom.FoodName
//        holder.tvRecommendationCalories.text = currentRekom.Cals_per100grams
//
//
//    }

//    override fun getItemCount(): Int {
//        return 4
//        return recommendationList.size
//    }

//    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        fun bindItem() {
//            val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
//            val tvRecommendationCalories: TextView =
//                itemView.findViewById(R.id.tv_recommendation_calories)
//        }


//        val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
//        val tvRecommendationCalories: TextView = itemView.findViewById(R.id.tv_recommendation_calories)
//
//
//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }
//    }

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
//}

//class HomeRecommendationListAdapter :
//    RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = ItemHomeRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(view)
//    }

//class HomeRecommendationListAdapter(private val recommendationList: ArrayList<Rekomendasi>) : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {










class HomeRecommendationListAdapter(private val context: Context, private val recommendationList: ArrayList<Rekomendasi>) : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
    //    //class HomeRecommendationListAdapter : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
//
    //untuk halaman detail
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = ItemHomeRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(itemView)

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_recommendation, parent, false)
        return ViewHolder(itemView, mListener)
//        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentRekom = recommendationList[position]

        //nek ra picaso
//        Picasso.get().load(currentRekom.Image).into(holder.ivImageFood)
//        holder.ivImageFood. = currentRekom.FoodItem
//        holder.ivImageFood.setImageResource(currentRekom.Image)

        //iki gaz nggo glide
//        Glide.with(context).load(currentRekom.Image).into(holder.ivImageFood)
        holder.tvRecommendationName.text = currentRekom.FoodItem
        holder.tvRecommendationCalories.text = currentRekom.Cals_per100grams
        holder.tvRecommendationPrice.text = currentRekom.Price

    }

    override fun getItemCount(): Int {
//        return 4
        return recommendationList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        val ivImageFood: ImageView = itemView.findViewById(R.id.iv_image_food)
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













//class HomeRecommendationListAdapter(
////    private val context: Context,
//    private val dataList: ArrayList<Rekomendasi>
//) : RecyclerView.Adapter<HomeRecommendationListAdapter.ViewHolder>() {
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvRecommendationName: TextView = itemView.findViewById(R.id.tv_recommendation_name)
//        val tvRecommendationCalories: TextView =
//            itemView.findViewById(R.id.tv_recommendation_calories)
//        val cvMain: CardView = itemView.findViewById(R.id.cv_Main)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_home_recommendation, parent, false)
//        return ViewHolder(itemView)
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentRekom = dataList[position]
//        holder.tvRecommendationName.text = currentRekom.FoodName
//        holder.tvRecommendationCalories.text = currentRekom.Cals_per100grams
////        holder.cvMain.setOnClickListener {
////            Toast.makeText(context, "" + currentRekom.FoodName, Toast.LENGTH_SHORT).show()
////        }
//
////        holder.tvRecommendationName.text = dataList.get(position).FoodName
//    }
//
//    fun setData(data: ArrayList<Rekomendasi>){
//        dataList.clear()
//        dataList.addAll(data)
//        notifyDataSetChanged()
//    }
//
//}



