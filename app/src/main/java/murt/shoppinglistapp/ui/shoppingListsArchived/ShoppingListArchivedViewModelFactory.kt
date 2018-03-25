package murt.shoppinglistapp.ui.shoppingListsArchived

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
/**
 * Piotr Murtowski on 20.02.2018.
 */
class ShoppingListArchivedViewModelFactory @Inject constructor(
    private val cacheService: CacheService
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListArchivedViewModel::class.java))
            return ShoppingListArchivedViewModel(cacheService = cacheService) as T
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}
