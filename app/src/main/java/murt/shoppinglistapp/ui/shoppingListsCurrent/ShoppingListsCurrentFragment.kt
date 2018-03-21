package murt.shoppinglistapp.ui.shoppingListsCurrent


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_shopping_list_current.*
import murt.data.model.ShoppingList

import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyFragment
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ShoppingListsCurrentFragment : MyFragment() {

    companion object {
        fun newInstance() = ShoppingListsCurrentFragment()
    }

    @Inject
    lateinit var viewModelFactor: ShoppingListCurrentViewModelFactory
    lateinit var mViewModel: ShoppingListCurrentViewModel

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

    private fun setUpView(){
        srf_list_current.setOnRefreshListener {
            mViewModel.refreshList()
        }

        rv_list_current.adapter = mAdapter

//        fab_add_shopping_list.setOnClickListener {
//            ShoppingListDetailsActivity.openShoppingListDetails(context!!, -1L)
//        }
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, viewModelFactor)
            .get(ShoppingListCurrentViewModel::class.java)

        mViewModel.getCurrentShoppingLists().observe(this, Observer {
            if(it == null) return@Observer

            mAdapter.updateList(it)
        })
    }

    private fun onShoppingListClick(shoppingList: ShoppingList){

    }

    private fun onArchiveShoppingListClick(shoppingList: ShoppingList){

    }
}// Required empty public constructor
