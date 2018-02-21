package murt.shoppinglistapp.ui.shoppingList

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListViewModelFactory @Inject constructor(

) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListViewModel::class.java))
            return ShoppingListViewModel() as T
        throw  IllegalArgumentException("Unknown ViewModel class")

    }
}