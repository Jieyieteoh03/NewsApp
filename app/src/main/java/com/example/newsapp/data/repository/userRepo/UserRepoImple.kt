package com.example.newsapp.data.repository.userRepo

import android.content.Context
import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.Role
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.model.user.UserSavedNews
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRepoImple(
    @ApplicationContext private val context: Context,
    private val dao: UserDao
): UserRepo {

    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun getAllUser(): Flow<List<User>> {
        return dao.getAllUser()
    }

    override fun getUserById(id: Int): User? {
        return dao.getUserById(id)
    }

    override fun getUserSavedNewsById(userId: Int): UserSavedNews? {
        return dao.getAllUserSavedNews(userId)
    }

    override fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)
    }

    override fun addUser(user: User) {
        dao.addUser(user)
    }

    override fun deleteUser(id: Int) {
        dao.deleteUser(id)
    }

    override fun updateUser(id: Int, user: User) {
        dao.updateUser(user.copy(userId = id))
    }


    override fun verifyLoginUser(email: String, password: String): User? {
        val bcrypt =  BCryptPasswordEncoder()
        val hashedPsw = getHashedPsw(email)
        val isValid = bcrypt.matches(password, hashedPsw)

        return if (isValid) {
            val user = dao.getUserByEmail(email)
            saveLoggedInUser(user?.userId!!);
            dao.getUserByEmail(email)
        } else {
            null
        }
    }

    override fun getHashedPsw(email: String): String {
        return dao.getHashedPsw(email).toString()
    }

    override fun isLoggedIn(): Boolean {
        return getLoggedInUser() != null
    }


    override fun getLoggedInUser(): Int? {
        return sharedPreferences.getString("userId", null)?.toInt()
    }

    override fun logOut() {
        val editor = sharedPreferences.edit()
        editor.remove("userId")
        editor.apply()
    }

    private fun saveLoggedInUser(userId: Int) {
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId.toString())
        editor.apply()
    }

    override fun getCurrentUser(): User? {
        val userId = getLoggedInUser()
        return if (userId != null) {
            getUserById(userId)
        } else {
            null
        }
    }

    override  fun changePsw(inputPassword: String): User? {
        val userId = getLoggedInUser()
        return userId?.let {
            val user = dao.getUserById(it)
            val bcrypt = BCryptPasswordEncoder()
            if (user != null && bcrypt.matches(inputPassword, user.password)) {
                user
            } else {
                null
            }
        }
    }
}