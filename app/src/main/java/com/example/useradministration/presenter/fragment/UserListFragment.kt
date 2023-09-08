package com.example.useradministration.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.useradministration.R
import com.example.useradministration.SwipeToDeleteCallback
import com.example.database.User
import com.example.useradministration.databinding.FragmentUserListBinding
import com.example.useradministration.presenter.adapter.UserAdapter
import com.example.useradministration.presenter.UserViewModel
import org.koin.android.ext.android.inject

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val userViewModel: UserViewModel by inject()

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
        userViewModel.users.observe(viewLifecycleOwner) {
            populateAdapter(it)
        }
        userViewModel.getUsers()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_userRegisterFragment)
        }
    }

    fun deleteItemFromDatabase(item: com.example.database.User) {
        item.id?.toLong()?.let { userViewModel.deleteUser(it) }
    }

    private fun populateAdapter(userList: List<com.example.database.User>) {
        if (userList.isNotEmpty()) {
            val adapter = UserAdapter(userList.toMutableList())
            val itemTouchHelper =
                ItemTouchHelper(SwipeToDeleteCallback(adapter, binding.recyclerView, this))
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.adapter = adapter
        }
    }

}