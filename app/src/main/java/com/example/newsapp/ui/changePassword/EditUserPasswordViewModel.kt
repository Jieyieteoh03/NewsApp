package com.example.newsapp.ui.changePassword

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.inject.Inject

@HiltViewModel
class EditUserPasswordViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val userDetails: LiveData<User> = _user
    val oldPassword: MutableLiveData<String> = MutableLiveData()
    val newPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val snackbar: MutableLiveData<String> = MutableLiveData()


    fun loggedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repo.getLoggedInUser()
            if (userId != null) {
                val user = repo.getUserById(userId)
                user?.let {
                    withContext(Dispatchers.Main) {
                        _user.postValue(it)
                    }
                }
            }
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.getUserById(id)
            user?.let {
                withContext(Dispatchers.Main) {
                    _user.postValue(it)
                }
            }
        }
    }

    fun changePassword() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.changePsw(oldPassword.value!!)
            if (user != null) {
                updatePassword()
            } else {
                withContext(Dispatchers.Main) {
                    snackbar.postValue("Incorrect old password")
                }
            }
        }
    }


    fun updatePassword() {
        if (oldPassword.value.isNullOrEmpty() || newPassword.value.isNullOrEmpty() || confirmPassword.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in all fields"
            return
        }
        if (confirmPassword.value != newPassword.value) {
            snackbar.postValue("Confirm Password does not match your Password")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val userId = repo.getLoggedInUser()
            val bcrypt = BCryptPasswordEncoder()
            if (userId != null) {
                val user = repo.getUserById(userId)
                user?.let {
                    val updatePassword = it.copy(
                        password = bcrypt.encode(confirmPassword.value)!!
                    )
                    repo.updateUser(userId, updatePassword)
                    withContext(Dispatchers.Main) {
                        snackbar.value = "User details updated successfully"
                    }
                    finish.emit(Unit)
                }
            }
        }
    }

}