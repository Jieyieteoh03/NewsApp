package com.example.newsapp.ui.viewUserProfile

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentViewUserBinding
import com.example.newsapp.ui.ContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewUserFragment : Fragment() {
    private lateinit var binding:FragmentViewUserBinding
    private val viewModel: ViewUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewUserBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {
            user.observe(viewLifecycleOwner) { user ->
                user?.let {
                    binding.etUsername.text = it.userName
                    binding.etEmail. text = it.email
                    binding.etPhoneNumber. text = it.phoneNumber
                    setImageValue(it.img)
                }
            }
            img.observe(viewLifecycleOwner) {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                binding.imgGallery.setImageBitmap(bitmap)
            }
            getUserById()
        }

        binding.run {
            btnEditUser.setOnClickListener {
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerToEditUser(id)
                )
            }
            btnLogout.setOnClickListener {
                viewModel.doLogout()
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerToLogin()
                )
            }
        }
    }
}