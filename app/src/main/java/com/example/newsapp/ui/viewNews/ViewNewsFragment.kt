package com.example.newsapp.ui.viewNews

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.user.Role
import com.example.newsapp.databinding.AlertSavedNewsBinding
import com.example.newsapp.databinding.FragmentViewNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ViewNewsFragment : Fragment() {
    private lateinit var binding: FragmentViewNewsBinding
    private val viewModel: ViewNewsViewModel by viewModels ()
    private val args: ViewNewsFragmentArgs by navArgs()
    private var saveMessage: String = ""

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
            news.observe(viewLifecycleOwner) {
                setNews()
                it?.let {
                    val image = File(it.img ?: "")
                    if(image.exists()) {
                        Glide.with(binding.ivImage.context)
                            .load(image)
                            .into(binding.ivImage)
                    }
                    Log.d("btn_debugging", it.isSaved.toString())
                    binding.btnSaveNews.icon = ContextCompat.getDrawable(
                        requireContext(),
                        if(it.isSaved) R.drawable.ic_ribbon_filled else R.drawable.ic_ribbon
                    )
                    saveMessage = "Do you want to ${if(it.isSaved) "unsave" else "save"} this news?"
                }

                loggedInUser.observe(viewLifecycleOwner) { user ->
                    user?.let {
                        val isAdmin = user.role == Role.ADMIN
                        binding.btnDeleteNews.isInvisible = !(isAdmin)
                        binding.btnEditNews.isInvisible = !(isAdmin)
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.finish.collect{ findNavController().popBackStack() }
            }

            binding.btnSaveNews.setOnClickListener {
                showAlert(saveMessage)
            }

            binding.btnDeleteNews.setOnClickListener {
                   showAlert("Do you want to delete this news?")
            }

            binding.btnEditNews.setOnClickListener {
                findNavController().navigate(
                    ViewNewsFragmentDirections.actionViewNewsToAddEditNews("Edit", args.id)
                )
            }

        }
    }

    private fun showAlert(type: String) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val alertBox = AlertSavedNewsBinding.inflate(layoutInflater)

        alertBox.run {
            tvBody.text = getString(R.string.alert_ques, type)
            btnNo.setOnClickListener { alertDialog.dismiss() }
            btnYes.setOnClickListener {
                if(type.contains("delete")) {
                    viewModel.deleteNews()
                } else if(type.contains("unsave") || type.contains("save")) {
                    viewModel.addEditSavedNews()
                    findNavController().popBackStack()
                }
                alertDialog.dismiss()
            }
        }
        alertDialog.setView(alertBox.root)
        alertDialog.show()
    }
}