package murt.shoppinglistapp.ui.main

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyActivity
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListsCurrentFragment
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedFragment

import android.support.design.widget.CoordinatorLayout
import murt.shoppinglistapp.ui.BottomNavigationBehavior


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
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigation_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        val layoutParams = navigation_bar.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationBehavior()

        supportFragmentManager.addOnBackStackChangedListener(this::onFragmentBackStackChanged)
        openFragment(MainFragmentTag.SHOPPING_LIST)
    }


    private fun openFragment(fragmentType: MainFragmentTag) {
        var frag: Fragment? = supportFragmentManager.findFragmentByTag(fragmentType.name)
        if (frag == null) {
            frag = when (fragmentType) {
                MainFragmentTag.SHOPPING_LIST -> ShoppingListsCurrentFragment.newInstance()
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

