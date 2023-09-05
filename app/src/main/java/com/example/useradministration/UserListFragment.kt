package com.example.useradministration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.useradministration.databinding.FragmentUserListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val userRepository: UserRepository2 by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

       // userRepository.updateUser(user)
        userRepository.deleteUser(2)

        /* viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
             val db = AppDatabase.getDatabase(requireContext())
             val userDao = db.userDao()
             val userList = userDao.getAllUsers()

             withContext(Dispatchers.Main) {
                 Log.d("Debug", "userList size: ${userList.size}")
                 userList.forEach { user ->
                     Log.d("Debug", "User: ${user.name}")
                 }
             }
         }*/

    }
}