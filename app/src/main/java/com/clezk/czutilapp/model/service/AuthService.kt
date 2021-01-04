package com.clezk.czutilapp.model.service

import com.clezk.czutilapp.model.dto.LoginInfo
import com.clezk.czutilapp.model.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("api/auth/login")
    fun login(@Body loginInfo: LoginInfo): Single<LoginResponse>
}