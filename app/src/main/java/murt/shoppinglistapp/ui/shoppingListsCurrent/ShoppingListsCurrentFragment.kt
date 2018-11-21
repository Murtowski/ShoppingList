package murt.shoppinglistapp.ui.shoppingListsCurrent


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_shopping_list_current.*
import murt.data.model.ShoppingList

import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.BaseFragment
import murt.shoppinglistapp.ui.RecyclerViewSwipeHelper
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ShoppingListsCurrentFragment : BaseFragment(), RecyclerViewSwipeHelper.RecyclerViewSwipeListener {

    companion object {
        fun newInstance() = ShoppingListsCurrentFragment()
    }

    private val viewModelFactor: ShoppingListCurrentViewModelFactory by instance(tag = ShoppingListCurrentViewModelFactory::class.java.simpleName)
    private lateinit var mViewModel: ShoppingListCurrentViewModel

    private var deletedShoppingList: ShoppingList ?= null

    private val mAdapter by lazy {
        ListOfShoppingListsAdapter(this::onShoppingListClick, this::onArchiveShoppingListClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setUpViewModel()
    }

    private fun setUpView() {
        srf_list_current.setOnRefreshListener {
            mViewModel.refreshList()
        }

        rv_list_current.adapter = mAdapter

        val swipeHelper = RecyclerViewSwipeHelper(this)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(rv_list_current)

    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactor)
            .get(ShoppingListCurrentViewModel::class.java)

        mViewModel.getCurrentShoppingLists().observe(viewLifecycleOwner, Observer {
            srf_list_current.isRefreshing = false

            if (it == null) return@Observer

            mAdapter.updateList(it)
            // show / hide empty list indicator
            (activity as ShoppingListsCurrentListener).showEmptyList(it.isEmpty())
        })
    }

    override fun onSwiped(viewHolder: RecyclerViewSwipeHelper.ViewHolderSwipe, direction: Int, position: Int) {
        val shoppingList = mAdapter.getShoppingList(position)
        deletedShoppingList = shoppingList
        mViewModel.deleteShoppingList(shoppingList)
        showSnackBarDeletedItem()
    }

    private fun showSnackBarDeletedItem(){
        com.google.android.material.snackbar.Snackbar
            .make(activity!!.findViewById(R.id.main_activity_container), R.string.shopping_list_deleted,
                com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
            .setAction(R.string.undo, this::undoLastDeletedItem)
            .show()
    }

    private fun undoLastDeletedItem(snackBarActionButton: View){
        deletedShoppingList?.let {
            mViewModel.restoreShoppingList(it)
            deletedShoppingList = null
        }

    }

    private fun onShoppingListClick(shoppingList: ShoppingList) {
        ShoppingListDetailsActivity.openShoppingListDetails(context!!, shoppingList.id!!, true)
    }

    private fun onArchiveShoppingListClick(shoppingList: ShoppingList) {
        mViewModel.moveToArchive(shoppingList)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onMessageEvent(event: ShoppingListUnarchived) {
//        mViewModel.refreshList()
//    }

    interface ShoppingListsCurrentListener{
        fun showEmptyList(isListEmpty: Boolean)
    }
}