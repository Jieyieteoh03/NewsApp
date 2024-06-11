package com.example.newsapp.ui.viewUserProfile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.data.model.user.User
import com.example.newsapp.databinding.FragmentUserDetailBinding
import com.example.newsapp.ui.home.HomeFragmentDirections
import com.example.newsapp.ui.viewNews.ViewNewsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )

        viewModel.userDetails.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.etUsername.text = it.userName
                binding.etEmail.text = it.email
                binding.etPhoneNumber.text = it.phoneNumber
//                binding.etPassword.setText(it.password)
            }
            Log.d("user", user.email)
        }

        val userId = args.id
        viewModel.getUserById(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewUserDetail = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loggedInUser()

        viewModel.run {
            getUserById(args.id)
            Log.d("argument", args.id.toString())
            userDetails.observe(viewLifecycleOwner){
                setUser()
            }

            binding.btnEditUser.setOnClickListener {
                findNavController().navigate(
                    UserDetailFragmentDirections.actionUserDetailFragmentToEditUserFragment(userId = id)
                )
            }
        }
    }

}