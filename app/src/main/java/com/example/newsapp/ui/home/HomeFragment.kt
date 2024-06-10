package com.example.newsapp.ui.home

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
import com.example.newsapp.data.model.news.News
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels ()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
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
        lifecycleScope.launch(Dispatchers.Main) {
            val loggedIn = withContext(Dispatchers.IO) {
                viewModel.loggedIn()
            }
            if(!loggedIn) {
                findNavController().navigate(R.id.loginFragment)
                return@launch
            }
        }
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
            btnEditUser.setOnClickListener {
//                viewModel.logOut()
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerToEditUser()
                )
            }
            btnLogout.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.logOut()
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        }
    }

        private fun setupAdapter() {
            val layoutManager = LinearLayoutManager(requireContext())
            adapter = NewsAdapter(emptyList())
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

        private fun filterNewsList(query: String?, newsList: List<News>) {
            val filteredList = if (query.isNullOrBlank()) {
                newsList
            } else {
                newsList.filter {
                    it.title.contains(query, true)
                            || it.description.contains(query, true)
                }
            }
            adapter.setNews(filteredList)
        }
    }