package com.example.newsapp.data.repository.authRepo

//import android.content.Context
//import android.util.Log
//import com.example.newsapp.data.db.NewsDao
//import com.example.newsapp.data.db.UserDao
//import com.example.newsapp.data.model.News
//import com.example.newsapp.data.repository.newsRepo.NewsRepo
//import kotlinx.coroutines.flow.Flow
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//
//class AuthRepoImple(
//    private val context: Context,
//    private val dao: UserDao
//): AuthRepo {
//
//    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
//    override fun login(email: String, password: String): Boolean {
//        val bcrypt =  BCryptPasswordEncoder()
//        val hashedPsw = dao.getHashedPsw(email)
//        val isValid = bcrypt.matches(password, hashedPsw)
//
//        return isValid
//    }
//
//    fun isLoggedIn(): Boolean {
//        return getLoggedInUser() != null
//    }
//
//    private fun getLoggedInUser(): String? {
//        return sharedPreferences.getString("user_email", null)
//    }
//
//    private fun saveLoggedInUser(username: String) {
//        val editor = sharedPreferences.edit()
//        editor.putString("user_email", username)
//        editor.apply()
//    }
//
//    private fun clearLoggedInUser() {
//        val editor = sharedPreferences.edit()
//        editor.remove("user_id")
//        editor.apply()
//    }
//}