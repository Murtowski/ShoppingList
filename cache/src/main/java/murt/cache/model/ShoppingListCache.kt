package murt.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import murt.cache.typeConverter.LocalDateTimeConverter
import murt.cache.typeConverter.ZonedDateTimeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime

/**
 * Piotr Murtowski on 22.02.2018.
 */
@Entity(tableName = ShoppingListCache.TABLE_NAME)
data class ShoppingListCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_PARENT_ID)
    val id: Long?,
    val title: String,
    @TypeConverters(ZonedDateTimeConverter::class)
    val updatedAt: ZonedDateTime,
    @ColumnInfo(name = COLUMN_IS_ARCHIVED)
    val isArchived: Boolean
) {

    companion object {
        const val TABLE_NAME = "ShoppingList"
        const val COLUMN_PARENT_ID = "id"
        const val COLUMN_IS_ARCHIVED = "isArchived"


        fun emptyInstance() = ShoppingListCache(null, "", ZonedDateTime.now(), true)
    }
}