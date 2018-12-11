package murt.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList

/**
 * Piotr Murtowski on 20.02.2018.
 */
interface CacheService {

    fun getAllShoppingList(isArchived: Boolean): Flowable<List<ShoppingList>>

    fun getShoppingList(id: Long): Single<ShoppingList>

    fun getShoppingListFLowable(id: Long): Flowable<ShoppingList>

    fun updateShoppingListAndItems(shoppingList: ShoppingList): Completable

    fun updateShoppingListDescription(shoppingList: ShoppingList): Completable

    fun deleteShoppingList(shoppingList: ShoppingList): Completable

    fun createShoppingList(shoppingList: ShoppingList): Single<ShoppingList>

    fun updateShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long): Completable

    fun createShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long): Single<ShoppingItem>

    fun deleteShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long): Completable
}