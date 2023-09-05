package com.example.useradministration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.useradministration.databinding.FragmentUserListBinding
import org.koin.android.ext.android.inject

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding

    private val userRepository: UserRepository2 by inject()

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

        val list = listOf(
            UserModel(
                id = 1,
                name = "bora",
                username = "Gn10",
                password = "123",
                email = "email@exampsle.com",
                birthdate = "01/01/1993",
                sex = "Masculinos",
                address = "asdasd",
                photoUrl = "asda",
                type = "ss",
                cpf_cnpj = "123456789023"
            ),
            UserModel(
                id = 1,
                name = "bora",
                username = "Gn10",
                password = "123",
                email = "email@exampsle.com",
                birthdate = "01/01/1993",
                sex = "Masculinos",
                address = "asdasd",
                photoUrl = "asda",
                type = "ss",
                cpf_cnpj = "123456789023"
            )
        )

        viewModel.users.observe(viewLifecycleOwner) {
            populateAdapter(it)
        }
        viewModel.getUsers()

        val user = User(
            id = 1,
            name = "bora",
            username = "Gn10",
            password = "123",
            email = "email@exampsle.com",
            birthdate = "01/01/1993",
            sex = "Masculinos",
            address = "asdasd",
            photoUrl = "asda",
            type = "ss",
            cpf_cnpj = "123456789023"
        )

        userRepository.addUser(user)

        // userRepository.updateUser(user)
        // userRepository.deleteUser(2)
    }

    fun deleteItemFromDatabase(item: User) {
        // Implemente a lógica para excluir o item do banco de dados usando userRepository.deleteUser(item.id)
        item.id?.let { userRepository.deleteUser(it.toLong()) }
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