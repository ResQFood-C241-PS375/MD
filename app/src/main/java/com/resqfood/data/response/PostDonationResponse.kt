package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class PostDonationResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: Users? = null
)
//
//@Parcelize
//data class User(
//
//	@field:SerializedName("no_hp")
//	val noHp: String? = null,
//
//	@field:SerializedName("user_id")
//	val userId: String? = null,
//
//	@field:SerializedName("nama_lengkap")
//	val namaLengkap: String? = null,
//
//	@field:SerializedName("donation")
//	val donation: Donation? = null
//) : Parcelable
//
//@Parcelize
//data class Donation(
//
//	@field:SerializedName("image")
//	val image: String? = null,
//
//	@field:SerializedName("user_id")
//	val userId: String? = null,
//
//	@field:SerializedName("location")
//	val location: String? = null,
//
//	@field:SerializedName("deskripsi")
//	val deskripsi: String? = null,
//
//	@field:SerializedName("title")
//	val title: String? = null,
//
//	@field:SerializedName("donation_id")
//	val donationId: String? = null
//) : Parcelable
