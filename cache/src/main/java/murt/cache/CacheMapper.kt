package murt.cache

import murt.cache.model.ShoppingItemCache
import murt.cache.model.ShoppingListAndItems
import murt.cache.model.ShoppingListCache
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList

/**
 * Piotr Murtowski on 26.02.2018.
 */
object CacheMapper {

    fun mapCacheToApp1(listOfShoppingItemsAndListAndItems: List<ShoppingListAndItems>): List<ShoppingList>{
        return listOfShoppingItemsAndListAndItems
            .map { mapCacheToApp(it) }
    }

    fun mapCacheToApp(shoppingListAndItems: ShoppingListAndItems): ShoppingList{
        return ShoppingList(
            id = shoppingListAndItems.shoppingList.id,
            updatedAt = shoppingListAndItems.shoppingList.updatedAt,
            title = shoppingListAndItems.shoppingList.title,
            items = mapCacheToApp(shoppingListAndItems.shoppingItems),
            isArchived = shoppingListAndItems.shoppingList.isArchived
        )
    }

    fun mapCacheToApp(listOfShoppingItemsCache: List<ShoppingItemCache>): List<ShoppingItem>{
        return listOfShoppingItemsCache
            .map { mapCacheToApp(it) }
    }

    fun mapCacheToApp(shoppingItemCache: ShoppingItemCache): ShoppingItem{
        return ShoppingItem(
            shoppingItemCache.id,
            shoppingItemCache.title,
            shoppingItemCache.createdAt
        )
    }

    fun mapAppToCache(shoppingList: ShoppingList): ShoppingListCache  {
        return ShoppingListCache(
            shoppingList.id,
            shoppingList.title,
            shoppingList.updatedAt,
            shoppingList.isArchived)
    }

    fun mapAppToShoppingListAndItemsCache(shoppingList: ShoppingList): ShoppingListAndItems{
        val shoppingListCache = mapAppToCache(shoppingList)
        val shoppingItemsCache = mapAppToCache(shoppingList.items, shoppingList.id!!)
        return ShoppingListAndItems(shoppingListCache, shoppingItemsCache)
    }


    fun mapAppToCache(shoppingItem: ShoppingItem, shoppingListId: Long): ShoppingItemCache{
        return ShoppingItemCache(
            shoppingItem.id,
            shoppingListId,
            shoppingItem.updatedAt,
            shoppingItem.title
        )
    }

    fun mapAppToCache(shoppingItems: List<ShoppingItem>, shoppingItemId: Long): List<ShoppingItemCache>{
        return shoppingItems
            .map { mapAppToCache(it, shoppingItemId) }
    }

}