package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
/**
 * Piotr Murtowski on 26.02.2018.
 */
class ShoppingListCurrentViewModelFactory @Inject constructor(
    private val cacheService: CacheService
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListCurrentViewModel::class.java))
            return ShoppingListCurrentViewModel(cacheService) as T
        throw  IllegalArgumentException("Unknown ViewModel class")

    }
}