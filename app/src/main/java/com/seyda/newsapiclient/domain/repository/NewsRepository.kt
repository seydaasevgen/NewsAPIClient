package com.seyda.newsapiclient.domain.repository

import com.seyda.newsapiclient.data.model.APIResponse
import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String,searchQuery: String,page: Int): Resource<APIResponse>

    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

    //Flow API in Kotlin is a better way to handle the stream of data asynchronously
    //Room library allows us to get the data as a flow
    //In the view Model class , we will collect this stream of data flow and emit it as a live data.
    //Since this function returns a data stream, we don't need to write this function as a suspending function
}