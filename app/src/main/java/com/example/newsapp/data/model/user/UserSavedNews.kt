package com.example.newsapp.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsapp.core.converter.Converter
import com.example.newsapp.data.model.news.News

@Entity
@TypeConverters(Converter::class)
data class UserSavedNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: Int,
    val savedNews: List<News> = emptyList()
)
