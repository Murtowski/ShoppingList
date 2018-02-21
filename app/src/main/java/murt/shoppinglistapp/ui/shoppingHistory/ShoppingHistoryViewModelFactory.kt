package murt.shoppinglistapp.ui.shoppingHistory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 20.02.2018.
 */
class ShoppingHistoryViewModelFactory @Inject constructor(

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingHistoryViewModel::class.java))
            return ShoppingHistoryViewModel() as T
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}
