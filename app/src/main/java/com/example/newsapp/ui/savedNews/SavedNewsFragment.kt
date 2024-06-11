package com.example.newsapp.ui.savedNews

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.databinding.LayoutNewsSortBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.ui.viewNews.ViewNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: ViewNewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter
    private var sortOrder: Categories = Categories.NORMAL_NEWS
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        sortNews(sortOrder)

        binding.ivSort.setOnClickListener { showSort() }

        lifecycleScope.launch {
            viewModel.run {

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
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = layoutManager
    }

    private fun showSort() {
        val sortDialog = AlertDialog.Builder(requireContext()).create()
        val alertBox = LayoutNewsSortBinding.inflate(layoutInflater)

        alertBox.run {
            mrbAsc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortOrder = Categories.HOT_NEWS
                }
            }
            mrbDesc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortOrder = Categories.NORMAL_NEWS
                }
            }
            btnConfirm.setOnClickListener {
                sortNews(sortOrder)
                sortDialog.dismiss()
            }
        }

        sortDialog.setView(alertBox.root)
        sortDialog.show()
    }

    private fun sortNews(sortOrder: Categories) {
        lifecycleScope.launch {
            viewModel.getSavedNews().collect { newsList ->
                val sortedList = when (sortOrder) {
                    Categories.HOT_NEWS -> newsList.filter { it.categories == Categories.HOT_NEWS }
                    Categories.NORMAL_NEWS -> newsList.filter { it.categories == Categories.NORMAL_NEWS }
                }
                adapter.updateList(sortedList)
            }
        }
    }
}