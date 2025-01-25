package com.coding.core.navigation

import com.coding.core.data.source.local.entity.NewsEntity

interface NavigationProvider {
    fun navigateToDetail(newsEntity: NewsEntity)
}
