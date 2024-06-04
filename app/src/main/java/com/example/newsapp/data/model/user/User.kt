package com.example.newsapp.data.model.user

import android.provider.MediaStore
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.news.Categories

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userName: String,
    val email: String,
    val role: Role = Role.User,
    val phoneNumber: Int,
    val password: String
)