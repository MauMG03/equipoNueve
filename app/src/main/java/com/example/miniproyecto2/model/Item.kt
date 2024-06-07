package com.example.miniproyecto2.model

import java.io.Serializable

data class Item (
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val image: String,
    val category: String
): Serializable