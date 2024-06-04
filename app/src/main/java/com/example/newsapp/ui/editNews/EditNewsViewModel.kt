package com.example.newsapp.ui.editNews

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.News
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.URL

@HiltViewModel
class EditNewsViewModel(
    private val repo: NewsRepo
): ViewModel() {
    private val _news: MutableLiveData<News> =  MutableLiveData()
    val news: LiveData<News> = _news
    val img: MutableLiveData<> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData("")
    val description: MutableLiveData<String> = MutableLiveData("")
    val categories: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData("")
    val source: MutableLiveData<String> = MutableLiveData("")
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getWordById(id: Int) {
        _news.postValue(repo.getNewsById(id))
    }

    fun setNews() {
        news.value?.let {
            img.value = it.img
            title.value = it.title
            description.value = it.description
            tags.value= it.tags
            source.value= it.source
        }
    }

    fun editWords() {
        viewModelScope.launch (Dispatchers.IO){
            if(
                title.value != "" &&
                description.value != "" &&
                categories.value != "" &&
                (title.value != _news.value?.title||
                description.value != _news.value?.description||
                tags.value != _news.value?.tags||
                source.value != _news.value?.source)
                ) {
                repo.updateNews(news.value!!.copy(
                    title = title.value!!,
                    description = description.value!!,
                    tags = tags.value!!,
                    source = source.value!!
                ))
            }
        }
    }


}