package com.coding.manewsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.core.data.Resource
import com.coding.core.ui.NewsAdapter
import com.coding.manewsapp.R
import com.coding.manewsapp.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = NewsAdapter { newsEntity ->
            val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(newsEntity)
            findNavController().navigate(action)
        }

        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        homeViewModel.news.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { newsAdapter.submitList(it) }
                    binding.progressBar.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
//                    binding.viewError.visibility = View.VISIBLE
//                    binding.viewError.textViewError.text = resource.message
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.viewError.root.visibility = View.GONE
    }

    private fun showError(message: String?) {
        binding.viewError.root.visibility = View.VISIBLE
        binding.viewError.tvError.text = message ?: getString(R.string.something_wrong)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
