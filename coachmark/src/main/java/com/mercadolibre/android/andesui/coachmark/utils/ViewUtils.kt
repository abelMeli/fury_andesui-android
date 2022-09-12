package com.mercadolibre.android.andesui.coachmark.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup

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

/**
 * Special implementation of [View.getAllViewsInHierarchy] method in where is called
 * with the root view of the screen.
 */
internal fun Activity.getAllViewsInScreen(): List<View> {
    val rootView =
        window.decorView.findViewById<ViewGroup>(android.R.id.content)
    return rootView.getAllViewsInHierarchy()
}

/**
 * Returns all the views inside the caller view, including nested layouts and its children.
 */
internal fun View.getAllViewsInHierarchy(): List<View> {
    if (this !is ViewGroup || childCount == 0) return listOf(this)

    return getAllChildren()
        .flatMap { it.getAllViewsInHierarchy() }
        .plus(this as View)
}