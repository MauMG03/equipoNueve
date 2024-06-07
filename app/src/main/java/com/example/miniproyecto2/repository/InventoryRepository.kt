package com.example.miniproyecto2.repository

import android.util.Log
import com.example.miniproyecto2.data.InventoryDao
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.model.SearchCriteria
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
    suspend fun searchItems(criteria: SearchCriteria):MutableList<Item>{
        return withContext(Dispatchers.IO){
            inventoryDao.searchItems(criteria)
        }
    }

    suspend fun addItem(item:Item){
        return withContext(Dispatchers.IO){
            inventoryDao.addItem(item)
        }
    }

    suspend fun editItem(item:Item){
        return withContext(Dispatchers.IO){
            inventoryDao.editItem(item)
        }
    }

    suspend fun deleteItem(item:Item){
        return withContext(Dispatchers.IO){
            inventoryDao.deleteItem(item)
        }
    }
}