package com.coding.manewsapp.ui.di

import com.coding.core.domain.usecase.NewsInteractor
import com.coding.core.domain.usecase.NewsUseCase
import com.coding.manewsapp.ui.detail.DetailViewModel
import com.coding.manewsapp.ui.home.HomeViewModel
import com.coding.manewsapp.ui.search.SearchViewModel
import com.coding.manewsapp.ui.setting.SettingPreferences
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    single { SettingPreferences.getInstance(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}