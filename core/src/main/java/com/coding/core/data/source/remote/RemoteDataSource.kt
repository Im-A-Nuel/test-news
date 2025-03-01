package com.coding.core.data.source.remote

import android.util.Log
import com.coding.core.data.source.remote.network.ApiResponse
import com.coding.core.data.source.remote.network.ApiService
import com.coding.core.data.source.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllNews(): Flow<ApiResponse<List<ArticlesItem>>> {
        return flow {
            try {
                val response = apiService.getNews(API_KEY)
                val articles = response.articles
                if (articles.isNotEmpty()) {
                    emit(ApiResponse.Success(articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchNews(newsTitle: String): Flow<ApiResponse<List<ArticlesItem>>> {
        return flow {
            try {
                val response = apiService.searchNews(query = newsTitle, apiKey = API_KEY)
                val articles = response.articles
                if (articles.isNotEmpty()) {
                    emit(ApiResponse.Success(articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Error fetching news: ${e.message}", e)
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val API_KEY = ""
    }
}

