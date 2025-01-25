package com.coding.core.domain.repository

import com.coding.core.data.Resource
import com.coding.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository{

    fun getAllNews(): Flow<Resource<List<News>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(tourism: News, state: Boolean)

    fun searchNews(query: String): Flow<List<News>>

}