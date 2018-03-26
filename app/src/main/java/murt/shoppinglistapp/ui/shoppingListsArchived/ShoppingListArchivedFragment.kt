package murt.shoppinglistapp.ui.shoppingListsArchived


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_shopping_list_archived.*
import murt.data.model.ShoppingList

import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyFragment
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import murt.shoppinglistapp.ui.shoppingListsCurrent.ListOfShoppingListsAdapter
import javax.inject.Inject




/**
 * A simple [Fragment] subclass.
 */
class ShoppingListArchivedFragment : MyFragment() {


    companion object {
        fun newInstance() = ShoppingListArchivedFragment()
    }

    @Inject
    lateinit var viewModelFactory: ShoppingListArchivedViewModelFactory
    private lateinit var mViewModel: ShoppingListArchivedViewModel

    private val mAdapter by lazy {
        ListOfShoppingListsAdapter(this::onShoppingListClick, this::onDeArchiveShoppingList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list_archived, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setUpViewModel()

    }

    private fun setUpView(){
        srf_list_archived.setOnRefreshListener {
            mViewModel.refreshList()
        }

        rv_list_archived.adapter = mAdapter
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ShoppingListArchivedViewModel::class.java)

        mViewModel.getArchivedShoppingList().observe(this, Observer {
            srf_list_archived.isRefreshing = false

            if(it == null) return@Observer

            mAdapter.updateList(it)
        })
    }

    private fun onShoppingListClick(shoppingList: ShoppingList){
        ShoppingListDetailsActivity.openShoppingListDetails(context!!, shoppingList.id!!, false)
    }

    private fun onDeArchiveShoppingList(shoppingList: ShoppingList){
        mViewModel.moveToCurrent(shoppingList)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onMessageEvent(event: ShoppingListArchived) {
//        mViewModel.refreshList()
//    }

}// Required empty public constructor
