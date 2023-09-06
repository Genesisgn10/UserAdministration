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

import com.example.useradministration.databinding.FragmentUserRegistrationBinding
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class UserRegisterFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ViewModel by inject()

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

    private var currentTextWatcher: TextWatcher? = null


    private fun setupRadioGroup() {
        val cpfMaskWatcher = MaskTextWatcher(binding.text, "###.###.###-##")
        binding.text.addTextChangedListener(cpfMaskWatcher)
        currentTextWatcher = cpfMaskWatcher
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Remove o TextWatcher existente, se houver
            currentTextWatcher?.let {
                binding.text.removeTextChangedListener(it)
            }

            when (checkedId) {
                R.id.radioPessoaFisica -> {
                    val cpfMaskWatcher = MaskTextWatcher(binding.text, "###.###.###-##")
                    binding.text.addTextChangedListener(cpfMaskWatcher)
                    currentTextWatcher = cpfMaskWatcher
                }

                R.id.radioPessoaJuridica -> {
                    val cnpjMaskWatcher = MaskTextWatcher(binding.text, "##.###.###/####-##")
                    binding.text.addTextChangedListener(cnpjMaskWatcher)
                    currentTextWatcher = cnpjMaskWatcher
                }

                else -> {

                }
            }
        }
    }

    private fun setupPasswordValidation() {
        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            validateField(
                text.toString(),
                binding.tvPassword,
                ValidationUtils::isValidPassword,
                R.string.error_password
            )
        }
    }

    private fun setupNameValidation() {
        binding.editName.doOnTextChanged { text, _, _, _ ->
            validateField(
                text.toString(),
                binding.tvName,
                ValidationUtils::isValidName,
                R.string.error_name
            )
        }
    }

    private fun setupEmailValidation() {
        binding.inputTextEmail.doOnTextChanged { text, _, _, _ ->
            validateField(
                binding.inputTextEmail.text.toString(),
                binding.tvEmail,
                ValidationUtils::isValidEmail,
                R.string.error_email
            )
        }

    }

    private fun setupSubmitButton() {
        binding.btSubmit.setOnClickListener {
            val passwordValid = validateField(
                binding.editPassword.text.toString(),
                binding.tvPassword,
                ValidationUtils::isValidPassword,
                R.string.error_password
            )
            val nameValid = validateField(
                binding.editName.text.toString(),
                binding.tvName,
                ValidationUtils::isValidName,
                R.string.error_name
            )
            val emailValid = validateField(
                binding.inputTextEmail.text.toString(),
                binding.tvEmail,
                ValidationUtils::isValidEmail,
                R.string.error_email
            )

            if (passwordValid && nameValid && emailValid) {
                // Todos os campos são válidos, permitir envio
                // Execute a lógica de envio aqui
                val s = binding.tvName.editText?.text.toString()
                val user = User(
                    id = null,
                    name = binding.tvName.editText?.text.toString(),
                    username = binding.tvUsername.editText?.text.toString(),
                    password = binding.tvPassword.editText?.text.toString(),
                    email = binding.tvEmail.editText?.text.toString(),
                    birthdate = binding.tvName.editText?.text.toString(),
                    sex = binding.tvName.editText?.text.toString(),
                    type = binding.radioGroup.checkedRadioButtonId.toString(),
                    cpf_cnpj = binding.tvName.editText?.text.toString(),
                    photoUrl = binding.tvName.editText?.text.toString(),
                    address = binding.tvAddress.editText?.text.toString()
                )
                viewmodel.addUser(user)
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
            textView.isErrorEnabled = false
            textView.error = null
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}