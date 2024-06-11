package com.example.newsapp.data.repository.newsRepo

import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.UserSavedNews
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    fun getAllNews(): Flow<List<News>>

    fun addSavedNews(savedNews: UserSavedNews)

    fun updateSavedNews(savedNews: UserSavedNews)

    fun getNewsById(id: Int) : News?

    fun addNews(news: News)

    fun deleteNews(news: News)

    fun updateNews(news: News)
}