package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import timber.log.Timber
import javax.inject.Inject

/**
 * Piotr Murtowski on 26.02.2018.
 */
class ShoppingListCurrentViewModel @Inject constructor(
    private val cacheService: CacheService
): ViewModel() {

    private var hasDataBeenLoaded = false

    private val currentShoppingLists = MutableLiveData<List<ShoppingList>>()

    fun getCurrentShoppingLists(): MutableLiveData<List<ShoppingList>>{
        if(!hasDataBeenLoaded){
            hasDataBeenLoaded = true
            refreshList()
        }
        return currentShoppingLists
    }

    fun refreshList(){
        cacheService.getListOfShoppingList(false)
            .subscribeOn(Schedulers.io())
            .map {
                it.sortedWith(compareByDescending({it.updatedAt}))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                currentShoppingLists.value = it
            }, onError = {
                Timber.e(it, "Error while loading shopping lists")
            })
    }




}