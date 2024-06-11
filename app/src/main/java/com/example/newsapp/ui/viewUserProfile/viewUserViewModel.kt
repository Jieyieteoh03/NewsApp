package com.example.newsapp.ui.viewUserProfile

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class viewUserViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {
    fun doLogout() = userRepo.logOut()
}