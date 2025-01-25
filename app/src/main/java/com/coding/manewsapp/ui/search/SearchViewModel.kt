package com.coding.manewsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.coding.core.domain.model.News
import com.coding.core.domain.usecase.NewsUseCase


class SearchViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val searchResults: LiveData<List<News>> = _query.switchMap { query ->
        liveData {
            emitSource(newsUseCase.searchNews(query).asLiveData())
        }
    }

    fun setQuery(query: String) {
        if (_query.value != query) {
            _query.value = query
        }
    }
}
