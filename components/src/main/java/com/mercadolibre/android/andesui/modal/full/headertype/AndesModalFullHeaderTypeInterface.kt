package com.mercadolibre.android.andesui.modal.full.headertype

import android.view.View

internal interface AndesModalFullHeaderTypeInterface {
    fun titleVisibility(): Int
    fun closeActionVisibility(): Int
    fun getAlpha(): Float = 0.0f
    fun containerVisibility(): Int = View.VISIBLE
}

/**
 * Neither the title nor the close button are visible.
 */
internal object AndesModalHeaderNone : AndesModalFullHeaderTypeInterface {
    override fun titleVisibility(): Int = View.GONE
    override fun closeActionVisibility(): Int = View.GONE
    override fun containerVisibility() = View.GONE
}

/**
 * Both title and close button are visible.
 */
internal object AndesModalHeaderTitleClose : AndesModalFullHeaderTypeInterface {
    override fun titleVisibility(): Int = View.VISIBLE
    override fun closeActionVisibility(): Int = View.VISIBLE
    override fun getAlpha(): Float = 1f
}

/**
 * Only title is visible.
 */
internal object AndesModalHeaderOnlyTitle : AndesModalFullHeaderTypeInterface {
    override fun titleVisibility(): Int = View.VISIBLE
    override fun closeActionVisibility(): Int = View.GONE
    override fun getAlpha(): Float = 1f
}

/**
 * Only close button is visible.
 */
internal object AndesModalHeaderOnlyClose : AndesModalFullHeaderTypeInterface {
    override fun titleVisibility(): Int = View.GONE
    override fun closeActionVisibility(): Int = View.VISIBLE
}
