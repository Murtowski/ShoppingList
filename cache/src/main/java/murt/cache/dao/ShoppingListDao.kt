package murt.cache.dao

import android.arch.persistence.room.*
import io.reactivex.Completable
import murt.cache.model.ShoppingItemCache
import murt.cache.model.ShoppingListAndItems
import murt.cache.model.ShoppingListCache

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Dao
interface ShoppingListDao {

    @Query("""SELECT * FROM ${ShoppingListCache.TABLE_NAME}
        WHERE ${ShoppingListCache.COLUMN_IS_ARCHIVED} = :isArchived""")
    fun getListOfShoppingList(isArchived: Boolean): List<ShoppingListAndItems>

    @Query("""SELECT * FROM ${ShoppingListCache.TABLE_NAME}
        WHERE ${ShoppingListCache.COLUMN_PARENT_ID} = :id""")
    fun getShoppingList(id: Long): ShoppingListAndItems

    @Update()
    fun updateShoppingList(shoppingList: ShoppingListCache)

    @Update
    fun updateShoppingItems(shoppingItemsList: List<ShoppingItemCache>)

    @Insert()
    fun insertShoppingList(shoppingListCache: ShoppingListCache): Long

    @Insert
    fun insertShoppingItems(shoppingItemsList: List<ShoppingItemCache>): List<Long>

    /**
     * Shopping items
     * */
    @Update
    fun updateShoppingItem(shoppingItemCache: ShoppingItemCache)

    @Insert
    fun insertShoppingItem(shoppingItemCache: ShoppingItemCache): Long

    @Delete
    fun deleteShoppingItem(shoppingItemCache: ShoppingItemCache)
}