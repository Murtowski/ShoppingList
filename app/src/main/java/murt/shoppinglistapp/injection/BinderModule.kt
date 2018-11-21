package murt.shoppinglistapp.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import murt.shoppinglistapp.injection.scopes.PerActivity
import murt.shoppinglistapp.injection.scopes.PerFragment
import murt.shoppinglistapp.ui.main.MainActivity
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedFragment
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListsCurrentFragment

/**
 * Piotr Murtowski on 17.03.2018.
 */
@Module
abstract class BinderModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [ShoppingListsCurrentModule::class])
    abstract fun bindShoppingListCurrent(): ShoppingListsCurrentFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ShoppingListArchivedModule::class])
    abstract fun bindShoppingListArchived(): ShoppingListArchivedFragment

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ShoppingDetailsModule::class])
    abstract fun bindDetailsActivity(): ShoppingListDetailsActivity

//    @ContributesAndroidInjector
//    abstract fun contributesFragmentInjector(): MyFragment
}