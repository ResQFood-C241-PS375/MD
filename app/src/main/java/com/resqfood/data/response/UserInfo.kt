package com.resqfood.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserInfo(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("users")
	val users: List<UsersItem>? = null
) : Parcelable

@Parcelize
data class UsersItem(

	@field:SerializedName("profile_img")
	val profileImg: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
