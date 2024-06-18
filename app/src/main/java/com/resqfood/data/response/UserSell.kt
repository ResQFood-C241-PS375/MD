package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserSell(

	@field:SerializedName("sells")
	val sells: List<SellsItems> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class SellsItems(

	@field:SerializedName("sell_img")
	val sellImg: String? = null,

	@field:SerializedName("sell_id")
	val sellId: String,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("expire")
	val expire: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable
