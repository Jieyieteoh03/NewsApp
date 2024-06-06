package com.example.newsapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.user.User

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val img: ByteArray,
    val title: String,
    val description: String,
    val tags: String,
    val categories: Categories = Categories.NORMAL_NEWS,
    val source: String,
    val userId: Int? = null
)
