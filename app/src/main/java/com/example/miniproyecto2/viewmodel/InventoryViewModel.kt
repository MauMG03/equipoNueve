package com.example.miniproyecto2.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.model.SearchCriteria
import com.example.miniproyecto2.repository.ImageResponseRepository
import com.example.miniproyecto2.repository.InventoryRepository
import com.example.miniproyecto2.view.fragment.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val imageResponseRepository: ImageResponseRepository
) : ViewModel() {

    private val _items = MutableLiveData<MutableList<Item>>()
    val items: LiveData<MutableList<Item>> get() = _items

    private val _categories = MutableLiveData<MutableList<String>>()
    val categories: LiveData<MutableList<String>> = _categories

    private val _closeDialog = MutableLiveData<Boolean>()
    val closeDialog: LiveData<Boolean> = _closeDialog

    private val _showClearButton = MutableLiveData<Boolean>(false)
    val showClearButton: LiveData<Boolean> = _showClearButton

    fun getItems() {
        viewModelScope.launch {
            try {
                _items.value = inventoryRepository.getItems()
                _showClearButton.postValue(false)
            } catch (_: Exception){

            }
        }
    }
    fun searchItems(criteria: SearchCriteria) {
        viewModelScope.launch {
            try {
                val results = inventoryRepository.searchItems(criteria)
                val updatedItems = if (results.isEmpty())
                    mutableListOf(Item("No se encontraron resultados", "", 0.0, 0, "", ""))
                else
                    results

                withContext(Dispatchers.Main) {
                    _items.value = updatedItems
                    Log.d("ViewModel", "Items updated: ${_items.value}")
                }
            } finally {
                _closeDialog.postValue(true)
                _showClearButton.postValue(true)
            }
        }
    }

    fun deleteItems() {
        viewModelScope.launch {
            _items.value = mutableListOf()
        }
    }

    private var initialized = false
    fun getCategories():MutableLiveData<MutableList<String>> {
       if(!initialized){
           val categories = mutableListOf("Jardineria","Cocina","Hogar","Electronicos","Plomeria","Vehiculos",
               "Muebles","Calzado","Accesorios","Licores")
           _categories.value = categories
           initialized = true
       }
        return _categories
    }

    fun addItem(name:String,des:String,price:Double,qty:Int,category:String){
        viewModelScope.launch {
            try {
                val image = imageResponseRepository.getImage(name)
                val item = Item(name,des,price,qty,image,category)
                inventoryRepository.addItem(item)
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}