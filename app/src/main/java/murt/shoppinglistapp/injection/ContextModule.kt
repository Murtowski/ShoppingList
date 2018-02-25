package murt.shoppinglistapp.injection

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */

@Module
open class ContextModule {

    @Singleton
    @Provides
    fun provideResources(application: Application): Resources {
        return application.resources
    }
}