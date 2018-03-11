package murt.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 25.02.2018.
 */
class CacheServiceImpl @Inject constructor(private val database: RoomDatabaseCache) : CacheService{

    val mapper = CacheMapper

    override fun getListOfShoppingList(isArchived: Boolean): Flowable<List<ShoppingList>> {
        return Flowable.defer {
            Flowable.just(database.shoppingListDao().getListOfShoppingList(isArchived))
        }.map {
            mapper.mapCacheToApp1(it)
        }
    }

    override fun getShoppingList(id: Long): Single<ShoppingList> {
        return Single.defer {
            Single.just(database.shoppingListDao().getShoppingList(id))
        }.map {
            mapper.mapCacheToApp(it)
        }
    }

    override fun updateShoppingList(shoppingList: ShoppingList): Completable {
        return Completable
            .fromAction {
                database.shoppingListDao().updateShoppingList(
                    mapper.mapAppToCache(shoppingList)
                )
            }
            .andThen(Completable.fromAction {
                database.shoppingListDao().updateShoppingItems(
                    mapper.mapAppToCache(shoppingList.items, shoppingList.id)
                )
            })
    }

    override fun createShoppingList(shoppingList: ShoppingList): Completable {
        return Completable
            .fromAction {
                database.shoppingListDao().insertShoppingList(
                    mapper.mapAppToCache(shoppingList)
                )
            }
            .andThen(Completable.fromAction {
                database.shoppingListDao().insertShoppingItems(
                    mapper.mapAppToCache(shoppingList.items, shoppingList.id)
                )
            })
    }
}