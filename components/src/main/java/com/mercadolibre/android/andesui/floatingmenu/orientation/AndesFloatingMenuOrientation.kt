package com.mercadolibre.android.andesui.floatingmenu.orientation

/**
 * Utility class that defines possible horizontal [AndesFloatingMenuOrientation] types.
 * AndesFloatingMenuOrientation will configure [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu]
 * required direction.
 * As a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'Left', and then I'll give you a proper implementation of that orientation.
 */
sealed class AndesFloatingMenuOrientation {
    object Left : AndesFloatingMenuOrientation()
    object Right : AndesFloatingMenuOrientation()

    companion object {
        fun fromString(value: String): AndesFloatingMenuOrientation {
            return when (value) {
                Right.javaClass.simpleName -> Right
                else -> Left
            }
        }
    }

    internal val orientation get() = getAndesFloatingMenuOrientation()

    private fun getAndesFloatingMenuOrientation(): AndesFloatingMenuOrientationInterface {
        return when (this) {
            Left -> AndesLeftFloatingMenuOrientation()
            Right -> AndesRightFloatingMenuOrientation()
        }
    }
}

/**
 * Utility class that defines possible vertical [AndesFloatingMenuVerticalOrientation] types.
 * AndesFloatingMenuVerticalOrientation will configure
 * [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] required vertical direction.
 */
internal sealed class AndesFloatingMenuVerticalOrientation {
    object Bottom : AndesFloatingMenuVerticalOrientation()
    object Top : AndesFloatingMenuVerticalOrientation()

    internal val orientation get() = getAndesFloatingMenuOrientation()

    private fun getAndesFloatingMenuOrientation(): AndesFloatingMenuOrientationInterface {
        return when (this) {
            Bottom -> AndesBottomFloatingMenuOrientation()
            Top -> AndesTopFloatingMenuOrientation()
        }
    }
}
