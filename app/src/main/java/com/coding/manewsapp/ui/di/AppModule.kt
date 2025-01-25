package com.coding.manewsapp.ui.di

import androidx.navigation.NavController
import com.coding.core.domain.usecase.NewsInteractor
import com.coding.core.domain.usecase.NewsUseCase
import com.coding.core.navigation.NavigationProvider
import com.coding.manewsapp.ui.detail.DetailViewModel
import com.coding.favorite_feature.ui.favorite.FavoriteViewModel
import com.coding.manewsapp.AppNavigationProvider
import com.coding.manewsapp.ui.home.HomeViewModel
import com.coding.manewsapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

val appModule = module {
    factory<NavigationProvider> { (navController: NavController) ->
        AppNavigationProvider(navController)
    }
}