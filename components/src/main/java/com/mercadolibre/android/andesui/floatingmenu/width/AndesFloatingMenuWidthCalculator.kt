package com.mercadolibre.android.andesui.floatingmenu.width

import android.view.View
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientationInterface
import com.mercadolibre.android.andesui.floatingmenu.orientation.AndesFloatingMenuOrientationVector

/**
 * Calculates [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] width and orientation.
 * The priority is to show the content width in any position, required or not. In case, it is
 * not possible will adjust width to the max possible in any direction.
 */
internal fun calculateFloatingMenuWidthVector(
    requiredOrientation: AndesFloatingMenuOrientationInterface,
    parentView: View,
    desiredWidth: Int
): AndesFloatingMenuOrientationVector {
    val requiredMaxSpace = requiredOrientation.getMaxAvailableSpace(parentView)
    val oppositeOrientation = requiredOrientation.getOppositeOrientation()
    val oppositeMaxSpace = oppositeOrientation.getMaxAvailableSpace(parentView)
    return when {
        desiredWidth <= requiredMaxSpace -> AndesFloatingMenuOrientationVector(requiredOrientation, desiredWidth)
        desiredWidth <= oppositeMaxSpace -> AndesFloatingMenuOrientationVector(oppositeOrientation, desiredWidth)
        oppositeMaxSpace <= requiredMaxSpace -> AndesFloatingMenuOrientationVector(requiredOrientation, requiredMaxSpace)
        else -> AndesFloatingMenuOrientationVector(oppositeOrientation, oppositeMaxSpace)
    }
}
