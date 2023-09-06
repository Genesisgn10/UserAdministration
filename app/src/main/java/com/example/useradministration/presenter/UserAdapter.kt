package com.example.useradministration.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.useradministration.User
import com.example.useradministration.databinding.UserItemBinding

class UserAdapter(
    private val user: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val itemView =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = user[position]

        with(holder) {
            binding.name.text = user.name
            binding.username.text = user.username
            binding.card.setOnClickListener {
                val action =
                    UserListFragmentDirections
                        .actionUserListFragmentToUserRegisterFragment(user)
                it.findNavController().navigate(action)
            }
        }
    }

    fun removeItem(position: Int): User {
        val deletedItem = user.removeAt(position)
        notifyItemRemoved(position)
        return deletedItem
    }

    fun restoreItem(position: Int, item: User) {
        user.add(position, item)
        notifyItemInserted(position)
    }

    override fun getItemCount() = user.size

    inner class UsersViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}