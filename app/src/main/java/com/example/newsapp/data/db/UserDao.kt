package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.user.UserSavedNews
import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): Flow<List<User>>

    @Query("SELECT * FROM UserSavedNews WHERE userId = :userId")
    fun getAllUserSavedNews(userId: Int): UserSavedNews?

    @Query("SELECT * FROM User WHERE userId = :id")
    fun getUserById(id: Int): User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser (user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Query("DELETE FROM User WHERE userId = :id")
    fun deleteUser(id: Int)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password ")
    fun userLogin(email: String, password: String): Flow<User?>

    @Query("SELECT password FROM User WHERE email = :email LIMIT 1")
    fun getHashedPsw(email: String): String?

}