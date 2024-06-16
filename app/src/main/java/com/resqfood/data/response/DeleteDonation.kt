package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DeleteDonation(

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
