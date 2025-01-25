package com.coding.manewsapp

import android.os.Bundle
import androidx.navigation.NavController
import com.coding.core.navigation.NavigationProvider
import com.coding.core.data.source.local.entity.NewsEntity

class AppNavigationProvider(private val navController: NavController) : NavigationProvider {
    override fun navigateToDetail(newsEntity: NewsEntity) {
        val bundle = Bundle().apply {
            putParcelable("newsEntity", newsEntity)
        }
        navController.navigate(R.id.detailFragment, bundle)
    }
}
