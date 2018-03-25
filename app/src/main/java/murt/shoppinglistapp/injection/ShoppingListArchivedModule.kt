package murt.shoppinglistapp.injection

import dagger.Module
import dagger.Provides
import murt.data.repository.CacheService
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedViewModelFactory

/**
 * Piotr Murtowski on 25.03.2018.
 */
@Module
open class ShoppingListArchivedModule {

    @Provides
    fun provideShoppingListArchivedViewModelFactory(cacheService: CacheService): ShoppingListArchivedViewModelFactory{
        return ShoppingListArchivedViewModelFactory(cacheService)
    }
}
