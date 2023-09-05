package com.example.useradministration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.useradministration.databinding.UserItemBinding

class UserAdapter(
    private val categories: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val itemView =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val category = categories[position]

        with(holder) {
            binding.name.text = category.name
            binding.username.text = category.username
        }
    }

    fun removeItem(position: Int): User {
        val deletedItem = categories.removeAt(position)
        notifyItemRemoved(position)
        return deletedItem
    }

    fun restoreItem(position: Int, item: User) {
        categories.add(position, item)
        notifyItemInserted(position)
    }

    override fun getItemCount() = categories.size

    inner class UsersViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}