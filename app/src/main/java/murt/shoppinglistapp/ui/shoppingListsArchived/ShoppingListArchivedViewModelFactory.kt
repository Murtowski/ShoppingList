package murt.shoppinglistapp.ui.shoppingListsArchived

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Piotr Murtowski on 20.02.2018.
 */
class ShoppingListArchivedViewModelFactory @Inject constructor(

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListArchivedViewModel::class.java))
            return ShoppingListArchivedViewModel() as T
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}
