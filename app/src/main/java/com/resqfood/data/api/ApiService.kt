package com.resqfood.data.api

import com.resqfood.data.response.DeleteDonation
import com.resqfood.data.response.DeleteSell
import com.resqfood.data.response.DetailDonationResponse
import com.resqfood.data.response.DetailSellResponse
import com.resqfood.data.response.DonationResponse
import com.resqfood.data.response.LoginResponse
import com.resqfood.data.response.PostDonationResponse
import com.resqfood.data.response.PostSellResponse
import com.resqfood.data.response.RegisterResponse
import com.resqfood.data.response.SellResponse
import com.resqfood.data.response.SellSearch
import com.resqfood.data.response.UpdateUser
import com.resqfood.data.response.UserDonation
import com.resqfood.data.response.UserInfo
import com.resqfood.data.response.UserSell
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("profile/{user_id}")
    fun getUserInfo(
        @Path("user_id") id: String
    ): Call<UserInfo>

    @Multipart
    @PUT("profile/{user_id}")
    fun updateUserInfo(
        @Path("user_id") userId: String,
        @Part("username") username: RequestBody,
        @Part("nama_lengkap") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("no_hp") noHp: RequestBody,
        @Part profileImg: MultipartBody.Part
    ): Call<UpdateUser>

    @GET("profile/donation/{user_id}")
    fun getUserDonationInfo(
        @Path("user_id") id: String
    ): Call<UserDonation>

    @GET("profile/sell/{user_id}")
    fun getUserSellInfo(
        @Path("user_id") id: String
    ): Call<UserSell>

    @DELETE("profile/donation/{donation_id}")
    fun deleteDonation(
        @Path("donation_id") id: String
    ): Call<DeleteDonation>

    @DELETE("profile/sell/{sell_id}")
    fun deleteSell(
        @Path("sell_id") id: String
    ): Call<DeleteSell>

    @GET("sell")
    fun getSale(): Call<SellResponse>

    @GET("sell/{user_id}")
    fun getSellUser(
        @Path("user_id") id: String
    ): Call<DetailSellResponse>

    @GET("donation")
    fun getDonation(): Call<DonationResponse>


    @GET("donation/{user_id}")
    fun getDonationUser(
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

    @Multipart
    @POST("sell")
    fun postSale(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("deskripsi") description: RequestBody,
        @Part("expire") expired: RequestBody,
        @Part("harga") price: RequestBody,
        @Part("user_id") userId: RequestBody
    ): Call<PostSellResponse>

    @GET("sell/search")
    fun getSearchSell(
        @Query("title") title: String
    ): Call<SellSearch>
}