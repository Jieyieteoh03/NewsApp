package com.example.newsapp

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.db.NewsDatabase
import com.example.newsapp.data.repository.userRepo.UserRepo
import com.example.newsapp.data.repository.userRepo.UserRepoImple
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp: Application() {

    lateinit var repo: UserRepo

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            this,
            NewsDatabase::class.java,
            NewsDatabase.NAME
        ).fallbackToDestructiveMigration().build()

        repo = UserRepoImple(database.getUserDao())
    }
}