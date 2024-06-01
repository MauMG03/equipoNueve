package com.example.miniproyecto2.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproyecto2.databinding.InventoryItemBinding
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.view.viewholder.InventoryViewHolder

class InventoryAdapter(private val listInventory: MutableList<Item>, private val navController: NavController) :
    RecyclerView.Adapter<InventoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = InventoryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return InventoryViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listInventory.size
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val inventory = listInventory[position]
        holder.setInventoryItem(inventory)
    }
}