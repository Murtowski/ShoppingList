package murt.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import murt.cache.typeConverter.LocalDateTimeConverter
import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Entity(tableName = ShoppingItemCache.TABLE_NAME)
class ShoppingItemCache(
    @PrimaryKey(autoGenerate = true)
    var id: Long = -1L,
    @ColumnInfo(name = ENTITY_COLUMN)
    var shoppingListId: Long = -1,
    @TypeConverters(LocalDateTimeConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var title: String = ""){

    companion object {
        const val ENTITY_COLUMN = "shoppingListId"
        const val TABLE_NAME = "ShoppingItem"
    }
}