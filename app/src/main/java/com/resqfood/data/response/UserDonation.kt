package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class UserDonation(

	@field:SerializedName("donations")
	val donations: List<DonationsItem> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class DonationsItem(

	@field:SerializedName("donation_img")
	val donationImg: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("donation_id")
	val donationId: String
) : Parcelable
