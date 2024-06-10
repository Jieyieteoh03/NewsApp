package com.example.newsapp.data.repository.userRepo

import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.EncryptionHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface UserRepo {

    fun getAllUser(): Flow<List<User>>

    fun getUserById(id: Int) : User?

    fun getUserByEmail(email: String) : User?  //new

    fun addUser(user: User): Int?

    fun deleteUser(id: Int)

    fun updateUser(id: Int, user: User)

//    fun verifyLoginUser(email: String, password: String) : Flow<User?> = flow {
//        val users = getAllUser().first()
//        val user = users.find { it.email == email }
//
//        user?.let {
//            val decryptedPassword = EncryptionHelper.decrypt(it.encryptedPassword)
//            if (decryptedPassword == password) {
//                emit(user)
//            } else {
//                emit(null)
//            }
//        } ?: emit(null)
//    }

    fun verifyLoginUser(email: String, password: String) : User?

    fun getHashedPsw(email: String): String

    fun getLoggedInUser(): Int?

    fun isLoggedIn(): Boolean

    fun logOut()

    fun getCurrentUser(): User?

}