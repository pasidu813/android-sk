package com.clezk.czutilapp.components.login

import android.util.Log
import com.clezk.czutil.Navigator
import com.clezk.czutilapp.util.MobiusVM
import com.clezk.czutilapp.components.home.HomeFragment
import com.clezk.czutilapp.model.AuthRepository
import com.clezk.czutilapp.model.SessionManager
import com.clezk.czutilapp.model.dto.User
import com.clezk.czutilapp.state.ApplicationState
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject
import com.spotify.mobius.Next.dispatch

import java.util.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun loginFragmentUpdate(
    model: LoginFragmentModel,
    event: LoginFragmentEvent
): Next<LoginFragmentModel, LoginFragmentEffect> {
    return when (event) {
        is DisplayLoading -> dispatch(setOf(ShowLoader(true)))
        is HideLoading -> dispatch(setOf(ShowLoader(false)))
        is Login -> dispatch(setOf(LoginEffect(event.email, event.password), ShowLoader(true)))
        is LoginFailed -> Next.next(model.copy(error = true, error_message = event.error, login = false), setOf(ShowLoader(false)))
        is LoginSuccess -> dispatch(setOf(ShowLoader(false), GenerateSession(event.user)))
    }
}

class LoginFragmentViewModel @Inject constructor(
    applicationState: ApplicationState,
    authRepository: AuthRepository,
    sessionManager: SessionManager,
    navigator: Navigator
) : MobiusVM<LoginFragmentModel, LoginFragmentEvent, LoginFragmentEffect>(
    "LoginFragmentViewModel",
    Update(::loginFragmentUpdate),
    LoginFragmentModel(),
    RxMobius.subtypeEffectHandler<LoginFragmentEffect, LoginFragmentEvent>()
        .addTransformer(LoginEffect::class.java) { upstream ->
            upstream.switchMap { effect ->
                authRepository.userLogin(effect.email, effect.password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map { response ->
                        LoginSuccess(User(response.id!!, response.name!!, response.email!!, response.access_token!!, true)) as LoginFragmentEvent
                    }
                    .onErrorReturn { e -> LoginFailed(e.message.toString()) }
            }
        }
        .addConsumer(ShowLoader::class.java) { event ->
            if (event.visible){
                applicationState.dispactEvent(com.clezk.czutilapp.state.DisplayLoading)
            }else{
                applicationState.dispactEvent(com.clezk.czutilapp.state.HideLoading)
            }
        }
        .addConsumer(GenerateSession::class.java){
            sessionManager.createSession(it.user)
            navigator.to(LoginFragmentDirections.loggedIn())
        }
        .build()
)