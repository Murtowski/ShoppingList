package murt.shoppinglistapp.ui.shoppingList

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_shopping.view.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.utils.getReadableDate
import murt.shoppinglistapp.ui.utils.inflate

/**
 * Piotr Murtowski on 21.02.2018.
 */
class ShoppingListAdapter(
    val items: MutableList<ShoppingItem> = mutableListOf(),
    val viewMode: VIEW_MODE = VIEW_MODE.VIEW,
    val onDeleteClick: (ShoppingItem) -> Unit
): RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {



    fun insertNewShoppingList(list: List<ShoppingItem>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(parent.inflate(R.layout.item_shopping))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    class ShoppingItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val title = view.item_shopping_title
        private val date = view.item_shopping_date

        fun onBind(item: ShoppingItem){
            title.text = item.title
            date.text = item.date.getReadableDate(date.context)
        }
    }

    enum class VIEW_MODE{
        EDIT,
        VIEW
    }
}