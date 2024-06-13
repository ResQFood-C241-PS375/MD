package com.resqfood.data.api

import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.data.response.DonationResponse
import com.resqfood.data.response.LoginResponse
import com.resqfood.data.response.PostDonationResponse
import com.resqfood.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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

    @GET("donation")
    fun getDonation(): Call<DonationResponse>

//    @GET("donation/{donation_id}")
//    fun getDetailDonation(
//        @Path("donation_id") id: String
//    ): Call<DetailDonationResponse>

    @GET("donation/{user_id}")
    fun getDetailDonation(
        @Path("user_id") id: String
    ): Call<DetailDonationResponse>

    @Multipart
    @POST("donation")
    fun postDonation(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("deskripsi") description: RequestBody,
        @Part("location") location: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Call<PostDonationResponse>
}