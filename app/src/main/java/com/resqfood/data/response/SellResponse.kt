package com.resqfood.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SellResponse(

	@field:SerializedName("sells")
	val sells: List<Sell> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

//@Parcelize
//data class SellsItem(
//
//	@field:SerializedName("sell_img")
//	val sellImg: String? = null,
//
//	@field:SerializedName("sell_id")
//	val sellId: String? = null,
//
//	@field:SerializedName("harga")
//	val harga: String? = null,
//
//	@field:SerializedName("user_id")
//	val userId: String? = null,
//
//	@field:SerializedName("expire")
//	val expire: String? = null,
//
//	@field:SerializedName("deskripsi")
//	val deskripsi: String? = null,
//
//	@field:SerializedName("title")
//	val title: String? = null
//): Parcelable
