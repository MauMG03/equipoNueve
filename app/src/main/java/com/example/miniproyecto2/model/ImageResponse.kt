package com.example.miniproyecto2.model

import com.google.gson.annotations.SerializedName

data class ImageResponse (
    val total:Int,
    val totalHits:Int,
    val hits: List<Map<String,Any>>
)