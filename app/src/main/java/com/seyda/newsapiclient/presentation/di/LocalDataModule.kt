package com.seyda.newsapiclient.presentation.di

import com.seyda.newsapiclient.data.db.ArticleDAO
import com.seyda.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.seyda.newsapiclient.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        articleDAO: ArticleDAO
    ): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}