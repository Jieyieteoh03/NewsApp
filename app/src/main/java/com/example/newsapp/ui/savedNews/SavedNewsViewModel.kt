package com.example.newsapp.ui.savedNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
): ViewModel() {

    private val _news: MutableLiveData<List<News>> = MutableLiveData()
    val news: LiveData<List<News>> = _news
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()


    fun getAll(): Flow<List<News>> = newsRepo.getSavedNews()
}