package com.example.newsapp.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.news.News
import com.example.newsapp.R
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.data.repository.userRepo.UserRepo
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.HotAdapter
import com.example.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var hotAdapter: HotAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(
            layoutInflater, container, false
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
            lifecycleScope.launch(Dispatchers.Main) {
                val loggedIn = withContext(Dispatchers.IO) {
                    viewModel.loggedIn()
                }
                if (!loggedIn) {
                    findNavController().navigate(R.id.loginFragment)
                    return@launch
                }
            }
            viewModel.getUsersAndNews()

            lifecycleScope.launch {
                viewModel.run {
                    tvTitle.observe(viewLifecycleOwner) { query ->
                        binding.svWord.setQuery(query, false)
                    }
                    news.observe(viewLifecycleOwner) { newsList ->
                        filterNewsList(query = binding.svWord.query.toString(), newsList = newsList)
                    }
                    getAll().collect {
                        adapter.setNews(it)
                    }
                }
            }

            binding.run {
                btnAddNews.setOnClickListener {
                    findNavController().navigate(
                        ContainerFragmentDirections.actionContainerToAddEditNews("Add", 0)
                    )
                }
            }
        }

        viewModel.loggedInUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.tvUser.text = it.userName
                binding.tvImg.setImageBitmap(BitmapFactory.decodeByteArray(it.img, 0, it.img.size))
            }
        }
    }


    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = NewsAdapter(requireContext(), emptyList())
        adapter.listener = object : NewsAdapter.Listener {
            override fun onClick(id: Int) {
                val action = ContainerFragmentDirections.actionContainerToViewNews(id)
                findNavController().navigate(action)
            }
        }
        binding.run {
            rvNews.adapter = adapter
            rvNews.layoutManager = layoutManager
            svWord.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(query: String?): Boolean {
                    filterNewsList(query, viewModel.news.value ?: emptyList())
                    return true
                }
            })
            viewModel.fetchNewsData()
        }
    }

    fun filterNewsList(query: String?, newsList: List<News>) {
        val filteredList = if (query.isNullOrBlank()) {
            newsList
        } else {
            newsList.filter {
                it.title.contains(query, true) || it.description.contains(query, true)
            }
        }
        adapter.setNews(filteredList)
    }

    private fun setupHotAdapter() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        hotAdapter = HotAdapter(emptyList())
        hotAdapter.listener = object : HotAdapter.Listener, NewsAdapter.Listener {
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



