package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.Role
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.userRepo.UserRepo

class UserRepoImple(
    private val dao: UserDao
): UserRepo {

    override fun addUser(user: User) {
        dao.addUser(user)
    }

    override fun getDummyUser(): User{
        return User(1,  "jane","jane.smith@example.com")
    }
}