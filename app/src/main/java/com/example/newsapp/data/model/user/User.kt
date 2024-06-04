package com.example.newsapp.data.model.user

import android.provider.MediaStore
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.Categories

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,
    val userName: String,
    @ColumnInfo(name = "email")
    val email: String,
    val role: Role = Role.USER,
    val phoneNumber: Int,
    @ColumnInfo(name = "password")
    val password: String
)
