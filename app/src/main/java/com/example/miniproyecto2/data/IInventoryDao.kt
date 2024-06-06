package com.example.miniproyecto2.data

import com.example.miniproyecto2.model.Item

interface IInventoryDao {
    suspend fun getItems(): MutableList<Item>
}