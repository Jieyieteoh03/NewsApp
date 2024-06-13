package com.example.newsapp.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.news.News
import com.example.newsapp.databinding.LayoutHotNewsCardItemBinding
import java.io.File

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
            binding.tvTitle.text = news.title
            binding.tvDesc.text = news.description
            val image = File(news.img ?: "")
            if(image.exists()) {
                Glide.with(binding.ivImage.context)
                    .load(image)
                    .into(binding.ivImage)
            }
            binding.cvHotNews.setOnClickListener { listener?.onClick(news.id!!) }
        }
    }

    interface Listener {
        fun onClick(id: Int)
    }
}