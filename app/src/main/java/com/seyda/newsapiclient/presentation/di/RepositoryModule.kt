package com.seyda.newsapiclient.presentation.di

import com.seyda.newsapiclient.data.repository.NewsRepositoryImpl
import com.seyda.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.seyda.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.seyda.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
    }
}