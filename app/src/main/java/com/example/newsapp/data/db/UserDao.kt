package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllUser(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUserById(id: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser (user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Query("DELETE FROM user_table WHERE user_id = :id")
    fun deleteUser(id: Int)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password ")
    fun userLogin(email: String, password: String): Flow<User?>

    @Query("SELECT password FROM user_table WHERE email = :email LIMIT 1")
    fun getHashedPsw(email: String): String?

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getUserByEmail(email: String): User?
}