package murt.shoppinglistapp.injection

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */

@Module
open class ContextModule(val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideResources(): Resources {
        return context.resources
    }
}