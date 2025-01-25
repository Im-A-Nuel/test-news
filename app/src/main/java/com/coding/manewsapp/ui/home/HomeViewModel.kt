package com.coding.manewsapp.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.coding.core.data.Resource
import com.coding.core.data.source.local.entity.NewsEntity
import com.coding.core.domain.usecase.NewsUseCase
import com.coding.core.utils.DataMapper
import kotlinx.coroutines.flow.map

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {

    val news: LiveData<Resource<List<NewsEntity>>> = newsUseCase.getAllNews()
        .map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val entities =
                        resource.data?.let { DataMapper.mapDomainToEntityList(it) } ?: emptyList()
                    Resource.Success(entities)
                }

                is Resource.Loading -> Resource.Loading()
                is Resource.Error -> Resource.Error("Error")
            }
        }
        .asLiveData()
}
