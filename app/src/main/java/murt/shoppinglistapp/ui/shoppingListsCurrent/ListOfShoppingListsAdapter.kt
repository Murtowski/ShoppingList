package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_shopping_list.view.*
import murt.cache.model.ShoppingListAndItems
import murt.data.model.ShoppingList
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.utils.SystemTools
import murt.shoppinglistapp.ui.utils.getReadableDate
import murt.shoppinglistapp.ui.utils.gone
import murt.shoppinglistapp.ui.utils.inflate

/**
 * Piotr Murtowski on 26.02.2018.
 */
class ListOfShoppingListsAdapter(
    val items: MutableList<ShoppingList>,
    val onItemCLick: (ShoppingList) -> Unit,
    val onArchiveButtonClick: (ShoppingList) -> Unit
): RecyclerView.Adapter<ListOfShoppingListsAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder(parent.inflate(R.layout.item_shopping_list))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    class ShoppingListViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val date = view.tv_last_update_time
        private val archiveButton = view.iv_archive_button
        private val title = view.tv_shopping_title
        private val itemsList = view.tv_shopping_list

        fun onBind(item: ShoppingList) {
            // Only for debug to know when values are updated
            if(SystemTools.isDebugMode)
                date.text = item.updatedAt.getReadableDate(date.context)
            else
                date.gone()

            

//            title.text = item.shoppingItems.



        }

    }
}