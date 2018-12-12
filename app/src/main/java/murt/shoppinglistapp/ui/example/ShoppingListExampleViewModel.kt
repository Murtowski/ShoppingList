package murt.shoppinglistapp.ui.example

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import murt.shoppinglistapp.ui.MyViewModel
import timber.log.Timber

/**
 * Piotr Murtowski on 12/12/2018.
 */
class ShoppingListExampleViewModel constructor(
    private val cache: CacheService, private val remote: RemoteService): MyViewModel() {

    private val shoppingListLiveData = MutableLiveData<ShoppingList>()

    init {
        refreshDataRemote()
        putDataIntoLiveData()
    }

    private fun refreshDataRemote(){
        remote.getShoppingList()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable {
                cache.updateShoppingListAndItems(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {
                Timber.e("Error updating Shopping List from rest")
            }, onComplete = {
                Timber.d("Shopping List updated")

            })
    }

    private fun putDataIntoLiveData(){
        cache.getShoppingListFLowable(0)
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                Timber.e("Error getting shopping list from cache")
            }, onNext = {
                shoppingListLiveData.postValue(it)
            })
    }

    fun getLiveDataShoppingList(): LiveData<ShoppingList>{
        return shoppingListLiveData
    }

}