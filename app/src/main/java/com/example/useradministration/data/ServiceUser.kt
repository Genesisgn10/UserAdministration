package com.example.useradministration.data

import com.example.useradministration.data.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ServiceUser {

    @POST("Desafio/rest/desafioRest")
    fun postUser(@Body userRequest: UserRequest) : Response<ResponseBody>

}