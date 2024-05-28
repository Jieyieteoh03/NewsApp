package com.example.newsapp.data.repository

import com.example.newsapp.data.model.news.News
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    fun getAllNews(): Flow<List<News>>

    fun getNewsById(id: Int) : News?


    fun addNews(news: News): Int?

    fun deleteNews(id: Int)

    fun updateNews(id: Int, news: News)
}