package com.coding.favorite.di

import com.coding.favorite.ui.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}

fun injectFavoriteModule() {
    loadKoinModules(favoriteModule)
}
