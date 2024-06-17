package com.resqfood.data.response

import com.google.gson.annotations.SerializedName

data class UpdateUser(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: UserUpdate
)

data class UserUpdate(

	@field:SerializedName("profile_img")
	val profileImg: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("no_hp")
	val noHp: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
