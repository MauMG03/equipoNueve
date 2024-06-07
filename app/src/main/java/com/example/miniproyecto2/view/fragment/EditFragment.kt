package com.example.miniproyecto2.view.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.FragmentEditBinding
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.viewmodel.InventoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class EditFragment : Fragment() {
    lateinit var binding: FragmentEditBinding
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private lateinit var receivedItem: Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val receivedBundle = arguments
        receivedItem = receivedBundle?.getSerializable("item") as Item
        binding = FragmentEditBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
        observadorViewModel()
    }

    private fun observadorViewModel(){
        observeCategories()
    }

    private fun controllers(){
        binding.btEdit.setOnClickListener {
            if(validateFields()){
                val id = receivedItem.id
                val name = binding.etName.text.toString()
                val qty = binding.etQuantity.text.toString().toInt()
                val unitPr = binding.etUnitPrice.text.toString().toDouble()
                val des = binding.etDescription.text.toString()
                val category = binding.atvCategory.text.toString()
                inventoryViewModel.editItem(id, name,des,unitPr,qty,category)
                findNavController().navigate(R.id.action_editFragment_to_homeFragment2)
            } else {
                Snackbar.make(binding.root, "Llene los campos faltantes", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun areFieldsFilled():Boolean {
        val name = binding.etName.text ?: ""
        val qty = binding.etQuantity.text ?: ""
        val unitP = binding.etUnitPrice.text ?: ""
        val des = binding.etDescription.text ?: ""
        val cat = binding.atvCategory.text ?: ""
        return name.isNotEmpty() && qty.isNotEmpty() && unitP.isNotEmpty() && des.isNotEmpty() && cat.isNotEmpty()
    }

    private fun validateFields(): Boolean{
        binding.etName.addTextChangedListener(TextWatcher)
        binding.etQuantity.addTextChangedListener(TextWatcher)
        binding.etUnitPrice.addTextChangedListener(TextWatcher)
        binding.etDescription.addTextChangedListener(TextWatcher)
        binding.atvCategory.addTextChangedListener(TextWatcher)

        binding.btEdit.isEnabled = areFieldsFilled()
        return areFieldsFilled()
    }

    private fun observeCategories(){
        inventoryViewModel.getCategories()
        inventoryViewModel.categories.observe(viewLifecycleOwner){ categories ->
            Log.d("CATEGORIES",categories.toString())
            val categoriesAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,categories)
            binding.atvCategory.setAdapter(categoriesAdapter)
            binding.atvCategory.threshold = 2
        }
    }

    private val TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: android.text.Editable?) {
            binding.btEdit.isEnabled = areFieldsFilled()
        }
    }
}