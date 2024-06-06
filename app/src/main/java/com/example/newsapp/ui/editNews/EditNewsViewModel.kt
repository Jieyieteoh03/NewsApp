package com.example.newsapp.ui.editNews

import android.media.Image
import android.util.Log
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
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class EditNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
): ViewModel() {
    private var news: News? = null
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData("")
    val description: MutableLiveData<String> = MutableLiveData("")
    val categories: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData("")
    val source: MutableLiveData<String> = MutableLiveData("")
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getNewsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            news = newsRepo.getNewsById(id)
            news?.let {
                withContext(Dispatchers.Main) {
                    img.value = it.img
                    title.value = it.title
                    description.value = it.description
                    categories.value = it.categories.toString()
                    tags.value= it.tags
                    source.value= it.source
                }
            }

        }
    }


    fun editNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryValue = Categories.fromString(categories.value!!)
            if (
                title.value != "" &&
                description.value != "" &&
                categories.value != "" &&
                (title.value != news?.title ||
                        description.value != news?.description ||
                        tags.value != news?.tags ||
                        source.value != news?.source)
            ) {
                news?.let { newsTemp ->
                    newsRepo.updateNews(
                        newsTemp.copy(
                            img = img.value!!,
                            title = title.value!!,
                            description = description.value!!,
                            categories = categoryValue ?: Categories.NORMAL_NEWS,
                            tags = tags.value!!,
                            source = source.value!!
                        )
                    )
                    finish.emit(Unit)
                }
            }
        }
    }


}