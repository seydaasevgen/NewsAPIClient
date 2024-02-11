package com.seyda.newsapiclient.domain.usecase

import com.seyda.newsapiclient.data.model.APIResponse
import com.seyda.newsapiclient.data.util.Resource
import com.seyda.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadlines(country,page)
    }
}