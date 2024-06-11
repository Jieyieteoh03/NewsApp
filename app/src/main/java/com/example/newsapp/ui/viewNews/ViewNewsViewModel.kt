package com.example.newsapp.ui.viewNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
):ViewModel() {
    private val _news: MutableLiveData<News> = MutableLiveData()
    val news: LiveData<News> = _news
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
    }



    fun deleteNews() {
        viewModelScope.launch (Dispatchers.IO){
            newsRepo.deleteNews(news.value!!)
            finish.emit(Unit)
        }
    }

    fun getSavedNews(): Flow<List<News>> = newsRepo.getSavedNews()


    fun savedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedNew = news.value?.copy(isSaved = true)
            newsRepo.updateNews(savedNew!!)
            finish.emit(Unit)
        }

    }
}