package com.seyda.newsapiclient.data.repository.dataSource

import com.seyda.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.Query

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>

    suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse>
}