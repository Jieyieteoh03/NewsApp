package com.example.newsapp.ui.Add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.repository.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(
    private val repo: NewsRepo
) :ViewModel() {
    val img: MutableLiveData<URL> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData()
    val categories: MutableLiveData<String> = MutableLiveData()
    val source: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun submit() {
        if (img.value != null &&
            title.value != null &&
            description.value != null &&
            categories.value != null
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.addNews(
                    News(
                        img = img.value!!,
                        title = title.value!!,
                        description = description.value!!,
                        tags = tags.value!!,
                        categories = Categories.NORMAL_NEWS,
                        source = source.value!!,
                        userId = 1
                    )
                )

                finish.emit(Unit)


            }
        }
    }
}