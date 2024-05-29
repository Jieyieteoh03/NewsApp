package com.example.newsapp.ui.account.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.user.User
import com.example.newsapp.data.repository.UserRepo
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
    val phoneNumber: MutableLiveData<Int> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val confirm_password: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val snackbar: MutableLiveData<String> = MutableLiveData()

    fun register() {
        if (
            userName.value.isNullOrEmpty() ||
            email.value.isNullOrEmpty() ||
            phoneNumber.value.toString().isEmpty() ||
            password.value.isNullOrEmpty() ||
            confirm_password.value.isNullOrEmpty()
            ) {
            snackbar.value = "Please fill in all fields"
            return
        }
        if (
            confirm_password.value == password.value
            ) {
            snackbar.value = "Confirm Password does not match your Password"
        }
        viewModelScope.launch (Dispatchers.IO){
            repo.addUser(User
                (
                userName = userName.value!!,
                email = email.value!!,
                phoneNumber = phoneNumber.value!!,
                password = password.value!!
            )
            )
            withContext(Dispatchers.Main) {
                snackbar.value = "Account Created Successfully. Please Sign In to proceed."
            }
            finish.emit(Unit)
        }
    }
}