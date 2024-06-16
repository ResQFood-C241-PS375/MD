package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailSellResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: Userz? = null
) : Parcelable

@Parcelize
data class Sell(

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

@Parcelize
data class Userz(

	@field:SerializedName("profile_img")
	val profileImg: String,

	@field:SerializedName("no_hp")
	val noHp: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("sell")
	val sell: Sell,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String
) : Parcelable
