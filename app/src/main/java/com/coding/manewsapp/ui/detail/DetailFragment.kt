package com.coding.manewsapp.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.coding.core.utils.DataMapper
import com.coding.core.utils.DateFormatter
import com.coding.manewsapp.R
import com.coding.manewsapp.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.TimeZone

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = DetailFragmentArgs.fromBundle(requireArguments()).news

        binding.newsTitle.text = news.title
        binding.newsDate.text = DateFormatter.formatDate(news.publishedAt, TimeZone.getDefault().id)
        binding.newsDescription.text = news.description
        Glide.with(this)
            .load(news.urlToImage)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.newsImage)

        var statusFavorite = news.isFavorite
        setStatusFavorite(statusFavorite)

        binding.btnFavorite.setOnClickListener {
            val newsEntity = DataMapper.mapEntityToDomain(news)

            statusFavorite = !statusFavorite

            detailViewModel.setFavoriteNews(newsEntity, statusFavorite)

            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.favorite_icon_full
                )
            )
        } else {
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.favorite_icon_border
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
