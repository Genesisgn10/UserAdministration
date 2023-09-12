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
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.database.User
import com.example.useradministration.R
import com.example.utils.ValidationUtils
import com.example.useradministration.databinding.FragmentUserRegistrationBinding
import com.example.useradministration.fromBase64
import com.example.useradministration.presenter.UserViewModel
import com.example.useradministration.resizeToMaxSize
import com.example.useradministration.showSnackbar
import com.example.useradministration.toBase64
import com.example.utils.Const.MASK_CNPJ
import com.example.utils.Const.MASK_CPF
import com.example.utils.Const.MASK_DATA
import com.example.utils.Const.maxSizeInBytes
import com.example.utils.MaskUtils.applyMaskToEditText
import com.example.utils.StateError
import com.example.utils.StateLoading
import com.example.utils.StateSuccessV2
import com.example.utils.ValidationUtils.calculateAgeFromBirthdate
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class UserRegisterFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by inject()
    private val args: UserRegisterFragmentArgs? by navArgs()

    private val maskMap = mapOf(
        R.id.radioPessoaFisica to MASK_CPF,
        R.id.radioPessoaJuridica to MASK_CNPJ
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
        observer()
        initView()
    }

    private fun initView() {
        initializeUI()
        setupRadioGroup()
        setupValidationListeners()
        setMask()
    }

    private fun observer() {
        userViewModel.users.observe(viewLifecycleOwner) {
            when (it) {
                is StateSuccessV2 -> handleSuccess()
                is StateLoading -> {}
                is StateError -> showError(it.errorData)
                else -> {}
            }
        }
    }

    private fun handleSuccess() {
        view?.showSnackbar(getString(R.string.usuario_state))
        findNavController().popBackStack()
    }

    private fun setMask() {
        applyMaskToEditText(binding.editData, MASK_DATA)
        updateCpfCnpjMask(binding.radioGroup.checkedRadioButtonId)
    }

    private fun initializeUI() {
        binding.appCompatButton.setOnClickListener { pickImage() }
        binding.btSubmit.setOnClickListener { setupSubmitButton() }
        args?.user?.let { populateUser(it) }
    }

    private fun isUpdateUser(): Boolean {
        return args?.user != null
    }

    private fun setupRadioGroup() {
        with(binding) {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                textEditCpfCnpj.text?.clear()
                updateCpfCnpjMask(checkedId)
            }
        }
    }

    private fun updateCpfCnpjMask(checkedId: Int) {
        with(binding) {
            val mask = maskMap[checkedId] ?: ""

            applyMaskToEditText(textEditCpfCnpj, mask)

            if (mask == maskMap[R.id.radioPessoaFisica]) {
                textInputCpfCnpj.hint = getString(R.string.hint_cpf)
            } else if (mask == maskMap[R.id.radioPessoaJuridica]) {
                textInputCpfCnpj.hint = getString(R.string.hint_cnpj)
            }
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

    private fun populateUser(user: User) {
        with(binding) {
            inputName.editText?.setText(user.name)
            tvUsername.editText?.setText(user.username)
            tvAddress.editText?.setText(user.address)
            inputPassword.editText?.setText(user.password)
            inputEmail.editText?.setText(user.email)
            inputData.editText?.setText(user.birthdate)
            imageView.setImageBitmap(user.photoUrl.fromBase64())
            textEditCpfCnpj.setText(user.cpf_cnpj)
        }
    }

    private fun setupValidationListeners() {
        setupFieldValidation(
            binding.editPassword,
            binding.inputPassword,
            ValidationUtils::isValidPassword,
            R.string.error_password
        )

        setupFieldValidation(
            binding.editName,
            binding.inputName,
            ValidationUtils::isValidName,
            R.string.error_name
        )

        setupFieldValidation(
            binding.inputTextEmail,
            binding.inputEmail,
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

    private fun showError(errorData: Error?) {
        if (errorData?.message?.contains(getString(R.string.username_j_existe)) == true) {
            view?.showSnackbar(errorData.message.toString())
        } else {
            binding.error.isVisible = true
            binding.scroll.isVisible = false
        }
    }

    private fun setupSubmitButton() {
        if (validateFields()) {
            val user = createUserFromInput()
            if (isUpdateUser()) {
                userViewModel.updateUser(user)
            } else {
                userViewModel.addUser(user)

            }
        }
    }

    private fun validateFields(): Boolean {
        val passwordValid = validateField(
            binding.editPassword.text.toString(),
            binding.inputPassword,
            ValidationUtils::isValidPassword,
            R.string.error_password
        )
        val nameValid = validateField(
            binding.editName.text.toString(),
            binding.inputName,
            ValidationUtils::isValidName,
            R.string.error_name
        )
        val emailValid = validateField(
            binding.inputTextEmail.text.toString(),
            binding.inputEmail,
            ValidationUtils::isValidEmail,
            R.string.error_email
        )
        val dataValid = validData()
        val cpf_cnpj = binding.textEditCpfCnpj.error == null

        return passwordValid && nameValid && emailValid && cpf_cnpj && dataValid
    }

    private fun validData(): Boolean {
        val idade = calculateAgeFromBirthdate(binding.editData.text.toString())
        if (idade >= 18) {
            binding.inputData.error = null
            binding.inputData.isErrorEnabled = false
        } else {
            binding.inputData.error = getString(R.string.a_pessoa_tem_menos_de_18_anos)
        }
        return idade >= 18
    }

    private fun getBitmapFromImageView(imageView: ImageView): Bitmap? {
        val drawable = imageView.drawable
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else null
    }

    private fun getImageSizeInBytes(bitmap: Bitmap): Long {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray().size.toLong()
    }

    private fun getSelectRadioButton(): String {
        return when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioPessoaFisica -> getString(R.string.hint_cpf)
            R.id.radioPessoaJuridica -> getString(R.string.hint_cnpj)
            else -> ""
        }
    }

    private fun createUserFromInput(): User {
        val name = binding.inputName.editText?.text.toString()
        val username = binding.tvUsername.editText?.text.toString()
        val password = binding.inputPassword.editText?.text.toString()
        val email = binding.inputEmail.editText?.text.toString()
        val birthdate = binding.inputData.editText?.text.toString()
        val sex = binding.inputData.editText?.text.toString()
        val type = getSelectRadioButton()
        val cpf_cnpj = binding.textInputCpfCnpj.editText?.text.toString()
        val address = binding.tvAddress.editText?.text.toString()
        val bitmap = getBitmapFromImageView(binding.imageView)

        val resizedBitmap = if (bitmap != null && getImageSizeInBytes(bitmap) > maxSizeInBytes) {
            bitmap.resizeToMaxSize(maxSizeInBytes)
        } else {
            bitmap
        }

        val photoUrl = resizedBitmap?.toBase64(80) ?: ""

        return User(
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

    companion object {
        private const val SELECT_IMAGE_REQUEST = 1
    }
}