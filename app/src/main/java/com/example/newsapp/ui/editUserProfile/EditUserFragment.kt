package com.example.newsapp.ui.editUserProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentEditUserBinding
import com.example.newsapp.ui.addEditNews.AddEditNewsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditUserFragment : Fragment() {
    private lateinit var binding: FragmentEditUserBinding
    private val editUserViewModel: EditUserViewModel by viewModels()
    private val args: EditUserFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserBinding.inflate(
            layoutInflater,
            container,
            false
        )

        editUserViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.etUsername.setText(it.userName)
                binding.etEmail.setText(it.email)
                binding.etPhoneNumber.setText(it.phoneNumber)
                binding.etPassword.setText(it.password)
            }
        }

        val userId = args.userId
        editUserViewModel.getUserById(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.updateUser = editUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupImagePicker()


        editUserViewModel.loggedInUser()

        lifecycleScope.launch {
            editUserViewModel.finish.collect {
                findNavController().popBackStack()
            }
        }

        editUserViewModel.snackbar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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
        startActivityForResult(intent, AddEditNewsFragment.REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddEditNewsFragment.REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                editUserViewModel.img.value = bytes
            }
            binding.imgGallery.setImageURI(selectedImageUri)
        }
    }

}