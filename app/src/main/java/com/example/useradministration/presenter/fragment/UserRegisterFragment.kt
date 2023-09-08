package com.example.useradministration.presenter.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.utils.MaskTextWatcher
import com.example.useradministration.R
import com.example.utils.ValidationUtils
import com.example.useradministration.databinding.FragmentUserRegistrationBinding
import com.example.useradministration.presenter.UserViewModel
import com.example.useradministration.toBase64
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class UserRegisterFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by inject()
    private val args: UserRegisterFragmentArgs? by navArgs()

    private var isUpdate = false
    private val SELECT_IMAGE_REQUEST = 1
    private var currentMaskWatcher: MaskTextWatcher? = null

    private val maskMap = mapOf(
        R.id.radioPessoaFisica to "###.###.###-##",
        R.id.radioPessoaJuridica to "##.###.###/####-##"
    )

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
        setupUI()
        setupValidationListeners()
        setupSubmitButton()
    }

    private fun setupUI() {
        binding.appCompatButton.setOnClickListener {
            pickImage()
        }

        args?.user?.let {
            populate(it)
            isUpdate = true
        }

        setupRadioGroup()
        updateCpfCnpjMask(binding.radioGroup.checkedRadioButtonId)
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            updateCpfCnpjMask(checkedId)
        }
    }

    private fun updateCpfCnpjMask(checkedId: Int) {
        val mask = maskMap[checkedId] ?: ""

        currentMaskWatcher?.let {
            binding.text.removeTextChangedListener(it)
        }

        val maskWatcher = MaskTextWatcher(binding.text, mask)
        binding.text.addTextChangedListener(maskWatcher)
        currentMaskWatcher = maskWatcher

        if (mask == maskMap[R.id.radioPessoaFisica]) {
            binding.textInputCpf.hint =getString( R.string.hint_cpf)
        } else if (mask == maskMap[R.id.radioPessoaJuridica]) {
            binding.textInputCpf.hint =getString( R.string.hint_cnpj)
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                selectedImageUri
            )
            binding.imageView.setImageBitmap(bitmap)
        }
    }

    private fun populate(user: com.example.database.User) {
        with(binding) {
            tvName.editText?.setText(user.name)
            tvUsername.editText?.setText(user.username)
            tvAddress.editText?.setText(user.address)
            tvPassword.editText?.setText(user.password)
            tvEmail.editText?.setText(user.email)
            tvNas.editText?.setText(user.birthdate)
        }
    }

    private fun setupValidationListeners() {
        setupFieldValidation(
            binding.editPassword,
            binding.tvPassword,
            ValidationUtils::isValidPassword,
            R.string.error_password
        )

        setupFieldValidation(
            binding.editName,
            binding.tvName,
            ValidationUtils::isValidName,
            R.string.error_name
        )

        setupFieldValidation(
            binding.inputTextEmail,
            binding.tvEmail,
            ValidationUtils::isValidEmail,
            R.string.error_email
        )
    }

    private fun setupFieldValidation(
        editText: EditText,
        textView: TextInputLayout,
        validationFunction: (String) -> Boolean,
        errorResId: Int
    ) {
        editText.doOnTextChanged { text, _, _, _ ->
            validateField(text.toString(), textView, validationFunction, errorResId)
        }
    }

    private fun setupSubmitButton() {
        binding.btSubmit.setOnClickListener {
            if (validateFields()) {
                val user = createUserFromInput()
                if (isUpdate) {
                    userViewModel.updateUser(user)
                } else {
                    userViewModel.addUser(user)
                }
                findNavController().popBackStack()
            }
        }
    }

    private fun validateFields(): Boolean {
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
        return passwordValid && nameValid && emailValid
    }

    private fun getBitmapFromImageView(imageView: ImageView): Bitmap? {
        val drawable = imageView.drawable
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else null
    }

    private fun createUserFromInput(): com.example.database.User {
        val name = binding.tvName.editText?.text.toString()
        val username = binding.tvUsername.editText?.text.toString()
        val password = binding.tvPassword.editText?.text.toString()
        val email = binding.tvEmail.editText?.text.toString()
        val birthdate = binding.tvNas.editText?.text.toString()
        val sex = binding.tvName.editText?.text.toString()
        val type = binding.radioGroup.checkedRadioButtonId.toString()
        val cpf_cnpj = binding.tvName.editText?.text.toString()
        val address = binding.tvAddress.editText?.text.toString()
        val bitmap = getBitmapFromImageView(binding.imageView)
        val photoUrl = bitmap?.toBase64(80) ?: ""

        return com.example.database.User(
            id = args?.user?.id,
            name = name,
            username = username,
            password = password,
            email = email,
            birthdate = birthdate,
            sex = sex,
            type = type,
            cpf_cnpj = cpf_cnpj,
            photoUrl = photoUrl,
            address = address
        )
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