package com.example.newsapp.ui.add

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(
    private val urepo: UserRepo,
    private val repo: NewsRepo
) :ViewModel() {
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val tags: MutableLiveData<String> = MutableLiveData()
    val categories: MutableLiveData<String> = MutableLiveData()
    val source: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun submit() {
        if (img.value == null &&
            title.value != null &&
            description.value != null &&
            categories.value != null
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("news_debug", urepo.getLoggedInUser().toString())
                val categoryValue = Categories.fromString(categories.value!!)
                repo.addNews(
                    News(
                        img = byteArrayOf(1, 2, 3, 4, 5),
                        title = title.value!!,
                        description = description.value!!,
                        tags = tags.value!!,
                        categories = categoryValue ?: Categories.NORMAL_NEWS,
                        source = source.value!!,
                        userId = urepo.getLoggedInUser()?.toInt()
                    )
                )

                finish.emit(Unit)


            }
        }
    }
}