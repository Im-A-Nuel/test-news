package com.coding.core.utils

import com.coding.core.data.source.local.entity.NewsEntity
import com.coding.core.data.source.remote.response.ArticlesItem
import com.coding.core.domain.model.News

object DataMapper {

    fun mapResponsesToEntities(input: List<ArticlesItem>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val newsEntity = NewsEntity(
                title = it.title,
                publishedAt = it.publishedAt,
                urlToImage = it.urlToImage,
                description = it.description,
                url = it.url,
                isFavorite = false
            )
            newsList.add(newsEntity)
        }
        return newsList
    }


    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                title = it.title,
                publishedAt = it.publishedAt,
                urlToImage = it.urlToImage,
                description = it.description,
                url = it.url,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        title = input.title,
        publishedAt = input.publishedAt,
        urlToImage = input.urlToImage,
        description = input.description,
        url = input.url,
        isFavorite = input.isFavorite
    )

}
