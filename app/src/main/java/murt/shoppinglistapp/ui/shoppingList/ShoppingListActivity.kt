package murt.shoppinglistapp.ui.shoppingList

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import murt.shoppinglistapp.R

import kotlinx.android.synthetic.main.activity_shopping_list.*
import kotlinx.android.synthetic.main.content_shopping_list.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.ui.MyActivity

class ShoppingListActivity : MyActivity() {

    private val shoppingAdapter by lazy { ShoppingListAdapter(onDeleteClick = this::onDeleteClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        shopping_list_recycler_view
    }

    private fun onDeleteClick(item: ShoppingItem){

    }

}
