package com.example.useradministration.data.model

import com.example.database.User

data class UserRequest(
    val nome: String,
    val username: String,
    val password: String,
    val foto: String,
    val endereco: String,
    val email: String,
    val dataNascimento: Long,
    val sexo: Int,
    val cpfCnpj: String
)

fun User.toUserRequest(): UserRequest {
    return UserRequest(
        nome = this.name,
        username = this.username,
        password = this.password,
        foto = this.photoUrl,
        endereco = this.address,
        email = this.email,
        dataNascimento =10,
        sexo = 1,
        cpfCnpj = this.cpf_cnpj
    )
}