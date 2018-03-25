package murt.shoppinglistapp.ui

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import murt.shoppinglistapp.ui.shoppingListDetails.ShoppingListDetailsAdapter

/**
 * Piotr Murtowski on 24.03.2018.
 */
class RecyclerViewSwipeHelper(private val listener: RecyclerViewSwipeListener):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

    private fun getForeground(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as ViewHolderSwipe).foreground
    }

    override fun onMove(
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder?,
        target: RecyclerView.ViewHolder?
    ): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(viewHolder != null){
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(getForeground(viewHolder))
        }
    }

    override fun onChildDraw(
        c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        ItemTouchHelper.SimpleCallback.getDefaultUIUtil()
            .onDraw(c, recyclerView, getForeground(viewHolder), dX, dY,
                actionState, isCurrentlyActive);
    }

    override fun onChildDrawOver(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder,
                                 dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
            getForeground(viewHolder) , dX, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder
    ) {
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(getForeground(viewHolder))
    }



    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
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