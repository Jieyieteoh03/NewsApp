package com.example.newsapp.ui.editUserProfile

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
    private val userRepo: UserRepo
): ViewModel() {
    private val _user: MutableLiveData<User> = MutableLiveData()
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val userDetails: LiveData<User> = _user
    val userName: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val snackbar: MutableLiveData<String> = MutableLiveData()

    fun loggedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepo.getLoggedInUser()
            if (userId != null) {
                val user = userRepo.getUserById(userId)
                user?.let {
                    withContext(Dispatchers.Main) {
                        setUser(it)
                    }
                }
            }
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepo.getUserById(id)
            user?.let {
                withContext(Dispatchers.Main) {
                    _user.value = it
                }
            }
        }
    }

    private fun setUser(user: User) {
        userName.value = user.userName
        email.value = user.email
        phoneNumber.value = user.phoneNumber
        img.value = user.img
    }

    fun updateProfile() {
        if (userName.value.isNullOrEmpty() ||
            email.value.isNullOrEmpty() ||
            phoneNumber.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in all fields"
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepo.getLoggedInUser()
            if (userId != null) {
                val user = userRepo.getUserById(userId)
                user?.let {
                    val updateUser = it.copy(
                        img = img.value!!,
                        userName = userName.value!!,
                        email = email.value!!,
                        phoneNumber = phoneNumber.value!!
                    )
                    userRepo.updateUser(userId, updateUser)
                    snackbar.postValue("User details updated successfully")
                    finish.emit(Unit)
                }
            }
        }
    }
}