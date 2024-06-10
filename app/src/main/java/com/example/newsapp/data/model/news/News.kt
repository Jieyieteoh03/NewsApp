package com.example.newsapp.data.model.news

import android.provider.MediaStore.Images
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.user.User
import java.net.URL

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
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
    val userId: Int,
    val isSaved: Boolean = false
)
