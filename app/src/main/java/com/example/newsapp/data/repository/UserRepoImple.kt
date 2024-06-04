package com.example.newsapp.data.repository

import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

class UserRepoImple(
    private val dao: UserDao
): UserRepo {
    override fun getAllUser(): Flow<List<User>> {
        return dao.getAllUser()
    }

    override fun getUserById(id: Int): User? {
        return dao.getUserById(id)
    }

    override fun addUser(user: User): Int? {
        dao.addUser(user)
        return null
    }

    override fun deleteUser(id: Int) {
        dao.deleteUser(id)
    }

    override fun updateUser(id: Int, user: User) {
        dao.updateUser(user.copy(userId = id))
    }

    override fun verifyLoginUser(email: String, password: String): Flow<User?> {
        return dao.userLogin(email, password)
    }
}