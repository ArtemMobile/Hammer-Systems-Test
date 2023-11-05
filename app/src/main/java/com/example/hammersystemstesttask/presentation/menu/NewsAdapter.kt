package com.example.hammersystemstesttask.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hammersystemstesttask.databinding.BannerCardBinding

class NewsAdapter(private val list: List<Int>) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    class NewsHolder(val binding: BannerCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(BannerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            imageView.setImageResource(item)
        }
    }
}