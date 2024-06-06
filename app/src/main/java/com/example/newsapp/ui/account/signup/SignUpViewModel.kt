package com.example.newsapp.ui.account.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.user.Role
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.EncryptionHelper
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirm_password: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val snackbar: MutableLiveData<String> = MutableLiveData()

//    private val key = EncryptionHelper.generateKey()

    fun register() {
        val userName = userName.value
        val email = email.value
        val phoneNumber = phoneNumber.value
        val password = password.value
        val confirmPassword = confirm_password.value

        if (userName.isNullOrEmpty() || email.isNullOrEmpty() || phoneNumber.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
            snackbar.value = "Please fill in all fields"
            return
        }

        if (confirmPassword != password) {
            snackbar.value = "Confirm Password does not match your Password"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val encryptedPassword = EncryptionHelper.encrypt(password)
                val user = User(
                    userName = userName,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password
//                    encryptedPassword = encryptedPassword
                )
                repo.addUser(user)
                withContext(Dispatchers.Main) {
                    snackbar.value = "User added Successfully"
                    finish.emit(Unit)
                }
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "Error adding user", e)
            }
        }
    }


}