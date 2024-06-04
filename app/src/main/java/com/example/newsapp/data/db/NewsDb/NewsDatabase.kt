package com.example.newsapp.data.db.NewsDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.core.Converters
import com.example.newsapp.data.model.news.News

@Database(entities = [News::class], version = 2)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object{
        const val NAME = "my_news_database"
    }
}