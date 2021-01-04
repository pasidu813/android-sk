package com.clezk.czutilapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class AuthDto(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val token: String
)