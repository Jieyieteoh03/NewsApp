package com.example.newsapp.core.converter

import androidx.room.TypeConverter
import com.example.newsapp.data.model.news.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converter {
    @TypeConverter
    fun fromNewsList(newsList: List<News>?): String? {
        val gson = Gson()
        return if (newsList == null) null else gson.toJson(newsList)
    }

    @TypeConverter
    fun toNewsList(newsListJson: String?): List<News>? {
        val gson = Gson()
        return if (newsListJson == null) null else gson.fromJson(newsListJson, object : TypeToken<List<News>>() {}.type)
    }
}