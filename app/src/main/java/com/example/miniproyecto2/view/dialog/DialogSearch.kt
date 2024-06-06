package com.example.miniproyecto2.view.dialog

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.DialogSearchBinding
import com.example.miniproyecto2.viewmodel.InventoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiDialogo : DialogFragment() {
    private val inventoryViewModel: InventoryViewModel by viewModels()
    private var _binding: DialogSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllers()
        observeCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateFieldState(checkbox: CheckBox, editText: EditText, hintText: String) {
        editText.isEnabled = checkbox.isChecked
        editText.hint = if (checkbox.isChecked) hintText else ""
        editText.text.clear()
        editText.background = resources.getDrawable(
            if (checkbox.isChecked) R.drawable.edittext_border else R.drawable.edittext_disabled_border
        )
    }

    private fun controllers(){
        binding.etName.addTextChangedListener(TextWatcher)
        binding.etDesde.addTextChangedListener(TextWatcher)
        binding.etHasta.addTextChangedListener(TextWatcher)
        binding.atvCategory.addTextChangedListener(TextWatcher)
        binding.etUsername.addTextChangedListener(TextWatcher)

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.cbName.setOnCheckedChangeListener { _, isChecked ->
            updateFieldState(binding.cbName, binding.etName, "Inserta el nombre a buscar")
        }

        binding.cbCategory.setOnCheckedChangeListener { _, isChecked ->
            updateFieldState(binding.cbCategory, binding.atvCategory, "Inserta la categoría a buscar")
        }

        binding.cbPrice.setOnCheckedChangeListener { _, isChecked ->
            updateFieldState(binding.cbPrice, binding.etDesde, "0")
            updateFieldState(binding.cbPrice, binding.etHasta, "10000000")
        }

        binding.cbUsername.setOnCheckedChangeListener { _, isChecked ->
            updateFieldState(binding.cbUsername, binding.etUsername, "Inserta el nombre de usuario")
        }

        binding.buttonSearch.setOnClickListener {
            if(isAFieldFilled()){
                val name = binding.etName.text.toString()
                val desde = binding.etDesde.text.toString()
                val hasta = binding.etHasta.text.toString()
                val category = binding.atvCategory.text.toString()
                val username = binding.etUsername.text.toString()

                //inventoryViewModel.searchItems(name,desde,hasta,category,username)
                dismiss()
            } else {
                Snackbar.make(binding.root, "Llene algun criterio de busqueda", Snackbar.LENGTH_LONG).show()
            }
        }
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

    fun isAFieldFilled():Boolean {
        val name = binding.etName.text.toString() != "" && binding.cbName.isChecked
        Log.d("NAME", name.toString())
        val desde = binding.etDesde.text.toString() != "" && binding.cbPrice.isChecked
        val hasta = binding.etHasta.text.toString() != "" && binding.cbPrice.isChecked
        val cat = binding.atvCategory.text.toString() != "" && binding.cbCategory.isChecked
        val username = binding.etUsername.text.toString() != "" && binding.cbUsername.isChecked
        return name || desde || hasta || cat || username
    }

    private val TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: android.text.Editable?) {
            if(isAFieldFilled()){
                binding.buttonSearch.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                binding.buttonSearch.compoundDrawableTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
            } else {
                binding.buttonSearch.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                binding.buttonSearch.compoundDrawableTintList = ContextCompat.getColorStateList(requireContext(), R.color.gray)
            }
        }
    }
}