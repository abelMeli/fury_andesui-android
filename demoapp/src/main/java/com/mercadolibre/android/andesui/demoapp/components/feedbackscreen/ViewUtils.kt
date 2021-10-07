package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.content.Context
import android.view.View
import android.view.ViewGroup

private const val EMPTY_VIEW_SIZE = 20

internal fun buildEmptyView(context: Context) = View(context).apply {
    layoutParams = ViewGroup.LayoutParams(EMPTY_VIEW_SIZE, EMPTY_VIEW_SIZE)
}
