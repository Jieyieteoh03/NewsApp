package com.example.newsapp.data.repository.newsRepo

import com.example.newsapp.data.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    fun getAllNews(): Flow<List<News>>

    fun getSavedNews(): Flow<List<News>>

    fun getNewsById(id: Int) : News?


    fun addNews(news: News)

    fun deleteNews(news: News)

    fun updateNews(news: News)
}