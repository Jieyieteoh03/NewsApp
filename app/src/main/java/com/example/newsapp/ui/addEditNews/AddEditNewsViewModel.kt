package com.example.newsapp.ui.addEditNews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.news.News
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditNewsViewModel @Inject constructor (
    private val newsRepo: NewsRepo,
    private val userRepo: UserRepo
) :ViewModel() {
    private var news: News? = null
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData("")
    val description: MutableLiveData<String> = MutableLiveData("")
    val tags: MutableLiveData<String> = MutableLiveData("")
    val categories: MutableLiveData<String> = MutableLiveData("")
    val source: MutableLiveData<String> = MutableLiveData("")
    val snackbar: MutableLiveData<String> = MutableLiveData("")
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

    fun submit() {
        if (img.value != null &&
            title.value != "" &&
            description.value != ""
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val categoryValue = Categories.fromString(categories.value!!)
                val currentUser = userRepo.getCurrentUser()
                if (currentUser != null) {
                try {
                    newsRepo.addNews(
                        News(
                        img = img.value!!,
                        title = title.value!!,
                        description = description.value!!,
                        tags = tags.value!!,
                        categories = categoryValue ?: Categories.NORMAL_NEWS,
                        source = source.value!!,
                        )
                    )
                    snackbar.postValue("Add successful")
                } catch (e: Exception) {
                    throw e
                    snackbar.postValue(e.message)
                }
                } else {
                    snackbar.postValue("Failed to get current user")
                }
                finish.emit(Unit)
            }
        } else {
            snackbar.postValue("Enter all required fields")
        }
    }

    fun editNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryValue = Categories.fromString(categories.value!!)
            if (title.value != "" &&
                description.value != "" &&
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