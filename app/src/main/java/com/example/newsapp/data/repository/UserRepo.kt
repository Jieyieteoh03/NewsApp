package com.example.newsapp.data.repository

import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getAllUser(): Flow<List<User>>

    fun getUserById(id: Int) : User?


    fun addUser(user: User): Int?

    fun deleteUser(id: Int)

    fun updateUser(id: Int, user: User)

}