package com.example.newsapp.data.model.news

enum class Categories {
    HOT_NEWS, NORMAL_NEWS, ALL_NEWS;

    companion object {
        fun fromString(value: String): Categories? {
            return entries.find { it.name == value }
        }
    }
}