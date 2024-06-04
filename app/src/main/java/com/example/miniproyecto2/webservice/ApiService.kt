package com.example.miniproyecto2.webservice

import com.example.miniproyecto2.model.ImageResponse
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.utils.Constants.BASE_URL
import com.example.miniproyecto2.utils.Constants.ENDPOINT_IMAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    suspend fun getImage(@Query("key") key:String, @Query("q") query:String): ImageResponse
}