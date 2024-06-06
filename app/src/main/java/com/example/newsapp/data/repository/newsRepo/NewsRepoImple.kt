package com.example.newsapp.data.repository.newsRepo

import android.util.Log
import com.example.newsapp.data.db.NewsDao
import com.example.newsapp.data.model.News
import kotlinx.coroutines.flow.Flow

class NewsRepoImple(
    private val dao: NewsDao
): NewsRepo {
    override fun getAllNews(): Flow<List<News>> {
        return dao.getAllNews()
    }

    override fun getSavedNews(): Flow<List<News>> {
       return dao.getSavedNews()
    }

    override fun getNewsById(id: Int): News? {
        return dao.getNewsById(id)
    }

    override fun addNews(news: News) {
        dao.addNews(news)
    }

    override fun deleteNews(news: News) {
        dao.deleteNews(news)
    }

    override fun updateNews(news: News) {
        Log.d("debugging 2", news.toString())
        dao.updateNews(news)
    }


}