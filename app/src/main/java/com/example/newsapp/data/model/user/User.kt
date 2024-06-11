package com.example.newsapp.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import javax.crypto.EncryptedPrivateKeyInfo

@Entity(tableName = "user_table", indices = [Index(value = ["user_email"],
    unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "image")
    val img: ByteArray,
    @ColumnInfo(name = "user_email")
    val email: String,
    @ColumnInfo(name = "user_role")
    val role: Role = Role.USER,
    @ColumnInfo(name = "user_phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "password")
    val password: String,
)
