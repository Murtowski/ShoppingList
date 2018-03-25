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

    /**
     * Shopping List
     *
     * */

    override fun getAllShoppingList(isArchived: Boolean): Flowable<List<ShoppingList>> {
        return database.shoppingListDao().getListOfShoppingList(isArchived)
            .map {
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

    override fun updateShoppingListAndItems(shoppingList: ShoppingList): Completable {
        return Completable
            .fromAction {
                database.shoppingListDao().updateShoppingList(
                    mapper.mapAppToCache(shoppingList)
                )
            }
            .andThen(Completable.fromAction {
                database.shoppingListDao().updateShoppingItems(
                    mapper.mapAppToCache(shoppingList.items, shoppingList.id!!)
                )
            })
    }

    override fun updateShoppingListDescription(shoppingList: ShoppingList): Completable {
        return Completable
            .fromAction {
                database.shoppingListDao().updateShoppingList(
                    mapper.mapAppToCache(shoppingList)
                )
            }
    }

    override fun createShoppingList(shoppingList: ShoppingList): Single<ShoppingList>{
        return Single.just(shoppingList)
            .map {
                // Map model
                mapper.mapAppToCache(shoppingList)
            }
            .flatMap {
                // Create object
                Single.just(
                    database.shoppingListDao().insertShoppingList(it)
                )
            }
            .doOnSuccess {
                // Save created ID
                shoppingList.id = it
            }
            .flatMap {
                // Create Shopping Items
                Single.just(
                    database.shoppingListDao().insertShoppingItems(
                        mapper.mapAppToCache(shoppingList.items, shoppingList.id!!)
                    )
                )
            }
            .map {
                // Save id of created Items
                if (shoppingList.items.isNotEmpty())
                    for (i in 0 .. it.size){
                        shoppingList.items[i].id = it[i]
                    }
                shoppingList
            }
    }

    override fun deleteShoppingList(shoppingList: ShoppingList): Completable {
        return Completable.fromAction {
            database.shoppingListDao().deleteShoppingList(
                mapper.mapAppToCache(shoppingList)
            )
        }.andThen(Completable.fromAction {
            database.shoppingListDao().deleteShoppingItems(
                mapper.mapAppToCache(shoppingList.items, shoppingList.id!!)
            )
        })

    }

    /**
     * Shopping Item
     * */

    override fun updateShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long): Completable {
        return Single.just(shoppingItem)
            .map {
                mapper.mapAppToCache(shoppingItem, shoppingListId)
            }
            .flatMapCompletable {
                Completable.fromAction {
                    database.shoppingListDao().updateShoppingItem(it)
                }
            }
    }

    override fun createShoppingItem(
        shoppingItem: ShoppingItem,
        shoppingListId: Long
    ): Single<ShoppingItem> {
        return Single
            .defer {
                val shoppingItemCache = mapper.mapAppToCache(shoppingItem, shoppingListId)
                Single.just(database.shoppingListDao().insertShoppingItem(shoppingItemCache))
            }
            .map {
                shoppingItem.apply {
                    id = it
                }
            }
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem, shoppingListId: Long): Completable {
        return Completable.fromAction {
            val shoppingItemCache = mapper.mapAppToCache(shoppingItem, shoppingListId)
            database.shoppingListDao().deleteShoppingItem(shoppingItemCache)
        }
    }
}