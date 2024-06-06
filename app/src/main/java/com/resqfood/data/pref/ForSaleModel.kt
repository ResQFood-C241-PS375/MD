package com.resqfood.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaleModel(
    val sale: List<ForSaleModel>
) : Parcelable

@Parcelize
data class ForSaleModel(
    val title: String,
    val image: Int
) : Parcelable
