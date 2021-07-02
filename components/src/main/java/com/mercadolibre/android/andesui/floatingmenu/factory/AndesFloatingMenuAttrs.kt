package com.mercadolibre.android.andesui.floatingmenu.factory

import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientation
import com.mercadolibre.android.andesui.floatingmenu.rows.AndesFloatingMenuRows
import com.mercadolibre.android.andesui.floatingmenu.width.AndesFloatingMenuWidth

internal data class AndesFloatingMenuAttrs(
    val rows: AndesFloatingMenuRows,
    val width: AndesFloatingMenuWidth,
    val orientation: AndesFloatingMenuOrientation
)
