package murt.shoppinglistapp.injection

import dagger.Component
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */
@Singleton
@Component(modules = [ContextModule::class, CacheModule::class, RemoteModule::class])
interface AppComponent {

}