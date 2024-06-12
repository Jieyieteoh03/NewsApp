package com.example.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.news.News
import com.example.newsapp.databinding.LayoutNewsCardItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File

class NewsAdapter(
    @ApplicationContext private val context: Context,
    private var news: List<News>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = LayoutNewsCardItemBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return NewsCardItemViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val new = news[position]
        if(holder is NewsCardItemViewHolder) {
            return holder.bind(new)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<News>) {
        news = newList
        notifyDataSetChanged()
    }

    fun getNews() = news

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(news: List<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    inner class NewsCardItemViewHolder(
        private val binding: LayoutNewsCardItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.tvTitle.text = news.title
            binding.tvDesc.text = news.description
            val image = File(news.img!!)
            if(image.exists()) {
                Glide.with(context)
                    .load(image)
                    .into(binding.ivImage)
            }
            binding.tvCategory.text = news.categories.toString()
            binding.cvNews.setOnClickListener { listener?.onClick(news.id!!) }
        }
    }

    interface Listener {
        fun onClick(id: Int)
    }

}