package com.example.newsapp.data.model

import android.provider.MediaStore.Images
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.user.User

@Entity(
    tableName = "news_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val imgPath: String,
    val title: String,
    val description: String,
    val tags: String,
    val categories: Categories = Categories.NORMAL_NEWS,
    val source: String,
    @ColumnInfo(name = "user_id")
    val userId: Int? = null
)
