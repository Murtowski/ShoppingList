package murt.data.repository

import io.reactivex.Single
import murt.data.model.ShoppingItem

/**
 * Piotr Murtowski on 20.02.2018.
 */
interface RemoteService {

    fun getShoppingList(): Single<List<ShoppingItem>>
}