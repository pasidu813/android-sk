package com.clezk.czutilapp.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.clezk.czutilapp.model.dto.User
import javax.inject.Inject

class SessionManager @Inject constructor(val context: Context) {
    private val sp: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val mode = 0

    companion object {
        const val PREFER_NAME = "CzPref"
        const val TOKEN = "token"
        const val IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_NAME = "user_name"
        const val KEY_USER_EMAIL = "user_email"
    }

    init {
        sp = context.getSharedPreferences(PREFER_NAME, mode)
        editor = sp.edit()
    }

    fun createSession(user: User) {
        editor.putBoolean(IS_LOGGED_IN, user.isLoggedIn)
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USER_NAME, user.name)
        editor.putString(KEY_USER_EMAIL, user.email)
        editor.putString(TOKEN, user.token)
        editor.commit()
    }

    val isLoggedIn: Boolean
        get() = sp.getBoolean(IS_LOGGED_IN, false)

    val userId: Int
        get() = sp.getInt(KEY_USER_ID, 0)

    val token: String?
        get() = sp.getString(TOKEN, "")

    val headers: String
        get() = "bearer " + sp.getString(TOKEN, null)

    val userName: String?
        get() = sp.getString(KEY_USER_NAME, null)

    val userEmail: String?
        get() = sp.getString(KEY_USER_EMAIL, null)

    fun logOut() {
        editor.clear()
        editor.commit()
    }

    fun user(): User{
        return User(this.userId, this.userName ?: "", this.userEmail ?: "", this.token ?: "", this.isLoggedIn)
    }


}