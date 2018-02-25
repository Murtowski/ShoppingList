package murt.shoppinglistapp.ui.shoppingList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import murt.shoppinglistapp.R

import kotlinx.android.synthetic.main.activity_shopping_list.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.ui.MyActivity
import android.view.MenuInflater
import android.view.MenuItem
import javax.inject.Inject


class ShoppingListActivity : MyActivity() {

    private val shoppingAdapter by lazy { ShoppingListAdapter(onDeleteClick = this::onDeleteClick) }

    private var deletedItem: ShoppingItem ?= null

    @Inject
    lateinit var mViewModelFactory: ShoppingListViewModelFactory
    lateinit var mViewModel: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        setSupportActionBar(toolbar)


    }

    private fun setUpView(){
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        shopping_list_recycler_view.adapter = shoppingAdapter
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(ShoppingListViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.shopping_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_edit_save -> {
                toggleView()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleView(){
        // TODO handle toogle between VIEW and EDIT mode
    }

    // Adapter handling

    private fun onDeleteClick(item: ShoppingItem){
        deletedItem = item
        showSnackBarDeletedItem()
    }

    private fun showSnackBarDeletedItem(){
        Snackbar
            .make(cl_shopping_list, R.string.item_deleted, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo, this::undoLastDeletedItem)
            .show()

    }

    private fun undoLastDeletedItem(snackBarActionButton: View){
        deletedItem?.let {
            shoppingAdapter.insertNewItem(it)
            deletedItem = null
        }

    }

}
