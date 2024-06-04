package com.example.newsapp.ui.account.login

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val snackbar: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()


    fun userLogin(view: View) {
        if (email.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in Email Address"
            return
        } else if (password.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in Password"
        }

        viewModelScope.launch(Dispatchers.IO) {
            val login = repo.verifyLoginUser(email.value!!, password.value!!)

//            login.
        }
    }

}
