package murt.shoppinglistapp.ui

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import murt.shoppinglistapp.R


/**
 * Piotr Murtowski on 21.03.2018.
 */
class BottomNavigationBehavior : androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<com.google.android.material.bottomnavigation.BottomNavigationView> {

    constructor(): super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: com.google.android.material.bottomnavigation.BottomNavigationView,
                                 dependency: View): Boolean {
        if (dependency is com.google.android.material.snackbar.Snackbar.SnackbarLayout) {
            updateSnackbar(child, dependency)
//            val fabButtonLayout = parent.findViewById<ConstraintLayout>(R.id.fab_button_container)
//            if(fabButtonLayout != null){
//                updateFabButton(fabButtonLayout, dependency)
//            }
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: View, snackbarLayout: com.google.android.material.snackbar.Snackbar.SnackbarLayout) {
        if (snackbarLayout.layoutParams is androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) {
            val params = snackbarLayout.layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams

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
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout,
            child: com.google.android.material.bottomnavigation.BottomNavigationView,
            directTargetChild: View,
            target: View,
            nestedScrollAxes: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout,
            child: com.google.android.material.bottomnavigation.BottomNavigationView,
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

    private fun hideBottomNavigationView(view: com.google.android.material.bottomnavigation.BottomNavigationView) {
        view.animate().translationY(view.height.toFloat())
    }

    private fun showBottomNavigationView(view: com.google.android.material.bottomnavigation.BottomNavigationView) {
        view.animate().translationY(0f)
    }
}