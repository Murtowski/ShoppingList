package murt.shoppinglistapp.ui.shoppingListDetails

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_shopping.view.*
import murt.data.model.ShoppingItem
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.utils.getReadableDate
import murt.shoppinglistapp.ui.utils.inflate



class ShoppingListExampleAdapter(
        val saveItem: (ShoppingItem) -> Unit
): RecyclerView.Adapter<ShoppingListExampleAdapter.ShoppingItemExampleViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, ShoppingItemListsDiffUtils())

    fun updateList(newShoppingList: List<ShoppingItem>){
        mDiffer.submitList(newShoppingList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemExampleViewHolder {
        return ShoppingItemExampleViewHolder(parent.inflate(R.layout.item_shopping_example))
    }

    override fun onBindViewHolder(holder: ShoppingItemExampleViewHolder, position: Int) {
        holder.onBindEdit(mDiffer.currentList[position])
    }

    override fun getItemCount() = mDiffer.currentList.size

    inner class ShoppingItemExampleViewHolder(val view: View): RecyclerView.ViewHolder(view){

        private val date = view.item_shopping_date
        private val title = view.item_shopping_title

        fun onBindEdit(item: ShoppingItem){

            date.text = item.updatedAt.getReadableDate(date.context)
            title.text = item.title

            view.setOnClickListener {
                saveItem(mDiffer.currentList[adapterPosition])
            }
        }

    }

    class ShoppingItemListsDiffUtils: DiffUtil.ItemCallback<ShoppingItem>(){

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
}