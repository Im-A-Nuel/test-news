package com.coding.core.domain.usecase

import com.coding.core.data.Resource
import com.coding.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>
    fun getFavoriteNews(): Flow<List<News>>
    fun setFavoriteNews(tourism: News, state: Boolean)
    fun searchNews(query: String): Flow<List<News>>
}