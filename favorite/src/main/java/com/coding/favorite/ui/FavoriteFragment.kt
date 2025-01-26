package com.coding.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.navigation.NavigationProvider
import com.coding.core.ui.NewsAdapter
import com.coding.core.utils.DataMapper
import com.coding.favorite.di.favoriteModule
import com.coding.favorite_feature.databinding.FragmentFavoriteBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()


    // Inject NavigationProvider
    private val navigationProvider: NavigationProvider by inject { parametersOf(findNavController()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        startKoin {
            modules(favoriteModule)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = NewsAdapter { newsEntity ->
            // Gunakan NavigationProvider untuk navigasi
            navigationProvider.navigateToDetail(newsEntity)
        }

        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        // Observe data dari ViewModel
        favoriteViewModel.favoriteNews.observe(viewLifecycleOwner) { dataNews ->
            val mappedNews = dataNews?.let { DataMapper.mapDomainToEntityList(it) }
            newsAdapter.submitList(mappedNews)

            binding.viewEmpty.root.visibility =
                if (mappedNews.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}