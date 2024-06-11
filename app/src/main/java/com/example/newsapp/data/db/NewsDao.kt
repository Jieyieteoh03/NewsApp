package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.news.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM News WHERE isSaved = 0 || 1")
    fun getAllNews(): Flow<List<News>>

    @Query("SELECT * FROM News WHERE isSaved = 1")
    fun getSavedNews(): Flow<List<News>>

    @Query("SELECT * FROM News WHERE id = :id")
    fun getNewsById(id: Int): News?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews (news: News)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: News)

   @Delete
   fun deleteNews(news: News)
}