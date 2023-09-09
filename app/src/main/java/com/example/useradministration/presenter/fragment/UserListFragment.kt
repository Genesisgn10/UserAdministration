package com.example.useradministration.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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

    private  var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by inject()
    private var userList: MutableList<User> = mutableListOf()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
        getUsers()
    }
    private fun setupObservers(){
        userViewModel.users.observe(viewLifecycleOwner) {
            populateAdapter(it)
            binding.progressBar.isVisible = false
        }
    }
    private fun getUsers() {
        binding.progressBar.isVisible = true
        userViewModel.getUsers()
    }
    private fun setupUI() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_userRegisterFragment)
        }
        binding.textEdit.doOnTextChanged { text, _, _, _ ->
            val searchText = text.toString().trim()
            filterUserList(searchText)
        }
    }
    fun deleteItemFromDatabase(item: User) {
        item.id?.toLong()?.let { userViewModel.deleteUser(it) }
    }
    private fun filterUserList(searchText: String) {
        val filteredList = userList.filter { user ->
            user.username.contains(searchText, ignoreCase = true)
        }
        userAdapter.updateData(filteredList)
    }
    private fun populateAdapter(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)

        if (userList.isNotEmpty()) {
            userAdapter = UserAdapter(userList.toMutableList())
            val itemTouchHelper =
                ItemTouchHelper(SwipeToDeleteCallback(userAdapter, binding.recyclerView, this))
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.adapter = userAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}