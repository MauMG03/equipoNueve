package com.example.miniproyecto2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.FragmentHomeBinding
import com.example.miniproyecto2.view.adapter.InventoryAdapter
import com.example.miniproyecto2.viewmodel.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var  binding: FragmentHomeBinding
    private val inventoryViewModel: InventoryViewModel by viewModels()

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
    }

    private fun observadorViewModel(){
        observerItems()
    }

    private fun observerItems(){
        inventoryViewModel.getItems()
        inventoryViewModel.items.observe(viewLifecycleOwner){items ->
            Toast.makeText(context, "${items}", Toast.LENGTH_SHORT).show()
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = InventoryAdapter(items, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}