package murt.cache

import androidx.room.Room
import android.content.Context

/**
 * Piotr Murtowski on 25.02.2018.
 */
class CacheServiceFactory {

    fun createRemoteService(context: Context): RoomDatabaseCache{
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDatabaseCache::class.java,
            "RoomDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}