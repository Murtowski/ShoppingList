package murt.shoppinglistapp.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import murt.shoppinglistapp.injection.scopes.PerActivity
import murt.shoppinglistapp.ui.MyActivity
import murt.shoppinglistapp.ui.MyFragment
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListsCurrentFragment

/**
 * Piotr Murtowski on 17.03.2018.
 */
@Module
abstract class BinderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [ShoppingListsCurrentModule::class])
    abstract fun bindShoppingListCurrent(): ShoppingListsCurrentFragment

//    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MyActivity

//    @ContributesAndroidInjector
//    abstract fun contributesFragmentInjector(): MyFragment
}