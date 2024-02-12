package com.seyda.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.seyda.newsapiclient.data.model.APIResponse
import com.seyda.newsapiclient.data.model.Article
import com.seyda.newsapiclient.data.util.Resource
import com.seyda.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.seyda.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.seyda.newsapiclient.domain.usecase.SaveNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.http.Query
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModel() {
    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    //val newsList = arrayListOf<Resource<APIResponse>>()
    //Inside this function we are going to execute a networking task.
    //So we need to do it using a separate thread
    fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {
            if (isInternetAvailable(app)) {
                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                //newsList.add(apiResult)
                newsHeadlines.postValue(apiResult)
            } else {
                newsHeadlines.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(country: String, searchQuery: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            searchedNews.postValue(Resource.Loading())
            try {
                if (isInternetAvailable(app)) {
                    val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                    searchedNews.postValue(response)
                } else {
                    searchedNews.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (e: Exception) {
                searchedNews.postValue(Resource.Error(e.message.toString()))
            }
        }

    //local data
    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)
    }
}