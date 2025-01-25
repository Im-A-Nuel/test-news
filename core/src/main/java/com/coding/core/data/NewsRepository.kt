package com.coding.core.data


import com.coding.core.data.source.local.LocalDataSource
import com.coding.core.data.source.remote.RemoteDataSource
import com.coding.core.data.source.remote.network.ApiResponse
import com.coding.core.data.source.remote.response.ArticlesItem
import com.coding.core.domain.model.News
import com.coding.core.domain.repository.INewsRepository
import com.coding.core.utils.AppExecutors
import com.coding.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {

    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<ArticlesItem>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>> {
                return remoteDataSource.getAllNews()
            }

            override suspend fun saveCallResult(data: List<ArticlesItem>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }

            override fun shouldFetch(data: List<News>?): Boolean = data.isNullOrEmpty()
        }.asFlow()

    override fun getFavoriteNews(): Flow<List<News>> {
        return localDataSource.getFavoriteNews().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteNews(newsEntity, state)
        }
    }

    override fun searchNews(query: String): Flow<List<News>> {
        return localDataSource.searchNews(query).map { DataMapper.mapEntitiesToDomain(it) }
    }

}

