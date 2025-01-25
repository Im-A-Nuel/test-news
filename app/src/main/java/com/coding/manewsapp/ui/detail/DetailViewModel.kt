package com.coding.manewsapp.ui.detail

import androidx.lifecycle.ViewModel
import com.coding.core.domain.model.News
import com.coding.core.domain.usecase.NewsUseCase

class DetailViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    fun setFavoriteNews(news: News, newStatus: Boolean) =
        newsUseCase.setFavoriteNews(news, newStatus)

}