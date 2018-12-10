package murt.shoppinglistapp.ui.shoppingListDetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.cache.CacheServiceFactory
import murt.cache.CacheServiceImpl
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import murt.remote.NetworkServiceFactory
import murt.remote.RemoteServiceImpl
import murt.shoppinglistapp.ui.BaseFragment
import murt.shoppinglistapp.ui.MyActivity
import murt.shoppinglistapp.ui.shoppingListsCurrent.ListOfShoppingListsAdapter
import timber.log.Timber

class ShoppingListExample: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View
        val adapter = createAdapter()

        // Services
        val remote = createRemoteService()
        val cache = createCacheService()


        var shoppingList: ShoppingList ?= null

        cache.getShoppingList(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    Toast.makeText(this, "Error getting data from cache", Toast.LENGTH_SHORT).show()
                }, onSuccess = {
                    adapter.updateList(it.items)
                    shoppingList = it
                })

        remote.getShoppingList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    Toast.makeText(this, "Error getting data from cache", Toast.LENGTH_SHORT).show()
                }, onSuccess = {

                    shoppingList!!.items = it
                    cache.updateShoppingListAndItems(shoppingList!!)
                            .subscribeOn(Schedulers.io())
                            .subscribeBy(onError = {
                                Timber.e("Error while updating shopping list in cache")
                            }, onComplete = {
                                Timber.i("Shopping List updated!")
                            })
                })
    }

    private fun createAdapter(): ShoppingListDetailsAdapter {
         return ShoppingListDetailsAdapter(
                 context = this, isListEditable =  true, saveItem = this::onUpdateClick)
    }

    private fun onUpdateClick(item: ShoppingItem){
        //updateShoppingItem(item, shoppingList!!.id!!)
    }

    private fun createRemoteService(): RemoteService{
        val networkService = NetworkServiceFactory().createNetworkService(isDebug = true)
        return RemoteServiceImpl(networkService)
    }

    private fun createCacheService(): CacheService{
        // context may be injected
        val database = CacheServiceFactory().createCacheService(context!!)
        return CacheServiceImpl(database)
    }




}