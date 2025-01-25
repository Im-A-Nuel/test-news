package com.coding.core.domain.usecase

import com.coding.core.data.Resource
import com.coding.core.domain.model.News
import com.coding.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow


class NewsInteractor(private val newsRepository: INewsRepository) : NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<News>>> = newsRepository.getAllNews()
    override fun getFavoriteNews(): Flow<List<News>> = newsRepository.getFavoriteNews()
    override fun setFavoriteNews(news: News, state: Boolean) =
        newsRepository.setFavoriteNews(news, state)

    override fun searchNews(query: String): Flow<List<News>> = newsRepository.searchNews(query)
}
