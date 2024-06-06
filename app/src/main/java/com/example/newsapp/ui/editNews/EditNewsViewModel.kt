package com.example.newsapp.ui.editNews

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class EditNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
): ViewModel() {
    private val _news: MutableLiveData<News> =  MutableLiveData()
    val news: LiveData<News> = _news
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData("")
    val description: MutableLiveData<String> = MutableLiveData("")
    val categories: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData("")
    val source: MutableLiveData<String> = MutableLiveData("")
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getWordById(id: Int) {
        _news.postValue(newsRepo.getNewsById(id))
    }

    fun setNews() {
        news.value?.let {
            img.value = it.img
            title.value = it.title
            description.value = it.description
            categories.value = it.categories.toString()
            tags.value= it.tags
            source.value= it.source
        }
    }

    fun editWords() {
        viewModelScope.launch (Dispatchers.IO){
            val categoryValue = Categories.fromString(categories.value!!)
            if(
                title.value != "" &&
                description.value != "" &&
                categories.value != "" &&
                (title.value != _news.value?.title||
                description.value != _news.value?.description||
                tags.value != _news.value?.tags||
                source.value != _news.value?.source)
                ) {
                newsRepo.updateNews(news.value!!.copy(
                    img = img.value!!,
                    title = title.value!!,
                    description = description.value!!,
                    categories = categoryValue ?: Categories.NORMAL_NEWS,
                    tags = tags.value!!,
                    source = source.value!!
                ))
            }
        }
    }


}