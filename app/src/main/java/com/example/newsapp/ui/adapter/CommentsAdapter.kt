package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.model.Comments
import com.example.newsapp.databinding.LayoutCommentCardBinding
class CommentsAdapter(
    private var comments: List<Comments>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutCommentCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentsCardItemViewHolder(binding)
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        if(holder is CommentsAdapter.CommentsCardItemViewHolder) {
            return holder.bind(comment)
        }
    }

    fun setComments(comments: List<Comments>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    inner class CommentsCardItemViewHolder(
        private val binding: LayoutCommentCardBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(comments: Comments) {
            binding.tvComments.text = comments.comments
//            binding.cvNews.setOnClickListener { listener?.onClick(comments.id!!) }
        }
    }
}