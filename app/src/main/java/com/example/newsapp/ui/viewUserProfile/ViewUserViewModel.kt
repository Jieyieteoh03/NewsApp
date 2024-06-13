package com.example.newsapp.ui.viewUserProfile

import android.graphics.BitmapFactory
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
class ViewUserViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {
    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user
    val img: MutableLiveData<ByteArray> = MutableLiveData()

    fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepo.getLoggedInUser()
            if (userId != null) {
                val user = userRepo.getUserById(userId)
                user?.let { _user.postValue(it) }
            }
        }
    }
    fun setImageValue(img: ByteArray) {
        this.img.value = img
    }
    fun doLogout() = userRepo.logOut()
}