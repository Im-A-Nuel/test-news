package com.coding.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.ui.NewsAdapter
import com.coding.favorite.di.injectFavoriteModule
import com.coding.favorite_feature.databinding.ActivityFavoriteBinding
import com.coding.manewsapp.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class FavoriteActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectFavoriteModule()

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        val newsAdapter = NewsAdapter { news ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("news", news)
            startActivity(intent)
        }

        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(this)

        binding.progressBar.visibility = View.VISIBLE // Show progress bar before data loading

        favoriteViewModel.favoriteNews.observe(this) { dataNews ->
            newsAdapter.submitList(dataNews)

            binding.viewEmpty.root.visibility =
                if (dataNews.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
