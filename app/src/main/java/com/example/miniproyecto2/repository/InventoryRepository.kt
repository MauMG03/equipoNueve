package com.example.miniproyecto2.repository

import com.example.miniproyecto2.data.InventoryDao
import com.example.miniproyecto2.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao,
) {
    suspend fun getItems():MutableList<Item>{
        return withContext(Dispatchers.IO){
            inventoryDao.getItems()
        }
    }
}