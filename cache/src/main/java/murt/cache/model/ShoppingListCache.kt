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
    @ColumnInfo(name = PARENT_COLUMN)
    var id: Long = -1L,
    var title: String = "",
    @TypeConverters(LocalDateTimeConverter::class)
    var createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {
        const val TABLE_NAME = "ShoppingList"
        const val PARENT_COLUMN = "id"

        fun emptyInstance() = ShoppingListCache(-1L, "", LocalDateTime.now())
    }
}