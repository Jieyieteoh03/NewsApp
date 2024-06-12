package com.example.newsapp.data.model.news

import android.provider.MediaStore.Images
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.user.User
import java.net.URL
import javax.xml.transform.Source

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val img: String?,
    val title: String,
    val description: String,
    val tags: String,
    val categories: Categories = Categories.NORMAL_NEWS,
//    val source: URL? = null,
    val source: String,
    val isSaved: Boolean = false
)
