package com.example.newsapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.User

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = News::class,
            parentColumns = ["id"],
            childColumns = ["newsId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Comments(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val comments: String,
    val userId: Int,
    val newsId: Int
)
