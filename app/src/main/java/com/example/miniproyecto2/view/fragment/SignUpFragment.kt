package com.example.miniproyecto2.view.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.example.miniproyecto2.viewmodel.LoginViewModel
import com.example.miniproyecto2.databinding.FragmentSignUpBinding
import com.example.miniproyecto2.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners(){
        binding.btnSignUp.setOnClickListener {
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        loginViewModel.registerUser(email,pass) { isRegister ->
            if (isRegister) {
                goToHome(email)
                Snackbar.make(binding.root, "Registro exitoso", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Error en el registro", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
    private fun goToHome(email: String?){
        val intent = Intent (requireActivity(), MainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
        requireActivity().finish()
    }
    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        loginViewModel.loginUser(email, pass) { isLogin ->
            if (isLogin) {
                goToHome(email)
                Snackbar.make(binding.root, "Login correcto", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Login incorrecto", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    private fun sesion(){
        val email = sharedPreferences.getString("email",null)
        loginViewModel.sesion(email){ isEnableView ->
            if (isEnableView){
                //binding..visibility = View.INVISIBLE
                //goToHome(email)
            }
        }
    }
}