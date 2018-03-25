package murt.shoppinglistapp.ui.shoppingListsCurrent

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_shopping_list.view.*
import murt.cache.model.ShoppingListAndItems
import murt.data.model.ShoppingList
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.RecyclerViewSwipeHelper
import murt.shoppinglistapp.ui.utils.*

/**
 * Piotr Murtowski on 26.02.2018.
 */
class ListOfShoppingListsAdapter(
    val onItemCLick: (ShoppingList) -> Unit,
    val onArchiveButtonClick: (ShoppingList) -> Unit
): RecyclerView.Adapter<ListOfShoppingListsAdapter.ShoppingListViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, ShoppingListsDiffUtils())

    fun getShoppingList(position: Int): ShoppingList{
        return mDiffer.currentList[position]
    }

    fun updateList(newShoppingLists: List<ShoppingList>){
        mDiffer.submitList(newShoppingLists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        return ShoppingListViewHolder(parent.inflate(R.layout.item_shopping_list))
    }

    override fun getItemCount() = mDiffer.currentList.size

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = mDiffer.currentList[position]
        holder.onBind(item)
    }

    inner class ShoppingListViewHolder(val view: View): RecyclerView.ViewHolder(view),
        RecyclerViewSwipeHelper.ViewHolderSwipe{

        override val foreground = view.shopping_list_foreground
        private val date = view.tv_last_update_time
        private val archiveButton = view.iv_archive_button
        private val title = view.tv_shopping_title
        private val itemsList = view.tv_shopping_list

        fun onBind(item: ShoppingList) {
            if(item.isArchived){
                archiveButton.setImageResource(R.drawable.ic_unarchive_24dp)
            }
            // Only for debug to know when values are updated
            if(SystemTools.isDebugMode) {
                date.visible()
                date.text = item.updatedAt.getReadableDate(date.context)
            }else
                date.gone()

            title.text = item.title

            itemsList.text = StringBuilder().apply {
                val lastIndex = item.items.size - 1
                item.items.forEachIndexed { index, shoppingItem ->
                    append(shoppingItem.title)
                    if(index != lastIndex)
                        append(", ")
                }
            }.toString()

            archiveButton.setOnClickListener {
                onArchiveButtonClick(item)
            }

            view.setOnClickListener{
                onItemCLick(item)
            }

        }

    }

    class ShoppingListsDiffUtils: DiffUtil.ItemCallback<ShoppingList>(){

        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem.title == newItem.title
        }



    }
}