package com.example.newsapp.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.userRepo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    private val _user : MutableLiveData<User> = MutableLiveData()
    val userDetails: LiveData<User> = _user
    val userName: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val snackbar: MutableLiveData<String> = MutableLiveData()

    fun getUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(repo.getUserById(id))
        }
    }

    fun setUser(user: User) {
        user?.let {
            userName.value = it.userName
            email.value = it.email
            phoneNumber.value = it.phoneNumber
            password.value = it.password
        }
    }

    fun updateProfile() {
        if (userName.value.isNullOrEmpty() || email.value.isNullOrEmpty() || phoneNumber.value.isNullOrEmpty() || password.value.isNullOrEmpty() || confirmPassword.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in all fields"
            return
        }

        if (confirmPassword != password) {
            snackbar.value = "CConfirm Password does not match your Password"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val user = userDetails.value
            if (user != null) {
                user.userId?.let {
                    repo.updateUser(
                        it,
                        user.copy(
                            userName = userName.value!!,
                            email = email.value!!,
                            phoneNumber = phoneNumber.value!!,
                            password = password.value!!,
//                            confirmPassword = confirmPassword.value!!
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    snackbar.value = "User details updates Succesfully"
                }
            }
            finish.emit(Unit)
        }
        Log.d("debugging", "${userName.value}, ${email.value}, ${phoneNumber.value}, ${password.value}")
    }
}