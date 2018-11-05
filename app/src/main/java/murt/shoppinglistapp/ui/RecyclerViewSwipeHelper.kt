package murt.shoppinglistapp.ui

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View


/**
 * Piotr Murtowski on 24.03.2018.
 */
class RecyclerViewSwipeHelper(private val listener: RecyclerViewSwipeListener):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

        private fun getForeground(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): View {
        return (viewHolder as ViewHolderSwipe).foreground
    }

    override fun onMove(
            recyclerView: androidx.recyclerview.widget.RecyclerView,
            viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
            target: androidx.recyclerview.widget.RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, actionState: Int) {
        if(viewHolder != null){
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(getForeground(viewHolder))
        }
    }

    override fun onChildDraw(
            c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        ItemTouchHelper.SimpleCallback.getDefaultUIUtil()
            .onDraw(c, recyclerView, getForeground(viewHolder), dX, dY,
                actionState, isCurrentlyActive);
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
            getForeground(viewHolder) , dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(
            recyclerView: androidx.recyclerview.widget.RecyclerView,
            viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder
    ) {
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(getForeground(viewHolder))
    }



    override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
        // Row is swiped from recycler view
        // remove it from adapter
        listener.onSwiped((viewHolder as ViewHolderSwipe), direction, viewHolder.adapterPosition)
    }

    interface RecyclerViewSwipeListener {
        fun onSwiped(viewHolder: ViewHolderSwipe, direction: Int, position: Int)
    }

    interface ViewHolderSwipe{
        val foreground: View
    }
}