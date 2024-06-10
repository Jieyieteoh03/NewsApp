package com.example.newsapp.data.repository.userRepo

import android.content.Context
import android.util.Log
import com.example.newsapp.data.db.UserDao
import com.example.newsapp.data.model.user.Role
import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRepoImple(
    private val context: Context,
    private val dao: UserDao
): UserRepo {

    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun getAllUser(): Flow<List<User>> {
        return dao.getAllUser()
    }

    override fun getUserById(id: Int): User? {
        return dao.getUserById(id)
    }

    override fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)
    }

    override fun addUser(user: User): Int? {
        dao.addUser(user)
        return null
    }

    override fun deleteUser(id: Int) {
        dao.deleteUser(id)
    }

    override fun updateUser(user_id: Int, user: User) {
        dao.updateUser(user.copy(userId = user_id))
    }


    override fun verifyLoginUser(email: String, password: String): User? {
        val bcrypth =  BCryptPasswordEncoder()
        val hashedPsw = getHashedPsw(email)
        val isValid = bcrypth.matches(password, hashedPsw)



        if (isValid) {
            val user = dao.getUserByEmail(email)
            Log.d("login", user?.userId.toString())
            saveLoggedInUser(user?.userId!!);
            return dao.getUserByEmail(email)
        } else {
            return null
        }
    }

    override fun getHashedPsw(email: String): String {
        return dao.getHashedPsw(email).toString()
    }

    override fun isLoggedIn(): Boolean {
        return getLoggedInUser() != null
    }


    override fun getLoggedInUser(): Int? {
        return sharedPreferences.getString("user_id", null)?.toInt()
    }

    override fun logOut() {
        val editor = sharedPreferences.edit()
        editor.remove("user_id")
        editor.apply()
    }

    private fun saveLoggedInUser(user_id: Int) {
        val editor = sharedPreferences.edit()
        editor.putString("user_id", user_id.toString())
        editor.apply()
    }

}