package com.example.miniproyecto2.data

import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.model.SearchCriteria

interface IInventoryDao {
    suspend fun getItems(): MutableList<Item>

    suspend fun addItem(item: Item): Unit

    suspend fun searchItems(criteria: SearchCriteria): MutableList<Item>
}