package com.example.miniproyecto2.repository

import com.example.miniproyecto2.data.InventoryDao
import com.example.miniproyecto2.webservice.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageResponseRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getImage(name:String):String{
        return withContext(Dispatchers.IO){
            try {
                var image = ""
                val query = name.replace(" ", "+")
                val key = "44153454-77c3036c9d338d8114d4a9d92"
                val response = apiService.getImage(key,query)

                if(response.total != 0){
                    image = response.hits[0].get("largeImageURL").toString()
                } else {
                    image = "https://pixabay.com/get/g1d7ba807a85fbd2198d0ec753e08066d8be6f8dde8b1c4363e9532fce3f5993334412f2648465fb3c4ecd03e6b520bb3702f37f93fc449f173fba59867b3bb7f_640.png"
                }
                image

            } catch (e:Exception){
                e.printStackTrace()
                val image = "https://pixabay.com/get/g1d7ba807a85fbd2198d0ec753e08066d8be6f8dde8b1c4363e9532fce3f5993334412f2648465fb3c4ecd03e6b520bb3702f37f93fc449f173fba59867b3bb7f_640.png"
                image
            }
        }
    }
}