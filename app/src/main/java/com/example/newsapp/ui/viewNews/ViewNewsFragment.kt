package com.example.newsapp.ui.viewNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentViewNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewNewsFragment : Fragment() {
    private lateinit var binding: FragmentViewNewsBinding
    private val viewModel: ViewNewsViewModel by viewModels ()
    private val args: ViewNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.run {
            getNewsById(args.id)
            news.observe(viewLifecycleOwner){
                setNews()
            }
            lifecycleScope.launch {
                viewModel.finish.collect{
                    findNavController().popBackStack(
                        findNavController().graph.startDestinationId,false
                    )
                }
            }

            binding.btnSaveNews.setOnClickListener {
                savedNews()
            }

            binding.btnAddComment.setOnClickListener {

            }

            binding.btnDeleteNews.setOnClickListener {
                    deleteNews()
            }

            binding.btnEditNews.setOnClickListener {
                findNavController().navigate(
                    ViewNewsFragmentDirections.actionViewNewsToAddEditNews("Edit", args.id)
                )
            }

        }
    }
}