package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
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
