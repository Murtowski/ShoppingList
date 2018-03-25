package murt.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import murt.cache.dao.ShoppingListDao
import murt.cache.model.ShoppingItemCache
import murt.cache.model.ShoppingListAndItems
import murt.cache.model.ShoppingListCache
import murt.cache.typeConverter.LocalDateTimeConverter

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Database(entities = [
    ShoppingItemCache::class,
    ShoppingListCache::class
], version = 3)

@TypeConverters(
    LocalDateTimeConverter::class
)
abstract class RoomDatabaseCache: RoomDatabase(){
    abstract fun shoppingListDao(): ShoppingListDao
}