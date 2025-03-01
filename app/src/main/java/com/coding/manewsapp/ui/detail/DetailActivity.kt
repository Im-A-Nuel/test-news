package com.coding.manewsapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.core.domain.model.News
import com.coding.core.utils.DateFormatter
import com.coding.manewsapp.R
import com.coding.manewsapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.TimeZone

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val news = intent.getParcelableExtra<News>(NEWS) ?: return

        binding.newsTitle.text = news.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.newsDate.text =
                DateFormatter.formatDate(news.publishedAt, TimeZone.getDefault().id)
        }
        binding.newsDescription.text = news.description
        Glide.with(this)
            .load(news.urlToImage)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.newsImage)

        var statusFavorite = news.isFavorite
        setStatusFavorite(statusFavorite)

        val isFavoriteModuleAvailable = checkFavoriteModule()

        binding.btnFavorite.setOnClickListener {
            if (isFavoriteModuleAvailable) {
                statusFavorite = !statusFavorite
                detailViewModel.setFavoriteNews(news, statusFavorite)
                setStatusFavorite(statusFavorite)
            } else {
                Toast.makeText(this, getString(R.string.modulnotfound), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnOpenUrl.setOnClickListener {
            openUrlInBrowser(news.url.toString())
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun checkFavoriteModule(): Boolean {
        return try {
            Class.forName("com.coding.favorite.ui.FavoriteActivity")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.favorite_icon_full
                )
            )
        } else {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.favorite_icon_border
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val NEWS = "news"
    }
}
