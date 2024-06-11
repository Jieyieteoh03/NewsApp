package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.core.Converters
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.model.user.UserSavedNews

@Database(entities = [News::class, User::class, UserSavedNews::class], version = 3)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    abstract fun getUserDao(): UserDao

    companion object {
        const val NAME = "my_news_database"
    }

}