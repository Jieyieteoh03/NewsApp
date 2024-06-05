package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.data.core.Converters
import com.example.newsapp.data.db.NewsDao
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.user.User

@Database(entities = [News::class, User::class], version = 2)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    abstract fun getUserDao(): UserDao

    companion object {
        const val NAME = "my_news_database"
    }

}