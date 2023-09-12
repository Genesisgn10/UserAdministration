package com.example.useradministration.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.database.User
import com.example.useradministration.R
import com.example.useradministration.databinding.FragmentUserListBinding
import com.example.useradministration.presenter.UserViewModel
import com.example.useradministration.presenter.adapter.UserAdapter
import com.example.useradministration.showSnackbar
import com.example.utils.RecyclerViewSwipeHelper
import com.example.utils.StateError
import com.example.utils.StateLoading
import com.example.utils.StateSuccess
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class UserListFragment : Fragment() {

    private  var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by inject()
    private var userList: MutableList<User> = mutableListOf()
    private var userAdapter = UserAdapter(mutableListOf())

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

    private fun showError(){
        binding.error.isVisible = true
        binding.recyclerView.isVisible = false
        binding.progressBar.isVisible =  false
    }

    private fun setupObservers(){
        userViewModel.users.observe(viewLifecycleOwner) {
            when(it){
                is StateSuccess -> populateAdapter(it.data)
                is StateLoading -> showLoading(it.loading)
                is StateError -> showError()
                else -> {}
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.recyclerView.isVisible = !loading
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

    private fun deleteItemFromDatabase(item: User) {
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
            binding.recyclerView.adapter = userAdapter
            enableSwipeToDeleteAndUndo()
        }
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeHelper = RecyclerViewSwipeHelper(requireContext()) { position ->
            val item = userAdapter.getData()[position]
            userAdapter.removeItem(position)
            deleteItemFromDatabase(item)

            binding.recyclerView.showSnackbar(getString(R.string.usuario_deletado_com_sucesso), Snackbar.LENGTH_LONG)
        }

        swipeHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}