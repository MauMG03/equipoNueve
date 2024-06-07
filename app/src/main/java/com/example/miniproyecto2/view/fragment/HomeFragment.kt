package com.example.miniproyecto2.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.FragmentHomeBinding
import com.example.miniproyecto2.view.LoginActivity
import com.example.miniproyecto2.view.MainActivity
import com.example.miniproyecto2.view.adapter.InventoryAdapter
import com.example.miniproyecto2.view.dialog.MiDialogo
import com.example.miniproyecto2.viewmodel.InventoryViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var  binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val inventoryViewModel: InventoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observadorViewModel()
        controllers()
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun controllers() {
        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_createItemFragment2)
        }

        binding.searchButton.setOnClickListener{
            showDialog()
        }

        binding.tvExit.setOnClickListener{
            logOut()
        }
    }

    private fun observadorViewModel(){
        observerItems()
        cleanButtonObserver()
    }

    private fun cleanButtonObserver(){
        inventoryViewModel.showClearButton.observe(viewLifecycleOwner) { showButton ->
            if (showButton) {
                binding.searchButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_clear))
                binding.searchButton.setOnClickListener{
                    findNavController().navigate(R.id.action_homeFragment2_self)
                }
                binding.searchButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.green)
            } else {
                binding.searchButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_search))
                binding.searchButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.dark_gray)
                binding.searchButton.setOnClickListener{
                    showDialog()
                }
            }
        }
    }

    fun observerItems(){
        inventoryViewModel.getItems()
        inventoryViewModel.items.observe(viewLifecycleOwner){items ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = InventoryAdapter(items, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun showDialog(){
        val dialog = MiDialogo()
        dialog.show(childFragmentManager, "dialog")
    }

    private fun dataLogin() {
        val bundle = requireActivity().intent.extras
        val email = bundle?.getString("email")
        //binding.tvTitleEmail.text = email ?: ""
        sharedPreferences.edit().putString("email",email).apply()
    }

    private fun logOut(){
        sharedPreferences.edit().clear().apply()
        FirebaseAuth.getInstance().signOut()
        (requireActivity() as MainActivity).apply {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}