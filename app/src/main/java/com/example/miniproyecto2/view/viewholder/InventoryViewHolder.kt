package com.example.miniproyecto2.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.miniproyecto2.R
import com.example.miniproyecto2.databinding.InventoryItemBinding
import com.example.miniproyecto2.model.Item

class InventoryViewHolder (binding: InventoryItemBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController

    fun setInventoryItem(item: Item, position: Int) {
        bindingItem.itemName.text = item.name
        bindingItem.itemDescription.text = item.description
        bindingItem.itemQuantity.text = "${item.quantity} unidades disponibles"
        bindingItem.itemPrice.text = "$${item.price} c/u"
        bindingItem.itemCategory.text = item.category
        bindingItem.itemPosition.text = "#${position}"

        Glide.with(bindingItem.root.context).load(item.image).into(bindingItem.imageItem)
        bindingItem.inventoryItem.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("item", item)
            navController.navigate(R.id.action_homeFragment2_to_editFragment, bundle)
        }
    }
}