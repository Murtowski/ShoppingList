package murt.shoppinglistapp.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import murt.cache.CacheServiceFactory
import murt.cache.CacheServiceImpl
import murt.cache.RoomDatabaseCache
import murt.data.repository.CacheService
import javax.inject.Singleton

/**
 * Piotr Murtowski on 25.02.2018.
 */

@Module
open class CacheModule(val context: Context) {

    @Singleton
    @Provides
    fun provideCacheService(database: RoomDatabaseCache): CacheService {
        return CacheServiceImpl(database)
    }

    @Singleton
    @Provides
    fun provideDatabase(): RoomDatabaseCache {
        return CacheServiceFactory().createRemoteService(context)
    }
}