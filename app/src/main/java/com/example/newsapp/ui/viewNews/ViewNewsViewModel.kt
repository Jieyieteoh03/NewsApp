package com.example.newsapp.ui.viewNews

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.model.user.UserSavedNews
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class ViewNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val userRepo: UserRepo
):ViewModel() {
    private val _news: MutableLiveData<News> = MutableLiveData()
    val news: LiveData<News> = _news
    val img: MutableLiveData<String?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val categories: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData()
    val source: MutableLiveData<String> = MutableLiveData()
    val snackbar: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    private var _savedNews: MutableLiveData<UserSavedNews> = MutableLiveData()
    val savedNews: LiveData<UserSavedNews> = _savedNews


    private val _loggedInUser = MutableLiveData<User?>()
    val loggedInUser: LiveData<User?> = _loggedInUser

    fun getNewsById(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
            _news.postValue(newsRepo.getNewsById(id))
            _loggedInUser.postValue(userRepo.getCurrentUser())
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
    }

    fun deleteNews() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                newsRepo.deleteNews(news.value!!)
                snackbar.postValue("Delete successful")
            } catch (e: Exception) {e.message}
            finish.emit(Unit)
        }
    }

    fun addEditSavedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepo.getLoggedInUser()
            userId?.let {
                val existingSavedNews = userRepo.getUserSavedNewsById(it)
                val finalNews: News?
                if(existingSavedNews != null) {
                    val newsList = existingSavedNews.savedNews.toMutableList()
                    val temp = newsList.filter { each -> each.id == news.value!!.id }
                    finalNews = news.value!!.copy(isSaved = temp.isEmpty())
                    if(temp.isNotEmpty()) { newsList.remove(news.value!!) }
                    else { newsList.add(finalNews) }
                    newsRepo.updateNews(finalNews)
                    newsRepo.updateSavedNews(existingSavedNews.copy(savedNews = newsList))
                } else {
                    finalNews = news.value!!.copy(isSaved = true)
                    newsRepo.updateNews(finalNews)
                    newsRepo.addSavedNews(UserSavedNews(
                        userId = it,
                        savedNews = listOf(finalNews)
                    ))
                }
            }
        }
    }

    fun getSavedNewsById(){
        viewModelScope.launch (Dispatchers.IO){
            val userId = userRepo.getLoggedInUser()
            userId?.let {
              _savedNews.postValue(userRepo.getUserSavedNewsById(userId))
            }
        }
    }
}