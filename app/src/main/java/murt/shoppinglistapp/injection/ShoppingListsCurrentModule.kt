package murt.shoppinglistapp.injection

import dagger.Module
import dagger.Provides
import murt.data.repository.CacheService
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListCurrentViewModelFactory

/**
 * Piotr Murtowski on 19.03.2018.
 */
@Module
open class ShoppingListsCurrentModule {

    @Provides
    fun provideShoopinfListsCurretViewModelFactor(cache: CacheService):
            ShoppingListCurrentViewModelFactory{

        return ShoppingListCurrentViewModelFactory(cache)
    }

}