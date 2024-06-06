package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.data.model.News
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels ()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
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



//        binding.btnFabAdd.setOnClickListener{
//
//        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dummyNewsData = generateDummyNews(10)
        adapter = NewsAdapter(dummyNewsData)
        adapter.listener = object: NewsAdapter.Listener {
            override fun onClick(id: Int) {
                val action = ContainerFragmentDirections.actionContainerToViewNews(id)
                findNavController().navigate(action)


            }
        }
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = layoutManager
    }

    private fun generateDummyNews(count:Int): List<News> {
        val newsList = mutableListOf<News>()
        for (i in 1..count) {
            val rand = (1..10).random()
            val card = News(
                id = rand,
                title = "Title $rand",
                description = "Description $rand",
                tags = "Tag $rand",
//                categories = listOf("Category $rand"),
                source = "Source $rand"
            )
            newsList.add(card)
        }
        return newsList
    }
}