package com.example.newsapp.ui.account.login

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {

}