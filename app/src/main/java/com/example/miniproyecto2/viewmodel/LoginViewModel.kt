package com.example.miniproyecto2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.miniproyecto2.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val repository: LoginRepository

) : ViewModel(){

    fun registerUser(email: String, password: String, isRegisterComplete: (Boolean)-> Unit ){
        repository.registerUser(email, password){
                response -> isRegisterComplete(response)
        }
    }

    fun loginUser(email: String, password: String, isLoginComplete: (Boolean)-> Unit){
        repository.loginUser(email, password){
                response -> isLoginComplete(response)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit){
        repository.session(email){
                response -> isEnableView(response)
        }
    }
}