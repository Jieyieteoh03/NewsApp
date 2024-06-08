package com.example.newsapp.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import javax.crypto.EncryptedPrivateKeyInfo

@Entity(tableName = "user_table", indices = [Index(value = ["email"],
    unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "email")
    val email: String,
    val role: Role = Role.USER,
    val phoneNumber: String,
    @ColumnInfo(name = "password")
    val password: String,
)
