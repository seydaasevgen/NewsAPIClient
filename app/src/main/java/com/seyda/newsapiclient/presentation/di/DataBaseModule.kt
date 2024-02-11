package com.seyda.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.seyda.newsapiclient.data.db.ArticleDAO
import com.seyda.newsapiclient.data.db.ArticleDatabase
import com.seyda.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.seyda.newsapiclient.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDao()
    }
}