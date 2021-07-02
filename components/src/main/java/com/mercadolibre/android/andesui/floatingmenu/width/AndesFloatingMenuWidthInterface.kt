package com.mercadolibre.android.andesui.floatingmenu.width

import android.view.View

/**
 * Defines width related properties that an
 * [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] needs to be drawn properly.
 */
internal interface AndesFloatingMenuWidthInterface {
    /**
     * Retrieves width that FloatingMenu should have.
     */
    fun getWidth(view: View): Int
}

internal class AndesFixedFloatingMenuWidth : AndesFloatingMenuWidthInterface {
    override fun getWidth(view: View) = view.measuredWidth
}

internal class AndesCustomFloatingMenuWidth(private val mWidth: Int) : AndesFloatingMenuWidthInterface {
    override fun getWidth(view: View) = mWidth
}
