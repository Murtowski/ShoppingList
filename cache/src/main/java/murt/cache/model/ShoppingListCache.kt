package murt.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import murt.cache.typeConverter.LocalDateTimeConverter
import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 22.02.2018.
 */
@Entity(tableName = ShoppingListCache.TABLE_NAME)
class ShoppingListCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_PARENT_ID)
    var id: Long? = null,
    var title: String = "",
    @TypeConverters(LocalDateTimeConverter::class)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = COLUMN_IS_ARCHIVED)
    var isArchived: Boolean = false
) {

    companion object {
        const val TABLE_NAME = "ShoppingList"
        const val COLUMN_PARENT_ID = "id"
        const val COLUMN_IS_ARCHIVED = "isArchived"


        fun emptyInstance() = ShoppingListCache(null, "", LocalDateTime.now(), true)
    }
}