package com.coding.manewsapp

import android.app.Application
import com.coding.core.di.databaseModule
import com.coding.core.di.networkModule
import com.coding.core.di.repositoryModule
import com.coding.manewsapp.ui.di.useCaseModule
import com.coding.manewsapp.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}