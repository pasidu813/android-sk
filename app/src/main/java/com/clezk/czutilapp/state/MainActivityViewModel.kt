package com.clezk.czutilapp.state

import com.clezk.czutilapp.util.MobiusVM
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject


//fun mainActivityUpdate(
//    model: ApplicationModel,
//    event: MainActivityEvent
//): Next<ApplicationModel, ApplicationEffect> {
//    return when (event){
//        is DisplayLoading -> next(model.copy(loading = true))
//        is HideLoading -> next(model.copy(loading = false))
//    }
//}
//
//class MainActivityViewModel @Inject constructor(
//
//) : MobiusVM<ApplicationModel, MainActivityEvent, ApplicationEffect>(
//    "MainActivityViewModel",
//    Update(::mainActivityUpdate),
//    ApplicationModel(),
//    RxMobius.subtypeEffectHandler<ApplicationEffect, MainActivityEvent>()
//
//        .build()
//){
//
//}