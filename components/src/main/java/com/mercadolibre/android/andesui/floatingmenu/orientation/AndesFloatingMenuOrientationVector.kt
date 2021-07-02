package com.mercadolibre.android.andesui.floatingmenu.orientation

/**
 * Class that represent a math vector. It has an orientation (TOP, BOTTOM, LEFT, RIGHT) and
 * a size in pixels.
 */
internal data class AndesFloatingMenuOrientationVector(
    val orientation: AndesFloatingMenuOrientationInterface,
    val size: Int
)
