package com.resqfood.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DonationModel(
    val title: String,
    val image: Int
) : Parcelable
