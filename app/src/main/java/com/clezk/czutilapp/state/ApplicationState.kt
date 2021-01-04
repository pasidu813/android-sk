package com.clezk.czutilapp.state

import androidx.lifecycle.ViewModel
import com.clezk.czutil.Navigator
import com.clezk.czutilapp.components.login.LoginFragmentDirections
import com.clezk.czutilapp.model.SessionManager
import com.clezk.czutilapp.model.dto.User
import com.clezk.czutilapp.util.BaseViewModel
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.functions.Producer
import com.spotify.mobius.rx2.RxEventSources
import com.spotify.mobius.rx2.RxMobius
import com.spotify.mobius.rx2.SchedulerWorkRunner
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


fun applicationUpdate(
    model: ApplicationModel,
    event: ApplicationEvent
): Next<ApplicationModel, ApplicationEffect> {
    return when (event){
        is DisplayLoading -> Next.next(model.copy(loading = true))
        is HideLoading -> Next.next(model.copy(loading = false))
        is InItApp -> Next.dispatch(setOf(InItAppEffect))
        is UpdateAuthState -> Next.next(model.copy(user = event.user), setOf(NavigateIfAuthenticated(event.user)))
    }
}


class ApplicationState @Inject constructor(
    sessionManager: SessionManager,
    navigator: Navigator
){

    private val effectHandler = RxMobius.subtypeEffectHandler<ApplicationEffect, ApplicationEvent>()
        .addConsumer(InItAppEffect::class.java){
            UpdateAuthState(sessionManager.user())
        }
        .addConsumer(NavigateIfAuthenticated::class.java){
            if(it.user.isLoggedIn){
                navigator.to(LoginFragmentDirections.loggedIn())
            }
        }
        .build()


    private val loop: MobiusLoop<ApplicationModel, ApplicationEvent, ApplicationEffect>

    init {
        var builder = RxMobius
            .loop(Update(::applicationUpdate), effectHandler)
            .eventRunner(Producer { SchedulerWorkRunner(AndroidSchedulers.mainThread()) })
            .effectRunner(Producer { SchedulerWorkRunner(AndroidSchedulers.mainThread()) })
            .logger(AndroidLogger("ApplicationState"))


        loop = builder.startFrom(ApplicationModel())
    }







    fun getModel(): Observable<ApplicationModel> {
        return Observable.create{ emitter ->
            val modelDisposable = loop.observe { model -> emitter.onNext(model) }
            emitter.setCancellable {
                modelDisposable.dispose()
            }
        }
    }


    fun dispactEvent(event: ApplicationEvent) {
        loop.dispatchEvent(event)
    }



    fun onDesctroy() {
        loop.dispose()
    }

}
