package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 26.02.2018.
 */
class ShoppingListCurrentViewModel @Inject constructor(
    cacheService: CacheService
): ViewModel() {


    fun refreshList(){

    }

    val currentShoppingLists = MutableLiveData<List<ShoppingList>>()
}