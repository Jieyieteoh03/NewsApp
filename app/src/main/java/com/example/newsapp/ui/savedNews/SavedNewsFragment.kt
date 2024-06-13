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
import com.example.newsapp.data.model.news.News
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
    private var sortOrder: Categories = Categories.ALL_NEWS
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

        binding.ivSort.setOnClickListener { showSort() }
        viewModel.run {
            lifecycleScope.launch {
                getSavedNewsById()
                savedNews.observe(viewLifecycleOwner) {
                    it?.let {
                        adapter.setNews(it.savedNews)
                        sortNews()
                    }
                }
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
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = layoutManager
    }

    private fun showSort() {
        val sortDialog = AlertDialog.Builder(requireContext()).create()
        val alertBox = LayoutNewsSortBinding.inflate(layoutInflater)

        when (sortOrder) {
            Categories.HOT_NEWS -> alertBox.btnHot.isChecked = true
            Categories.NORMAL_NEWS -> alertBox.btnNormal.isChecked = true
            Categories.ALL_NEWS -> alertBox.btnAll.isChecked = true
        }
        alertBox.run {
            btnAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortOrder = Categories.ALL_NEWS
                }
            }
            btnHot.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortOrder = Categories.HOT_NEWS
                }
            }
            btnNormal.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sortOrder = Categories.NORMAL_NEWS
                }
            }
            btnConfirm.setOnClickListener {
                sortNews()
                sortDialog.dismiss()
            }
        }

        sortDialog.setView(alertBox.root)
        sortDialog.show()
    }

    private fun sortNews() {
        val data = viewModel.savedNews.value
        data?.let { list ->
            val savedNews = list.savedNews
            val sortedList = when (sortOrder) {
                Categories.HOT_NEWS -> savedNews.filter { it.categories == Categories.HOT_NEWS }
                Categories.NORMAL_NEWS -> savedNews.filter { it.categories == Categories.NORMAL_NEWS }
                Categories.ALL_NEWS -> savedNews
            }
            adapter.updateList(sortedList)
        }
    }
}