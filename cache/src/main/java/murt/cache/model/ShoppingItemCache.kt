package murt.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Entity(tableName = ShoppingItemCache.TABLE_NAME)
class ShoppingItemCache(
    @PrimaryKey(autoGenerate = true)
    var id: Long = -1L,
    @ColumnInfo(name = ENTITY_COLUMN)
    var shoppingListId: Long = -1,
    var title: String = ""){

    companion object {
        const val ENTITY_COLUMN = "shoppingListId"
        const val TABLE_NAME = "ShoppingItem"
    }
}