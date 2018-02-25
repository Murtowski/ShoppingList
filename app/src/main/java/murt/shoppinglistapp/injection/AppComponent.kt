package murt.shoppinglistapp.injection

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import murt.shoppinglistapp.AndroidApplication
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */
@Singleton
@Component(modules = [ContextModule::class, CacheModule::class, RemoteModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
//
//        @BindsInstance
//        fun contextModule(contextModule: ContextModule): Builder
//
//        @BindsInstance
//        fun cacheModule(cacheModule: CacheModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: AndroidApplication)
}