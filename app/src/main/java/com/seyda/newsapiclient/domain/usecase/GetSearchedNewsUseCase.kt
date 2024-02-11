package com.seyda.newsapiclient.domain.usecase

import com.seyda.newsapiclient.data.model.APIResponse
import com.seyda.newsapiclient.data.util.Resource
import com.seyda.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String,searchQuery:String,page:Int):Resource<APIResponse>{
        return newsRepository.getSearchedNews(country,searchQuery,page)
    }
}