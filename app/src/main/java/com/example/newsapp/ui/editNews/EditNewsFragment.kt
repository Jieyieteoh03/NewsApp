package com.example.newsapp.ui.editNews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentEditNewsBinding
import com.example.newsapp.databinding.FragmentViewNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditNewsFragment : Fragment() {
    private lateinit var binding: FragmentEditNewsBinding
    private val viewModel: EditNewsViewModel by viewModels()
    private val args: EditNewsFragmentArgs by navArgs()
    val REQUEST_IMAGE_PICK = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNewsBinding.inflate(
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

        setupImagePicker()

        lifecycleScope.launch {
            viewModel.run {
                getNewsById(args.id)
                finish.collect{findNavController().popBackStack()}
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
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                viewModel.img.value = bytes
            }
            binding.imgGallery.setImageURI(selectedImageUri)
        }
    }

}