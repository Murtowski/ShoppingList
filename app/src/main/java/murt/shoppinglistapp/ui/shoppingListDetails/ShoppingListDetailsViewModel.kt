package murt.shoppinglistapp.ui.shoppingListDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import murt.data.model.ShoppingList
import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListDetailsViewModel @Inject constructor(
    cacheService: CacheService
): ViewModel() {

    fun createNewShoppingList(){

    }

    fun getShoppingList(id: Long): LiveData<ShoppingList>?{
        return null
    }
}