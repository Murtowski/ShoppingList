package murt.shoppinglistapp.ui.shoppingListDetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListDetailsViewModelFactory @Inject constructor(
    private val cacheService: CacheService
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListDetailsViewModel::class.java))
            return ShoppingListDetailsViewModel(cacheService) as T
        throw  IllegalArgumentException("Unknown ViewModel class")

    }
}