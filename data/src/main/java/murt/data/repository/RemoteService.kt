package murt.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList

/**
 * Piotr Murtowski on 20.02.2018.
 */
interface RemoteService {

    fun getShoppingItems(): Single<List<ShoppingItem>>

    fun getShoppingList(): Single<ShoppingList>
}