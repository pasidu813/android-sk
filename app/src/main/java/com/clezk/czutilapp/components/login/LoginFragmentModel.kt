package com.clezk.czutilapp.components.login

data class LoginFragmentModel(
    val error: Boolean = false,
    val error_message: String = "",
    val login: Boolean = false
)