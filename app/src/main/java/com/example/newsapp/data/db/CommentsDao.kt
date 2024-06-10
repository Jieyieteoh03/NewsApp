package com.example.newsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapp.data.model.Comments
import com.example.newsapp.data.model.news.News
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {
    @Query("SELECT * FROM Comments")
    fun getAllComments(): Flow<List<Comments>>

    @Query("SELECT * FROM Comments WHERE id = :id")
    fun getCommentsById(id: Int): Comments?

    // To find all comments of one news post
    @Query("SELECT * FROM Comments WHERE newsId = newsId")
    fun getCommentsByNewsId(newsId: Int): List<Comments>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(comments: Comments)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateComment(comments: Comments)

    @Delete
    fun deleteComment(comments: Comments)

}