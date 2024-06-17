package com.resqfood.data.response

import com.google.gson.annotations.SerializedName

data class SellSearch(

	@field:SerializedName("sells")
	val sells: List<Sell> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
//
//data class SellsItem(
//
//	@field:SerializedName("sell_img")
//	val sellImg: String,
//
//	@field:SerializedName("sell_id")
//	val sellId: String,
//
//	@field:SerializedName("harga")
//	val harga: Int,
//
//	@field:SerializedName("user_id")
//	val userId: String,
//
//	@field:SerializedName("expire")
//	val expire: String,
//
//	@field:SerializedName("deskripsi")
//	val deskripsi: String,
//
//	@field:SerializedName("title")
//	val title: String
//)
