package com.coding.favorite_feature.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.coding.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase) : ViewModel() {

    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()

}