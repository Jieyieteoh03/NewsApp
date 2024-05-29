package com.example.newsapp.data.db.newsdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.model.news.News

@Database(entities = [News::class], version = 2)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object{
        const val NAME = "my_news_database"
    }
}