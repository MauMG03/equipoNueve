package com.example.miniproyecto2.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun registerUser(email: String, password: String, isRegisterComplete: (Boolean)-> Unit){
        if (email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    isRegisterComplete(true)
                }else{
                    isRegisterComplete(false)
                }
            }
        } else {
            isRegisterComplete(false)
        }
    }

    fun loginUser(email: String, password: String, isLoginComplete: (Boolean)-> Unit){
        if (email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    isLoginComplete(true)
                }else{
                    isLoginComplete(false)
                }
            }
        } else {
            isLoginComplete(false)
        }
    }

    fun session(email: String?, isEnableView: (Boolean) -> Unit): Unit{
        if (email != null){
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}