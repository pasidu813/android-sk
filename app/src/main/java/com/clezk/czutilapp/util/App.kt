package com.clezk.czutilapp.util

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.clezk.czutilapp.util.di.ApplicationComponent
import com.clezk.czutilapp.util.di.DaggerApplicationComponent
import kotlin.reflect.KClass

class App: Application(){

    val component by lazy {
        DaggerApplicationComponent.builder()
            .context(this)
            .build()
    }
}

val Context.component: ApplicationComponent
    get() = (this.applicationContext as App).component

fun <T, M, E> Fragment.getViewModel(type: KClass<T>): BaseViewModel<M, E> where T : ViewModel, T : BaseViewModel<M, E>{
    val factory = this.context!!.component.viewModelFactory()
    return ViewModelProviders.of(this, factory)[type.java]
}


fun <T, M, E> FragmentActivity.getViewModel(type: KClass<T>): BaseViewModel<M, E> where T : ViewModel, T : BaseViewModel<M, E>{
    val factory = this.applicationContext!!.component.viewModelFactory()
    return ViewModelProviders.of(this, factory)[type.java]
}

