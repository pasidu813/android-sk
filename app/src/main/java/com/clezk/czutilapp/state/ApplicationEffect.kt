package com.clezk.czutilapp.state

import com.clezk.czutilapp.model.dto.User

sealed class ApplicationEffect

object InItAppEffect: ApplicationEffect()

data class NavigateIfAuthenticated(val user: User): ApplicationEffect()
