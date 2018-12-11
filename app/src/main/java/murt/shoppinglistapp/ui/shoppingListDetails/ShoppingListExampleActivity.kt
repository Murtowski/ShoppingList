package murt.shoppinglistapp.ui.shoppingListDetails

import android.os.Bundle
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shopping_list_details.*
import murt.cache.CacheServiceFactory
import murt.cache.CacheServiceImpl
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import murt.remote.NetworkServiceFactory
import murt.remote.RemoteServiceImpl
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyActivity
import timber.log.Timber

class ShoppingListExampleActivity: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // View
        val adapter = createAdapter()

        // Services
        val remote = createRemoteService()
        val cache = createCacheService()

        cache.getShoppingListFLowable(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {
                Toast.makeText(this, "Error getting data from cache", Toast.LENGTH_SHORT).show()
            }, onNext = {
                adapter.updateList(it.items)
            })

        remote.getShoppingList()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                cache.updateShoppingListAndItems(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {
                Toast.makeText(this, "Error downloading fresh data", Toast.LENGTH_SHORT).show()
            }, onComplete = {
                Timber.i("Shopping List updated!")

            })
    }

    private fun createAdapter(): ShoppingListExampleAdapter {
        return ShoppingListExampleAdapter(saveItem = this::onClick)
    }

    private fun onClick(item: ShoppingItem) {
        Toast.makeText(this, "${item.title} clicked!", Toast.LENGTH_SHORT).show()
    }

    private fun createRemoteService(): RemoteService {
        val networkService = NetworkServiceFactory().createNetworkService(isDebug = true)
        return RemoteServiceImpl(networkService)
    }

    private fun createCacheService(): CacheService {
        // context may be injected
        val database = CacheServiceFactory().createCacheService(this)
        return CacheServiceImpl(database)
    }
}
