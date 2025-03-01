package com.coding.manewsapp.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.coding.core.data.Resource
import com.coding.core.domain.model.News
import com.coding.core.domain.usecase.NewsUseCase
import kotlinx.coroutines.flow.map

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {

    val news: LiveData<Resource<List<News>>> = newsUseCase.getAllNews()
        .map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val domainList = resource.data ?: emptyList()
                    Resource.Success(domainList)
                }

                is Resource.Loading -> Resource.Loading()
                is Resource.Error -> Resource.Error(resource.message ?: "Error")
            }
        }
        .asLiveData()
}

