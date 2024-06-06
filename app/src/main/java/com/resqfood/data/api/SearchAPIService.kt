package com.resqfood.data.api

import com.resqfood.data.pref.SaleModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// misal seperti ini

interface SearchAPIService {
    @GET("search")
    suspend fun searchProduct(@Query("keyword") keyword: String): Response<SaleModel>
}