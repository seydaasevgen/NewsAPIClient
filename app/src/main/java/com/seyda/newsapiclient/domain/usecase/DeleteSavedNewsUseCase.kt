package com.seyda.newsapiclient.domain.usecase

import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}