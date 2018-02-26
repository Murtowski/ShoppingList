package murt.shoppinglistapp.ui.shoppingListsArchived


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import murt.shoppinglistapp.R


/**
 * A simple [Fragment] subclass.
 */
class ShoppingListArchivedFragment : Fragment() {


    companion object {
        fun newInstance() = ShoppingListArchivedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list_archived, container, false)
    }

}// Required empty public constructor
