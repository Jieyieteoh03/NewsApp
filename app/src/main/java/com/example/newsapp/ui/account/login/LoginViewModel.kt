package com.example.newsapp.ui.account.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.EncryptionHelper
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
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

    fun isLoggedIn() = repo.isLoggedIn()

    fun userLogin() {
        if (email.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in Email Address"
            return
        }
        if (password.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in Password"
            return
        }


        viewModelScope.launch(Dispatchers.IO) {
            val login = repo.verifyLoginUser(email.value!!, password.value!!)
            Log.d("", login.toString())

//            val user = login.first()
            if (login != null) {
                finish.emit(Unit)
            } else {
                snackbar.postValue("Incorrect email or passwowrd ")
            }
        }
    }

}
