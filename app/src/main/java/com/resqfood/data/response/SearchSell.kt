package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SearchRequest(
	val title: String
)

data class SearchSell(

	@field:SerializedName("sells")
	val sells: List<SellsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)


@Parcelize
data class SellsItem(

	@field:SerializedName("sell_img")
	val sellImg: String,

	@field:SerializedName("sell_id")
	val sellId: String,

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("expire")
	val expire: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("title")
	val title: String
) : Parcelable
