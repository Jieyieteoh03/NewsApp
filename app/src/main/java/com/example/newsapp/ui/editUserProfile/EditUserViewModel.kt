package com.example.newsapp.ui.editUserProfile

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    private val _user: MutableLiveData<User> = MutableLiveData()
    val img: MutableLiveData<ByteArray?> = MutableLiveData()
    val userDetails: LiveData<User> = _user
    val userName: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData()
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
                        setUser(it)
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

    private fun setUser(user: User) {
        userName.value = user.userName
        email.value = user.email
        phoneNumber.value = user.phoneNumber
    }

    fun updateProfile() {
        if (userName.value.isNullOrEmpty() || email.value.isNullOrEmpty() || phoneNumber.value.isNullOrEmpty() || password.value.isNullOrEmpty() || confirmPassword.value.isNullOrEmpty()) {
            snackbar.value = "Please fill in all fields"
            return
        }

        if (confirmPassword.value != password.value) {
            snackbar.value = "Confirm Password does not match your Password"
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val userId = repo.getLoggedInUser()
            val bcrypt = BCryptPasswordEncoder()
            if (userId != null) {
                val user = repo.getUserById(userId)
                user?.let {
                    val updateUser = it.copy(
                        img = byteArrayOf(1,2,3,4,5),
                        userName = userName.value!!,
                        email = email.value!!,
                        phoneNumber = phoneNumber.value!!,
                        password = bcrypt.encode(password.value)!!
                    )
                    repo.updateUser(userId, updateUser)
                    withContext(Dispatchers.Main) {
                        snackbar.value = "User details updated successfully"
                    }
                    finish.emit(Unit)
                }
            }
        }
        Log.d("userDetailDebugging", "${userName.value}, ${email.value}, ${phoneNumber.value}, ${password.value}")
    }
}
