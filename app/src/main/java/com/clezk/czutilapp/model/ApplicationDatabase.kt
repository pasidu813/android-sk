package com.clezk.czutilapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clezk.czutilapp.model.dao.AuthDao
import com.clezk.czutilapp.model.dto.AuthDto

@Database(
    entities = [AuthDto::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao
}