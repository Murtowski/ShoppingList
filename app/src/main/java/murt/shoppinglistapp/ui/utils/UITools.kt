package murt.shoppinglistapp.ui.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Piotr Murtowski on 21.02.2018.
 */
object UITools {

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
