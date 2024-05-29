package com.example.newsapp.data.repository

import com.example.newsapp.data.db.newsdb.NewsDao
import com.example.newsapp.data.model.news.News
import kotlinx.coroutines.flow.Flow

class NewsRepoImple(
    private val dao: NewsDao
): NewsRepo {
    override fun getAllNews(): Flow<List<News>> {
        return dao.getAllNews()
    }

    override fun getNewsById(id: Int): News? {
        return dao.getNewsById(id)
    }

    override fun addNews(news: News): Int? {
        dao.addNews(news)
        return null
    }

    override fun deleteNews(id: Int) {
        dao.deleteNews(id)
    }

    override fun updateNews(id: Int, news: News) {
        dao.updateNews(news.copy(id = id))
    }


}