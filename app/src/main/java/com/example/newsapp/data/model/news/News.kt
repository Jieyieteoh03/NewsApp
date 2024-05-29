package com.example.newsapp.data.model.news

import android.provider.MediaStore.Images
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val img: Images,
    val title: String,
    val description: String,
    val tags: String,
    val categories: Categories = Categories.NORMAL_NEWS,
    val source: String
)
