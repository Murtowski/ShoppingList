package murt.shoppinglistapp.ui.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import murt.data.repository.RemoteService
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListExampleViewModelFactory @Inject constructor(
    private val cacheService: CacheService, private val remoteService: RemoteService
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListExampleViewModel::class.java))
            return ShoppingListExampleViewModel(cacheService, remoteService) as T
        throw  IllegalArgumentException("Unknown ViewModel class")

    }
}