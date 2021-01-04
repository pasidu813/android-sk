package com.clezk.czutilapp.util.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.clezk.czutil.ActivityService
import com.clezk.czutil.Navigator
import com.clezk.czutilapp.R
import com.clezk.czutilapp.components.login.LoginFragmentViewModel
import com.clezk.czutilapp.model.ApplicationDatabase
import com.clezk.czutilapp.model.SessionManager
import com.clezk.czutilapp.model.service.AuthService
import com.clezk.czutilapp.state.ApplicationModel
import com.clezk.czutilapp.state.ApplicationState
import com.clezk.czutilapp.util.base_url
import dagger.*
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, ApiModule::class, DatabaseModule::class])
interface ApplicationComponent{
    fun viewModelFactory(): ViewModelProvider.Factory

    fun activityService(): ActivityService

    fun applicationState(): ApplicationState

    fun sessionManager(): SessionManager

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Module
object ApplicationModule{
    @Provides
    @Singleton
    @JvmStatic
    fun viewModels(providers: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(providers)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun activityService(): ActivityService = ActivityService()

    @Provides
    @Singleton
    @JvmStatic
    fun navigator(activityService: ActivityService): Navigator {
        return Navigator(R.id.navigation_host_fragment, activityService)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun getApplicationState(sessionManager: SessionManager, navigator: Navigator): ApplicationState = ApplicationState(sessionManager, navigator)

}


@Module
abstract class ViewModelModule{


    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun loginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel
}


@Module
object ApiModule {

    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun authService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}



@Module
object DatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun applicationDatabase(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(context, ApplicationDatabase::class.java, "application")
            .build()
    }




    @Provides
    @Singleton
    @JvmStatic
    fun authDao(database: ApplicationDatabase) = database.authDao()
}