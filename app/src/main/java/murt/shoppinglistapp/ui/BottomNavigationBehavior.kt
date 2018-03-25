package murt.shoppinglistapp.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import murt.shoppinglistapp.R


/**
 * Piotr Murtowski on 21.03.2018.
 */
class BottomNavigationBehavior : CoordinatorLayout.Behavior<BottomNavigationView> {

    constructor(): super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout, child: BottomNavigationView,
                                 dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            updateSnackbar(child, dependency)
//            val fabButtonLayout = parent.findViewById<ConstraintLayout>(R.id.fab_button_container)
//            if(fabButtonLayout != null){
//                updateFabButton(fabButtonLayout, dependency)
//            }
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: View, snackbarLayout: Snackbar.SnackbarLayout) {
        if (snackbarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams

            params.anchorId = child.id
            params.anchorGravity = Gravity.TOP
            params.gravity = Gravity.TOP
            snackbarLayout.layoutParams = params
        }
    }
//
//    private fun updateFabButton(fabBtnLayout: ConstraintLayout, snackbarLayout: Snackbar.SnackbarLayout){
//        val params = fabBtnLayout.layoutParams as CoordinatorLayout.LayoutParams
//
//        params.anchorId = snackbarLayout.id
//        params.anchorGravity = Gravity.TOP
//        fabBtnLayout.layoutParams = params
//    }



    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray
    ) {
        if (dy < 0) {
            showBottomNavigationView(child)
        } else if (dy > 0) {
            hideBottomNavigationView(child)
        }
    }

    private fun hideBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(view.height.toFloat())
    }

    private fun showBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(0f)
    }
}