package murt.shoppinglistapp.ui.shoppingListsArchived

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import murt.shoppinglistapp.events.ShoppingListUnarchived
import murt.shoppinglistapp.ui.MyViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Piotr Murtowski on 20.02.2018.
 */
class ShoppingListArchivedViewModel @Inject constructor(
    private val cacheService: CacheService
): MyViewModel() {

    private val archivedShoppingLists = MutableLiveData<List<ShoppingList>>()

    fun getArchivedShoppingList(): LiveData<List<ShoppingList>>{
        if(archivedShoppingLists.value == null){
            refreshList()
        }
        return archivedShoppingLists
    }

    fun refreshList(){
        cacheService.getAllShoppingList(true)
            .subscribeOn(Schedulers.io())
            .map {
                it.sortedWith(compareByDescending({it.updatedAt}))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                archivedShoppingLists.value = it
            }, onError = {
                Timber.e(it, "Error while loading shopping lists")
            }).addTo(disposable)
    }

    fun moveToCurrent(shoppingList: ShoppingList){
        shoppingList.isArchived = false
        cacheService.updateShoppingListDescription(shoppingList)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                Timber.i("Shopping List Unarchived")
            }, onError = {
                Timber.e(it, "Error while moving shopping list to current")
            })
    }
}