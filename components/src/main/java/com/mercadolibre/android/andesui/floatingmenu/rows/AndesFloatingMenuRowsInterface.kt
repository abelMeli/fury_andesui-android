package com.mercadolibre.android.andesui.floatingmenu.rows

/**
 * Defines height related properties that an
 * [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] needs to be drawn properly.
 */
internal interface AndesFloatingMenuRowsInterface {
    /**
     * Number of rows that should be shown in the FloatingMenu
     */
    fun maxItemsShown(): Int?
}

internal class AndesSmallFloatingMenuRows : AndesFloatingMenuRowsInterface {
    override fun maxItemsShown(): Int = 3
}

internal class AndesMediumFloatingMenuRows : AndesFloatingMenuRowsInterface {
    override fun maxItemsShown(): Int = 5
}

internal class AndesMaxFloatingMenuRows : AndesFloatingMenuRowsInterface {
    override fun maxItemsShown(): Int? = null
}
