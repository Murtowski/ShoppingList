package murt.shoppinglistapp.ui.shoppingListDetails

import android.content.Context
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.item_shopping.view.*
import murt.data.model.ShoppingItem
import murt.data.model.ShoppingList
import murt.shoppinglistapp.R
import murt.shoppinglistapp.ui.utils.*
import org.threeten.bp.LocalDateTime
import java.util.concurrent.TimeUnit


/**
 * Piotr Murtowski on 21.02.2018.
 */

/**
 * @param viewMode - decide whenever user may edit items
 * */
class ShoppingListDetailsAdapter(
    val context: Context,
    val items: MutableList<ShoppingItem> = mutableListOf(),
    val isListEditable: Boolean = false,
    val onDeleteClick: (ShoppingItem) -> Unit,
    val saveItem: (ShoppingItem) -> Unit
): RecyclerView.Adapter<ShoppingListDetailsAdapter.ShoppingItemViewHolder>() {

    var editedItemPosition: Int = -1
    val mTextWatcher = EditableItemTextWatcher()

    fun insertItem(shoppingItem: ShoppingItem){
        editedItemPosition = 0
        items.add(0,shoppingItem)
        notifyItemInserted(0)
    }

    fun updateList(newShoppingLists: List<ShoppingItem>){
        items.clear()
        items.addAll(newShoppingLists)
        notifyDataSetChanged()

    }

    fun savePreviousEdit(){
        if(editedItemPosition != -1){
            val savePosition = editedItemPosition
            val item = items[editedItemPosition]
            editedItemPosition = -1
            saveItem(item)
            notifyItemChanged(savePosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(parent.inflate(R.layout.item_shopping))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val isEditable = (isListEditable && position == editedItemPosition)
        holder.onBindEdit(items[position], isEditable, position)
    }

    inner class ShoppingItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private val date = view.item_shopping_date
        private val title = view.item_shopping_title
        private val inputWrapper = view.item_shopping_input_wrapper
        private val input = view.item_shopping_input

        fun onBindEdit(item: ShoppingItem, editMode: Boolean, position: Int){
            if(SystemTools.isDebugMode) {
                date.visible()
                date.text = item.updatedAt.getReadableDate(date.context)
            }else
                date.gone()

            if(editMode){
                // View in EDIT mode
                inputWrapper.visible()
                title.gone()
                input.setText(item.title)
                mTextWatcher.shoppingItem = item
                input.addTextChangedListener(mTextWatcher)
                input.requestFocus()
            }else{
                // View in VIEW mode
                title.visible()
                inputWrapper.gone()
                title.text = item.title
            }

            view.setOnClickListener {
                if(isListEditable){
                    savePreviousEdit()
                    // List is editable and no item is edited
                    editedItemPosition = adapterPosition
                    notifyItemChanged(editedItemPosition)
                }else{
                    Toast.makeText(view.context, "Cannot edit archived items", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    inner class EditableItemTextWatcher: TextWatcher{

        private val disposable = CompositeDisposable()

        init {
            Observable.create<String> { obsEmitter ->
                emitter = obsEmitter
            }
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeBy(onNext = {
                    shoppingItem.title = it
                    shoppingItem.updatedAt = LocalDateTime.now()
                }).addTo(disposable)
        }

        lateinit var emitter : ObservableEmitter<String>
        lateinit var shoppingItem: ShoppingItem

        override fun afterTextChanged(text: Editable?) {
            emitter.onNext(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        fun dispose(){
            disposable.dispose()
        }
    }

    class ShoppingItemListsDiffUtils: DiffUtil.ItemCallback<ShoppingItem>(){

        override fun areContentsTheSame(oldItem: ShoppingItem?, newItem: ShoppingItem?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areItemsTheSame(oldItem: ShoppingItem?, newItem: ShoppingItem?): Boolean {
            return oldItem?.title == newItem?.title
        }
    }
}