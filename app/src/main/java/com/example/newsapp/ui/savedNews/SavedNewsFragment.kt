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
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.databinding.LayoutNewsSortBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import com.example.newsapp.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: SavedNewsViewModel by viewModels()
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

    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
//        val dummyNewsData = generateDummyNews(10)
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

//    private fun generateDummyNews(count: Int): List<News> {
//        val newsList = mutableListOf<News>()
//        val dummyImageBase64 = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAnJJREFUeNqkU0tIVGEYftn/TU1qTWohBkJX3XTRQVFECCKktHVbBEXQRUUE7rrpIjCwPxQURQTdhYuMViVmhYFSc5XN1Ezm65kXXzznfe8UbcK8vdzfc+D5Hb7nDETEf8oYA2ZmjeMilmiWaKFZ4tLBfwRyPcm2x5nKxQwZTsj4yCyzQWBuEGwCUHK2AczMAM4AmAUsAlB0zOXA01ks3nmK6oUBNN7tRj4QxP0f/WhzaDjSWoGxeAr7KDSoB6tRXUrhVLUHcdfN9vDETqzxZbDFVcYuXOYqAF0AnmRyuDdqRNNvD/zD2GEyQEIBODQNjQEvBlxhYGIWCT2PGzEjnjsDaHXVQv1cwt3oeRxpqkSlp5w4IqVSYFbpaMj84YrUY/DXNG6N78Zbnfub6oP4SWDC5MHyoBNJyTYX+gWdJRcKy3y0u9rG1yS/eD/iLCFxczQyESt8RXTQ3f+AVuCjpCCZ0Xrwhiy8qFyBWZzDQNIrIWd0ZuMAe+7yvYfB1/qc0fBOYnvSJWN+YaMxYBEgoaCx1GQAcwAnrSfAxDLVxzDbIx47aQB26u15s39kfAiJN9kd6ZEDChfS2kZDAO8+YBBgJ1WMp842HEtPhQu+QDDZJ1B15zlqjSwmoxaS4wdMWE+O2Mn19wwG5AUMOLv4Fkd6xiBqXIyoAd5Sm9OjxJwNu1x98DhCGGJrIJ87ZAIwuNjeBVvQQ+dJmkV5oGnLVV8KXWDDOcD5mKT1kKtAz2Jz0ktXFZGgCUe4a5rL2WQcxQ69Rquf6mTZ8sQ0HV+iyQaaDTFRPHZAthpgpmugRSWqOT2YHqI8Wlr+GZFsWTBZnz5n/FbvlUH+kv8KMAADw5oqtZkHpQAAAABJRU5ErkJggg=="
//
//        for (i in 1..count) {
//            val rand = (1..10).random()
//            val card = News(
//                id = rand,
//                img = dummyImageBase64.decodeBase64(),
//                title = "Title $rand",
//                description = "Description $rand",
//                tags = "Tag $rand",
//                source = "Source $rand"
//            )
//            newsList.add(card)
//        }
//        return newsList
//    }

//    @OptIn(ExperimentalEncodingApi::class)
//    private fun String.decodeBase64(): ByteArray {
//        return Base64.decode(this, CoroutineStart.DEFAULT)
//    }


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
            viewModel.getAll().collect { newsList ->
                val sortedList = when (sortOrder) {
                    Categories.HOT_NEWS -> newsList.filter { it.categories == Categories.HOT_NEWS }
                    Categories.NORMAL_NEWS -> newsList.filter { it.categories == Categories.NORMAL_NEWS }
                }
                adapter.updateList(sortedList)
            }
        }
    }
}