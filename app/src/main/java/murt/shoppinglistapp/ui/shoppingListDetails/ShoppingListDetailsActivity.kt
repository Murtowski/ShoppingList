package murt.shoppinglistapp.ui.shoppingListDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.View
import murt.shoppinglistapp.R

import kotlinx.android.synthetic.main.activity_shopping_list_details.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.ui.MyActivity
import murt.data.model.ShoppingList
import murt.shoppinglistapp.ui.utils.UITools
import murt.shoppinglistapp.ui.utils.createKeyboardActionListener
import murt.shoppinglistapp.ui.utils.invisible
import murt.shoppinglistapp.ui.utils.visible
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit


class ShoppingListDetailsActivity : MyActivity() {

    companion object {
        private const val EXTRA_SHOPPING_LIST_ID = "shoppingListID"
        private const val EXTRA_EDIT_ENABLED = "editEnabled"

        fun openShoppingListDetails(context: Context, shoppingListID: Long, isEditEnabled: Boolean = true){
            val intent = Intent(context, ShoppingListDetailsActivity::class.java).apply {
                putExtra(EXTRA_SHOPPING_LIST_ID, shoppingListID)
                putExtra(EXTRA_EDIT_ENABLED, isEditEnabled)
            }
            context.startActivity(intent)
        }
    }

    // Shopping List staff
    private val shoppingAdapter: ShoppingListDetailsAdapter by lazy {
        ShoppingListDetailsAdapter(
            context = this,
            onDeleteClick = this::onDeleteClick,
            isListEditable = isEditable,
            saveItem = this::onUpdateClick
        )
    }

    private var deletedItem: ShoppingItem ?= null
    protected var shoppingList: ShoppingList ?= null

    private lateinit var titleTextWatcher: TitleTextWatcher

    private val isEditable by lazy { intent.getBooleanExtra(EXTRA_EDIT_ENABLED, true) }

    // View Model
    @Inject
    lateinit var mViewModelFactory: ShoppingListDetailsViewModelFactory
    private lateinit var mViewModel: ShoppingListDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shopping_list_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpView()
        setUpViewModel()
        observeViewModel()
    }

    override fun onStop() {
        shoppingAdapter.mTextWatcher.dispose()
        titleTextWatcher.dispose()
        super.onStop()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.shopping_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            android.R.id.home -> {
                shoppingAdapter.savePreviousEdit()
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setUpView(){
        shopping_list_recycler_view.adapter = shoppingAdapter
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        shopping_list_recycler_view.addItemDecoration(itemDecor)

        if(isEditable){
            fab_add_shopping_item.show()
            fab_add_shopping_item.setOnClickListener {
                shoppingAdapter.savePreviousEdit()
                mViewModel.createShoppingItem(ShoppingItem.empty(), shoppingList!!.id!!)
                showEmptyListIndicator(false)
            }

            toolbar_textview.setOnClickListener {
                showEditableShoppingListTitle(true)
            }

            toolbar_edittext.createKeyboardActionListener(runnable = Runnable {
                UITools.hideSoftKeyboard(this, toolbar_edittext)
            })

            titleTextWatcher = TitleTextWatcher()
            toolbar_edittext.addTextChangedListener(titleTextWatcher)
        }else{

        }

    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(ShoppingListDetailsViewModel::class.java)

        val shoppingListId = intent.getLongExtra(EXTRA_SHOPPING_LIST_ID, -1L)
        if(shoppingListId == -1L){
            mViewModel.createNewShoppingList()
        }else{
            mViewModel.getShoppingList(shoppingListId)
        }


    }

    private fun observeViewModel(){
        mViewModel.shoppingListLiveData.observe(this, Observer {
            if(it == null) return@Observer

            shoppingList = it

            shoppingAdapter.updateList(it.items)

            setUpTitle(it.title)

            if(isEditable){
                showEmptyListIndicator(it.items.isEmpty())
                showEditableShoppingListTitle(it.title.isNullOrBlank())
            }

        })

        mViewModel.shoppingItemLiveData.observe(this, Observer {
            if(it == null) return@Observer

            shoppingAdapter.insertItem(it)
            mViewModel.updateShoppingListTitle(shoppingList!!)
        })
    }

    private fun setUpTitle(title: String){
        toolbar_textview.text = title
        toolbar_edittext.setText(title)
    }

    private fun showEmptyListIndicator(isEmpty: Boolean){
        tv_empty_current_list_message.visibility = if(isEmpty) View.VISIBLE else View.GONE
    }

    private fun showEditableShoppingListTitle(show: Boolean){
        if(show){
            toolbar_textview.invisible()
            toolbar_edittext.visible()
        }else {
            toolbar_textview.visible()
            toolbar_edittext.invisible()
        }

    }



    private fun onUpdateClick(item: ShoppingItem){
        mViewModel.updateShoppingItem(item, shoppingList!!.id!!)
        mViewModel.updateShoppingListTitle(shoppingList!!)
    }

    /**
     * Undo delete
     * */
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
            shoppingAdapter.savePreviousEdit()
            mViewModel.createShoppingItem(it, shoppingList!!.id!!)
            deletedItem = null
        }

    }

    inner class TitleTextWatcher: TextWatcher {

        private val disposable = CompositeDisposable()

        init {
            Observable.create<String> { obsEmitter ->
                emitter = obsEmitter
            }
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribeBy(onNext = {
                    val newTitle = it
                    shoppingList?.let {
                        it.title = newTitle
                        mViewModel.updateShoppingListTitle(shoppingList!!)
                    }
                }).addTo(disposable)
        }

        lateinit var emitter : ObservableEmitter<String>

        override fun afterTextChanged(text: Editable?) {
            emitter.onNext(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        fun dispose(){
            disposable.dispose()
        }
    }
}
