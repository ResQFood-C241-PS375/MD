package com.resqfood.data.api

import com.resqfood.data.response.LoginResponse
import com.resqfood.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("register")
    fun register(
        @Part("username") username: RequestBody,
        @Part("nama_lengkap") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confirmPass") confirmPass: RequestBody,
        @Part("no_hp") noHp: RequestBody,
        @Part profileImg: MultipartBody.Part
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>


}