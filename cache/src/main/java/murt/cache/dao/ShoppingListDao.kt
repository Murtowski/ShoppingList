package murt.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import murt.cache.model.ShoppingListAndItems
import murt.cache.model.ShoppingListCache

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Dao
interface ShoppingListDao {

    @Query("""SELECT * FROM ${ShoppingListCache.TABLE_NAME}
        WHERE ${ShoppingListCache.COLUMN_IS_ARCHIVED} = :arg0""")
    fun getListOfShoppingList(isArchived: Boolean): List<ShoppingListAndItems>

    @Query("""SELECT * FROM ${ShoppingListCache.TABLE_NAME}
        WHERE ${ShoppingListCache.COLUMN_PARENT_ID} = :arg0""")
    fun getShoppingList(id: Long): ShoppingListAndItems

    @Update()
    fun updateShoppingList(shoppingListAndItems: ShoppingListAndItems)
}