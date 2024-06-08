package com.example.newsapp.ui.viewNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Comments
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.repository.commentsRepo.CommentsRepo
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val commentsRepo: CommentsRepo
):ViewModel() {
    private val _news: MutableLiveData<News> = MutableLiveData()
    val news: LiveData<News> = _news
    private val _comments: MutableLiveData<List<Comments>> = MutableLiveData()
    val comments: LiveData<List<Comments>> = _comments
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val categories: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData()
    val source: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getNewsById(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
            _news.postValue(newsRepo.getNewsById(id))
        }
    }

    fun setNews() {
        news.value?.let {
            img.value = it.img
            title.value = it.title
            description.value = it.description
            categories.value = it.categories.toString()
            tags.value = it.tags
            source.value = it.source

        }
        getCommentsByNewsId(news.value?.id!!)
    }

    private fun getCommentsByNewsId(newsId:Int) {
        viewModelScope.launch {
            _comments.postValue(commentsRepo.getCommentsByNewsId(newsId))
        }
    }

    fun deleteNews() {
        viewModelScope.launch (Dispatchers.IO){
            newsRepo.deleteNews(news.value!!)
            finish.emit(Unit)
        }
    }

    fun addComments(comment: Comments) {
        if(comment.comments != "") {
            viewModelScope.launch (Dispatchers.IO){
                commentsRepo.addComment(comment)
            }
        }
    }
}