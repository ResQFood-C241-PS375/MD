package com.resqfood.data.pref

data class UserModel(
    val username: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean = false
)
