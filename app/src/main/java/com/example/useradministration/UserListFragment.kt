package com.example.useradministration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.useradministration.databinding.FragmentUserListBinding
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
            uid = 104,
            name = "Genesis",
            username = "Aquino",
            password = "123",
            email = "email@exampsle.com",
            dateOfBirth = "01/01/1993",
            sex = "Masculinos",
            address = "asdasd",
            photoUrl = "asda",
            userType = "ss",
            cpfOrCnpj = "123456789023"
        )

        userRepository.addUser(user)
    }
}