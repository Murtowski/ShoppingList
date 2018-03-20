package murt.shoppinglistapp.injection

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import murt.shoppinglistapp.AndroidApplication
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */
@Singleton
@Component(modules = [
    ContextModule::class,
    CacheModule::class,
    RemoteModule::class,
    BinderModule::class,
    AndroidSupportInjectionModule::class // support v4.Fragment
])

interface AppComponent: AndroidInjector<AndroidApplication> {



    @Component.Builder
//    abstract class Builder: AndroidInjector.Builder<AndroidApplication>()
    interface Builder{
        @BindsInstance
        fun application(app: Application):Builder
        fun build(): AppComponent
    }
}