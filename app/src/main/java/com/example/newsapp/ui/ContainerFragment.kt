package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentContainerBinding
import com.example.newsapp.ui.adapter.TabAdapter
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.ui.savedNews.SavedNewsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContainerBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            vpTabs.adapter = TabAdapter(
                this@ContainerFragment,
                listOf(HomeFragment(),SavedNewsFragment(), )
            )
        }

        TabLayoutMediator(binding.tlTabs, binding.vpTabs) { tab, position ->
            when (position) {
                0 -> tab.text = "News"
                else -> tab.text = "Saved News"
            }
        }.attach()
    }


}