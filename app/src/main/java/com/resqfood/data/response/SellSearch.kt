package com.resqfood.data.response

import com.google.gson.annotations.SerializedName

data class SellSearch(

	@field:SerializedName("sells")
	val sells: List<Sell> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)