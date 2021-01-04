package com.clezk.czutilapp.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    val access_token: String?,
    val error: String?,
    val name: String?,
    val email: String?,
    val id: Int?
)