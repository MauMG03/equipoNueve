package com.example.miniproyecto2.data

import android.util.Log
import android.widget.Toast
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.model.SearchCriteria
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
                        id = "${document.get("id")}",
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
            val documentReference = firestore.collection("item")
                .add(item)
                .addOnSuccessListener {  documentReference ->
                    Log.d("Item", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener{e ->
                    Log.w("Item", "Error adding the item", e)
                }
                .await()

            val id = documentReference.id
            firestore.collection("item").document(id)
                .update("id", id)
                .addOnSuccessListener {
                    Log.d("Item", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w("Item", "Error updating document", e)
                }
       }

        override suspend fun editItem(item: Item) {
            Log.d("Item", "Editing item: $item")
            val query: Query = firestore.collection("item")
            val itemDocs = query.get().await()

            for (document in itemDocs.documents){
                Log.d("Edit", document.get("name").toString())
                Log.d("Edit", document.get("id").toString() + "=" + item.id)
                if(document.get("id").toString() == item.id){
                    firestore.collection("item").document(document.id)
                        .update(
                            "name", item.name,
                            "description", item.description,
                            "price", item.price,
                            "quantity", item.quantity,
                            "image", item.image,
                            "category", item.category
                        )
                        .addOnSuccessListener {
                            Log.d("Item", "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Item", "Error updating document", e)
                        }
                }
            }
        }

    override suspend fun deleteItem(item: Item) {
        Log.d("Item", "Deleting item: $item")
        val query: Query = firestore.collection("item")
        val itemDocs = query.get().await()

        for (document in itemDocs.documents){
            Log.d("Delete", document.get("name").toString())
            Log.d("Delete", document.get("id").toString() + "=" + item.id)
            if(document.get("id").toString() == item.id){
                firestore.collection("item").document(document.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("Item", "DocumentSnapshot successfully deleted!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Item", "Error deleting document", e)
                    }
            }
        }
    }

        override suspend fun searchItems(criteria: SearchCriteria): MutableList<Item> {
            return try {
                val query: Query = firestore.collection("item")
                val itemDocs = query.get().await()

                val items:MutableList<Item> = mutableListOf()
                for (document in itemDocs.documents){
                    var ifName = !criteria.name.isNullOrBlank()
                    var ifMinPrice = criteria.minPrice != null && criteria.minPrice > 0
                    var ifMaxPrice = criteria.maxPrice != null && criteria.maxPrice > 0
                    var ifCategory = !criteria.category.isNullOrBlank()
                    var ifUsername = !criteria.username.isNullOrBlank()

                    if(ifName){
                        ifName = !("${document.get("name")}".contains(criteria.name!!, ignoreCase = true))
                    }

                    if(ifMinPrice){
                        ifMinPrice = !("${document.get("price")}".toDouble() >= criteria.minPrice!!)
                    }
                    if(ifMaxPrice){
                        ifMaxPrice = !("${document.get("price")}".toDouble() <= criteria.maxPrice!!)
                    }

                    if(ifCategory){
                        ifCategory = !("${document.get("category")}".contains(criteria.category!!, ignoreCase = true))
                    }

                    if(!ifName && !ifMinPrice && !ifMaxPrice && !ifCategory && !ifUsername){
                        items.add(Item(
                            id = "${document.get("id")}",
                            name = "${document.get("name")}",
                            description = "${document.get("description")}",
                            price = "${document.get("price")}".toDouble(),
                            quantity = "${document.get("quantity")}".toInt(),
                            image = "${document.get("image")}",
                            category = "${document.get("category")}"
                        ))
                    }
                }
                items
            }
            catch (e: Exception){
                Log.d("Dao", "Exception: $e")
                mutableListOf()
            }
        }
}