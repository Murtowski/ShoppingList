package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.shoppinglistapp.events.ShoppingListArchived
import murt.shoppinglistapp.ui.MyViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Piotr Murtowski on 26.02.2018.
 */
class ShoppingListCurrentViewModel @Inject constructor(
    private val cacheService: CacheService
): MyViewModel() {

    private val currentShoppingLists = MutableLiveData<List<ShoppingList>>()

    fun getCurrentShoppingLists(): LiveData<List<ShoppingList>> {
        if(currentShoppingLists.value == null){
            refreshList()
        }
        return currentShoppingLists
    }

    fun refreshList(){
        cacheService.getAllShoppingList(false)
            .subscribeOn(Schedulers.io())
            .map {
                it.sortedWith(compareByDescending({it.updatedAt}))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                currentShoppingLists.value = it
            }, onError = {
                Timber.e(it, "Error while loading shopping lists")
            }).addTo(disposable)
    }

    fun moveToArchive(shoppingList: ShoppingList){
        shoppingList.isArchived = true
        cacheService.updateShoppingListDescription(shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                Timber.i("Shopping List Archived")
            }, onError = {
                Timber.e(it, "Error while moving shopping list to archive")
            })
    }

    fun deleteShoppingList(shoppingList: ShoppingList){
        cacheService.deleteShoppingList(shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                Timber.i("Shopping List Deleted")
            }, onError = {
                Timber.e(it, "Error while deleting shopping list ")
            })
    }

    fun restoreShoppingList(shoppingList: ShoppingList){
        cacheService.createShoppingList(shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(onSuccess = {
                Timber.i("Shopping List restored")
            }, onError = {
                Timber.e(it, "Error while deleting shopping list ")
            })
    }




}