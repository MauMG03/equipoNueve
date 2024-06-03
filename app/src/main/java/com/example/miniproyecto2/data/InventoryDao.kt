package com.example.miniproyecto2.data

import android.util.Log
import android.widget.Toast
import com.example.miniproyecto2.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InventoryDao @Inject constructor(
    private val firestore: FirebaseFirestore
) : IInventoryDao {
        override suspend fun getItems(): MutableList<Item> {
            return try {
                val itemDocs = firestore.collection("item").get().await()
                val items:MutableList<Item> = mutableListOf()
                for (document in itemDocs.documents){
                    items.add(Item(
                        name = "${document.get("name")}",
                        description = "${document.get("description")}",
                        price = "${document.get("price")}".toDouble(),
                        quantity = "${document.get("quantity")}".toInt(),
                        image = "${document.get("image")}",
                        category = "${document.get("category")}"
                    ))
                }
                items
            }
            catch (e: Exception){
                mutableListOf()
            }
        }

       override suspend fun addItem(item:Item) {
            firestore.collection("item")
                .add(item)
                .addOnSuccessListener {  documentReference ->
                    Log.d("Item", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener{e ->
                    Log.w("Item", "Error adding the item", e)
                }
       }
}