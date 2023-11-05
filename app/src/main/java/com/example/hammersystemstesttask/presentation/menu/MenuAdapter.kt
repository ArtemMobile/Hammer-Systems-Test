package com.example.hammersystemstesttask.presentation.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hammersystemstesttask.databinding.FoodCardBinding
import com.example.hammersystemstesttask.domain.model.FoodUiModel

class MenuAdapter(
    private val context: Context,
    private var food: List<FoodUiModel>
) : RecyclerView.Adapter<MenuAdapter.MenuItemHolder>() {

    class MenuItemHolder(val binding: FoodCardBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<FoodUiModel>) {
        this.food = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        return MenuItemHolder(FoodCardBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = food.size

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        val item = food[position]
        with(holder.binding){
            textViewTitle.text = item.name
            textViewDescription.text = item.description
            textViewPrice.text = "от ${item.startPrice} р"
            Glide.with(context)
                .load(item.photo)
                .into(imageViewFood)
        }
    }
}