package murt.shoppinglistapp.ui.example

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Piotr Murtowski on 12/12/2018.
 */
@BindingAdapter("binding_adapter")
fun RecyclerView.setData(data: List<Nothing>?){
    val mAdapter = adapter
    data?.let {
        if (mAdapter is ListAdapter<*,*>) {
            mAdapter.submitList(data)
        }
    }
}