package com.example.newsapp.data.repository.newsRepo

import com.example.newsapp.data.db.NewsDao
import com.example.newsapp.data.model.News
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

    override fun addNews(news: News) {
        dao.addNews(news)
    }

    override fun deleteNews(id: Int) {
        dao.deleteNews(id)
    }

    override fun updateNews(id: News, news: News) {
        dao.updateNews(news.copy(id = id))
    }


}