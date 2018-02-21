package murt.shoppinglistapp.ui.shoppingHistory

import android.os.Bundle
import android.support.design.widget.Snackbar
import murt.shoppinglistapp.R

import kotlinx.android.synthetic.main.activity_shopping_history.*
import murt.shoppinglistapp.ui.MyActivity

class ShoppingHistoryActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_history)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
