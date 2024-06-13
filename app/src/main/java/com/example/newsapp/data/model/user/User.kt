package com.example.newsapp.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import javax.crypto.EncryptedPrivateKeyInfo

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val userName: String,
    val img: ByteArray,
    val email: String,
    val role: Role = Role.USER,
    val phoneNumber: String,
    val password: String,
)
