package murt.shoppinglistapp.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.core.content.res.ResourcesCompat
import android.text.InputFilter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
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

    fun createKeyboardActionListener(editorInfoImeAction: Int = EditorInfo.IME_ACTION_DONE,
                                                runnable: Runnable?): TextView.OnEditorActionListener {
        //
        return TextView.OnEditorActionListener { _, i, keyEvent ->
            if (keyEvent != null && keyEvent.action != KeyEvent.ACTION_DOWN) {
                return@OnEditorActionListener false
            } else if (/*keyEvent != null && */i == editorInfoImeAction) {
                runnable?.run()
                true
            }else
                false
        }
    }

    fun showSoftKeyboard(context: Context, editText: EditText?) {
        if (editText == null)
            return

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }

    fun hideSoftKeyboard(context: Context, et: EditText?) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et!!.windowToken, 0)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }

    fun setupHidingKeyboard(view: View, activity: Activity) {
        // Set up touch listener for non-description box views to hide keyboard.
        if (view !is EditText && view !is TextView && view !is ProgressBar) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(activity)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            (0 until view.childCount)
                .map { view.getChildAt(it) }
                .forEach { setupHidingKeyboard(it, activity) }
        }
    }



}

fun EditText.createKeyboardActionListener(editorInfoImeAction: Int = EditorInfo.IME_ACTION_DONE, runnable: Runnable? = null){
    this.setOnEditorActionListener(
        UITools.createKeyboardActionListener(editorInfoImeAction, runnable)
    )
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

fun TextView.getTextTrimmed() = this.text.toString().trim()

fun EditText.onlyUpperCase() {
    this.filters = this.filters.toMutableList().apply {
        add(InputFilter.AllCaps())
    }.toTypedArray()
}
