package com.clezk.czutilapp.components.login

import com.clezk.czutilapp.model.dto.User

sealed class LoginFragmentEvent

object DisplayLoading: LoginFragmentEvent()

object HideLoading: LoginFragmentEvent()

data class Login(val email: String, val password: String): LoginFragmentEvent()

data class LoginFailed(val error: String): LoginFragmentEvent()

data class LoginSuccess(val user: User): LoginFragmentEvent()