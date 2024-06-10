package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.HotAdapter
import com.example.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels ()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var hotAdapter: HotAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupHotAdapter()

        viewModel.tvTitle.observe(viewLifecycleOwner) { query ->
            binding.svWord.setQuery(query, false)
        }

        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            filterAndSetNewsList(query = binding.svWord.query.toString(), newsList = newsList)
        }

        binding.btnAddNews.setOnClickListener{
            findNavController().navigate(
                ContainerFragmentDirections.actionContainerToAddNews()
            )
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = NewsAdapter(emptyList())
        adapter.listener = object: NewsAdapter.Listener {
            override fun onClick(id: Int) {
                val action = ContainerFragmentDirections.actionContainerToViewNews(id)
                findNavController().navigate(action)


            }
        }

        binding.svWord.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterAndSetNewsList(query, viewModel.news.value ?: emptyList())
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                filterAndSetNewsList(query, viewModel.news.value ?: emptyList())
                return true
            }
        })


        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = layoutManager

        viewModel.fetchNewsData()
    }


    private fun setupHotAdapter() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        hotAdapter = HotAdapter(emptyList())
        hotAdapter.listener = object: HotAdapter.Listener, NewsAdapter.Listener {
            override fun onClick(id: Int) {
                val action = ContainerFragmentDirections.actionContainerToViewNews(id)
                findNavController().navigate(action)


            }
        }

        binding.rvHotNews.adapter = hotAdapter
        binding.rvHotNews.layoutManager = layoutManager

    }

    private fun filterAndSetNewsList(query: String?, newsList: List<News>) {
        val filteredNewsList = if (query.isNullOrBlank()) {
            newsList
        } else {
            newsList.filter {
                it.title.contains(query, true) || it.description.contains(query, true)
            }
        }

        val hotNewsList = filteredNewsList.filter { it.categories == Categories.HOT_NEWS }
        val normalNewsList = filteredNewsList.filter { it.categories != Categories.HOT_NEWS }

        hotAdapter.setNews(hotNewsList)
        adapter.setNews(normalNewsList)
    }
}