package com.example.miniproyecto2.webservice

import com.example.miniproyecto2.model.ImageResponse
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.utils.Constants.ENDPOINT_IMAGE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(ENDPOINT_IMAGE)
    suspend fun getImage(@Path("key") key:String, @Path("query") query:String): ImageResponse
}