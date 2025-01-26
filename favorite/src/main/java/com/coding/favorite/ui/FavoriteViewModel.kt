package com.coding.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.coding.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {

    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()

}