package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.model.user.UserSavedNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface UserRepo {

    fun getAllUser(): Flow<List<User>>

    fun getUserSavedNewsById(userId: Int): UserSavedNews?

    fun getUserById(id: Int) : User?

    fun getUserByEmail(email: String) : User?

    fun addUser(user: User)

    fun deleteUser(id: Int)

    fun updateUser(id: Int, user: User)

    fun verifyLoginUser(email: String, password: String) : User?

    fun getHashedPsw(email: String): String

    fun getLoggedInUser(): Int?

    fun isLoggedIn(): Boolean

    fun logOut()

    fun getCurrentUser(): User?

    fun changePsw(inputPassword: String): User?



}