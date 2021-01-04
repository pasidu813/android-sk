package com.clezk.czutilapp.model.dto

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
    val isLoggedIn: Boolean
)