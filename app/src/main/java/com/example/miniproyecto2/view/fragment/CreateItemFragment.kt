package com.example.miniproyecto2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.FragmentCreateItemBinding
import com.example.miniproyecto2.databinding.FragmentHomeBinding
import com.example.miniproyecto2.viewmodel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateItemFragment : Fragment() {
    lateinit var binding: FragmentCreateItemBinding
    private val inventoryViewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateItemBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observadorViewModel()
    }

    private fun observadorViewModel(){

    }
}