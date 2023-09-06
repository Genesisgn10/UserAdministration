package com.example.useradministration.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.useradministration.R
import com.example.useradministration.SwipeToDeleteCallback
import com.example.useradministration.User
import com.example.useradministration.databinding.FragmentUserListBinding
import org.koin.android.ext.android.inject

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val viewModel: ViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.users.observe(viewLifecycleOwner) {
            populateAdapter(it)
        }
        viewModel.getUsers()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_userRegisterFragment)
        }
    }

    fun deleteItemFromDatabase(item: User) {
        item.id?.toLong()?.let { viewModel.deleteUser(it) }
    }

    private fun populateAdapter(userList: List<User>) {
        if (userList.isNotEmpty()) {
            val adapter = UserAdapter(userList.toMutableList())
            val itemTouchHelper =
                ItemTouchHelper(SwipeToDeleteCallback(adapter, binding.recyclerView, this))
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.adapter = adapter
        }
    }

}