package com.example.newsapp.data.repository.commentsRepo

import com.example.newsapp.data.db.CommentsDao
import com.example.newsapp.data.model.Comments
import kotlinx.coroutines.flow.Flow

class CommentsRepoImple(
    private val dao: CommentsDao
): CommentsRepo {

    override fun getAllComments(): Flow<List<Comments>> {
        return dao.getAllComments()
    }

    override fun getCommentsById(id: Int): Comments? {
        return dao.getCommentsById(id)
    }

    override fun getCommentsByNewsId(newsId: Int): List<Comments> {
        return dao.getCommentsByNewsId(newsId)
    }

    override fun addComment(comments: Comments) {
        dao.addComment(comments)
    }

    override fun deleteComment(comments: Comments) {
        dao.deleteComment(comments)
    }

    override fun updateComment(comments: Comments) {
        dao.updateComment(comments)
    }
}