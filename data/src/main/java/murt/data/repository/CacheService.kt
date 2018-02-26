package murt.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import murt.data.model.ShoppingList

/**
 * Piotr Murtowski on 20.02.2018.
 */
interface CacheService {

    fun getListOfShoppingList(isArchived: Boolean): Flowable<List<ShoppingList>>

    fun getShoppingList(id: Long): Single<ShoppingList>

    fun updateShoppingList(shoppingList: ShoppingList): Completable
}