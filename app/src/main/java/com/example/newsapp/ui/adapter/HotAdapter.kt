package com.example.newsapp.ui.adapter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.news.News
import com.example.newsapp.databinding.LayoutHotNewsCardItemBinding
import com.example.newsapp.databinding.LayoutNewsCardItemBinding

class HotAdapter(
    private var news: List<News>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: NewsAdapter.Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutHotNewsCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsHotCardItemViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val new = news[position]
        if (holder is NewsHotCardItemViewHolder) {
            return holder.bind(new)
        }
    }

    fun getNews() = news

    fun setNews(news: List<News>) {
        Log.d("debugging", news.toString())
        Log.d("debugging", this.news.toString())

        this.news = news
        notifyDataSetChanged()
    }

    inner class NewsHotCardItemViewHolder(
        private val binding: LayoutHotNewsCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            val bitmap = BitmapFactory.decodeByteArray(news.img, 0, news.img.size)
            val drawable = BitmapDrawable(binding.root.context.resources, bitmap)
            binding.ivImage.background = drawable
            binding.tvTitle.text = news.title
            binding.tvDesc.text = news.description
            binding.cvHotNews.setOnClickListener { listener?.onClick(news.id!!) }
        }
    }

    interface Listener {
        fun onClick(id: Int)
    }
}