package com.example.miniproyecto2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.repository.ImageResponseRepository
import com.example.miniproyecto2.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val imageResponseRepository: ImageResponseRepository
) : ViewModel() {

    private val _items = MutableLiveData<MutableList<Item>>()
    val items: LiveData<MutableList<Item>> get() = _items

    fun getItems() {
        viewModelScope.launch {
            try {
                _items.value = inventoryRepository.getItems()
            } catch (e: Exception){

            }
        }
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