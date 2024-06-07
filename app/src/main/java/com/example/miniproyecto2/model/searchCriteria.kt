package com.example.miniproyecto2.model

data class SearchCriteria(
    val name: String? = null,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val category: String? = null,
    val description: String? = null
)