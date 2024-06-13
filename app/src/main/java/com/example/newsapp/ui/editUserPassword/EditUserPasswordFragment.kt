package com.example.newsapp.ui.editUserPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentEditUserPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditUserPasswordFragment : Fragment() {
    private lateinit var binding: FragmentEditUserPasswordBinding
    private val viewModel: EditUserPasswordViewModel by viewModels()
    private val args: EditUserPasswordFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditUserPasswordBinding.inflate(layoutInflater, container, false)

        val userId = args.id
        viewModel.getUserById(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updatePassword = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loggedInUser()

        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack()
            }
        }

        viewModel.snackbar.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}