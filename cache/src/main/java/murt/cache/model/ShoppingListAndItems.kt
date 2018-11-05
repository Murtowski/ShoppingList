package murt.cache.model

import androidx.room.*

/**
 * Piotr Murtowski on 20.02.2018.
 */

class ShoppingListAndItems(
    @Embedded
    var shoppingList: ShoppingListCache = ShoppingListCache.emptyInstance(),
    @Relation(
        parentColumn = ShoppingListCache.COLUMN_PARENT_ID,
        entityColumn = ShoppingItemCache.ENTITY_COLUMN)
    var shoppingItems: List<ShoppingItemCache> = emptyList()
)