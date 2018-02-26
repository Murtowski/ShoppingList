package murt.shoppinglistapp.ui.shoppingListDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.View
import murt.shoppinglistapp.R

import kotlinx.android.synthetic.main.activity_shopping_list_details.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.ui.MyActivity
import android.view.MenuItem
import dagger.android.AndroidInjection
import javax.inject.Inject


class ShoppingListDetailsActivity : MyActivity() {

    companion object {
        private const val EXTRA_SHOPPING_LIST_ID = "shoppingListID"

        fun openShoppingListDetails(context: Context, shoppingListID: Long){
            val intent = Intent(context, ShoppingListDetailsActivity::class.java).apply {
                putExtra(EXTRA_SHOPPING_LIST_ID, shoppingListID)
            }
            context.startActivity(intent)
        }
    }

    private val shoppingAdapter by lazy { ShoppingListDetailsAdapter(onDeleteClick = this::onDeleteClick) }

    private var deletedItem: ShoppingItem ?= null
    var shoppingListId: Long = -1L // -1 means that have to create new shopping list

    @Inject
    lateinit var mViewModelFactory: ShoppingListDetailsViewModelFactory
    private lateinit var mViewModel: ShoppingListDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_details)
        setSupportActionBar(toolbar)

        shoppingListId = intent.getLongExtra(EXTRA_SHOPPING_LIST_ID, -1L)

        setUpView()
        setUpViewModel()

    }

    private fun setUpView(){
        fab.setOnClickListener { view ->
            shoppingAdapter.insertNewItem(ShoppingItem.new())
        }

        shopping_list_recycler_view.adapter = shoppingAdapter
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(ShoppingListDetailsViewModel::class.java)

        if(shoppingListId == -1L){
            mViewModel.createNewShoppingList()
        }else{
            observeShoppingList(shoppingListId)
        }
    }

    private fun observeShoppingList(id: Long){
        mViewModel.getShoppingList(id)?.observe(this, Observer {

        })
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
