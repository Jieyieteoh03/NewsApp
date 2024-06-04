package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.userRepo.UserRepo

class UserRepoImple(
    private val dao: UserDao
): UserRepo {

    override fun addUser(user: User) {
        dao.addUser(user)
    }
}