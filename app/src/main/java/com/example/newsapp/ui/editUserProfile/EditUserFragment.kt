package com.example.newsapp.ui.editUserProfile

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
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentEditUserBinding
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
            }
        }

        val userId = args.userId
        editUserViewModel.getUserById(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.updateUser = editUserViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        editUserViewModel.loggedInUser()

        binding.btnEditPsw.setOnClickListener {
            findNavController().navigate(
                EditUserFragmentDirections.actionEditUserFragmentToEditUserPasswordFragment(editUserViewModel.getLoggedInUser()!!)
            )
        }

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

}