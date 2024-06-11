package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.UserSavedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    fun getAllNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSavedNews(savedNews: UserSavedNews)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSavedNews(savedNews: UserSavedNews)

    @Query("SELECT * FROM News WHERE id = :id")
    fun getNewsById(id: Int): News?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews (news: News)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: News)

   @Delete
   fun deleteNews(news: News)
}