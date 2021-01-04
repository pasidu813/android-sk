package com.clezk.czutilapp.model

import com.clezk.czutilapp.model.dao.AuthDao
import com.clezk.czutilapp.model.dto.LoginInfo
import com.clezk.czutilapp.model.response.LoginResponse
import com.clezk.czutilapp.model.service.AuthService
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val authDao: AuthDao
){

    fun userLogin(email: String, password: String): Single<LoginResponse>{
        return authService.login(LoginInfo(email, password))
    }
}