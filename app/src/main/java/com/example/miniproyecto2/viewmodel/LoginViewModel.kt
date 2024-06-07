package com.example.miniproyecto2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.miniproyecto2.repository.LoginRepository

class LoginViewModel : ViewModel(){
    private val repository = LoginRepository()
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

    fun session(email: String?, isEnableView: (Boolean) -> Unit){
        repository.session(email){
                response -> isEnableView(response)
        }
    }
}