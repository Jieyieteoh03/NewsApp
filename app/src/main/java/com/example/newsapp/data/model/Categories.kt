package com.example.newsapp.data.model.news

enum class Categories {
    HOT_NEWS, NORMAL_NEWS;

    companion object {
        fun fromString(value: String): Categories? {
            return values().find { it.name == value }
        }
    }
}