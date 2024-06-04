package com.example.newsapp.ui.home

import com.example.newsapp.data.repository.NewsRepo
import com.example.newsapp.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val newsRepo: NewsRepo
){
    init {

    }


}