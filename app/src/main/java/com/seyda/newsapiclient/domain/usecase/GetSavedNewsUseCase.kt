package com.seyda.newsapiclient.domain.usecase

import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}