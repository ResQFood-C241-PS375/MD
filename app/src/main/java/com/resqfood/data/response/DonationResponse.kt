package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class DonationResponse(

	@field:SerializedName("donations")
	val donations:  List<Donation> = emptyList(),

	@field:SerializedName("message")
	val message: String? = null
)

