package com.clezk.czutilapp.state

import com.clezk.czutilapp.model.dto.User

sealed class ApplicationEvent

object DisplayLoading: ApplicationEvent()

object HideLoading: ApplicationEvent()

object InItApp: ApplicationEvent()

data class UpdateAuthState(val user: User): ApplicationEvent()