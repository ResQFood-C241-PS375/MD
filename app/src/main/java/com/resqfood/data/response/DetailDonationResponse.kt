package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DetailDonationResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: Users? = null
)

@Parcelize
data class Donation(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("donation_id")
	val donationId: String
) : Parcelable

@Parcelize
data class Users(

	@field:SerializedName("no_hp")
	val noHp: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("profile_img")
	val profileImg: String,

	@field:SerializedName("donation")
	val donation: Donation
) : Parcelable
