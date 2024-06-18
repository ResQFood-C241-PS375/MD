package com.resqfood.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteSell(

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
