package com.mercadolibre.android.andesui.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.tooltip.extensions.getActionBarHeight
import com.mercadolibre.android.andesui.tooltip.extensions.getStatusBarHeight
import com.mercadolibre.android.andesui.tooltip.extensions.getViewPointOnScreen
import com.mercadolibre.android.andesui.tooltip.extensions.isActionBarVisible

internal fun View.openKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * Returns copied text or "null" if the clipboard is empty
 */
internal fun View.getTextInClipboard(): CharSequence? {
    val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val pasteData = manager.primaryClip
    val item = pasteData?.getItemAt(0)
    return item?.text
}

internal fun View.getTopSpace(): Int {
    val startY = getViewPointOnScreen().y
    val actionBarHeight = getActionBarHeight() + getStatusBarHeight(true)

    return if (isActionBarVisible()) {
        startY - actionBarHeight
    } else {
        startY
    }
}

internal fun View.getBottomSpace(): Int {
    val endY = getViewPointOnScreen().y + height
    return getVisibleDisplayRect().bottom - endY
}

internal fun View.getLeftSpace(): Int {
    return getViewPointOnScreen().x + width
}

internal fun View.getRightSpace(): Int {
    val startX = getViewPointOnScreen().x
    return context.displaySize().x - startX
}

internal fun View.getVisibleDisplayRect(): Rect {
    return Rect().apply { getWindowVisibleDisplayFrame(this) }
}

internal fun ConstraintLayout.setConstraints(actions: ConstraintSet.() -> Unit) {
    ConstraintSet().apply {
        clone(this@setConstraints)
        actions()
        applyTo(this@setConstraints)
    }
}

internal fun View.elevateWithShadow(shadow: Boolean = false) {
    this.elevation = resources.getDimension(R.dimen.andes_card_elevated_shadow)
    if (!shadow) {
        this.outlineProvider = null
    }
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.startToStartOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@startToStartOf,
        ConstraintSet.START,
        secondViewToConnectTo,
        ConstraintSet.START
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.startToEndOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@startToEndOf,
        ConstraintSet.START,
        secondViewToConnectTo,
        ConstraintSet.END
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.endToEndOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@endToEndOf,
        ConstraintSet.END,
        secondViewToConnectTo,
        ConstraintSet.END
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.endToStartOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@endToStartOf,
        ConstraintSet.END,
        secondViewToConnectTo,
        ConstraintSet.START
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.topToTopOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@topToTopOf,
        ConstraintSet.TOP,
        secondViewToConnectTo,
        ConstraintSet.TOP
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.topToBottomOf(@IdRes secondViewToConnectTo: Int): ConstraintSet.() -> Unit = {
    connect(
        this@topToBottomOf,
        ConstraintSet.TOP,
        secondViewToConnectTo,
        ConstraintSet.BOTTOM
    )
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.marginStartOf(value: Int): ConstraintSet.() -> Unit = {
    setMargin(this@marginStartOf, ConstraintSet.START, value)
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.marginEndOf(value: Int): ConstraintSet.() -> Unit = {
    setMargin(this@marginEndOf, ConstraintSet.END, value)
}

@SuppressLint("SupportAnnotationUsage")
@IdRes
internal infix fun Int.marginTopOf(value: Int): ConstraintSet.() -> Unit = {
    setMargin(this@marginTopOf, ConstraintSet.TOP, value)
}

/**
 * Returns a list with the children views contained in the viewGroup.
 * Returns an empty list if the viewgroup does not have children.
 */
internal fun ViewGroup.getAllChildren(): List<View> {
    val viewList = mutableListOf<View>()
    (0 until childCount).forEach { childIndex ->
        viewList.add(getChildAt(childIndex))
    }
    return viewList
}

internal fun <T> MutableList<T>.replaceWith(items: List<T>) {
    with(this) {
        clear()
        addAll(items)
    }
}

internal fun View.hideKeyboard() {
    (context.getSystemService(
            Activity.INPUT_METHOD_SERVICE
    ) as? InputMethodManager)?.hideSoftInputFromWindow(windowToken, 0)
}

internal fun View.xStart() = x

internal fun View.xMiddle() = x + width / 2f

internal fun View.xEnd() = x + width

internal fun View.yStart() = y

internal fun View.yMiddle() = y + height / 2f

internal fun View.yEnd() = y + height

/**
 * Returns all siblings from the caller view, or an empty list if the view is not attached
 * to a parent
 */
fun View.getAllSiblings(): List<View> {
    val viewParent = parent as? ViewGroup ?: return listOf()
    val allViews = viewParent.getAllChildren()
    return allViews.filter { it != this }
}

/**
 * Finds and returns the view by id.
 * The difference with the regular [View.findViewById] is that this method will scan the
 * whole screen from the top instead of the local layout.
 *
 * Returns null when [this] is created with an applicationContext instead of an activity
 *
 * Returns null if the passed [id] does not correspond to a view in the same screen,
 * or the referenced view is not attached yet to the screen
 */
internal fun View.getViewInScreenById(@IdRes id: Int): View? {
    val activity = context as? Activity ?: return null
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    val view = rootView.findViewById<View>(id) ?: return null
    return view.takeUnless { it == this }
}
