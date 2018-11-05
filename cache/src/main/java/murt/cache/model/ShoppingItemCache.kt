package murt.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import murt.cache.typeConverter.LocalDateTimeConverter
import murt.cache.typeConverter.ZonedDateTimeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Entity(tableName = ShoppingItemCache.TABLE_NAME)
data class ShoppingItemCache constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long ?,
    @ColumnInfo(name = ENTITY_COLUMN)
    val shoppingListId: Long ,
    @TypeConverters(ZonedDateTimeConverter::class)
    val createdAt: ZonedDateTime ,
    val title: String ){

    companion object {
        const val ENTITY_COLUMN = "shoppingListId"
        const val TABLE_NAME = "ShoppingItem"
    }
}