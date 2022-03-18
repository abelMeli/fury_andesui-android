package com.mercadolibre.android.andesui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.buttongroup.utils.*
import com.mercadolibre.android.andesui.tooltip.extensions.displaySize
import com.mercadolibre.android.andesui.tooltip.extensions.getActionBarHeight
import com.mercadolibre.android.andesui.tooltip.extensions.getStatusBarHeight
import com.mercadolibre.android.andesui.tooltip.extensions.getViewPointOnScreen
import com.mercadolibre.android.andesui.tooltip.extensions.isActionBarVisible

internal fun View.openKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
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