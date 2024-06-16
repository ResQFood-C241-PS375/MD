package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class PostSellResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: Userz? = null
)

//@Parcelize
//data class User(
//
//	@field:SerializedName("profile_img")
//	val profileImg: String? = null,
//
//	@field:SerializedName("no_hp")
//	val noHp: String? = null,
//
//	@field:SerializedName("user_id")
//	val userId: String? = null,
//
//	@field:SerializedName("sell")
//	val sell: Sell? = null,
//
//	@field:SerializedName("nama_lengkap")
//	val namaLengkap: String? = null
//) : Parcelable
//
//@Parcelize
//data class Sell(
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
//	@field:SerializedName("expire")
//	val expire: String? = null,
//
//	@field:SerializedName("deskripsi")
//	val deskripsi: String? = null,
//
//	@field:SerializedName("title")
//	val title: String? = null
//) : Parcelable
