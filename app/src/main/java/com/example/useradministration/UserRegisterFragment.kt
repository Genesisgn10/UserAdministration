package com.example.useradministration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.useradministration.MaskUtils.Companion.applyCnpjMask
import com.example.useradministration.MaskUtils.Companion.applyCpfMask
import com.example.useradministration.databinding.FragmentUserRegistrationBinding

class UserRegisterFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Defina um ouvinte de seleção para o RadioGroup
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioPessoaFisica -> {
                    applyCpfMask(binding.text)
                    binding.tvCpf.hint = "CPF"
                }

                R.id.radioPessoaJuridica -> {
                    applyCnpjMask(binding.text)
                    binding.tvCpf.hint = "CPF"
                }
            }
        }

    }
}


