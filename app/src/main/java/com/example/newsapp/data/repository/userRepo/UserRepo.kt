package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getAllUser(): Flow<List<User>>

    fun getUserById(id: Int) : User?

    fun getUserByEmail(email: String) : User?

    fun addUser(user: User): Int?

    fun deleteUser(id: Int)

    fun updateUser(id: Int, user: User)


    fun verifyLoginUser(email: String, password: String) : User?

    fun getHashedPsw(email: String): String

    fun getLoggedInUser(): Int?

    fun isLoggedIn(): Boolean

    fun logOut()

}