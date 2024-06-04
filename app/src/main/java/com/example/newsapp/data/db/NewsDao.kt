
package com.example.newsapp.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<News>>

    @Query("SELECT * FROM news_table WHERE id = :id")
    fun getNewsById(id: Int): News?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews (news: News)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNews(news: News)

    @Query ("DELETE FROM news_table WHERE id = :id")
    fun deleteNews(id: Int)
}