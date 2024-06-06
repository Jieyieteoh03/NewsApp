package com.example.newsapp

import android.app.Application
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.newsapp.data.db.NewsDatabase
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp:Application() {

}