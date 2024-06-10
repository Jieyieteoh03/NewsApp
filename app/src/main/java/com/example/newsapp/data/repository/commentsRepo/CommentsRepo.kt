package com.example.newsapp.data.repository.commentsRepo

import com.example.newsapp.data.model.Comments
import kotlinx.coroutines.flow.Flow
import org.w3c.dom.Comment

interface CommentsRepo {

    fun getAllComments(): Flow<List<Comments>>

    fun getCommentsById(id: Int) : Comments?

    fun getCommentsByNewsId(newsId:Int): List<Comments>

    fun addComment(comments: Comments)

    fun deleteComment(comments: Comments)

    fun updateComment(comments: Comments)
}