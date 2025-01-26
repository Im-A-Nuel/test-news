package com.coding.manewsapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.navigation.NavigationProvider
import com.coding.core.ui.NewsAdapter
import com.coding.core.utils.DataMapper
import com.coding.favorite.di.favoriteModule
import com.coding.favorite.ui.FavoriteViewModel
import com.coding.favorite_feature.databinding.ActivityFavoriteBinding
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    // Inject NavigationProvider
//    private val navigationProvider: NavigationProvider by org.koin.core.component.inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@FavoriteActivity)
            modules(listOf(favoriteModule))
        }

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsAdapter = NewsAdapter { newsEntity ->
            // Gunakan NavigationProvider untuk navigasi
//            navigationProvider.navigateToDetail(newsEntity)
        }

        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(this)

        // Observe data dari ViewModel
        favoriteViewModel.favoriteNews.observe(this) { dataNews ->
            val mappedNews = dataNews?.let { DataMapper.mapDomainToEntityList(it) }
            newsAdapter.submitList(mappedNews)

            binding.viewEmpty.root.visibility =
                if (mappedNews.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }
}
