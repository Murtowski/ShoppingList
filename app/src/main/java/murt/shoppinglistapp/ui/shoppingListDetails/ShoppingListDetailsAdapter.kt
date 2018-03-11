package murt.shoppinglistapp.ui.shoppingListDetails

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.item_shopping.view.*
import murt.data.model.ShoppingItem
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
    val items: MutableList<ShoppingItem>,
    val isListEditable: Boolean = false,
    val onDeleteClick: (ShoppingItem) -> Unit
): RecyclerView.Adapter<ShoppingListDetailsAdapter.ShoppingItemViewHolder>() {

    var editedItemPosition: Int = -1
    val mTextWatcher = EditableItemTextWatcher()

    fun insertDeletedItem(item: ShoppingItem){
        // create new list
        val newList = mutableListOf<ShoppingItem>()
        newList.addAll(items)
        newList.add(item)

        val diffResult = DiffUtil.calculateDiff(MyDiffUtils(items, newList))
        items.add(item)
        diffResult.dispatchUpdatesTo(this)
    }

    fun insertNewItem(){
        items.add(ShoppingItem.empty())
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(parent.inflate(R.layout.item_shopping))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val isEditable = (isListEditable && position == editedItemPosition)
        holder.onBindEdit(items[position], isEditable)
    }

    inner class ShoppingItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private val date = view.item_shopping_date
        private val title = view.item_shopping_title
        private val inputWrapper = view.item_shopping_input_wrapper
        private val input = view.item_shopping_input

        fun onBindEdit(item: ShoppingItem, isEditable: Boolean){
            if(SystemTools.isDebugMode)
                date.text = item.updatedAt.getReadableDate(date.context)
            else
                date.gone()

            if(isEditable){
                // View in EDIT mode
                inputWrapper.visible()
                title.gone()
                input.setText(item.title)
                mTextWatcher.shoppingItem = item
                input.addTextChangedListener(mTextWatcher)

            }else{
                // View in VIEW mode
                title.visible()
                inputWrapper.gone()
                title.text = item.title
            }

            view.setOnClickListener {
                if(isListEditable){
                    // List is editable and no item is edited
                    editedItemPosition = adapterPosition
                    notifyItemChanged(editedItemPosition)
                }else{
                    Toast.makeText(view.context, "Cannot edit archived items", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    protected val disposable = CompositeDisposable()

    inner class EditableItemTextWatcher: TextWatcher{
        init {
            Observable.create<String> { obsEmitter ->
                emitter = obsEmitter
            }
                .debounce(1000, TimeUnit.MILLISECONDS)
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