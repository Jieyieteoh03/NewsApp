package com.example.newsapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.db.NewsDatabase
import com.example.newsapp.data.repository.newsRepo.NewsRepo
import com.example.newsapp.data.repository.newsRepo.NewsRepoImple
import com.example.newsapp.data.repository.userRepo.UserRepo
import com.example.newsapp.data.repository.userRepo.UserRepoImple
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context, NewsDatabase::class.java, NewsDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsRepo(db: NewsDatabase): NewsRepo {
        return NewsRepoImple(db.getNewsDao())
    }

    @Provides
    @Singleton
    fun provideUserRepo(@ApplicationContext context: Context, db: NewsDatabase): UserRepo {
        return UserRepoImple(context, db.getUserDao())
    }

}