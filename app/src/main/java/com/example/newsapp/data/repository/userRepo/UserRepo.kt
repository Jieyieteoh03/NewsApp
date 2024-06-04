package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.model.user.User

interface UserRepo {
    fun addUser(user: User)
}