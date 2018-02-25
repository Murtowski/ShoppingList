package murt.cache.model

import android.arch.persistence.room.*
import murt.cache.typeConverter.LocalDateTimeConverter
import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */

class ShoppingListAndItems(
    @Embedded
    var shoppingList: ShoppingListCache = ShoppingListCache.emptyInstance(),
    @Relation(parentColumn = ShoppingListCache.PARENT_COLUMN, entityColumn = ShoppingItemCache.ENTITY_COLUMN)
    var shoppingItems: List<ShoppingItemCache> = emptyList()
)