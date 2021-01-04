package com.clezk.czutilapp.components.login

import com.clezk.czutilapp.model.dto.User

sealed class LoginFragmentEffect

data class ShowLoader(val visible: Boolean) : LoginFragmentEffect()

data class LoginEffect(val email: String, val password: String): LoginFragmentEffect()

data class GenerateSession(val user: User) : LoginFragmentEffect()