package com.coding.manewsapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.ui.NewsAdapter
import com.coding.manewsapp.databinding.FragmentSearchBinding
import com.coding.manewsapp.ui.detail.DetailActivity
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
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("news", news)
            startActivity(intent)
        }

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        searchViewModel.searchResults.observe(viewLifecycleOwner) { newsList ->

            newsList.let {
                newsAdapter.submitList(it)
                binding.viewEmpty.root.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }

            binding.progressBar.visibility = View.GONE
        }

        val initialQuery = binding.edPlace.text.toString()
        if (initialQuery.isNotBlank()) {
            searchViewModel.setQuery(initialQuery)
        } else {
            searchViewModel.setQuery("default")
        }

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
