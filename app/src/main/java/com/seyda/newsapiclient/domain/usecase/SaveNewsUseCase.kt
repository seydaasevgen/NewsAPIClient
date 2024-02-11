package com.seyda.newsapiclient.domain.usecase

import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.domain.repository.NewsRepository

//Let's add a reference to the repository as a constructor parameter
class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}