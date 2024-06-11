package com.example.newsapp.ui.viewUserProfile

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
class UserDetailViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    private val _user: MutableLiveData<User> = MutableLiveData()
    val userDetails: LiveData<User> = _user
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val userName: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val phoneNumber: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()


    fun loggedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = repo.getLoggedInUser()
            if (userId != null) {
                val user = repo.getUserById(userId)
                user?.let {
                    withContext(Dispatchers.Main) {
                        setUser()
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
                    _user.value = it
                }
            }
        }
    }

    fun setUser() {
        userDetails.value?.let {
            img.value = it.img
            userName.value = it.userName
            email.value = it.email
            phoneNumber.value = it.phoneNumber

        }
    }
}