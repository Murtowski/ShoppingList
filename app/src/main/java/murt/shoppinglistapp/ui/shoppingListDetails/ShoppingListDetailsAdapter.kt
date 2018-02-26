package murt.shoppinglistapp.ui.shoppingListDetails

import android.support.v7.util.DiffUtil
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
class ShoppingListDetailsAdapter(
    val items: MutableList<ShoppingItem> = mutableListOf(),
    val viewMode: EnumViewMode = EnumViewMode.VIEW,
    val onDeleteClick: (ShoppingItem) -> Unit
): RecyclerView.Adapter<ShoppingListDetailsAdapter.ShoppingItemViewHolder>() {

    fun insertNewShoppingList(list: List<ShoppingItem>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun insertNewItem(item: ShoppingItem){
        // create new list
        val newList = mutableListOf<ShoppingItem>()
        newList.addAll(items)
        newList.add(item)

        val diffResult = DiffUtil.calculateDiff(MyDiffUtils(items, newList))
        diffResult.dispatchUpdatesTo(this)
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
            date.text = item.createdAt.getReadableDate(date.context)
        }
    }




    class MyDiffUtils(
        val oldList: List<ShoppingItem>,
        val newList: List<ShoppingItem>
    ): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

    }
}