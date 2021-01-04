package com.clezk.czutilapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clezk.czutilapp.model.dto.AuthDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class AuthDao {


    @Query("SELECT * FROM AuthDto WHERE id=1")
    abstract fun getProduct(): Single<AuthDto>

    @Insert
    abstract fun insert(authDto: AuthDto): Completable

    @Query("UPDATE authdto SET name=:name, email=:email, token=:token WHERE id=1")
    abstract fun update(name: String, email: String, token: String): Completable
}