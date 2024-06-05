package com.example.newsapp.ui.add

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.news.Categories
import com.example.newsapp.databinding.FragmentAddNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class AddNewsFragment : Fragment() {
    private lateinit var binding: FragmentAddNewsBinding
    private val addViewModel: AddNewViewModel by viewModels()
    val REQUEST_IMAGE_PICK = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddNewsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addViewModel = addViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        lifecycleScope.launch {
            addViewModel.finish.collect {

                findNavController().popBackStack()
            }
        }
        setupImagePicker()
        setupCategorySpinner()
    }

    // upload image

    private fun setupImagePicker() {
        binding.btnSelectImage.setOnClickListener {
            openImagePicker()
        }
    }

    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                addViewModel.img.value = bytes
            }
            binding.imgGallery.setImageURI(selectedImageUri)
        }
    }

    // category
    private fun setupCategorySpinner() {
        val categoryOptions = listOf("HOT_NEWS", "NORMAL_NEWS")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.etCategory.adapter = adapter

        binding.etCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                addViewModel.categories.value = selectedCategory
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }



}
