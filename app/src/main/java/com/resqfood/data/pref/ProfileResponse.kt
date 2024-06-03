package com.resqfood.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileResponse(
    val profile: Profile
) : Parcelable

@Parcelize
data class Profile(
    val username: String,
    val fullname: String,
    val email: String,
    val password: String,
    val whatsapp: String,
    val pictureID: String
) : Parcelable
