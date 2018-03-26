package murt.shoppinglistapp.ui.main

import android.os.Bundle
import android.support.annotation.IntRange
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyActivity
import murt.shoppinglistapp.ui.shoppingListsCurrent.ShoppingListsCurrentFragment
import murt.shoppinglistapp.ui.shoppingListsArchived.ShoppingListArchivedFragment

import android.support.design.widget.CoordinatorLayout
import android.view.View
import murt.cache.typeConverter.LocalDateTimeConverter
import murt.shoppinglistapp.ui.BottomNavigationBehavior
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import murt.shoppinglistapp.ui.utils.getReadableDate
import murt.shoppinglistapp.ui.utils.getZonedDateTime
import murt.shoppinglistapp.ui.utils.gone
import murt.shoppinglistapp.ui.utils.visible
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime


class MainActivity : MyActivity(), ShoppingListsCurrentFragment.ShoppingListsCurrentListener {

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
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance)

        navigation_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.addOnBackStackChangedListener(this::onFragmentBackStackChanged)
        openFragment(MainFragmentTag.SHOPPING_LIST, false)

        fab_add_shopping_list.setOnClickListener {
            ShoppingListDetailsActivity.openShoppingListDetails(this, -1L)
        }

    }


    private fun openFragment(fragmentType: MainFragmentTag, withAnimation: Boolean = true) {
        var frag: Fragment? = supportFragmentManager.findFragmentByTag(fragmentType.name)
        if (frag == null) {
            frag = when (fragmentType) {
                MainFragmentTag.SHOPPING_LIST -> ShoppingListsCurrentFragment.newInstance()
                MainFragmentTag.ARCHIVED_SHOPPING_LIST -> ShoppingListArchivedFragment.newInstance()
            }
        }

        if (!frag.isVisible) {
            val transaction = supportFragmentManager.beginTransaction()

            if(withAnimation)
                transaction.setCustomAnimations(R.anim.show_alpha, R.anim.hide_alpha)

            transaction
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
                showFabButton(true)
            }
            MainFragmentTag.ARCHIVED_SHOPPING_LIST.name -> {
                setTite(stringResId = R.string.title_archived_shopping_list)
                showFabButton(false)
            }
            else -> {
                throw IllegalStateException("Cant handle this fragment, name: $currentlyDisplayedFragTag")
            }
        }
    }

    private fun showFabButton(show: Boolean){
        if(show){
            fab_button_container.visible()
        }else{
            fab_button_container.gone()
        }
    }

    override fun showEmptyList(isListEmpty: Boolean) {
        tv_empty_current_list_message.visibility = if(isListEmpty) View.VISIBLE else View.GONE
    }
}

enum class MainFragmentTag{
    SHOPPING_LIST,
    ARCHIVED_SHOPPING_LIST
}

