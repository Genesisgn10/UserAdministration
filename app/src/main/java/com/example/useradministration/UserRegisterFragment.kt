package com.example.useradministration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.useradministration.MaskUtils.Companion.applyCnpjMask
import com.example.useradministration.MaskUtils.Companion.applyCpfMask
import com.example.useradministration.databinding.FragmentUserRegistrationBinding
import com.google.android.material.textfield.TextInputLayout

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

        setupRadioGroup()
        setupPasswordValidation()
        setupNameValidation()
        setupEmailValidation()
        setupSubmitButton()
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val hintResId = when (checkedId) {
                R.id.radioPessoaFisica -> {
                    applyCpfMask(binding.text)
                    R.string.hint_cpf
                }

                R.id.radioPessoaJuridica -> {
                    applyCnpjMask(binding.text)
                    R.string.hint_cnpj
                }

                else -> R.string.hint_default
            }
            binding.tvCpf.hint = getString(hintResId)
        }
    }

    private fun setupPasswordValidation() {
        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            validateField(
                text.toString(),
                binding.tvPassword,
                ::isValidPassword,
                R.string.error_password
            )
        }
    }

    private fun setupNameValidation() {
        binding.editName.doOnTextChanged { text, _, _, _ ->
            validateField(text.toString(), binding.tvName, ::isValidName, R.string.error_name)
        }
    }

    private fun setupEmailValidation() {
        binding.inputTextEmail.doOnTextChanged { text, _, _, _ ->
            validateField(
                binding.inputTextEmail.text.toString(),
                binding.tvEmail,
                ::isValidEmail,
                R.string.error_email
            )
        }
    }

    private fun setupSubmitButton() {
        binding.btSubmit.setOnClickListener {
            val passwordValid = validateField(
                binding.editPassword.text.toString(),
                binding.tvPassword,
                ::isValidPassword,
                R.string.error_password
            )
            val nameValid = validateField(
                binding.editName.text.toString(),
                binding.tvName,
                ::isValidName,
                R.string.error_name
            )
            val emailValid = validateField(
                binding.inputTextEmail.text.toString(),
                binding.tvEmail,
                ::isValidEmail,
                R.string.error_email
            )

            if (passwordValid && nameValid && emailValid) {
                // Todos os campos são válidos, permitir envio
                // Execute a lógica de envio aqui
            }
        }
    }

    private fun validateField(
        text: String,
        textView: TextInputLayout,
        validationFunction: (String) -> Boolean,
        errorResId: Int
    ): Boolean {
        val isValid = validationFunction(text)
        if (!isValid) {
            textView.error = getString(errorResId)
        } else {
            textView.error = null
        }
        return isValid
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex =
            "^(?=.*[A-Z])(?=.*\\d).{8,}$" // Pelo menos 1 letra maiúscula, 1 número, mínimo de 8 caracteres
        return password.matches(passwordRegex.toRegex())
    }

    private fun isValidName(name: String): Boolean {
        return name.length >= 30
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}