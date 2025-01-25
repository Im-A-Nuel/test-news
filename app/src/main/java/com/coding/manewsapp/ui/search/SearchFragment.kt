package com.coding.manewsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.ui.NewsAdapter
import com.coding.core.utils.DataMapper
import com.coding.manewsapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = NewsAdapter { news ->
            val action = SearchFragmentDirections.actionNavigationSearchToDetailFragment(news)
            findNavController().navigate(action)
        }

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        // Observe hasil pencarian
        searchViewModel.searchResults.observe(viewLifecycleOwner) { newsList ->
            val mappedNews = newsList.map { news ->
                DataMapper.mapDomainToEntity(news)
            }

            mappedNews.let {
                newsAdapter.submitList(it)
                binding.viewEmpty.root.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }

            binding.progressBar.visibility = View.GONE
        }

        // Tambahkan trigger pencarian awal
        val initialQuery = binding.edPlace.text.toString()
        if (initialQuery.isNotBlank()) {
            searchViewModel.setQuery(initialQuery)
        } else {
            // Tetapkan query default (misalnya untuk placeholder atau default search)
            searchViewModel.setQuery("default")
        }

        // Setup TextWatcher untuk input pencarian
        binding.edPlace.addTextChangedListener(object : TextWatcher {
            private var searchJob: Job? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    s?.toString()?.let { query ->
                        if (query.isNotBlank()) {
                            searchViewModel.setQuery(query) // Update query ke ViewModel
                        } else {
                            newsAdapter.submitList(emptyList())
                            binding.viewEmpty.root.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
