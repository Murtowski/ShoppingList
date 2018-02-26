package murt.shoppinglistapp.ui.main

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_shopping_list.*
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyActivity
import murt.shoppinglistapp.ui.shoppingListCurrent.ShoppingListCurrentFragment
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedFragment

class MainActivity : MyActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_current -> {
                    openFragment(MainFragmentTag.SHOPPING_LIST)
                }
                R.id.navigation_archived -> {
                    openFragment(MainFragmentTag.ARCHIVED_SHOPPING_LIST)
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFragment(fragmentType: MainFragmentTag) {
        var frag: Fragment? = supportFragmentManager.findFragmentByTag(fragmentType.name)
        if (frag == null) {
            frag = when (fragmentType) {
                MainFragmentTag.SHOPPING_LIST -> ShoppingListCurrentFragment.newInstance()
                MainFragmentTag.ARCHIVED_SHOPPING_LIST -> ShoppingListArchivedFragment.newInstance()
            }
        }

        if (!frag.isVisible) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.show_translate, R.anim.hide_translate)
                .replace(R.id.fragment_container, frag, fragmentType.name)
                .addToBackStack(fragmentType.name)
                .commit()
        }
    }

    private fun setTite(@StringRes stringResId: Int){
        supportActionBar?.title = getString(stringResId)
    }

    private fun onFragmentBackStackChanged() {
        val fm = supportFragmentManager
        val lastItemIndex = fm.backStackEntryCount - 1
        val currentlyDisplayedFragTag = fm.getBackStackEntryAt(lastItemIndex).name

        when (currentlyDisplayedFragTag) {
            MainFragmentTag.SHOPPING_LIST.name ->{
                setTite(stringResId = R.string.title_shopping_list)
            }
            MainFragmentTag.ARCHIVED_SHOPPING_LIST.name -> {
                setTite(stringResId = R.string.title_archived_shopping_list)
            }

//            MENU_FRAGMENT -> {
//                enableCollapsingToolbar(true)
//                selectNavigationBarView(0)
//                setDefaultLogo()
//            }
//            DOCUMENTS_FRAG -> {
//                enableCollapsingToolbar(false)
//                setTitle("Moje Wnioski")
//                toggleBackButtonToolbar(false)
//                selectNavigationBarView(1)
//            }
//            CAR_CONTRACT_FRAGMENT -> {
//                enableCollapsingToolbar(false)
//                setTitle(R.string.title_car_details)
//                toggleBackButtonToolbar(false)
//                selectNavigationBarView(2)
//            }
//            NOTIFICATIONS_FRAGMENT -> {
//                enableCollapsingToolbar(false)
//                setTitle(R.string.title_notifications)
//                toggleBackButtonToolbar(false)
//                selectNavigationBarView(3)
//            }
//            SETTINGS_FRAGMENT -> {
//                enableCollapsingToolbar(false)
//                setTitle(R.string.title_settings)
//                toggleBackButtonToolbar(false)
//                selectNavigationBarView(4)
//            }
            else -> {
                throw IllegalStateException("Cant handle this fragment, name: $currentlyDisplayedFragTag")
            }
        }
    }



}

enum class MainFragmentTag{
    SHOPPING_LIST,
    ARCHIVED_SHOPPING_LIST
}

