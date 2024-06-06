package com.example.newsapp.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.data.model.user.User
import com.example.newsapp.databinding.FragmentEditUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditUserFragment : Fragment() {
    private lateinit var binding: FragmentEditUserBinding
    private val EditUserViewModel: EditUserViewModel by viewModels()
    private var userDetail = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditUserBinding.inflate(
            layoutInflater,
            container,
            false
        )

        EditUserViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            user.let {
                EditUserViewModel.setUser(it)
            }
        }

        arguments?.let {
            userDetail = EditUserFragmentArgs.fromBundle(it).id
            EditUserViewModel.getUserById(userDetail)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.updateUser = EditUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycleScope.launch {
            EditUserViewModel.finish.collect{
                Log.d("UserProfile", "Updated Successfully")
                findNavController().popBackStack()
            }
        }

        EditUserViewModel.snackbar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}