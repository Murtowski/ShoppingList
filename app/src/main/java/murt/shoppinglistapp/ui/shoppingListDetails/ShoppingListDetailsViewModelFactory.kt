package murt.shoppinglistapp.ui.shoppingListDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
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