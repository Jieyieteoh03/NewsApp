package com.example.newsapp.ui.addEditNews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAddEditNewsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNewsFragment : Fragment() {
    private lateinit var binding: FragmentAddEditNewsBinding
    private val viewModel: AddEditNewsViewModel by viewModels()
    private val args: AddEditNewsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddEditNewsBinding.inflate(
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
        binding.btnAddEditNews.text = args.type
        binding.tvAddEditNews.text = getString(R.string.add_edit_news, args.type)
        binding.btnAddEditNews.setOnClickListener {
            if (args.type == "Edit") {
                viewModel.editNews()
            } else  {
                viewModel.submit()
            }

        }


        setupImagePicker()
        setupCategorySpinner()

        lifecycleScope.launch {
            viewModel.finish.collect{ findNavController().popBackStack()}
        }

        viewModel.run {
            Log.d("argsId", args.id.toString())
            if(args.type == "Edit") {
                getNewsById(args.id)
            }
            snackbar.observe(viewLifecycleOwner) {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupImagePicker() {
        binding.btnSelectImage.setOnClickListener {
            openImagePicker()
        }
    }


    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, Companion.REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                viewModel.img.value = bytes
            }
            binding.imgGallery.setImageURI(selectedImageUri)
        }
    }

    private fun setupCategorySpinner() {
        val categoryOptions = listOf("HOT_NEWS", "NORMAL_NEWS")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.etCategory.adapter = adapter

        binding.etCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                viewModel.categories.value = selectedCategory
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

    }

    companion object {
        const val REQUEST_IMAGE_PICK = 1
    }

}