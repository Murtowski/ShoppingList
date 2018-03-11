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

    // Shopping List staff
    private lateinit var shoppingAdapter: ShoppingListDetailsAdapter
    private var deletedItem: ShoppingItem ?= null
    private val shoppingList = mutableListOf<ShoppingItem>()


    // View Model
    @Inject
    lateinit var mViewModelFactory: ShoppingListDetailsViewModelFactory
    private lateinit var mViewModel: ShoppingListDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_details)
        setSupportActionBar(toolbar)

        setUpView()
        setUpViewModel()
        observeViewModel()

    }

    private fun setUpView(){
        fab.setOnClickListener { view ->
            shoppingAdapter.insertNewItem()
        }
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(ShoppingListDetailsViewModel::class.java)

        val shoppingListId = intent.getLongExtra(EXTRA_SHOPPING_LIST_ID, -1L)
        if(shoppingListId == -1L){
            mViewModel.createNewShoppingList()
        }else{
            mViewModel.refreshShoppingList(shoppingListId)
        }


    }

    private fun observeViewModel(){
        mViewModel.shoppingListLiveData?.observe(this, Observer {
            if(it == null || shoppingList.isNotEmpty()) return@Observer

            shoppingList.addAll(it.items)

            shoppingAdapter = ShoppingListDetailsAdapter(
                items = shoppingList,
                onDeleteClick = this::onDeleteClick,
                isListEditable = !it.isArchived
            )
            shopping_list_recycler_view.adapter = shoppingAdapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.shopping_list_menu, menu)
        return true
    }

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
            shoppingAdapter.insertDeletedItem(it)
            deletedItem = null
        }

    }

}
