package murt.shoppinglistapp.ui.shoppingListCurrent


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_shopping_list_current.*

import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.MyFragment
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsActivity
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ShoppingListCurrentFragment : MyFragment() {

    companion object {
        fun newInstance() = ShoppingListCurrentFragment()
    }

    @Inject
    lateinit var viewModelFactor: ShoppingListCurrentViewModelFactory
    lateinit var mViewModel: ShoppingListCurrentViewModel

    private val mAdapter by lazy { ShoppingListCurrentAdapter() }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
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


        srf_list_current.setOnRefreshListener {
            mViewModel.refreshList()
        }

        rv_list_current.adapter = mAdapter

        fab_add_shopping_list.setOnClickListener {
            ShoppingListDetailsActivity.openShoppingListDetails(context!!, -1L)
        }

        setUpViewModel()
    }

    private fun setUpViewModel(){
        mViewModel = ViewModelProviders.of(this, viewModelFactor)
            .get(ShoppingListCurrentViewModel::class.java)
    }
}// Required empty public constructor
