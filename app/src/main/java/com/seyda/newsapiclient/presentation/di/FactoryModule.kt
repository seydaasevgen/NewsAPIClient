package com.seyda.newsapiclient.presentation.di

import android.app.Application
import com.seyda.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.seyda.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.SaveNewsUseCase
import com.seyda.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            application,
            getNewsHeadlinesUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            deleteSavedNewsUseCase
        )
    }
}