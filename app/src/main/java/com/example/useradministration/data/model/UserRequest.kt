package com.example.useradministration.data.model
data class UserRequest(
    val nome: String,
    val username: String,
    val password: String,
    val foto: String, // Este é um exemplo de como você pode representar uma imagem como String
    val endereco: String,
    val email: String,
    val dataNascimento: Long, // Timestamp como um Long
    val sexo: Int,
    val cpfCnpj: String
)