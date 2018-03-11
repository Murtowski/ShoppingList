package murt.shoppinglistapp.ui.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Piotr Murtowski on 21.02.2018.
 */
object UITools {

    fun setDrawableOnTextView(context: Context, textView: TextView,
                              @DrawableRes left: Int, @DrawableRes top: Int,
                              @DrawableRes right: Int, @DrawableRes bottom: Int) {
        if (SystemTools.isAtLeastJellyBeanMR1)
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        else {
            val res = context.resources
            val leftDraw = getNullOrDrawable(res, left)
            val topDraw = getNullOrDrawable(res, top)
            val rightDraw = getNullOrDrawable(res, right)
            val bottomDraw = getNullOrDrawable(res, bottom)

            textView.setCompoundDrawablesWithIntrinsicBounds(leftDraw, topDraw, rightDraw, bottomDraw)
        }
    }

    fun getNullOrDrawable(res: Resources, @DrawableRes drawableRes: Int): Drawable? {
        return if (drawableRes == 0)
            null
        else
            getDrawable(res, drawableRes)
    }

    fun getDrawable(res: Resources, @DrawableRes drawableResId: Int): Drawable? {
        return ResourcesCompat.getDrawable(res, drawableResId, null)
    }

}

fun TextView.setDrawable(@DrawableRes left: Int, @DrawableRes top: Int,
                         @DrawableRes right: Int, @DrawableRes bottom: Int){
    UITools.setDrawableOnTextView(this.context, this, left, top, right, bottom)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun View.invisible(){
    this.visibility = View.INVISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}
fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View{
    return LayoutInflater.from(this.context!!).inflate(layoutRes, this, false)
}
