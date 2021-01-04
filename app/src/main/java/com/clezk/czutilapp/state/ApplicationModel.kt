package com.clezk.czutilapp.state

import com.clezk.czutilapp.model.dto.User

data class ApplicationModel(
    val loading: Boolean = false,
    val waiting: Boolean = false,
    val user: User = User(0, "", "", "", false)
)