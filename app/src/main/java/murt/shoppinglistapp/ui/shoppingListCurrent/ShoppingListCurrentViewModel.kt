package murt.shoppinglistapp.ui.shoppingListCurrent

import android.arch.lifecycle.ViewModel
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
}