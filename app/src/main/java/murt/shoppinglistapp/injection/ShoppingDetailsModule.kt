package murt.shoppinglistapp.injection

import dagger.Module
import dagger.Provides
import murt.data.repository.CacheService
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsViewModelFactory

/**
 * Piotr Murtowski on 22.03.2018.
 */
@Module

open class ShoppingDetailsModule {

    @Provides
    fun provideShoopingDetailsViewModelFactory(cache: CacheService)
            : ShoppingListDetailsViewModelFactory{
        return ShoppingListDetailsViewModelFactory(cache)
    }
}