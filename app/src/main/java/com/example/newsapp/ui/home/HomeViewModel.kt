package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.News
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepo: NewsRepo
): ViewModel() {
    private val _news: MutableLiveData<List<News>> = MutableLiveData()
    val tvTitle: MutableLiveData<String> = MutableLiveData("")
    val news: LiveData<List<News>> = _news
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getAll(): Flow<List<News>> = newsRepo.getAllNews()

    fun fetchNewsData() {
        viewModelScope.launch {
            newsRepo.getAllNews().collect {
                _news.value = it
            }
        }
    }

    fun updateTvTitle(query: String) {
        tvTitle.value = query
    }



}